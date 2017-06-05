package com.usercase

import com.model.TFIDF
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import web.HttpServing

/**
  * Hello world!
  */
object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
      // .setMaster("local")

  val sc = new SparkContext(conf)

  // val ssc = new StreamingContext(sc,Seconds(10))

  def main(args: Array[String]): Unit = {

    val server = new HttpServing

    val Array(o, f) =  args
    // Array("file:///Users/devops/workspace/shell/jd/result-map",
    //  "file:///Users/devops/workspace/shell/jd/formatResult")

    TFIDF.apply(sc, o, f)

    server.init()

    server.getServer.listen(20999)

    /*ssc.start()
    ssc.awaitTermination()*/

  }

}
