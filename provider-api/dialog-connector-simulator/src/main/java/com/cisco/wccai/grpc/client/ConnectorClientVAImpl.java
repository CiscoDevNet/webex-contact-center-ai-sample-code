package com.cisco.wccai.grpc.client;

import com.cisco.wcc.ccai.v1.AnalyzeContentServiceGrpc;
import com.cisco.wcc.ccai.v1.CcaiApi;
import com.cisco.wcc.ccai.v1.Tts;
import com.cisco.wcc.ccai.v1.Virtualagent;
import com.cisco.wccai.grpc.config.Config;
import com.cisco.wccai.grpc.observer.ClientStreamResponseObserver;
import com.cisco.wccai.grpc.observer.StopObserver;
import com.cisco.wccai.grpc.utils.Utils;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * The type Connector client VA Impl.
 */
public class ConnectorClientVAImpl {

    private static final Logger logger = LoggerFactory.getLogger(ConnectorClientVAImpl.class);
    public static final String NATHAN = "nathan";
    AnalyzeContentServiceGrpc.AnalyzeContentServiceStub stub;

    /**
     * Instantiates a new Connector client VA.
     *
     * @param stub the stub
     */
    public ConnectorClientVAImpl(AnalyzeContentServiceGrpc.AnalyzeContentServiceStub stub) {
        this.stub = stub;
    }

    /**
     * Execute welcome client stream response observer.
     *
     * @param sessionId         the session id
     * @param recognitionConfig the recognition config
     * @param countDownLatch    the countdown latch
     * @return the client stream response observer
     */
    public ClientStreamResponseObserver executeCallStart(String sessionId, CcaiApi.RecognitionConfig recognitionConfig, final CountDownLatch countDownLatch) {
        ClientStreamResponseObserver clientStreamResponseObserver = new ClientStreamResponseObserver(countDownLatch);
        StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> streamingAnalyzeContentRequestStreamObserver = stub.streamingAnalyzeContent(clientStreamResponseObserver);
        Virtualagent.InputEvent inputEvent = Virtualagent.InputEvent.newBuilder().setEventType(Virtualagent.InputEvent.EventType.CALL_START).build();
        CcaiApi.StreamingAnalyzeContentRequest streamingAnalyzeContentRequest = CcaiApi.StreamingAnalyzeContentRequest.newBuilder()
                .setOutputAudioConfig(CcaiApi.OutputAudioConfig.newBuilder().setConfig(Tts.AudioConfig.newBuilder().setAudioEncoding(Tts.OutputAudioEncoding.OUTPUT_MULAW)
                        .setVoice(Tts.SpeakerParams.newBuilder().setName(NATHAN).build()).build())).setEvent(inputEvent).setInterimResults(true).setSingleUtterance(true).
                setConversationId(sessionId).setRequestType(CcaiApi.StreamingAnalyzeContentRequest.RequestType.VIRTUAL_AGENT).setCcaiConfigId(Config.CONFIG_ID).setOrgId(Config.ORG_ID).setInputAudioConfig(recognitionConfig).build();

        streamingAnalyzeContentRequestStreamObserver.onNext(streamingAnalyzeContentRequest);
        streamingAnalyzeContentRequestStreamObserver.onCompleted();
        return clientStreamResponseObserver;
    }

    /**
     * Execute stream client stream response observer.
     *
     * @param sessionId         the session id
     * @param recognitionConfig the recognition config
     * @param countDownLatch    the count down latch
     * @return the client stream response observer
     */
    public ClientStreamResponseObserver executeAudioStream(String sessionId, CcaiApi.RecognitionConfig recognitionConfig, final CountDownLatch countDownLatch) {

        ClientStreamResponseObserver clientStreamResponseObserver = new ClientStreamResponseObserver(countDownLatch);
        StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> streamingAnalyzeContentRequestStreamObserver = stub.streamingAnalyzeContent(clientStreamResponseObserver);
        clientStreamResponseObserver.setStreamingAnalyzeContentRequestStreamObserver(streamingAnalyzeContentRequestStreamObserver);
        streamingAnalyzeContentRequestStreamObserver.onNext(CcaiApi.StreamingAnalyzeContentRequest.newBuilder()
                .setOutputAudioConfig(CcaiApi.OutputAudioConfig.newBuilder().setConfig(Tts.AudioConfig.newBuilder()
                        .setAudioEncoding(Tts.OutputAudioEncoding.OUTPUT_MULAW).setVoice(Tts.SpeakerParams.newBuilder().setName(NATHAN).build()).build())).setConversationId(sessionId).
                setInputAudioConfig(recognitionConfig).setRequestType(CcaiApi.StreamingAnalyzeContentRequest.RequestType.VIRTUAL_AGENT).setOrgId(Config.ORG_ID).setCcaiConfigId(Config.CONFIG_ID).build());
        try {

            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() <= startTime + 10000 && !clientStreamResponseObserver.isStopStreaming()) {
                streamingAnalyzeContentRequestStreamObserver.onNext(CcaiApi.StreamingAnalyzeContentRequest.newBuilder().
                        setConversationId(sessionId).
                        setAudio(ByteString.copyFrom(Utils.getAudioBytes())).build());
                Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            }
            logger.info("done streaming the audio, client calling onCompleted");
            streamingAnalyzeContentRequestStreamObserver.onCompleted();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clientStreamResponseObserver;
    }

