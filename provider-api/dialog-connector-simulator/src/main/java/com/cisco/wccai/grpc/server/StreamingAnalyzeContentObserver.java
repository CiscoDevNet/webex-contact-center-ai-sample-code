package com.cisco.wccai.grpc.server;

import com.cisco.wcc.ccai.v1.CcaiApi;
import com.cisco.wcc.ccai.v1.Virtualagent;
import com.cisco.wccai.grpc.model.State;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StreamingAnalyzeContentObserver implements StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> {
    private final StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> responseObserver;
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamingAnalyzeContentObserver.class);
    public static final String AGENT_ASSIST = "AGENT_ASSIST";
    public static final String VIRTUAL_AGENT = "VIRTUAL_AGENT";
    private String conversationId;
    private final Map<String, String> requestTypeMap = new HashMap<>();
    VAResponse vaResponse;
    private boolean isFirstTime = true;

    private CcaiApi.StreamingAnalyzeContentRequest streamingAnalyzeContentRequest;

    public StreamingAnalyzeContentObserver(StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(CcaiApi.StreamingAnalyzeContentRequest streamingAnalyzeContentRequest) {

        if (StringUtils.isNotBlank(streamingAnalyzeContentRequest.getConversationId()) && !requestTypeMap.containsKey(streamingAnalyzeContentRequest.getConversationId())) {
            String orgIdFromRequest = streamingAnalyzeContentRequest.getOrgId();
            LOGGER.info("orgId from request : {}", orgIdFromRequest);
            conversationId = streamingAnalyzeContentRequest.getConversationId();
            setStreamingAnalyseContentRequest(streamingAnalyzeContentRequest);
            LOGGER.info("RequestType received from client : {}, for conversationId : {}", streamingAnalyzeContentRequest.getRequestType(), streamingAnalyzeContentRequest.getConversationId());
            requestTypeMap.putIfAbsent(conversationId, streamingAnalyzeContentRequest.getRequestType().name());
        }

        if (requestTypeMap.get(conversationId).equalsIgnoreCase(AGENT_ASSIST)) {
            AAResponse.buildAAResponse(responseObserver);
        } else {
            if(isFirstTime){
                isFirstTime = false;
                vaResponse = new VAResponse();
            }
            vaResponse.buildVAResponse(streamingAnalyzeContentRequest,responseObserver);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.info("Error occurred for conversationId : {} , getMessage : {} ", conversationId, throwable.getMessage());
        LOGGER.info("Error occurred for conversationId : {} , throwable : {} ", conversationId, throwable);
        LOGGER.info("Error occurred for conversationId : {} , getCause : {} ", conversationId, throwable.getCause());

        responseObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        LOGGER.info("in onCompleted for conversationId : {} ", conversationId);
        if(requestTypeMap.get(conversationId).equalsIgnoreCase(VIRTUAL_AGENT)) {
            if (vaResponse.isEndOfInput()) {
                LOGGER.info("writing response from onCompleted to client for IS_END_OF_INPUT event, conversationId : {}", conversationId);
                responseObserver.onNext(Context.getResponse(State.VA).getFinalVAResponse());
            } else if ((streamingAnalyzeContentRequest.getEvent().getEventType() == Virtualagent.InputEvent.EventType.CALL_END)) {
                LOGGER.info("writing empty response from onCompleted to client for CALL_END event, conversationId : {}", conversationId);
                responseObserver.onNext(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().build());
            } else if((streamingAnalyzeContentRequest.getEvent().getEventType() == Virtualagent.InputEvent.EventType.CALL_START))
            {
                LOGGER.info("writing response from onCompleted to client for CALL_START event, conversationId : {}", conversationId);
                responseObserver.onNext(Context.getResponse(State.CALL_START).getCallStartResponse());
            }else if(vaResponse.isDtmfReceived()){
                LOGGER.info("writing response from onCompleted to client, conversationId : {}", conversationId);
                responseObserver.onNext(Context.getResponse(State.VA).getFinalVAResponse());
            }
        } else if (requestTypeMap.get(conversationId).equalsIgnoreCase(AGENT_ASSIST)) {
            LOGGER.info("received onCompleted from client, sending final response and AA result for conversationId : {}", conversationId);
            responseObserver.onNext(Context.getResponse(State.FINAL_RECOGNITION).getFinalRecognitionResponse());
            responseObserver.onNext(Context.getResponse(State.AA).getAaResponse());
        }
        responseObserver.onCompleted();
    }

    private void setStreamingAnalyseContentRequest(CcaiApi.StreamingAnalyzeContentRequest streamingAnalyzeContentRequest) {
        this.streamingAnalyzeContentRequest = streamingAnalyzeContentRequest;
    }

}
