package com.cisco.wccai.grpc.server;

import com.cisco.wccai.grpc.server.interceptors.ServiceExceptionHandler;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The type Grpc server.
 */
public class GrpcServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServer.class);
    private static final int PORT = 8086;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String [] args) throws IOException, InterruptedException {

       Server server = ServerBuilder.forPort(PORT)
                .addService(new GrpcServerImpl())
                .intercept(new ServiceExceptionHandler())
                .build()
                .start();

        LOGGER.info("server started at port : {}", PORT );

       LOGGER.info("Initializing the context");
        Context.init();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            LOGGER.info("Received Shutdown Request");
            server.shutdown();
            LOGGER.info("Successfully Stopped, Shutting down the server");
        }));


        // await for Termination of Program
        server.awaitTermination();
    }
}
