package com.grpc

import com.grpc.server.GreeterServer
import com.model.TFIDF
import org.apache.spark.{SparkConf, SparkContext}

import scala.concurrent.ExecutionContext

/**
  * Created by Jerry on 2017/5/21.
  */
object GrpcMainServer {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setAppName("GrpcServer")
      //.setMaster("local")
      )
    val port = 50051
    TFIDF.apply(sc, args(0), args(1))
    val server = GreeterServer.apply(port, ExecutionContext.global)
    server.start
    server.blockUntilShutdown
  }
}
