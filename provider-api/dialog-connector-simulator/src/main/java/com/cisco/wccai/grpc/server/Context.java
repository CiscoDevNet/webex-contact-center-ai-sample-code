package com.cisco.wccai.grpc.server;


import com.cisco.wccai.grpc.model.Response;
import com.cisco.wccai.grpc.model.State;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;


public class Context {

    Context()
    {

    }
    private static final Map<State, Response> responseMap = new EnumMap<>(State.class);

    public static void init() throws IOException {

        Response callStartResponse             =  PrepareResponse.prepareCallStartResponse();
        Response startOfInputResponse          =  PrepareResponse.startOfInputResponse();
        Response partialRecognitionResponse    =  PrepareResponse.preparePartialRecognitionResponse();
        Response endOfInputResponse            =  PrepareResponse.prepareEndOfInputResponse();
        Response finalRecognitionResponse      =  PrepareResponse.prepareFinalRecognitionResponse();
        Response finalVAResponse              =  PrepareResponse.prepareFinalVAResponse();
        Response aaResponse                    =  PrepareResponse.prepareAAResponse();
        Response dtmfResponse                  =  PrepareResponse.prepareDTMFResponse();
        Response callEndResponse               =  PrepareResponse.prepareCallEndResponse();


        responseMap.put(State.CALL_START, callStartResponse);
        responseMap.put(State.START_OF_INPUT, startOfInputResponse);
        responseMap.put(State.PARTIAL_RECOGNITION, partialRecognitionResponse);
        responseMap.put(State.END_OF_INPUT, endOfInputResponse);
        responseMap.put(State.FINAL_RECOGNITION, finalRecognitionResponse);
        responseMap.put(State.VA, finalVAResponse);
        responseMap.put(State.DTMF, dtmfResponse);
        responseMap.put(State.CALL_END, callEndResponse);
        responseMap.put(State.AA, aaResponse);

    }

    public static Response getResponse(State state){
       return responseMap.get(state);
    }

}
