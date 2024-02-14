package net.serg.client;

import net.serg.server.PingPongServer;
import net.serg.stubs.pingpong.PingPongServiceGrpc;
import net.serg.stubs.pingpong.PingRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.serg.stubs.pingpong.PongResponse;

import java.util.logging.Logger;

public class PingPongClient {

    private static final Logger logger = Logger.getLogger(PingPongClient.class.getName());
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                                                      .usePlaintext()
                                                      .build();

        PingPongServiceGrpc.PingPongServiceBlockingStub stub = PingPongServiceGrpc.newBlockingStub(channel);

        PingRequest request = PingRequest.newBuilder().setMessage("Ping").build();
        PongResponse response;

        response = stub.ping(request);

        logger.info(response.getMessage());
        channel.shutdown();
    }
}