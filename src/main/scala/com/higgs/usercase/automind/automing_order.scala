package com.higgs.usercase.automind

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

  def filter_resume_project_detail(line: String) = {
    val label = "HR通过"
    val json = new JsonObject(line)
    val resume_id = json.getInteger("resume_id", 0)
    val project_id = json.getInteger("project_id", 0)
    val project = json.getJsonObject("resume", new JsonObject())
    val resume = json.getJsonObject("project", new JsonObject())
    val js = new JsonObject()
    js.put("label", label)
    js.put("resumeId", resume_id)
    js.put("projectId", project_id)
    js.put("resumeContent", resume)
    js.put("projectDetail", project)
    js.toString
  }


  def main(args: Array[String]): Unit = {

    var src = "/Users/devops/Downloads/resume_project_id/automind_order.json"

    var data = sc.textFile(src).map { x =>
      // filter_only_order_data(x)
      filter_resume_project_detail(x)
    }.saveAsTextFile("/Users/devops/Downloads/resume_project_id/automind_order_result")

  }


}
