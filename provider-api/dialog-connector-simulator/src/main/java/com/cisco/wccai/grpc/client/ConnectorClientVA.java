package com.cisco.wccai.grpc.client;

import com.cisco.wcc.ccai.v1.AnalyzeContentServiceGrpc;
import com.cisco.wcc.ccai.v1.CcaiApi;
import com.cisco.wccai.grpc.observer.ClientStreamResponseObserver;
import com.cisco.wccai.grpc.observer.StopObserver;
import io.grpc.ManagedChannel;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.CountDownLatch;

import static com.cisco.wccai.grpc.config.Config.NO_OF_DTMF_REQUEST_PER_CALL;
import static com.cisco.wccai.grpc.config.Config.NO_OF_VOICE_REQUEST_PER_CALL;

/**
 * ConnectorClientVA - Sample Client to test VA
 */
public class ConnectorClientVA {

    private static final Logger logger = LoggerFactory.getLogger(ConnectorClientVA.class);
    public static final String CONVERSATION_ID = " conversationId :";

    /**
     * The Channel.
     */
    ManagedChannel channel;

    /**
     * Sets channel.
     *
     * @param channel the channel
     */
    public void setChannel(ManagedChannel channel) {
        this.channel = channel;
    }

    /**
     * Execute single call.
     */
    public void executeSingleCall()  {
        try {
            String convId = RandomStringUtils.randomAlphanumeric(10);
            MDC.putCloseable("conversation_id",convId);


            AnalyzeContentServiceGrpc.AnalyzeContentServiceStub stub = AnalyzeContentServiceGrpc.newStub(channel);

            ConnectorClientVAImpl client = new ConnectorClientVAImpl(stub);

            // setting recognition config
            CcaiApi.RecognitionConfig recognitionConfig = CcaiApi.RecognitionConfig.newBuilder().
                    setLanguageCode("en-US").setEncoding(CcaiApi.AudioEncoding.MULAW)
                    .build();
            CountDownLatch countDownLatch = new CountDownLatch(1);

            //sending CALL_START event
            ClientStreamResponseObserver clientStreamResponseObserverCallStart =  client.executeCallStart(convId,recognitionConfig,countDownLatch);
            countDownLatch.await();
            if(clientStreamResponseObserverCallStart.isFailure()){
                logger.error("{}:" + CONVERSATION_ID + " {}",clientStreamResponseObserverCallStart.getErrorMessage(),convId);
                return;
            }

            // Sending subsequent audio requests with prompt duration of 10 seconds
            for(int i=0;i<NO_OF_VOICE_REQUEST_PER_CALL;i++){
                CountDownLatch countDownLatchVoice = new CountDownLatch(1);
                ClientStreamResponseObserver clientStreamResponseObserver = client.executeAudioStream(convId, recognitionConfig, countDownLatchVoice);
                countDownLatchVoice.await();
                if(clientStreamResponseObserver.isFailure()){
                    logger.error("{}:" + CONVERSATION_ID + " {}",clientStreamResponseObserverCallStart.getErrorMessage(),convId);
                    return;
                }
            }

            // Sending DTMF event with initial and term character
            for(int i=0;i<NO_OF_DTMF_REQUEST_PER_CALL;i++){
                CountDownLatch countDownLatchDtmf= new CountDownLatch(1);
                ClientStreamResponseObserver clientStreamResponseObserver = client.executeDTMFStream(convId, recognitionConfig, countDownLatchDtmf);
                countDownLatchDtmf.await();
                if (clientStreamResponseObserver.isFailure()) {
                    logger.error("{}:" + CONVERSATION_ID + " {}",clientStreamResponseObserverCallStart.getErrorMessage(),convId);
                    return;
                }
            }

//            //STOP REQUEST
            CountDownLatch countDownLatchStop = new CountDownLatch(1);
            StopObserver stopObserver =  client.stop(convId,recognitionConfig, countDownLatchStop);
            countDownLatchStop.await();
            if(stopObserver.isFailure()){
                logger.error(stopObserver.getErrorMessage(), "ConversationId= " + convId);
            }
        }catch (Exception ex){
            logger.error("exception is executeSingleCall : {}",ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        CCAIClient ccaiClient = new CCAIClient();
        ConnectorClientVA vaClientDriver = new ConnectorClientVA();
        vaClientDriver.setChannel(ccaiClient.getChannel());
        vaClientDriver.executeSingleCall();
        long endTime = System.currentTimeMillis();
        logger.info("Total time taken by VA call: {}" , (endTime-startTime));
    }
}

