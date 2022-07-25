import {GRPC_MESSAGE_EVENT, MAXIMUM_RECONNECTS, MAXIMUM_RECONNECT_WAIT_TIME, GRPC_CONNECTION_STATUS_EVENT,
    GRPC_CONNECTION_STATUS_RECONNECTING, GRPC_CONNECTION_STATUS_ERROR, 
    GRPC_CONNECTION_STATUS_RECONNECTION_SUCCESS, 
    TRANSCRIPT_GRPC_MESSAGE_EVENT, ANSWERS_GRPC_MESSAGE_EVENT, WAIT_TIME_TO_RESET_PAGE_RELOADING, ServiceType} from '../constants';
import { GADGET_TYPE_TO_SERVICES_MAP } from '../components/finesseGadget/constants.js';
/* eslint-disable */
import {AiInsightClient} from '../proto/serving_grpc_web_pb.js';
import {InsightServingRequest, StreamingInsightServingRequest, StreamingInsightServingResponse} from '../proto/serving_pb.js';
import { getElementToDispatchEvents, messageLoggingEnabled, commonServiceInitializer } from '../utils.js';

import { errorMessageGenerator } from './constants';
import {clientLogger} from '../logger/loggerUtils';

/**
 * Initialize gRPC connection
 * @param {String} activeCallId
 * @param {String} authToken
 * @param {String} u2cHost
 * @param {String} orgId
 * @param {String} gadgetType
 * @param {Array<String>} servicesList List of services enabled for an agent
 */
let stream;
let reconnectionCount = 0;
let reconnectionTimeoutReference;
let isPageReloading = false;

export const openConnection = (callGuid, authToken, host, orgId, gadgetType, servicesList) => {
    if (commonServiceInitializer.isInitializer(gadgetType)) {
        clientLogger.log(
            clientLogger.level.INFO,
            clientLogger.context.GRPC_CONNECTION,
            `Gadgets: openConnection called for ${callGuid}.`,
            clientLogger.direction.SENT
        );
        // Reset reconnection count. So that we retry N-times, each time openConnection is called
        reconnectionCount = 0;

        let url = '';
        // This is done for FTstage to reach mock server
        if (host) {
            url = host === "0.0.0.0" ? 'http://0.0.0.0:8082' : (host.indexOf('http') === 0 ? host: 'https://' + host);
        }
        const bearerAuthToken = "Bearer " + authToken;
        createChannel(url, bearerAuthToken, callGuid, orgId, gadgetType, servicesList);
    }
};

/**
 * gRPC Channel creation using stub methods
 * @param {String} url
 * @param {String} bearerAuthToken
 * @param {String} activeCallId
 * @param {String} orgId
 * @param {String} gadgetType
 * @param {Array<String>} servicesList List of services enabled for an agent
 */
function createChannel(url, bearerAuthToken, activeCallId, orgId, gadgetType, servicesList) {

        if (stream) {
            stream.cancel();
        }

        const client = new AiInsightClient(url, null,null);
        const insightServingRequest = new InsightServingRequest();

        insightServingRequest.setConversationid(activeCallId);

        // If agentAnswers is enabled for agent, add answers params
        if (servicesList.indexOf(GADGET_TYPE_TO_SERVICES_MAP.answers) !== -1) {
            insightServingRequest.setRealtimeagentassist(true);
            insightServingRequest.setHistoricalagentassist(true);
        }

        // If Transcript is enabled for agent, add Transcript params
        if (servicesList.indexOf(GADGET_TYPE_TO_SERVICES_MAP.transcript) !== -1) {
            insightServingRequest.setRealtimetranscripts(true);
            insightServingRequest.setHistoricaltranscripts(true);
            insightServingRequest.setHistoricalvirtualagent(true);
            insightServingRequest.setHistoricalmessage(true);
            insightServingRequest.setRealtimemessage(true);
        }
        insightServingRequest.setOrgid(orgId);
        
        const streamInsightServingReq = new StreamingInsightServingRequest();
        streamInsightServingReq.setInsightservingrequest(insightServingRequest);

        // access_token passed as metaData key-value pair
        stream = client.streamingInsightServing(streamInsightServingReq, {"authorization": bearerAuthToken});
        stream.on('data', (response) => {
            try {
                if (reconnectionCount > 0) {
                    reconnectionCount = 0; 
                    publishConnectionStatus(gadgetType, GRPC_CONNECTION_STATUS_RECONNECTION_SUCCESS);
                }
                if (response.hasInsightservingresponse()) {
                    // For latency calculations after gRPC initializer receives response
                    messageLoggingEnabled && console.log('Gadgets: Response received at: ', new Date());
                    const streamInsightServingResponse = new StreamingInsightServingResponse();
                    streamInsightServingResponse.setInsightservingresponse(response.getInsightservingresponse());
                    const responseJSON = streamInsightServingResponse.toObject(true, streamInsightServingResponse);
                    publishMessageEvent(gadgetType, responseJSON);
                }
            } catch(error) {
                clientLogger.log(
                    clientLogger.level.ERROR,
                    clientLogger.context.GRPC_CONNECTION,
                    `createChannel: Failed to handle the GRPC response` + error,
                    clientLogger.direction.RECEIVED
                );
            }
        });
        stream.on('error', (err) => {
            clientLogger.log(
                clientLogger.level.ERROR,
                clientLogger.context.GRPC_CONNECTION,
                `createChannel: Error occurred. Unexpected stream error: code = ${err.code} message = "${err.message}"`,
                clientLogger.direction.RECEIVED
            );

            const errorKey = errorMessageGenerator(err.code);
            if (reconnectionCount <= MAXIMUM_RECONNECTS) {
                clientLogger.log(
                    clientLogger.level.INFO,
                    clientLogger.context.GRPC_CONNECTION,
                    `createChannel: Attempting reconnection, ${reconnectionCount}`
                );
                reconnectionCount = reconnectionCount + 1;
                /** Fix for CJBUAI-243. If page is reloading, do not show red banner on UI.
                 * But Reconnect is attempted to handle any edge cases. Eg: User clicks on refresh, But an
                 * actual error comes by the time user leaves the page.
                */
                if (!isPageReloading) {
                    publishConnectionStatus(gadgetType, GRPC_CONNECTION_STATUS_RECONNECTING);
                }

                triggerAutoReconnect(reconnectionCount, url, bearerAuthToken, activeCallId, orgId, gadgetType, servicesList);
            } else {
                publishConnectionStatus(gadgetType, GRPC_CONNECTION_STATUS_ERROR, {errorKey})
            }
        });
}

