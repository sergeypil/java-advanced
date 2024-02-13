package net.serg.service;

import io.grpc.stub.StreamObserver;
import net.serg.stubs.pingpong.PingPongServiceGrpc;
import net.serg.stubs.pingpong.PingRequest;
import net.serg.stubs.pingpong.PongResponse;

public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {
    @Override
    public void ping(PingRequest request, StreamObserver<PongResponse> responseObserver) {
        PongResponse reply = PongResponse.newBuilder().setMessage("Pong").build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
