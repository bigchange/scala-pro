package com.higgs.usercase.data_mining

import java.util.regex.Pattern

import com.higgs.util.Utils
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
// import com.higgs.web.HttpServing

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

  var regExp = Pattern.compile("Strategy[:](\\w+),Version[:](\\w+),Source[:](\\w+),DID[:](\\w+),ResumeId[:](\\w+),ConsultantId[:](\\w+),Page[:](\\d+),Total[:](\\d+)\\[(\\S+)\\]")
  var regExp2 = Pattern.compile("Strategy[:](\\w+),Source[:](\\w+),DID[:](\\w+),ResumeId[:](\\w+),ConsultantId[:](\\w+),Page[:](\\d+),Total[:](\\d+)\\[(\\S+)\\]")

  var jobDistSearchExp = Pattern.compile("job_dist_search-DID[:](\\w+),ConsultantId[:](\\w+),Ret_num[:](\\d+)\\[(\\S+)\\]")

  var jobDistRecExp = Pattern.compile(",DID[:](\\w+),ConsultantId[:](\\w+),Page_size[:](\\d+),Ret_num[:](\\d+)\\[(\\S+)\\]")

  var matchResult = Pattern.compile("\\((\\d+),(\\d\\.\\d+)\\)+")

  def test_rec_regex(): Unit = {
    var str = "I0801 08:55:03.613091 20447 job_recommend_service.cc:886] " +
      "Strategy:AdaptiveStrategy,Version:794e8d4b434d03daeef288a96e072da22fd66147,Source:ONLINE,DID:FFF4FCF5774B035D54DBEE23FF6B16A7,ResumeId:196046,ConsultantId:6822,Page:1,Total:12[(12163,0.919172),(14440,0.887519),(10833,0.976254),(13803,0.923169),(16091,0.913143),(10863,0.899743),(16092,0.89939),(10084,0.882962),(14638,0.871624),(14635,0.863403),(13212,0.850223),(12871,0.8456)]"
    var str2 = "I0801 08:55:03.613091 20447 job_recommend_service.cc:886] " +
      "Strategy:AdaptiveStrategy,Source:ONLINE,DID:FFF4FCF5774B035D54DBEE23FF6B16A7,ResumeId:196046,ConsultantId:6822,Page:1,Total:12[(12163,0.919172),(14440,0.887519),(10833,0.976254),(13803,0.923169),(16091,0.913143),(10863,0.899743),(16092,0.89939),(10084,0.882962),(14638,0.871624),(14635,0.863403),(13212,0.850223),(12871,0.8456)]"

    /*regExp.findAllMatchIn(str).foreach {x =>
      println(x.matched)
    }*/

    var matcher = regExp.matcher(str)
    if (matcher.find()) {
      var count = matcher.groupCount()
      println("found:" + count)
      for (i <- 0 to (count)) {
        println("group:" + i + ",match:" + matcher.group(i))
      }
    }

    var matcher2 = regExp2.matcher(str2)
    if (matcher2.find()) {
      var count = matcher2.groupCount()
      println("found:" + count)
      for (i <- 0 to (count)) {
        println("group:" + i + ",match:" + matcher2.group(i))
      }
    }
  }

  def test_job_dist_regex(): Unit = {
    var str = ",DID:4A0F9EEF1F1A9993FF192A6DFFBC78FA,ConsultantId:12709,Page_size:10,Ret_num:10[(14731,0.916788),(15592,0.925788),(15823,0.935279),(15205,0.935602),(16472,0.936273),(11765,0.96802),(9053,0.966402),(12457,0.960392),(9465,0.961154),(8290,0.96746)]"
    var matcher = jobDistRecExp.matcher(str)
    if (matcher.find()) {
      var count = matcher.groupCount()
      println("found:" + count)
      for (i <- 0 to (count)) {
        println("group:" + i + ",match:" + matcher.group(i))
      }
      var ids = matcher.group(5)
      var id_matcher = matchResult.matcher(ids)
      while(id_matcher.find()) {
        var count = id_matcher.groupCount()
        println("ids found:" + id_matcher.group())
        for (i <- 0 to (count)) {
          println("group:" + i + ",match:" + id_matcher.group(i))
        }
      }
    }
  }

  // project_id,cid, time
  def extractor_job_dist(log: String):ListBuffer[(String, String, String)] = {
    var res = new ListBuffer[(String, String, String)]
    var cid = ""
    var listBuffer = new ListBuffer[String]
    var time = ""
    var matcher = jobDistRecExp.matcher(log)
    if (matcher.find()) {
      time = getTime(log)
      cid = matcher.group(2)
      listBuffer = matchIds(matcher.group(5))
    }
    for (item <- listBuffer) {
      res.+=((item, cid, time))
    }
    res
  }

  def getTime(log: String) = {
    log.substring(1,5)
  }
  def matchIds(ids: String): ListBuffer[String] = {
    var id_matcher = matchResult.matcher(ids)
    val listBuffer = new ListBuffer[String]
    while(id_matcher.find()) {
      var projectId = id_matcher.group(1)
      listBuffer.+=:(projectId)
    }
    listBuffer
  }
  // local generate result test
  def test_job_dist(): Unit = {
    val src = "/Users/devops/workspace/gitlab/data_mining/job_dist/*/*"
    var out = "/Users/devops/workspace/gitlab/data_mining/job_dist_extractor_result"
    Utils.deleteDir(out)
    sc.textFile(src)
      .flatMap(x=>extractor_job_dist(x))
      .map { x=>
        x._1 + "," + x._2 + "," + x._3
      }.saveAsTextFile(out)

  }

  def generateCount(): Unit = {
    var src = "/Users/devops/workspace/gitlab/data_mining/job_dist_extractor_result"
    var out = "/Users/devops/workspace/gitlab/data_mining/job_dist_project_cid_count_result"
    Utils.deleteDir(out)
    sc.textFile(src)
      .map{ x=>
        val sp = x.split(",")
        (sp(0), sp(1), sp(2))
      }.groupBy(_._1).flatMap { x =>
      var listBuffer = new ListBuffer[String]
      val pid = x._1
      val items = x._2
      var cidMap = new mutable.HashMap[String, Int]()
      for (item <- items) {
        var cid = item._2
        if (cidMap.contains(cid)) {
            cidMap.put(cid, cidMap.get(cid).get + 1)
        } else {
          cidMap.put(cid, 1)
        }
      }
      val iterator = cidMap.iterator
      while (iterator.hasNext) {
        var item = iterator.next()
        var rse = pid + "," + item._1 + "," +  item._2
        listBuffer.+=(rse)
      }
      listBuffer
    }.saveAsTextFile(out)

  }

  def generateCsv(): Unit = {
    var src = "/Users/devops/workspace/gitlab/data_mining/job_dist_project_cid_count_result"
    var out = "/Users/devops/workspace/gitlab/data_mining/job_dist_csv"
    Utils.deleteDir(out)
    val data = sc.textFile(src)
      .map{ x=>
        val sp = x.split(",")
        val value = sp(0) + " =>" + sp(1) + "," + sp(2)
        new Tuple2[(Int, Int, Int), String]((sp(0).toInt, sp(1).toInt, sp(2).toInt), value)
      }.cache()

    val skb = data.sortBy(t => t._1)(new Ordering[(Int, Int, Int)] {
      override def compare(x: (Int, Int, Int), y: (Int, Int, Int)): Int = {
        var ret = -(x._1 - y._1)
        if (ret == 0) {
          ret = -(x._3 - y._3)
        }
        ret
      }

    }, ClassTag.Object.asInstanceOf[ClassTag[(Int, Int, Int)]])

    skb.map(x => x._1._1 + "=>" + x._1._2 + "," + x._1._3)
      .repartition(1)
      .saveAsTextFile(out)

  }

  def main(args: Array[String]): Unit = {
    // test_job_dist_regex()
    // test_job_dist()
    // generateCount()
    generateCsv()

  }

}
