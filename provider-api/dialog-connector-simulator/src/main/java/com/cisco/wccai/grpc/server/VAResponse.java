package com.cisco.wccai.grpc.server;

import com.cisco.wcc.ccai.v1.CcaiApi;
import com.cisco.wcc.ccai.v1.Virtualagent;
import com.cisco.wccai.grpc.model.State;
import com.cisco.wccai.grpc.utils.LoadProperties;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class VAResponse {

    private static final Logger LOGGER = LoggerFactory.getLogger(VAResponse.class);
    public static final String CALL_START = "CALL_START";
    public static final String CALL_END = "CALL_END";
    public static final String CUSTOM = "CUSTOM";
    private boolean isFirstTimeDTMF = Boolean.TRUE;
    private boolean isTermCharacter = Boolean.TRUE;

    private static final Properties properties = LoadProperties.loadProperties();
    private static final int PROMPT_DURATION_SEC = Integer.parseInt(properties.getProperty("PROMPT_DURATION_MS"));
    private final long startTime = System.currentTimeMillis();
    private boolean isStartOfInput = false;
    private boolean isEndOfInput = false;
    private boolean isDtmfReceived = false;


    VAResponse()
    {

    }

    public void buildVAResponse(CcaiApi.StreamingAnalyzeContentRequest request, StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> responseObserver) {

        Virtualagent.VirtualAgentResult result;

        LOGGER.info("request from client : {} ", request);
        if (request.hasEvent()) {
            switch (request.getEvent().getEventType().toString()) {
                case CALL_START:
                    LOGGER.info("received CALL_START event for conversationId : {} ", request.getConversationId());
                    break;
                case CALL_END:
                    LOGGER.info("received CALL_END event for conversationId : {} ", request.getConversationId());
                    responseObserver.onNext(Context.getResponse(State.CALL_END).getCallEndResponse());
                    break;
                case CUSTOM:
                    LOGGER.info("received CUSTOM event for conversationId : {} ", request.getConversationId());
                    break;
                default:
                    result = Virtualagent.VirtualAgentResult.newBuilder().setResponsePayload("UNSPECIFIED EVENT RECEIVED").build();
                    responseObserver.onNext(CcaiApi.StreamingAnalyzeContentResponse.newBuilder().setVaResult(result).build());
                    break;

            }
        } else if (request.hasDtmf()) {
            LOGGER.info("received dtmf event for conversationId : {} ", request.getConversationId());
            processDTMF(request, responseObserver);
        } else {

            LOGGER.info("received audio from client for conversationId : {} ", request.getConversationId());
            processAudio(responseObserver);
        }
    }

    public boolean isDtmfReceived() {
        return isDtmfReceived;
    }

    private  void processDTMF(CcaiApi.StreamingAnalyzeContentRequest request, StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> responseObserver) {

        Virtualagent.Dtmf dtmf = request.getDtmf().getDtmfEventsList().get(0);
        if (isFirstTimeDTMF) {
            isDtmfReceived = true;
            isFirstTimeDTMF = false;
            LOGGER.info("received first character for conversationId : {} , sending START_OF_INPUT event ", request.getConversationId());
            responseObserver.onNext(Context.getResponse(State.START_OF_INPUT).getStartOfInputResponse());
        } else if ((Virtualagent.Dtmf.DTMF_POUND == dtmf) && isTermCharacter) {
            LOGGER.info("received term character for conversationId : {} , sending END_OF_INPUT event", request.getConversationId());
            isTermCharacter = false;
            responseObserver.onNext(Context.getResponse(State.END_OF_INPUT).getEndOfInputResponse());
        }
    }

    // Refer readme for detail flow. (Virtual Agent mode section)
    private void processAudio(StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> responseObserver) {

        // upon receiving the first interim response, sending START_OF_INPUT event, will be used by client for barge-in.
        if(System.currentTimeMillis()>startTime + PROMPT_DURATION_SEC*0.2 && !isStartOfInput){
            isStartOfInput = true;
            responseObserver.onNext(Context.getResponse(State.START_OF_INPUT).getStartOfInputResponse());
        }
        // sending partial recognition responses
        if(System.currentTimeMillis()>startTime + PROMPT_DURATION_SEC*0.4 && System.currentTimeMillis() < startTime + PROMPT_DURATION_SEC*0.8){
            responseObserver.onNext(Context.getResponse(State.PARTIAL_RECOGNITION).getPartialRecognitionResponse());
        }

        // sending END_OF_INPUT when user takes pause ( Ex. END_OF_SINGLE_UTTERANCE for Google).
        if(System.currentTimeMillis()>startTime + PROMPT_DURATION_SEC*0.8 && !isEndOfInput){
            isEndOfInput = true;
            responseObserver.onNext(Context.getResponse(State.END_OF_INPUT).getEndOfInputResponse());
        }
    }

    public  boolean isEndOfInput() {
        return isEndOfInput;
    }

}

