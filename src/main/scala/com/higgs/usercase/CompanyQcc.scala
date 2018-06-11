package com.higgs.usercase

import io.vertx.core.json.{JsonArray, JsonObject}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * Created by Jerry on 2017/7/24.
  */
object CompanyQcc {

  val conf = new SparkConf().setAppName("QCC").setMaster("local")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")

  val src = "/Users/devops/workspace/shell/resume/company/qcc/*"
  val namePath = "/Users/devops/workspace/shell/resume/company"

  def main(args: Array[String]): Unit = {

    // val Array(src, namePath, industryPath) = args

    val data = sc.textFile(src).map(_.split("\t")).map(_(0))

    // name
    val name = data.flatMap { x =>
      val lb = new ListBuffer[(String, String)]
      val json = new JsonObject(x)
      val name = json.getString("name", "")
      val core = json.getString("core", "")
      if (core != null) {
        lb.+=((core, name))
      }
      lb
    }

    val company_map = name.map(x => x._1 + "\t" + x._2)
    .repartition(1)
    .saveAsTextFile(namePath + "/company_name")

    val cores = name.map(x => (x._1,1)).reduceByKey(_ + _).map(x => x._1 + "\t" + x._2)
      .repartition(1)
      .saveAsTextFile(namePath + "/core_name")

    /*// industry
    val industry = data.map{ x =>
      val json = new JsonObject(x)
      val industry = json.getString("industry", "")
      val industry_match = json.getJsonArray("industry_match", new JsonArray())
    }.repartition(1)
    .saveAsTextFile(industryPath)*/

  }

}
