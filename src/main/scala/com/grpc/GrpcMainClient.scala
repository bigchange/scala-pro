package com.grpc

import com.grpc.client.GreeterClient

/**
  * Created by Jerry on 2017/5/21.
  */
object GrpcMainClient {

  def main(args: Array[String]): Unit = {
    // val Array(host, msg) = args
    val host = "localhost"
    val msg = "出纳员"   // 出纳员,花艺工程
    val port = 50051
    val client = GreeterClient.apply(host, port)
    client.greeter(msg)
  }
}
