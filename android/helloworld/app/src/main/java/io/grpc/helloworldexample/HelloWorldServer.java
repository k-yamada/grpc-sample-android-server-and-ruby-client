package io.grpc.helloworldexample;

/**
 * Created by kyamada on 2016/09/13.
 */

import android.util.Log;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import static io.grpc.examples.helloworld.HelloReply.*;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class HelloWorldServer {
    private static final String TAG =  "HelloWorldServer";

    /* The port on which the server should run */
    private int port = 50051;
    private Server server;

    void start() throws IOException {
        server = ServerBuilder.forPort(port)
            .addService(new GreeterImpl())
            .build()
            .start();
        Log.i(TAG, "Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorldServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) {
        try {
            final HelloWorldServer server = new HelloWorldServer();
            server.start();
            server.blockUntilShutdown();
        } catch (Exception e) {
            Log.d(TAG, "error:" + e.getMessage());
        }
    }

    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = newBuilder().setMessage("Hello " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}