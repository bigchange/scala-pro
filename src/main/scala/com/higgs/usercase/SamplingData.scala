package com.higgs.usercase

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Jerry on 2017/8/9.
  */
object SamplingData {

  val conf = new SparkConf().setAppName("sampling").setMaster("local")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {

    val Array(src, des) = args

    val output = des
    val fileSystem = FileSystem.get(sc.hadoopConfiguration)
    fileSystem.delete(new Path(output), true)
    sc.textFile(src).map(_.split("\t")).map { x=> (x(0), 1)}
    .saveAsTextFile(des)

  }

}

class RDDMultipleTextOutputFormat[K, V]() extends MultipleTextOutputFormat[K, V]() {
  override def generateFileNameForKeyValue(key: K, value: V, name: String) : String = {
    (key + "/" + name)
  }
}
