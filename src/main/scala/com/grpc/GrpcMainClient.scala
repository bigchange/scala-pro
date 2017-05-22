package com.grpc

import com.grpc.client.GreeterClient

/**
  * Created by Jerry on 2017/5/21.
  */
object GrpcMainClient {

  def main(args: Array[String]): Unit = {

    val port = 50051
    val host = "localhost"
    val client = GreeterClient.apply(host, port)

    client.greeter("java高级工程师")
  }
}
