package com.grpc.client

import java.util.concurrent.TimeUnit

import com.bgfurfeature.hello.rpc.{GreeterGrpc, HelloRequest}
import com.bgfurfeature.hello.rpc.GreeterGrpc
import io.grpc.ManagedChannelBuilder

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

/**
  * Created by Jerry on 2017/5/21.
  */
class GreeterClient (host: String, port: Int) { self =>

  private val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build()

  private val blockingStub = GreeterGrpc.blockingStub(channel)

  implicit val executionContext = ExecutionContext.global

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  /**
    * using blcoking stub call
    * @param msg
    */
  def greeter (msg:String) = {

    val request = HelloRequest.apply(msg)

    val response = Try {
      blockingStub.sayHello(request)
    }

    response match {
      case Success(s) =>
        println("reply is : " + s.message)
      case Failure(exception) =>
        this.shutdown()
        println("Gprc failed!! " + exception.getMessage)
    }
  }

  /**
    * Async call - 异步调用
    * 测试出现无法连接上
    * @param msg
    */
  def greeterAsync (msg: String) = {
    val request = HelloRequest(msg)
    val stub = GreeterGrpc.stub(channel)
    val f = stub.sayHello(request)
    f onComplete { x =>
      x match {
        case Success(s) =>
          println("reply is :" + s.message)
        case Failure(e) =>
          println("Gprc failed!!" + e.getMessage)
      }
    }
  }

}

object GreeterClient {
  def apply(host: String, port: Int): GreeterClient = new GreeterClient(host, port)
}
