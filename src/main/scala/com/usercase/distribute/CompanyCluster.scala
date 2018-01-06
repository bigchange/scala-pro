package com.usercase.distribute

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object CompanyCluster {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  var lb = new ListBuffer[String]
  var index = 0
  var blankCount = 0

  var mapReverse = new mutable.HashMap[String, String]()

  def deleteDup(): Unit = {
    val src = "/Users/devops/Downloads/聚类后公司名_删除多家公司.txt"
    val data = sc.textFile(src).foreach { x =>
      if ("".equals(x)) {
        blankCount = blankCount + 1
        if (blankCount > 1) {
        } else {
          index = index + 1
        }
      } else {
        blankCount = 0
        lb.+=(x + "\t" + index)
      }
    }
    sc.parallelize(lb).saveAsTextFile("/Users/devops/Downloads/聚类后公司名_index.txt")
  }

  def indexMapping(): Unit = {
    var src = "/Users/devops/Downloads/聚类后公司名_index.txt/*"
    val srcCounter = "/Users/devops/workspace/shell/resume/count_result/companyClusterCounter.txt"

    sc.textFile(src).map(x => x.split("\t")).foreach {x =>
      mapReverse.put(x(1), x(0))
    }

    val data = sc.textFile(srcCounter).map(x => x.split("\t")).map(x => (x(0), x(1)))

    sc.parallelize(mapReverse.toSeq).leftOuterJoin(data).map(x => x._2._1 + "\t" + x._2._2.getOrElse(-1))
      .saveAsTextFile("/Users/devops/workspace/shell/resume/count_result/companycluster_mapping")

  }

  def main(args: Array[String]): Unit = {

    // deleteDup()
    indexMapping()

  }

}
