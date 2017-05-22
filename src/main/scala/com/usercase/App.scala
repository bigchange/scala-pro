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

    val path = "hdfs://hg001:8020/user/idmg/jd/jobui/format"
    val savePath = "hdfs://hg001:8020/user/idmg/jd/jobui/format/jobTitle_extractor"
    val localSavePath = "file:///Users/devops/workspace/shell/jd/format"
    // val path = args(0)
    // val savePath = args(1)
    val data = sc.textFile(path).map(x => new JSONObject(x))
      .map(x => x.getString("title"))
      .filter(x => !"".equals(x))
    println("count:" + data.count())
    data.saveAsTextFile("file:///Users/devops/workspace/shell/jd/format")

    // data.foreach(println)
  }

}
