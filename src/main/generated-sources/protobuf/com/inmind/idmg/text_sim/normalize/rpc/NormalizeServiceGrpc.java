package com.inmind.idmg.text_sim.normalize.rpc;

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
    comments = "Source: normalize_service.proto")
public final class NormalizeServiceGrpc {

  private NormalizeServiceGrpc() {}

  public static final String SERVICE_NAME = "com.inmind.idmg.text_sim.normalize.rpc.NormalizeService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest,
      com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply> METHOD_NORMALIZE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.inmind.idmg.text_sim.normalize.rpc.NormalizeService", "Normalize"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest,
      com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply> METHOD_BATCH_NORMALIZE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "com.inmind.idmg.text_sim.normalize.rpc.NormalizeService", "BatchNormalize"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest,
      com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply> METHOD_IDENTIFY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.inmind.idmg.text_sim.normalize.rpc.NormalizeService", "Identify"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest,
      com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply> METHOD_BATCH_IDENTIFY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "com.inmind.idmg.text_sim.normalize.rpc.NormalizeService", "BatchIdentify"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NormalizeServiceStub newStub(io.grpc.Channel channel) {
    return new NormalizeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NormalizeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new NormalizeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static NormalizeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new NormalizeServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class NormalizeServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Normalize a single query.
     * </pre>
     */
    public void normalize(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest request,
        io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_NORMALIZE, responseObserver);
    }

    /**
     * <pre>
     * TODO(Alan): Normalize a query batch.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest> batchNormalize(
        io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_BATCH_NORMALIZE, responseObserver);
    }

    /**
     * <pre>
     * Identify a query.
     * </pre>
     */
    public void identify(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest request,
        io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_IDENTIFY, responseObserver);
    }

    /**
     * <pre>
     * Identify a query batch.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest> batchIdentify(
        io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_BATCH_IDENTIFY, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_NORMALIZE,
            asyncUnaryCall(
              new MethodHandlers<
                com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest,
                com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply>(
                  this, METHODID_NORMALIZE)))
          .addMethod(
            METHOD_BATCH_NORMALIZE,
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest,
                com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply>(
                  this, METHODID_BATCH_NORMALIZE)))
          .addMethod(
            METHOD_IDENTIFY,
            asyncUnaryCall(
              new MethodHandlers<
                com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest,
                com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply>(
                  this, METHODID_IDENTIFY)))
          .addMethod(
            METHOD_BATCH_IDENTIFY,
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest,
                com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply>(
                  this, METHODID_BATCH_IDENTIFY)))
          .build();
    }
  }

  /**
   */
  public static final class NormalizeServiceStub extends io.grpc.stub.AbstractStub<NormalizeServiceStub> {
    private NormalizeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NormalizeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NormalizeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NormalizeServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Normalize a single query.
     * </pre>
     */
    public void normalize(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest request,
        io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_NORMALIZE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * TODO(Alan): Normalize a query batch.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest> batchNormalize(
        io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_BATCH_NORMALIZE, getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Identify a query.
     * </pre>
     */
    public void identify(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest request,
        io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_IDENTIFY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Identify a query batch.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest> batchIdentify(
        io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_BATCH_IDENTIFY, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class NormalizeServiceBlockingStub extends io.grpc.stub.AbstractStub<NormalizeServiceBlockingStub> {
    private NormalizeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NormalizeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NormalizeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NormalizeServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Normalize a single query.
     * </pre>
     */
    public com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply normalize(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_NORMALIZE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Identify a query.
     * </pre>
     */
    public com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply identify(com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_IDENTIFY, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class NormalizeServiceFutureStub extends io.grpc.stub.AbstractStub<NormalizeServiceFutureStub> {
    private NormalizeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private NormalizeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NormalizeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new NormalizeServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Normalize a single query.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply> normalize(
        com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_NORMALIZE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Identify a query.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply> identify(
        com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_IDENTIFY, getCallOptions()), request);
    }
  }

  private static final int METHODID_NORMALIZE = 0;
  private static final int METHODID_IDENTIFY = 1;
  private static final int METHODID_BATCH_NORMALIZE = 2;
  private static final int METHODID_BATCH_IDENTIFY = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NormalizeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NormalizeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_NORMALIZE:
          serviceImpl.normalize((com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormRequest) request,
              (io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply>) responseObserver);
          break;
        case METHODID_IDENTIFY:
          serviceImpl.identify((com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyRequest) request,
              (io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply>) responseObserver);
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
        case METHODID_BATCH_NORMALIZE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.batchNormalize(
              (io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.NormReply>) responseObserver);
        case METHODID_BATCH_IDENTIFY:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.batchIdentify(
              (io.grpc.stub.StreamObserver<com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.IdentifyReply>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class NormalizeServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.inmind.idmg.text_sim.normalize.rpc.NormalizeServiceOuterClass.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NormalizeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NormalizeServiceDescriptorSupplier())
              .addMethod(METHOD_NORMALIZE)
              .addMethod(METHOD_BATCH_NORMALIZE)
              .addMethod(METHOD_IDENTIFY)
              .addMethod(METHOD_BATCH_IDENTIFY)
              .build();
        }
      }
    }
    return result;
  }
}
