package com.usercase

import com.model.TFIDF
import org.apache.spark.{SparkConf, SparkContext}
import web.HttpServing

/**
  * Hello world!
  */
object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
  // .set("spark.driver.userClassPathFirst", "true")
  // .set("spark.executor.userClassPathFirst", "true")
     .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
     .set("spark.jars.exclude", "io.netty:netty-common")
     .setMaster("local")

  val sc = new SparkContext(conf)

  // val ssc = new StreamingContext(sc,Seconds(10))

  def main(args: Array[String]): Unit = {

    val server = new HttpServing

    val Array(o, f) =  // args
     Array("file:///Users/devops/workspace/shell/jd/result-map",
      "file:///Users/devops/workspace/shell/jd/formatResult")

    TFIDF.apply(sc, o, f)

    server.init()

    server.getServer.listen(20999)

    /*ssc.start()
    ssc.awaitTermination()*/

  }

}
