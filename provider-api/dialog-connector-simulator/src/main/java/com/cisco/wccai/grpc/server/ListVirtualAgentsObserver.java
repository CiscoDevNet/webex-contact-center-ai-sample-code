package com.cisco.wccai.grpc.server;

import com.cisco.wcc.ccai.v1.CcaiApi;
import com.cisco.wcc.ccai.v1.Virtualagent;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListVirtualAgentsObserver{

    private static final Logger LOGGER = LoggerFactory.getLogger(ListVirtualAgentsObserver.class);

    public void processAPICall(CcaiApi.ListVirtualAgentsRequest request, StreamObserver<CcaiApi.ListVirtualAgentsResponse> responseObserver){
        LOGGER.info("Request received for listing virtual agents");
        CcaiApi.ListVirtualAgentsResponse.Builder responseBuilder = CcaiApi.ListVirtualAgentsResponse.newBuilder();
        responseBuilder.addVirtualAgents(Virtualagent.VirtualAgent.newBuilder().setVirtualAgentId("1").setVirtualAgentName("VA1").build());
        responseBuilder.addVirtualAgents(Virtualagent.VirtualAgent.newBuilder().setVirtualAgentId("2").setVirtualAgentName("VA2").build());
        responseBuilder.addVirtualAgents(Virtualagent.VirtualAgent.newBuilder().setVirtualAgentId("3").setVirtualAgentName("VA3").build());
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
