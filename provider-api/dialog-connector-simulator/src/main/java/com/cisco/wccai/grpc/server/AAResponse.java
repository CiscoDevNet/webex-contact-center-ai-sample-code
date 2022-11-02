package com.cisco.wccai.grpc.server;

import com.cisco.wcc.ccai.v1.CcaiApi;
import com.cisco.wccai.grpc.model.State;
import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class AAResponse {

    AAResponse() {

    }

    public static void buildAAResponse(StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> responseObserver) {
       // sending partial response from server for every onNext()
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        responseObserver.onNext(Context.getResponse(State.PARTIAL_RECOGNITION).getPartialRecognitionResponse());
    }
}

