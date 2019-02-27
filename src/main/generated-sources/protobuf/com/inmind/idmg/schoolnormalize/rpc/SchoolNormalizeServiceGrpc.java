package com.inmind.idmg.schoolnormalize.rpc;

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
    comments = "Source: school_normalize.proto")
public final class SchoolNormalizeServiceGrpc {

  private SchoolNormalizeServiceGrpc() {}

  public static final String SERVICE_NAME = "com.inmind.idmg.schoolnormalize.rpc.SchoolNormalizeService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest,
      com.inmind.idmg.schoolnormalize.rpc.NormalizeReply> METHOD_DO_NORMALIZE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.inmind.idmg.schoolnormalize.rpc.SchoolNormalizeService", "doNormalize"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.schoolnormalize.rpc.NormalizeReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SchoolNormalizeServiceStub newStub(io.grpc.Channel channel) {
    return new SchoolNormalizeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SchoolNormalizeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SchoolNormalizeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static SchoolNormalizeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SchoolNormalizeServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SchoolNormalizeServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void doNormalize(com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest request,
        io.grpc.stub.StreamObserver<com.inmind.idmg.schoolnormalize.rpc.NormalizeReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DO_NORMALIZE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_DO_NORMALIZE,
            asyncUnaryCall(
              new MethodHandlers<
                com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest,
                com.inmind.idmg.schoolnormalize.rpc.NormalizeReply>(
                  this, METHODID_DO_NORMALIZE)))
          .build();
    }
  }

  /**
   */
  public static final class SchoolNormalizeServiceStub extends io.grpc.stub.AbstractStub<SchoolNormalizeServiceStub> {
    private SchoolNormalizeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SchoolNormalizeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SchoolNormalizeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SchoolNormalizeServiceStub(channel, callOptions);
    }

    /**
     */
    public void doNormalize(com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest request,
        io.grpc.stub.StreamObserver<com.inmind.idmg.schoolnormalize.rpc.NormalizeReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DO_NORMALIZE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SchoolNormalizeServiceBlockingStub extends io.grpc.stub.AbstractStub<SchoolNormalizeServiceBlockingStub> {
    private SchoolNormalizeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SchoolNormalizeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SchoolNormalizeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SchoolNormalizeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.inmind.idmg.schoolnormalize.rpc.NormalizeReply doNormalize(com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DO_NORMALIZE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SchoolNormalizeServiceFutureStub extends io.grpc.stub.AbstractStub<SchoolNormalizeServiceFutureStub> {
    private SchoolNormalizeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SchoolNormalizeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SchoolNormalizeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SchoolNormalizeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.inmind.idmg.schoolnormalize.rpc.NormalizeReply> doNormalize(
        com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DO_NORMALIZE, getCallOptions()), request);
    }
  }

  private static final int METHODID_DO_NORMALIZE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SchoolNormalizeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SchoolNormalizeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DO_NORMALIZE:
          serviceImpl.doNormalize((com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest) request,
              (io.grpc.stub.StreamObserver<com.inmind.idmg.schoolnormalize.rpc.NormalizeReply>) responseObserver);
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

  private static final class SchoolNormalizeServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.inmind.idmg.schoolnormalize.rpc.SchoolNormalize.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SchoolNormalizeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SchoolNormalizeServiceDescriptorSupplier())
              .addMethod(METHOD_DO_NORMALIZE)
              .build();
        }
      }
    }
    return result;
  }
}
