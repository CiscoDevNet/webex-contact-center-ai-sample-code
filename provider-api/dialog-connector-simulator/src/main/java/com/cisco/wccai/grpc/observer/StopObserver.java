package com.cisco.wccai.grpc.observer;

import com.cisco.wcc.ccai.v1.CcaiApi;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class StopObserver implements StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StopObserver.class);
    final CountDownLatch countDownLatch;
    private boolean isFailure = false;
    private String errorMessage;

    public StopObserver(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(CcaiApi.StreamingAnalyzeContentResponse streamingAnalyzeContentResponse) {
        LOGGER.info("---->Received Response: {}", streamingAnalyzeContentResponse);
    }

    @Override
    public void onError(Throwable throwable) {
        isFailure = true;
        errorMessage = throwable.getMessage();
        throwable.printStackTrace();
        countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        isFailure = false;
        countDownLatch.countDown();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isFailure() {
        return isFailure;
    }
}
