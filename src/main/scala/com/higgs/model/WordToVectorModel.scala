package com.higgs.model

import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by Jerry on 2017/5/25.
  */

class WordToVectorModel(sc:SparkContext, trainDataPath:String, modelPath: String) {

  private var model: Word2VecModel = null

  def loadData = {
    val data = sc.textFile(trainDataPath).map(_.split("\t")).map { x => x(0).split(",").toSeq.distinct}
    data
  }

  def trainModel(data: RDD[Seq[String]], iterations: Int, vectorSize:Int, savePath: String = "") = {
    val word2vec = new Word2Vec()
    word2vec.setMinCount(1)
    word2vec.setNumIterations(iterations)
    word2vec.setVectorSize(math.pow(2,vectorSize).toInt)
    if (savePath.nonEmpty) {
      println("train model.....")
      model = word2vec.fit(data)
      model.save(sc, path = savePath)
    } else {
      println("load model......")
      model = Word2VecModel.load(sc, path = modelPath)
    }

    model

  }

  def similarity(word:String, number: Int)  = {
    val result = model.findSynonyms(word, 10)
    println("====== top 10 sim ======")
    result.foreach(println)
    result
  }
}

object WordToVectorModel {

  private var wvm: WordToVectorModel = null

  def apply(sc: SparkContext, trainDataPath: String, modelPath: String): WordToVectorModel = {

    if (wvm == null) {
      wvm = new WordToVectorModel(sc, trainDataPath, modelPath)
    }
    wvm
  }

  def getInstance = wvm

}
