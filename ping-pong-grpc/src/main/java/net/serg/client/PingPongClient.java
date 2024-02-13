package net.serg.client;

import net.serg.stubs.pingpong.PingPongServiceGrpc;
import net.serg.stubs.pingpong.PingRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.serg.stubs.pingpong.PongResponse;

public class PingPongClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5000)
                                                      .usePlaintext()
                                                      .build();

        PingPongServiceGrpc.PingPongServiceBlockingStub stub = PingPongServiceGrpc.newBlockingStub(channel);

        PingRequest request = PingRequest.newBuilder().setMessage("Ping").build();
        PongResponse response;

        response = stub.ping(request);

        System.out.println(response.getMessage());
        channel.shutdown();
    }
}