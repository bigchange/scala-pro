package com.inmind.idmg.major.rpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.3.0)",
    comments = "Source: major.proto")
public final class ResumeMajorServiceGrpc {

  private ResumeMajorServiceGrpc() {}

  public static final String SERVICE_NAME = "com.inmind.idmg.major.rpc.ResumeMajorService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.inmind.idmg.major.rpc.MajorRequest,
      com.inmind.idmg.major.rpc.MajorReply> METHOD_DO_MAJOR =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.inmind.idmg.major.rpc.ResumeMajorService", "doMajor"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.major.rpc.MajorRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.major.rpc.MajorReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ResumeMajorServiceStub newStub(io.grpc.Channel channel) {
    return new ResumeMajorServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ResumeMajorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ResumeMajorServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static ResumeMajorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ResumeMajorServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ResumeMajorServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void doMajor(com.inmind.idmg.major.rpc.MajorRequest request,
        io.grpc.stub.StreamObserver<com.inmind.idmg.major.rpc.MajorReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DO_MAJOR, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_DO_MAJOR,
            asyncUnaryCall(
              new MethodHandlers<
                com.inmind.idmg.major.rpc.MajorRequest,
                com.inmind.idmg.major.rpc.MajorReply>(
                  this, METHODID_DO_MAJOR)))
          .build();
    }
  }

  /**
   */
  public static final class ResumeMajorServiceStub extends io.grpc.stub.AbstractStub<ResumeMajorServiceStub> {
    private ResumeMajorServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ResumeMajorServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResumeMajorServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ResumeMajorServiceStub(channel, callOptions);
    }

    /**
     */
    public void doMajor(com.inmind.idmg.major.rpc.MajorRequest request,
        io.grpc.stub.StreamObserver<com.inmind.idmg.major.rpc.MajorReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DO_MAJOR, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ResumeMajorServiceBlockingStub extends io.grpc.stub.AbstractStub<ResumeMajorServiceBlockingStub> {
    private ResumeMajorServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ResumeMajorServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResumeMajorServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ResumeMajorServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.inmind.idmg.major.rpc.MajorReply doMajor(com.inmind.idmg.major.rpc.MajorRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DO_MAJOR, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ResumeMajorServiceFutureStub extends io.grpc.stub.AbstractStub<ResumeMajorServiceFutureStub> {
    private ResumeMajorServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ResumeMajorServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ResumeMajorServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ResumeMajorServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.inmind.idmg.major.rpc.MajorReply> doMajor(
        com.inmind.idmg.major.rpc.MajorRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DO_MAJOR, getCallOptions()), request);
    }
  }

  private static final int METHODID_DO_MAJOR = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ResumeMajorServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ResumeMajorServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DO_MAJOR:
          serviceImpl.doMajor((com.inmind.idmg.major.rpc.MajorRequest) request,
              (io.grpc.stub.StreamObserver<com.inmind.idmg.major.rpc.MajorReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class ResumeMajorServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.inmind.idmg.major.rpc.Major.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ResumeMajorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ResumeMajorServiceDescriptorSupplier())
              .addMethod(METHOD_DO_MAJOR)
              .build();
        }
      }
    }
    return result;
  }
}