    /**
     * Execute dtmf stream client stream response observer.
     *
     * @param sessionId         the session id
     * @param recognitionConfig the recognition config
     * @param countDownLatch    the countdown latch
     * @return the client stream response observer
     */
    public ClientStreamResponseObserver executeDTMFStream(String sessionId, CcaiApi.RecognitionConfig recognitionConfig, final CountDownLatch countDownLatch) {
        ClientStreamResponseObserver clientStreamResponseObserver = new ClientStreamResponseObserver(countDownLatch);
        StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> streamingAnalyzeContentRequestStreamObserver = stub.streamingAnalyzeContent(clientStreamResponseObserver);
        clientStreamResponseObserver.setStreamingAnalyzeContentRequestStreamObserver(streamingAnalyzeContentRequestStreamObserver);
        streamingAnalyzeContentRequestStreamObserver.onNext(CcaiApi.StreamingAnalyzeContentRequest.newBuilder()
                .setOutputAudioConfig(CcaiApi.OutputAudioConfig.newBuilder().setConfig(Tts.AudioConfig.newBuilder()
                        .setAudioEncoding(Tts.OutputAudioEncoding.OUTPUT_MULAW).setVoice(Tts.SpeakerParams.newBuilder().setName(NATHAN).build()).build())).setConversationId(sessionId).
                setInputAudioConfig(recognitionConfig).setRequestType(CcaiApi.StreamingAnalyzeContentRequest.RequestType.VIRTUAL_AGENT).setOrgId(Config.ORG_ID).setCcaiConfigId(Config.CONFIG_ID).build());

        streamingAnalyzeContentRequestStreamObserver.onNext(
                CcaiApi.StreamingAnalyzeContentRequest.newBuilder().setConversationId(sessionId).setDtmf(
                        Virtualagent.DtmfEvents.newBuilder().addDtmfEvents(Virtualagent.Dtmf.DTMF_EIGHT).build()).build());

        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);

        logger.info("sending terminal character");
        streamingAnalyzeContentRequestStreamObserver.onNext(
                CcaiApi.StreamingAnalyzeContentRequest.newBuilder().setConversationId(sessionId).setDtmf(
                        Virtualagent.DtmfEvents.newBuilder().addDtmfEvents(Virtualagent.Dtmf.DTMF_POUND).build()).build());

        logger.info("done with DTMF, client calling onCompleted");
        streamingAnalyzeContentRequestStreamObserver.onCompleted();
        return clientStreamResponseObserver;
    }

    /**
     * Stop observer.
     *
     * @param sessionId         the session id
     * @param recognitionConfig the recognition config
     * @param countDownLatch    the count-down latch
     * @return the stop observer
     */
    public StopObserver stop(String sessionId, CcaiApi.RecognitionConfig recognitionConfig, final CountDownLatch countDownLatch) {
        logger.info("=====stop in StopObserver======");
        StopObserver stopObserver = new StopObserver(countDownLatch);
        StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> streamingAnalyzeContentRequestStreamObserver = stub.streamingAnalyzeContent(stopObserver);

        // sending call end event to server
        streamingAnalyzeContentRequestStreamObserver.onNext(CcaiApi.StreamingAnalyzeContentRequest.newBuilder()
                .setOutputAudioConfig(CcaiApi.OutputAudioConfig.newBuilder().setConfig(Tts.AudioConfig.newBuilder()
                        .setAudioEncoding(Tts.OutputAudioEncoding.OUTPUT_MULAW).setVoice(Tts.SpeakerParams.newBuilder().setName(NATHAN).build()).build())).setConversationId(sessionId).
                setInputAudioConfig(recognitionConfig).setRequestType(CcaiApi.StreamingAnalyzeContentRequest.RequestType.VIRTUAL_AGENT).setOrgId(Config.ORG_ID).setCcaiConfigId(Config.CONFIG_ID)
                .setEvent(Virtualagent.InputEvent.newBuilder()
                        .setEventType(Virtualagent.InputEvent.EventType.CALL_END).build()).build());

        streamingAnalyzeContentRequestStreamObserver.onCompleted();
        return stopObserver;
    }

}
