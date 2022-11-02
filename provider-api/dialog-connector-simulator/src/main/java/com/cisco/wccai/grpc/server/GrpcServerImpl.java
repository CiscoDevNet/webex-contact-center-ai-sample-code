package com.cisco.wccai.grpc.server;

import com.cisco.wcc.ccai.v1.AnalyzeContentServiceGrpc;
import com.cisco.wcc.ccai.v1.CcaiApi;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The type Grpc server.
 */
public class GrpcServerImpl extends AnalyzeContentServiceGrpc.AnalyzeContentServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServerImpl.class);

    @Override
    public StreamObserver<CcaiApi.StreamingAnalyzeContentRequest> streamingAnalyzeContent(StreamObserver<CcaiApi.StreamingAnalyzeContentResponse> responseObserver) {
        LOGGER.info("RPC request received");
        return new StreamingAnalyzeContentObserver(responseObserver);
    }


}











