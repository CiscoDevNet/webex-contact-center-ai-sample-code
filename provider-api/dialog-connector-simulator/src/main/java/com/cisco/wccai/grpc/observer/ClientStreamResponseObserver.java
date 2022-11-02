package com.cisco.wccai.grpc.observer;

import com.cisco.wcc.ccai.v1.CcaiApi;
import com.cisco.wcc.ccai.v1.Recognize;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ClientStreamResponseObserver implements StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientStreamResponseObserver.class);
    private final CountDownLatch countDownLatch;
    private boolean isFailure = false;
    private String errorMessage;
    StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> streamingAnalyzeContentRequestStreamObserver;
    boolean stopStreaming = false;

    public ClientStreamResponseObserver(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    public boolean isStopStreaming() {
        return stopStreaming;
    }

    public void setStreamingAnalyzeContentRequestStreamObserver(StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> streamingAnalyzeContentRequestStreamObserver) {
        this.streamingAnalyzeContentRequestStreamObserver = streamingAnalyzeContentRequestStreamObserver;
    }

    @Override
    public void onNext(CcaiApi.StreamingAnalyzeContentResponse streamingAnalyzeContentResponse) {
        LOGGER.info("---->Received Response: {}", streamingAnalyzeContentResponse);
        // sending onCompleted from client upon receiving call End event.
       if(null!=streamingAnalyzeContentResponse && streamingAnalyzeContentResponse.hasRecognitionResult() && streamingAnalyzeContentResponse.getRecognitionResult().getResponseEvent() == Recognize.OutputEvent.EVENT_END_OF_INPUT){
           stopStreaming = true;
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();

        LOGGER.error("Error in streamingRecognize , throwable {} ", throwable.getMessage());
        isFailure = true;
        errorMessage = throwable.getMessage();
        countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        LOGGER.info("received onComplete from server");
        isFailure = false;
        countDownLatch.countDown();

    }

    public boolean isFailure() {
        return isFailure;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
