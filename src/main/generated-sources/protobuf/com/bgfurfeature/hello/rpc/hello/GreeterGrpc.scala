package com.bgfurfeature.hello.rpc.hello

object GreeterGrpc {
  val METHOD_SAY_HELLO: _root_.io.grpc.MethodDescriptor[com.bgfurfeature.hello.rpc.hello.HelloRequest, com.bgfurfeature.hello.rpc.hello.HelloReply] =
    _root_.io.grpc.MethodDescriptor.create(
      _root_.io.grpc.MethodDescriptor.MethodType.UNARY,
      _root_.io.grpc.MethodDescriptor.generateFullMethodName("com.bgfurfeature.com.bgfurfeature.hello.rpc.Greeter", "SayHello"),
      new com.trueaccord.scalapb.grpc.Marshaller(com.bgfurfeature.hello.rpc.hello.HelloRequest),
      new com.trueaccord.scalapb.grpc.Marshaller(com.bgfurfeature.hello.rpc.hello.HelloReply))
  
  val METHOD_SAY_HELLO_AGAIN: _root_.io.grpc.MethodDescriptor[com.bgfurfeature.hello.rpc.hello.HelloRequest, com.bgfurfeature.hello.rpc.hello.HelloReply] =
    _root_.io.grpc.MethodDescriptor.create(
      _root_.io.grpc.MethodDescriptor.MethodType.UNARY,
      _root_.io.grpc.MethodDescriptor.generateFullMethodName("com.bgfurfeature.com.bgfurfeature.hello.rpc.Greeter", "SayHelloAgain"),
      new com.trueaccord.scalapb.grpc.Marshaller(com.bgfurfeature.hello.rpc.hello.HelloRequest),
      new com.trueaccord.scalapb.grpc.Marshaller(com.bgfurfeature.hello.rpc.hello.HelloReply))
  
  trait Greeter extends _root_.com.trueaccord.scalapb.grpc.AbstractService {
    override def serviceCompanion = Greeter
    def sayHello(request: com.bgfurfeature.hello.rpc.hello.HelloRequest): scala.concurrent.Future[com.bgfurfeature.hello.rpc.hello.HelloReply]
    def sayHelloAgain(request: com.bgfurfeature.hello.rpc.hello.HelloRequest): scala.concurrent.Future[com.bgfurfeature.hello.rpc.hello.HelloReply]
  }
  
  object Greeter extends _root_.com.trueaccord.scalapb.grpc.ServiceCompanion[Greeter] {
    implicit def serviceCompanion: _root_.com.trueaccord.scalapb.grpc.ServiceCompanion[Greeter] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.bgfurfeature.hello.rpc.hello.HelloProto.javaDescriptor.getServices().get(0)
  }
  
  trait GreeterBlockingClient {
    def serviceCompanion = Greeter
    def sayHello(request: com.bgfurfeature.hello.rpc.hello.HelloRequest): com.bgfurfeature.hello.rpc.hello.HelloReply
    def sayHelloAgain(request: com.bgfurfeature.hello.rpc.hello.HelloRequest): com.bgfurfeature.hello.rpc.hello.HelloReply
  }
  
  class GreeterBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterBlockingStub](channel, options) with GreeterBlockingClient {
    override def sayHello(request: com.bgfurfeature.hello.rpc.hello.HelloRequest): com.bgfurfeature.hello.rpc.hello.HelloReply = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_SAY_HELLO, options), request)
    }
    
    override def sayHelloAgain(request: com.bgfurfeature.hello.rpc.hello.HelloRequest): com.bgfurfeature.hello.rpc.hello.HelloReply = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_SAY_HELLO_AGAIN, options), request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterBlockingStub = new GreeterBlockingStub(channel, options)
  }
  
  class GreeterStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterStub](channel, options) with Greeter {
    override def sayHello(request: com.bgfurfeature.hello.rpc.hello.HelloRequest): scala.concurrent.Future[com.bgfurfeature.hello.rpc.hello.HelloReply] = {
      com.trueaccord.scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_SAY_HELLO, options), request))
    }
    
    override def sayHelloAgain(request: com.bgfurfeature.hello.rpc.hello.HelloRequest): scala.concurrent.Future[com.bgfurfeature.hello.rpc.hello.HelloReply] = {
      com.trueaccord.scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_SAY_HELLO_AGAIN, options), request))
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterStub = new GreeterStub(channel, options)
  }
  
  def bindService(serviceImpl: Greeter, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
    _root_.io.grpc.ServerServiceDefinition.builder("com.bgfurfeature.com.bgfurfeature.hello.rpc.Greeter")
    .addMethod(
      METHOD_SAY_HELLO,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[com.bgfurfeature.hello.rpc.hello.HelloRequest, com.bgfurfeature.hello.rpc.hello.HelloReply] {
        override def invoke(request: com.bgfurfeature.hello.rpc.hello.HelloRequest, observer: _root_.io.grpc.stub.StreamObserver[com.bgfurfeature.hello.rpc.hello.HelloReply]): Unit =
          serviceImpl.sayHello(request).onComplete(com.trueaccord.scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_SAY_HELLO_AGAIN,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[com.bgfurfeature.hello.rpc.hello.HelloRequest, com.bgfurfeature.hello.rpc.hello.HelloReply] {
        override def invoke(request: com.bgfurfeature.hello.rpc.hello.HelloRequest, observer: _root_.io.grpc.stub.StreamObserver[com.bgfurfeature.hello.rpc.hello.HelloReply]): Unit =
          serviceImpl.sayHelloAgain(request).onComplete(com.trueaccord.scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .build()
  
  def blockingStub(channel: _root_.io.grpc.Channel): GreeterBlockingStub = new GreeterBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): GreeterStub = new GreeterStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.bgfurfeature.hello.rpc.hello.HelloProto.javaDescriptor.getServices().get(0)
  
}