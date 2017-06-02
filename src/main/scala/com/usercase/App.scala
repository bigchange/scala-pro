package com.usercase
import org.apache.spark.{SparkConf, SparkContext}
import org.json.JSONObject

/**
  * Hello world!
  *
  */
object App  {

  val sc = new SparkContext(
    new SparkConf()
    .setAppName("scala-pro")
    .setMaster("local")
    )
  sc.setLogLevel("ERROR")

  def main(args: Array[String]): Unit = {

  }

}