export const triggerAutoReconnect = (reconnectCount, url, bearerAuthToken, activeCallId, orgId, gadgetType, servicesList) => {
    let timeout = ((reconnectCount * 2) - 1);
    timeout = timeout < MAXIMUM_RECONNECT_WAIT_TIME ? timeout : MAXIMUM_RECONNECT_WAIT_TIME;
    reconnectionTimeoutReference = setTimeout(createChannel.bind(this, url, bearerAuthToken, activeCallId, orgId, gadgetType, servicesList), (timeout * 1000));
}

/**
 * 
 * @param {String} gadgetType
 * @param {String} invokerName // info about who is calling this method
 * @param {Array<String>} serviceList
 */
export const closeConnection = (gadgetType, invokerName, activeCallId) => {
    if (commonServiceInitializer.isInitializer(gadgetType)) {
        clearTimeout(reconnectionTimeoutReference);
        if(stream) {
            stream.cancel();
            clientLogger.log(
                clientLogger.level.DEBUG,
                clientLogger.context.GRPC_CONNECTION,
                `closeConnection: gRPC connection closed with details: ${invokerName} for ${activeCallId}`,
                clientLogger.direction.SENT
            );
        } else {
            clientLogger.log(
                clientLogger.level.DEBUG,
                clientLogger.context.GRPC_CONNECTION,
                `closeConnection: No active connection to close for ${activeCallId}`,
                clientLogger.direction.SENT
            );
        }
    }
};

export const publishMessageEvent = (gadgetType, data) => {
    const element = getElementToDispatchEvents(gadgetType);
    let topicName = '';
    const insightType = data.insightservingresponse.insighttype;

    if (insightType === ServiceType.ANSWERS_INSIGHT_TYPE) {
        topicName = ANSWERS_GRPC_MESSAGE_EVENT;
        messageLoggingEnabled && console.log('Gadgets: Answer received: ', new Date(), data);
    } else if (insightType === ServiceType.TRANSCRIPT_INSIGHT_TYPE) {
        topicName = TRANSCRIPT_GRPC_MESSAGE_EVENT;
        messageLoggingEnabled && console.log('Gadgets: Transcript received: ', new Date(), data);
    }

    const event = new CustomEvent(GRPC_MESSAGE_EVENT, {
        detail: {
            data: data,
            gadgetType: topicName 
        },
        bubbles: true
    });
    element.dispatchEvent(event);
};

export const publishConnectionStatus = (gadgetType, status, data) => {
    const element = getElementToDispatchEvents(gadgetType);
    const event = new CustomEvent(GRPC_CONNECTION_STATUS_EVENT, {
        detail: {
            status: status,
            data: data
        },
        bubbles: true
    });
    element.dispatchEvent(event);
}

export const handleGRPCConnectionOnReload = () => {
    isPageReloading = true;
    setTimeout(() => {
        isPageReloading = false
    }, WAIT_TIME_TO_RESET_PAGE_RELOADING);
}

window.addEventListener('beforeunload', handleGRPCConnectionOnReload);
