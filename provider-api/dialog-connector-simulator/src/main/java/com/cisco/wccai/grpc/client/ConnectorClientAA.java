package com.cisco.wccai.grpc.client;

import com.cisco.wcc.ccai.v1.*;
import com.cisco.wccai.grpc.utils.LoadProperties;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ConnectorClientAA - Sample Client to test AA Flow
 */

public class ConnectorClientAA {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectorClientAA.class);
    private static final Properties PROPERTY = LoadProperties.loadProperties();
    private static final String ORG_ID = PROPERTY.getProperty("ORG_ID");
    private static final String AUDIO_ENCODING_TYPE = PROPERTY.getProperty("AUDIO_ENCODING_TYPE");
    private static final int BUFFER_SIZE = Integer.parseInt(PROPERTY.getProperty("BUFFER_SIZE"));
    public static final String LANGUAGE_CODE = "LANGUAGE_CODE";
    public static final String SAMPLE_RATE_HERTZ = "SAMPLE_RATE_HERTZ";

    // CountDownLatch to check that the server has completed on its side

    /**
     * The Finish latch.
     */
    final CountDownLatch finishLatch = new CountDownLatch(1);

    /**
     * The Ccai client.
     */
    CCAIClient ccaiClient = new CCAIClient();

    /**
     * Recognize.
     *
     */
    public void recognize() throws InterruptedException {

        byte[] buffer = new byte[BUFFER_SIZE];

        // Building recognition settings
        CcaiApi.RecognitionConfig recognitionConfig = CcaiApi.RecognitionConfig.newBuilder()
                .setEncoding(CcaiApi.AudioEncoding.valueOf(AUDIO_ENCODING_TYPE))
                .setLanguageCode(PROPERTY.getProperty(LANGUAGE_CODE))
                .setSampleRateHertz(Integer.parseInt(PROPERTY.getProperty(SAMPLE_RATE_HERTZ)))
                .build();

        // Response Stream observer to listen to responses from server
        StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> responseStreamObserver =
                new StreamObserver<>() {
                    @Override
                    public void onNext(CcaiApi.StreamingAnalyzeContentResponse streamingAnalyzeContentResponse) {

                        try {
                            if (streamingAnalyzeContentResponse.hasRecognitionResult())
                                LOGGER.info(" Recognition result : {}", streamingAnalyzeContentResponse.getRecognitionResult());
                            else if (streamingAnalyzeContentResponse.hasAgentAnswerResult()) {
                                LOGGER.info(" AA result : {}", streamingAnalyzeContentResponse.getAgentAnswerResult());
                            } else if (streamingAnalyzeContentResponse.hasVaResult()) {
                                LOGGER.info(" VA result : {}", streamingAnalyzeContentResponse.getVaResult());
                            } else
                                LOGGER.info(" message result : {}", streamingAnalyzeContentResponse.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            LOGGER.error("Response received for streamingRecognize{} }", streamingAnalyzeContentResponse);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();

                        LOGGER.error("Error in streamingRecognize , throwable {} ", throwable.toString());
                        LOGGER.error("Error in streamingRecognize , getMessage {} ", throwable.getMessage());
                        LOGGER.error("Error in streamingRecognize , getCause {} ", throwable.getCause().getMessage());
                        LOGGER.warn("Transcription failed: {} ", throwable.getMessage());
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        LOGGER.info("onCompleted event received from server");
                        finishLatch.countDown();
                    }
                };


        // Request Observer, writing to channel from client
        StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> requestObserver = ccaiClient.asyncStub.streamingAnalyzeContent(responseStreamObserver);

        // The first request must **only** contain the audio configuration
        requestObserver.onNext(CcaiApi.StreamingAnalyzeContentRequest.newBuilder()
                .setInputAudioConfig(recognitionConfig)
                .setConversationId(RandomStringUtils.randomAlphanumeric(10))
                .setAudio(ByteString.copyFrom(new byte[]{1, 2, 3}, 0, 3))
                .setRole(CcaiApi.Role.HUMAN_AGENT)
                .setCcaiConfigId("AXRydXoBECsy_j49LSAb")
                .setOrgId(ORG_ID)
                .setInterimResults(true)
                .setRequestType(CcaiApi.StreamingAnalyzeContentRequest.RequestType.AGENT_ASSIST)
                .build());


        // Subsequent requests must **only** contain the audio data.
        // Streaming data with 8KBPS speed for 58 secs

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + 20000) {
            requestObserver.onNext(
                    CcaiApi.StreamingAnalyzeContentRequest.newBuilder()
                            .setAudio(ByteString.copyFrom(buffer))
                            .build());
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        }

        LOGGER.info("calling onCompleted from client");
        requestObserver.onCompleted();
        finishLatch.await();
        LOGGER.info("finishing the call");
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws InterruptedException {

        ConnectorClientAA connectorClientAA = new ConnectorClientAA();
        long startTime = System.currentTimeMillis();
        connectorClientAA.recognize();
        long endTime = System.currentTimeMillis();
        LOGGER.info("Total time taken by AA call : {}", (endTime - startTime));
    }
}
