package com.higgs.usercase

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Jerry on 2017/6/9.
  */
object CounterResume {

  val sc = new SparkContext(new SparkConf().setAppName("scala-pro")
    .set("spark.driver.maxResultSize", "2g")
    .setMaster("local")
  )

  def filter(item: String) = {
    item.forall(Character.isDigit)
  }

  def sort(file: String) = {

    val data = sc.textFile(file).map(_.split("\t")).filter(x => x.length == 2).map(x => (x(0), x(1).toInt))
      .sortBy(_._2, ascending = false).map(x => x._1 + "\t" + x._2).repartition(1)
      .saveAsTextFile("file:///Users/devops/workspace/shell/resume/count_result/normal_result/sort")

  }

  def countCompany(file:String) = {
    val data = sc.textFile(file).map(_.split("\t")).map(x => (x(0).split("_")(0), 1))
      .reduceByKey(_ + _).sortBy(_._2, ascending = false).map(x => x._1 + "\t" + x._2).repartition(1)
    .saveAsTextFile("file:///Users/devops/workspace/shell/resume/count_result/normal_result" +
      "/companyCount")
  }

  def normalJobCounter() = {
    val src = "/Users/devops/workspace/shell/resume/count_result/normalJob/*"
    var des = "/Users/devops/workspace/shell/resume/count_result/normalJob/sample_dict"

    val data = sc.textFile(src)
      .map(_.split("\t")).filter(x => x.length == 2).map(x => (x(0), x(1).toInt))

      // .filter(x => x._1.contains("_unk_"))
      .filter(x => !x._1.contains("_unk_"))

      .sortBy(_._2, ascending = false).map(x => x._1 + "\t" + x._2)

      .repartition(1)

      //.sum()

      //println(data)

      .saveAsTextFile(des)
  }

  def normalMajorCounter() = {
    val src = "/Users/devops/workspace/shell/resume/count_result/normalMajor/normalMajor/*"
    var des = "/Users/devops/workspace/shell/resume/count_result/normalMajor/unk_major"

    val data = sc.textFile(src)
      .map(_.split("\t")).filter(x => x.length == 2).map(x => (x(0), x(1).toInt))

      .filter(x => x._1.contains("_unk_"))

      .sortBy(_._2, ascending = false).map(x => x._1 + "\t" + x._2)

      .repartition(1)

      .saveAsTextFile(des)
  }

  def timeEduCounter ()  = {
    val src = "/Users/devops/workspace/shell/resume/count_result/timeCounter/edu*/*"
    val des = "/Users/devops/workspace/shell/resume/count_result/timeCounter/ranking_edu"

    val org = sc.textFile(src).map(_.split("\t")).map(x => (x(0),x(1),x(2), x(3)))
     .filter(x =>  x._3.toInt > 0)


    org.sortBy(x => x._3.toInt, ascending = false)
      .repartition(1)
      .saveAsTextFile(des)


    val number = org.map(x => x._4.toInt).sum()

    val sum = org.map(x => x._3.toDouble * x._4.toInt).sum()

    val mean = sum / number

    val data = org.map(x => x._3.toDouble)

    val max = data.max()
    val min = data.min()

    println(mean, max, min, number, sum)

  }

  def timeWorkCounter ()  = {
    val src = "/Users/devops/workspace/shell/resume/count_result/timeCounter/work*/*"
    val des = "/Users/devops/workspace/shell/resume/count_result/timeCounter/ranking_work"

    val org = sc.textFile(src).map(_.split("\t")).map(x => (x(0),x(1),x(2), x(3)))
     // .filter(x => x._1.compareTo("2018") < 0 && x._3.toInt > 0 && x._3.toInt < 360)


   org.sortBy(x => x._3.toInt, ascending = false)
      .repartition(1)
      .saveAsTextFile(des)

    val number = org.map(x => x._4.toInt).sum()

    val sum = org.map(x => x._3.toDouble * x._4.toInt).sum()

    val mean = sum / number

    val data = org.map(x => x._3.toDouble)

    val max = data.max()
    val min = data.min()

    println(mean, max, min, number, sum)

  }

  def timeDurationCounter ()  = {
    val src = "/Users/devops/workspace/shell/resume/count_result/timeCounter/duration/*"
    val des = "/Users/devops/workspace/shell/resume/count_result/timeCounter/ranking_duration"

    val org = sc.textFile(src).map(_.split("\t")).map(x => (x(0), x(1), x(2), x(3)))

      .filter(x => x._3.toInt > 0)


    org.sortBy(x => x._3.toInt, ascending = false)
       .repartition(1)
       .saveAsTextFile(des)

    val number = org.map(x => x._4.toInt).sum()

    val sum = org.map(x => x._3.toDouble * x._4.toInt).sum()

    val mean = sum / number

    val data = org.map(x => x._3.toDouble)

    val max = data.max()
    val min = data.min()

    println(mean, max, min, number, sum)

  }

  def main(args: Array[String]): Unit = {

     normalJobCounter()

    // normalMajorCounter()

     // timeWorkCounter()

      // timeEduCounter()

      // timeDurationCounter()

  }

}
