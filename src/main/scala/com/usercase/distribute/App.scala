package com.usercase.distribute

import io.vertx.core.json.JsonObject
import org.apache.spark.rdd.RDD
import org.apache.spark._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by Jerry on 2017/8/22.
  */
object App {

  val conf = new SparkConf().setAppName("TfIdfTest")
    // .set("spark.driver.userClassPathFirst", "true")
    // .set("spark.executor.userClassPathFirst", "true")
    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
    .set("spark.jars.exclude", "io.netty:netty-common")
    .setMaster("local")

  val sc = new SparkContext(conf)

  val filterCertificationList = sc.textFile("/Users/devops/Downloads/filter_certification.txt")
    .collect()
  // val ssc = new StreamingContext(sc,Seconds(10))

  def getData(src: String) = {
    val data = sc.textFile(src).map(_.split("\t"))

    val reduce = data.map(x => (x(0), x(1), x(2).toInt))

    reduce
  }


  def skillNumber(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter" +
      "/skillNumber"

    val data = getData(src).cache()

    val data1 = data.map(x => (x._1, x._2.toInt, x._3))

    val count = data1.map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below25 = data1.filter(x => x._2 > 1)
      .map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below36 = data1.filter(x => x._2 >2).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below41 = data1.filter(x => x._2> 3 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below44 = data1.filter(x => x._2> 4 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below45 = data1.filter(x => x._2 > 5 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below46 = data1.filter(x => x._2 > 6 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below47 = data1.filter(x => x._2 > 7 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below48 = data1.filter(x => x._2 > 8 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below49 = data1.filter(x => x._2 > 9 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below10 = data1.filter(x => x._2 > 10 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    count.leftOuterJoin(below25)
      .leftOuterJoin(below36)
      .leftOuterJoin(below41)
      .leftOuterJoin(below44)
      .leftOuterJoin(below45)
      .leftOuterJoin(below46)
      .leftOuterJoin(below47)
      .leftOuterJoin(below48)
      .leftOuterJoin(below49)
      .leftOuterJoin(below10)
      .map { x =>
        val update = System.currentTimeMillis()
        val create = System.currentTimeMillis()
        val json = new JsonObject()
        json.put("count", x._2._1._1._1._1._1._1._1._1._1._1)
        json.put("1", x._2._1._1._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("2", x._2._1._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("3", x._2._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("4", x._2._1._1._1._1._1._1._2.getOrElse(0))
        json.put("5", x._2._1._1._1._1._1._2.getOrElse(0))
        json.put("6", x._2._1._1._1._1._2.getOrElse(0))
        json.put("7", x._2._1._1._1._2.getOrElse(0))
        json.put("8", x._2._1._1._2.getOrElse(0))
        json.put("9", x._2._1._2.getOrElse(0))
        json.put("10", x._2._2.getOrElse(0))
        "6" + "\t" + x._1 + "\t" + json + "\t" + update + "\t" + create
      }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
      "/skillNumberJson")
  }


  val skilllb = new ListBuffer[(String, String, Int, Int)]
  val certlb = new ListBuffer[(String, String, Int, Int)]

  def skills() = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter/skillsrank"

    val data = getData(src).cache()

    val calss = data.map(x => x._1).distinct().collect()

    val filter = data.cache().map(x => (x._1, (x._2, x._3)))
    val total = filter.map(x => (x._1, x._2._2)).reduceByKey(_ + _ )
    val top = filter.leftOuterJoin(total)
      .map(x=> (x._1, (x._2._1._1, x._2._1._2, x._2._2.getOrElse(1)))).cache()

    /*top.map(x => (x._1 , x._2._1 , x._2._2 , x._2._3))
      .sortBy(x => (x._1, x._3), ascending =  false)
      .map(x => x._1 + "\t" + x._2 + "\t" + x._3 + "\t" + x._4)
      .repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
        "/skillsrankAll")*/

    top.groupByKey().foreach { x =>
      val key = x._1
      val value = x._2.toSeq.sortBy(x => -(x._2)).take(10).foreach {y =>
        skilllb.+=((key, y._1, y._2, y._3))
      }

    }
    sc.parallelize(skilllb).sortBy(x => (x._1, x._3), ascending =  false)
      .map(x => x._1 + "\t" + x._2 + "\t" + x._3 + "\t" + x._4)
      .repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
        "/skillsrankTop")

    println(skilllb.size)

  }



  def filter(text:String): Unit = {

  }

  def certificate() = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter/certsrank"

    val data = getData(src).cache()

    val calss = data.map(x => x._1).distinct().collect()

    val validate = data.map(x => (x._1, (x._2, x._3)))

    val filter = data.map(x => (x._1, (x._2, x._3)))
      .filter(x => !filterCertificationList.contains(x._2._1))

    val total = validate
      .filter(x => !filterCertificationList.contains(x._2._1))
      .map(x => (x._1, x._2._2))
      .reduceByKey(_ + _ )

    val saveToMYysql = filter.leftOuterJoin(total)
      .map(x=> (x._1, (x._2._1._1, x._2._1._2, x._2._2.getOrElse(1)))).cache()

    val top = filter.leftOuterJoin(total)
      .map(x=> (x._1, (x._2._1._1, x._2._1._2, x._2._2.getOrElse(1)))).cache()

    /*top.map(x => (x._1 , x._2._1 , x._2._2 , x._2._3))
      .sortBy(x => (x._1, x._3), ascending =  false)
      .map(x => x._1 + "\t" + x._2 + "\t" + x._3 + "\t" + x._4)
      .repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
        "/certsrankAll")*/

      top.groupByKey().foreach { x =>
      val key = x._1
      val value = x._2.toSeq.sortBy(x => -(x._2)).take(10).foreach {y =>
        certlb.+=((key, y._1, y._2, y._3))
      }

    }
    sc.parallelize(certlb).sortBy(x => (x._1, x._3), ascending =  false)
      .map(x => x._1 + "\t" + x._2 + "\t" + x._3 + "\t" + x._4)
      .repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
      "/certsrankTop")

    println(certlb.size)
    println("filterCertificationList size:" + filterCertificationList.size)

  }

  def age (): Unit = {

    val src = "/Users/devops/workspace/shell/resume/distribute_counter/age"

    val data = getData(src)


    val data1 = data.map(x => (x._1, x._2.toInt, x._3))

    val count = data1.map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below25 = data1.filter(x => x._2 < 25)
      .map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below36 = data1.filter(x => x._2 >= 25 && x._2 <= 35).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below41 = data1.filter(x => x._2 >= 36 && x._2 <= 40).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below46 = data1.filter(x => x._2 >= 41 && x._2 <= 45).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val over45 = data1.filter(x => x._2 > 45).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    count.leftOuterJoin(below25)
      .leftOuterJoin(below36)
      .leftOuterJoin(below41)
      .leftOuterJoin(below46)
      .leftOuterJoin(over45)
      .map { x =>
      val json = new JsonObject()
      val update = System.currentTimeMillis()
        val create = System.currentTimeMillis()
      json.put("count",x._2._1._1._1._1._1)
      json.put("1", x._2._1._1._1._1._2.getOrElse(0))
      json.put("2", x._2._1._1._1._2.getOrElse(0))
        json.put("3", x._2._1._1._2.getOrElse(0))
        json.put("4", x._2._1._2.getOrElse(0))
        json.put("5", x._2._2.getOrElse(0))
      "1" + "\t" + x._1 + "\t" + json + "\t" + update + "\t" + create
    }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter/ageJson")


  }

  def degree(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter/degree"
    val data = getData(src)

    val data1 = data.map(x => (x._1, x._2.toInt, x._3))

    val count = data1.filter(x => x._2 > 0).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below25 = data1.filter(x => x._2 == 1)
      .map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below36 = data1.filter(x => x._2 == 2).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below41 = data1.filter(x => x._2 == 3).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below46 = data1.filter(x => x._2 == 4).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val over45 = data1.filter(x => x._2 == 5).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val mba = data1.filter(x => x._2 == 6).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    count.leftOuterJoin(below25)
      .leftOuterJoin(below36)
      .leftOuterJoin(below41)
      .leftOuterJoin(below46)
      .leftOuterJoin(over45)
      .leftOuterJoin(mba)
      .map { x =>
        val update = System.currentTimeMillis()
        val create = System.currentTimeMillis()
        val json = new JsonObject()
        json.put("count",x._2._1._1._1._1._1._1)
        json.put("1", x._2._1._1._1._1._1._2.getOrElse(0))
        json.put("2", x._2._1._1._1._1._2.getOrElse(0))
        json.put("3", x._2._1._1._1._2.getOrElse(0))
        json.put("4", x._2._1._1._2.getOrElse(0))
        json.put("5", x._2._1._2.getOrElse(0))
        json.put("6", x._2._2.getOrElse(0))
        "2" + "\t" + x._1 + "\t" + json + "\t" + update + "\t" + create
      }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
        "/degreeJson")
  }

  def gender = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter/gender"
    val data = getData(src)

    val data1 = data.map(x => (x._1, x._2.toInt, x._3))

    val count = data1.filter(x => x._2 >= 1).map(x => (x._1, x._3)).reduceByKey(_ + _)


    val male = data1.filter(x => x._2 == 1).map(x => (x._1, x._3)).reduceByKey( _ + _)

    val female = data1.filter(x => x._2 == 2).map(x => (x._1, x._3)).reduceByKey( _ + _)

    count.leftOuterJoin(male).leftOuterJoin(female)
      .map { x =>
        val update = System.currentTimeMillis()
        val create = System.currentTimeMillis()
        val json = new JsonObject()
        json.put("count",x._2._1._1)
        json.put("1", x._2._1._2.getOrElse(0))
        json.put("2", x._2._2.getOrElse(0))
        "3" + "\t" + x._1 + "\t" + json + "\t" + update + "\t" + create
      }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
      "/genderJson")
  }

  def projectDuration: Unit = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter/projectDuration"

    val data = getData(src)

    val data1 = data.map(x => (x._1, x._2.toDouble, x._3))

    val count = data1.map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below25 = data1.filter(x => x._2 <= 3.0)
      .map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below36 = data1.filter(x => x._2 > 3.0 && x._2 <= 6.0).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below41 = data1.filter(x => x._2 > 6.0 && x._2 <= 12.0 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below44 = data1.filter(x => x._2 > 12.0 && x._2 <= 24.0 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below45 = data1.filter(x => x._2 > 24.0 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    count.leftOuterJoin(below25)
      .leftOuterJoin(below36)
      .leftOuterJoin(below41)
      .leftOuterJoin(below44)
      .leftOuterJoin(below45)
      .map { x =>
        val update = System.currentTimeMillis()
        val create = System.currentTimeMillis()
        val json = new JsonObject()
        val count = x._2._1._1._1._1._1
        json.put("count", count)
        val one = count - x._2._1._1._1._1._2.getOrElse(0)
        json.put("1", one)
        val two = one - x._2._1._1._1._2.getOrElse(0)
        json.put("2", two)
        val three = two - x._2._1._1._2.getOrElse(0)
        json.put("3", three)
        val four = three - x._2._1._2.getOrElse(0)
        json.put("4", four)
        val five = four - x._2._2.getOrElse(0)
        json.put("5", five)
        "5" + "\t" + x._1 + "\t" + json + "\t" + update + "\t" + create
      }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
      "/projectDurationJson")
  }

  def projectSize: Unit = {

    val src = "/Users/devops/workspace/shell/resume/distribute_counter/projectSize"

    val data = getData(src)

    val data1 = data.map(x => (x._1, x._2.toInt, x._3))

    val count = data1.map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below25 = data1.filter(x => x._2 > 1)
      .map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below36 = data1.filter(x => x._2 > 2).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below41 = data1.filter(x => x._2 > 3 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below44 = data1.filter(x => x._2 > 4 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below45 = data1.filter(x => x._2 > 5 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below46 = data1.filter(x => x._2 > 6 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below47 = data1.filter(x => x._2 > 7 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below48 = data1.filter(x => x._2 > 8 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below49 = data1.filter(x => x._2 > 9 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below10 = data1.filter(x => x._2 > 10 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    count.leftOuterJoin(below25)
      .leftOuterJoin(below36)
      .leftOuterJoin(below41)
      .leftOuterJoin(below44)
      .leftOuterJoin(below45)
      .leftOuterJoin(below46)
      .leftOuterJoin(below47)
      .leftOuterJoin(below48)
      .leftOuterJoin(below49)
      .leftOuterJoin(below10)
      .map { x =>
        val update = System.currentTimeMillis()
        val create = System.currentTimeMillis()
        val json = new JsonObject()
        json.put("count", x._2._1._1._1._1._1._1._1._1._1._1)
        json.put("1", x._2._1._1._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("2", x._2._1._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("3", x._2._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("4", x._2._1._1._1._1._1._1._2.getOrElse(0))
        json.put("5", x._2._1._1._1._1._1._2.getOrElse(0))
        json.put("6", x._2._1._1._1._1._2.getOrElse(0))
        json.put("7", x._2._1._1._1._2.getOrElse(0))
        json.put("8", x._2._1._1._2.getOrElse(0))
        json.put("9", x._2._1._2.getOrElse(0))
        json.put("10", x._2._2.getOrElse(0))
        "6" + "\t" + x._1 + "\t" + json + "\t" + update + "\t" + create
      }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
      "/projectSizeJson")
  }

  def seniority: Unit = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter/seniority"
    val data = getData(src)

    val data1 = data.map(x => (x._1, x._2.toInt, x._3))

    val count = data1.map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below25 = data1.filter(x => x._2 > 1)
      .map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below36 = data1.filter(x => x._2 > 2).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below41 = data1.filter(x => x._2 > 3 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below44 = data1.filter(x => x._2 > 4 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below45 = data1.filter(x => x._2 > 5 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below46 = data1.filter(x => x._2 > 6 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below47 = data1.filter(x => x._2 > 7 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below48 = data1.filter(x => x._2 > 8 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below49 = data1.filter(x => x._2 > 9 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below10 = data1.filter(x => x._2 > 10 ).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    count.leftOuterJoin(below25)
      .leftOuterJoin(below36)
      .leftOuterJoin(below41)
      .leftOuterJoin(below44)
      .leftOuterJoin(below45)
      .leftOuterJoin(below46)
      .leftOuterJoin(below47)
      .leftOuterJoin(below48)
      .leftOuterJoin(below49)
      .leftOuterJoin(below10)
      .map { x =>
        val update = System.currentTimeMillis()
        val create = System.currentTimeMillis()
        val json = new JsonObject()
        json.put("count", x._2._1._1._1._1._1._1._1._1._1._1)
        json.put("1", x._2._1._1._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("2", x._2._1._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("3", x._2._1._1._1._1._1._1._1._2.getOrElse(0))
        json.put("4", x._2._1._1._1._1._1._1._2.getOrElse(0))
        json.put("5", x._2._1._1._1._1._1._2.getOrElse(0))
        json.put("6", x._2._1._1._1._1._2.getOrElse(0))
        json.put("7", x._2._1._1._1._2.getOrElse(0))
        json.put("8", x._2._1._1._2.getOrElse(0))
        json.put("9", x._2._1._2.getOrElse(0))
        json.put("10", x._2._2.getOrElse(0))
        "7" + "\t" + x._1 + "\t" + json + "\t" + update + "\t" + create
      }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
      "/seniorityJson")
  }

  def jumpingAvg: Unit = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter/jumpingAvg"
    val data = getData(src)

    val data1 = data.map(x => (x._1, x._2.toDouble, x._3))

    val count = data1.map(x => (x._1, x._3)).reduceByKey(_ + _)
      .reduceByKey(_ + _)

    val jumpCount = data1.map(x => (x._1, x._2 * x._3)).reduceByKey(_ + _)

     count.leftOuterJoin(jumpCount).map { x =>
       val update = System.currentTimeMillis()
       val create = System.currentTimeMillis()
       val json = new JsonObject()
       val count = x._2._1
       val total = x._2._2.getOrElse(0.0)
       val avg = total / (count * 1.0)
       json.put("avg", avg)
       "4" + "\t"  + x._1 + "\t" + json + "\t" + update + "\t" + create
     }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
      "/jumpingAvgJson")
  }

  def companySize(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/distribute_counter/companySize"
    val data = getData(src)

    val data1 = data.map(x => (x._1, x._2.toInt, x._3))

    val count = data1.map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below25 = data1.filter(x => x._2 == 1)
      .map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below36 = data1.filter(x => x._2 == 2).map(x => (x._1, x._3))
      .reduceByKey( _ + _)

    val below41 = data1.filter(x => x._2 == 3).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    val below46 = data1.filter(x => x._2 == 4).map(x => (x._1, x._3))
      .reduceByKey(_ + _)

    count.leftOuterJoin(below25)
      .leftOuterJoin(below36)
      .leftOuterJoin(below41)
      .leftOuterJoin(below46)
      .map { x =>
        val update = System.currentTimeMillis()
        val create = System.currentTimeMillis()
        val json = new JsonObject()
        json.put("count",x._2._1._1._1._1)
        json.put("1", x._2._1._1._1._2.getOrElse(0))
        json.put("2", x._2._1._1._2.getOrElse(0))
        json.put("3", x._2._1._2.getOrElse(0))
        json.put("4", x._2._2.getOrElse(0))
        "8" + "\t" + x._1 + "\t" + json + "\t" + update + "\t" + create
      }.repartition(1)
      .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_counter" +
        "/companySizeJson")

  }

  def finalScore(): Unit = {
    val src = "/Users/devops/workspace/shell/resume/distribute_score/final_scores.txt"
    val data = getData(src)
    val data1 = data.map(x => ((x._1, x._2.toDouble.formatted("%.2f")), x._3))
    .reduceByKey(_ + _)
     .map(x => x._1._1 + "\t" + x._1._2 + "\t" + x._2)
    .repartition(1)
    .saveAsTextFile("/Users/devops/workspace/shell/resume/distribute_score/final_scores_format" +
      ".txt")

  }

  def main(args: Array[String]): Unit = {
       /*age()
       gender
       jumpingAvg
       projectDuration
       projectSize*/
       // degree()
       // seniority
       certificate()
       // skills()
       // companySize()
      // finalScore()

  }

}
