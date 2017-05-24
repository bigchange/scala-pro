package com.bgfurfeature.hello.rpc

import com.bgfurfeature.hello.rpc

object GreeterGrpc {
  val METHOD_SAY_HELLO: _root_.io.grpc.MethodDescriptor[HelloRequest, HelloReply] =
    _root_.io.grpc.MethodDescriptor.create(
      _root_.io.grpc.MethodDescriptor.MethodType.UNARY,
      _root_.io.grpc.MethodDescriptor.generateFullMethodName("com.bgfurfeature.com.bgfurfeature.hello.rpc.Greeter", "SayHello"),
      new com.trueaccord.scalapb.grpc.Marshaller(rpc.HelloRequest),
      new com.trueaccord.scalapb.grpc.Marshaller(rpc.HelloReply))
  
  val METHOD_SAY_HELLO_AGAIN: _root_.io.grpc.MethodDescriptor[HelloRequest, HelloReply] =
    _root_.io.grpc.MethodDescriptor.create(
      _root_.io.grpc.MethodDescriptor.MethodType.UNARY,
      _root_.io.grpc.MethodDescriptor.generateFullMethodName("com.bgfurfeature.com.bgfurfeature.hello.rpc.Greeter", "SayHelloAgain"),
      new com.trueaccord.scalapb.grpc.Marshaller(rpc.HelloRequest),
      new com.trueaccord.scalapb.grpc.Marshaller(rpc.HelloReply))
  
  trait Greeter extends _root_.com.trueaccord.scalapb.grpc.AbstractService {
    override def serviceCompanion = Greeter
    def sayHello(request: HelloRequest): scala.concurrent.Future[HelloReply]
    def sayHelloAgain(request: HelloRequest): scala.concurrent.Future[HelloReply]
  }
  
  object Greeter extends _root_.com.trueaccord.scalapb.grpc.ServiceCompanion[Greeter] {
    implicit def serviceCompanion: _root_.com.trueaccord.scalapb.grpc.ServiceCompanion[Greeter] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = HelloProto.javaDescriptor.getServices().get(0)
  }
  
  trait GreeterBlockingClient {
    def serviceCompanion = Greeter
    def sayHello(request: HelloRequest): HelloReply
    def sayHelloAgain(request: HelloRequest): HelloReply
  }
  
  class GreeterBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterBlockingStub](channel, options) with GreeterBlockingClient {
    override def sayHello(request: HelloRequest): HelloReply = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_SAY_HELLO, options), request)
    }
    
    override def sayHelloAgain(request: HelloRequest): HelloReply = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_SAY_HELLO_AGAIN, options), request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterBlockingStub = new GreeterBlockingStub(channel, options)
  }
  
  class GreeterStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterStub](channel, options) with Greeter {
    override def sayHello(request: HelloRequest): scala.concurrent.Future[HelloReply] = {
      com.trueaccord.scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_SAY_HELLO, options), request))
    }
    
    override def sayHelloAgain(request: HelloRequest): scala.concurrent.Future[HelloReply] = {
      com.trueaccord.scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_SAY_HELLO_AGAIN, options), request))
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterStub = new GreeterStub(channel, options)
  }
  
  def bindService(serviceImpl: Greeter, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
    _root_.io.grpc.ServerServiceDefinition.builder("com.bgfurfeature.com.bgfurfeature.hello.rpc.Greeter")
    .addMethod(
      METHOD_SAY_HELLO,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[HelloRequest, HelloReply] {
        override def invoke(request: HelloRequest, observer: _root_.io.grpc.stub.StreamObserver[HelloReply]): Unit =
          serviceImpl.sayHello(request).onComplete(com.trueaccord.scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_SAY_HELLO_AGAIN,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[HelloRequest, HelloReply] {
        override def invoke(request: HelloRequest, observer: _root_.io.grpc.stub.StreamObserver[HelloReply]): Unit =
          serviceImpl.sayHelloAgain(request).onComplete(com.trueaccord.scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .build()
  
  def blockingStub(channel: _root_.io.grpc.Channel): GreeterBlockingStub = new GreeterBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): GreeterStub = new GreeterStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = rpc.HelloProto.javaDescriptor.getServices().get(0)
  
}