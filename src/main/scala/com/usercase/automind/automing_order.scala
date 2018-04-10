package com.usercase.automind

import io.vertx.core.json.{Json, JsonObject}
import org.apache.spark.{SparkConf, SparkContext}

object automing_order {

  val conf = new SparkConf().setAppName("resume_project_proto_transform")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)


  def filter_only_order_data(line:String): String = {
    val json = new JsonObject(line)
    val resume_id = json.getInteger("resume_id", 0)
    val project_id = json.getInteger("project_id", 0)
    val remark_type = json.getInteger("remark_type", 0)
    val status = json.getInteger("status", 0)
    val id = json.getInteger("id", 0)
    val refuse_remark = json.getString("refuse_remark","")
    val created_at = json.getString("created_at", "")

    id + "," + resume_id + "," + project_id + "," + remark_type + "," + status + "," +
      refuse_remark + "," + created_at

  }

  def main(args: Array[String]): Unit = {

    var src = "/Users/devops/Downloads/order_2018_3_28.json"

    var data = sc.textFile(src).map { x =>
      filter_only_order_data(x)
    }.saveAsTextFile("/Users/devops/Downloads/only_order_2018_3_28.csv")

  }


}
