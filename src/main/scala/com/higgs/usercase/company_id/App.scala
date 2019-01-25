package com.higgs.usercase.company_id

import com.higgs.util.Utils
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)


  def countOrgId(): Unit = {
    val src = "/Users/devops/workspace/shell/e2e/sample_result_withOrgID"
    sc.textFile(src).map(_.split("\t")).filter(x => x.length == 8).map { x =>
      if (x(1).toInt > 0 ) {
        (1, 1)
      } else {
        (0, 1)
      }
    }.reduceByKey(_ + _).repartition(1)
        .saveAsTextFile("/Users/devops/workspace/shell/e2e/counter_sample_result_withOrgID")
  }


  def filter_(line: String) ={
    var ids = line.split(",")
    var size = ids.size
    var falg = false
    for (i <- 0 until size) {
      if (!"0".eq(ids.apply(i))) {
        falg = true
      }
    }
    falg
  }


  def dealCompanySeq(): Unit = {
    val src = "/Users/devops/workspace/gitlab/mindcube/candate_company_seq_id/candidate_company_seq_id.txt"
    var dest = "/Users/devops/workspace/gitlab/mindcube/candate_company_seq_id" +
      "/candidate_company_seq_id_format"
    Utils.deleteDir(dest)
    var data=sc.textFile(src).map {x =>
      var lastIndexOf = x.lastIndexOf("##########")
      var ids = x.substring(0, lastIndexOf)
      ids
    }.distinct()

    println("data:" +  data.count())
    var res = data.map(_.split(","))
      .map { x =>
        val bf = new ListBuffer[String]
        for (i <- 0 until x.size) {
          if (x(i) != "0") {
            bf.append(x(i) + ",")
          }
        }
       bf.mkString(",")
      }
    println("res:" +  res.count())
    res.map(x => x.trim())
      .filter{ x =>
      val xA = x.trim().split(",")
      if (xA.size >=2) {
        true
      } else {
        false
      }
    }.map(_.split(",").mkString(" "))
      .repartition(1)
      .saveAsTextFile(dest)
  }

  def dealCompanyNameDict(): Unit ={
    val src = "/Users/devops/workspace/gitlab/mindcube/candate_company_seq_id" +
      "/company_name_index.txt"
    var dest = "/Users/devops/workspace/gitlab/mindcube/candate_company_seq_id" +
      "/company_name_index_format"
    Utils.deleteDir(dest)
    sc.textFile(src).map {x =>
      var lastIndexOf = x.lastIndexOf("\t")
      if (lastIndexOf == -1) {
        ""
      } else {
        var dict = x.substring(0, lastIndexOf)
        var lastIndexOfS = dict.lastIndexOf("\u0001")
        if (lastIndexOfS == -1) {
          ""
        } else {
          var name = dict.substring(0, lastIndexOfS)
          var index = dict.substring(lastIndexOfS + "\u0001".length())
          dict
        }
      }
    }.filter(x => !"".eq(x))
      .repartition(1)
      .saveAsTextFile(dest)
  }

  def genUniqueId(): Unit = {
    val src = "/Users/devops/workspace/gitlab/mindcube/candate_company_seq_id/company_hash.txt"
    var dest = "/Users/devops/workspace/gitlab/mindcube/candate_company_seq_id" +
      "/candidate_company_unique_id"
    Utils.deleteDir(dest)
    sc.textFile(src).map(_.split("\t")(0))
      .repartition(1)
      .zipWithIndex()
      .map(x => x._1 + "\t" + (x._2 + 1))
      .repartition(1)
      .saveAsTextFile(dest)
  }

  def reMappWord2vecWordWithCompanyName(): Unit = {
    var src = "/Users/devops/workspace/gitlab/mindcube/candidate_company_seq_id/word2vec" +
      "/companyId_vec_rpl_id.txt"
    var name_index = "/Users/devops/workspace/gitlab/mindcube/candidate_company_seq_id/company_name_index_format.txt"
    var nameMap = sc.textFile(name_index).map(x => x.split("\u0001")).map(x => (x(1),x(0)))
      .collectAsMap()
    var nameBroadCast = sc.broadcast(nameMap)

    var dest = "/Users/devops/workspace/gitlab/mindcube/candidate_company_seq_id/word2vec" +
      "/companyId_vec_rpl_with_name"
    Utils.deleteDir(dest)
    sc.textFile(src).map{x =>
      var wordSp = x.replaceAll("\n", "").split(" ")
      var word = nameBroadCast.value.getOrElse(wordSp(0), "</s>")
        .replaceAll("\t", "")
        .replaceAll(" ","")
      var vec = wordSp.slice(1, wordSp.length).mkString(" ")
      word + " " + vec
    }.repartition(1)
      .saveAsTextFile(dest)

  }


  def main(args: Array[String]): Unit = {
    // dealCompanySeq
    // genUniqueId()
    // dealCompanyNameDict
    reMappWord2vecWordWithCompanyName
  }

}
