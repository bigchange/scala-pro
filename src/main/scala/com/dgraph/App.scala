package com.dgraph

import java.util

import dgraph.DgraphClient
import org.apache.spark.{SparkConf, SparkContext}

object App {

  val conf = new SparkConf().setAppName("dgraph")
    .setMaster("local")

  val sc = new SparkContext(conf)
  val host = "172.20.0.68"
  val port = 9080
  val client = new DgraphClient(host, port)

  // dgraph write data
  def test_mutation(): Unit = {

    val src = "/Users/devops/workspace/shell/part-m-00371"
    val resumes = sc.textFile(src).map(_.split("\t")).map (_(1)).collect()
    val list = new util.ArrayList[String]()
    resumes.foreach(x => list.add(x))
    client.mutation(list)
  }

  def main(args: Array[String]): Unit = {
    test_mutation()
  }
}
