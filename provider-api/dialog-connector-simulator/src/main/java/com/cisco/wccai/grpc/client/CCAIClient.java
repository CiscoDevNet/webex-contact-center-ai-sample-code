package com.cisco.wccai.grpc.client;


import com.cisco.wcc.ccai.v1.*;
import com.cisco.wccai.grpc.utils.LoadProperties;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * The type Ccai client.
 */
public class CCAIClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CCAIClient.class);
    /**
     * The constant API_URL.
     */
    public static final String API_URL = "API_URL";
    /**
     * The constant PORT.
     */
    public static final String PORT = "PORT";
    /**
     * The constant USE_TLS.
     */
    public static final String USE_TLS = "USE_TLS";
    /**
     * The Blocking stub.
     */
    protected final AnalyzeContentServiceGrpc.AnalyzeContentServiceBlockingStub blockingStub;
    /**
     * The Async stub.
     */
    protected final AnalyzeContentServiceGrpc.AnalyzeContentServiceStub asyncStub;
    /**
     * The Channel.
     */
    ManagedChannel channel = null;
    /**
     * The Property.
     */
    Properties property = LoadProperties.loadProperties();
    /**
     * The Api url.
     */
    String apiUrl = property.getProperty(API_URL);
    /**
     * The Port used.
     */
    int portUsed =  Integer.parseInt(property.getProperty(PORT));
    /**
     * The Use tls.
     */
    boolean useTLS = Boolean.parseBoolean(property.getProperty(USE_TLS));

    /**
     * Gets channel.
     *
     * @return the channel
     */
    public ManagedChannel getChannel() {
        return channel;
    }

    /**
     * Create a new channel.
     */
    public CCAIClient() {
        try {
            if (useTLS) {
                channel = NettyChannelBuilder.forAddress(apiUrl, portUsed)
                         .negotiationType(NegotiationType.TLS)
                        .idleTimeout(5, TimeUnit.SECONDS)
                        .build();
                LOGGER.debug("building TLS channel with API Endpoint : {} , port : {}" , apiUrl, portUsed);

            } else {
                channel = NettyChannelBuilder.forAddress(apiUrl, portUsed)
                        .negotiationType(NegotiationType.PLAINTEXT)
                        .idleTimeout(5, TimeUnit.SECONDS)
                        .build();
                LOGGER.debug("building NonTLS channel with API Endpoint : {} , port : {}" , apiUrl, portUsed);
            }
        } catch (Exception e) {
            LOGGER.info(e.toString());
            e.printStackTrace();
        }
        blockingStub = AnalyzeContentServiceGrpc.newBlockingStub(channel);
        asyncStub = AnalyzeContentServiceGrpc.newStub(channel);
    }

    /**
     * Shutdown - To shut down the channel explicitly from client.
     */
    public void shutDown() {
        try {

            if (channel != null) {
                channel.shutdown().awaitTermination(3, TimeUnit.SECONDS);
                channel.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException: ", e);
            Thread.currentThread().interrupt();

        }

    }

}
