package com.higgs.util

import java.{io, util}

import scala.collection.mutable
import scala.reflect.io.File

/**
  * Created by Jerry on 2017/7/26.
  */
object Utils {

  private val CHARSET = Array(
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
    'u', 'v', 'w', 'x', 'y', 'z',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z'
  )

  private val chartCount = CHARSET.size

  val CHARSET_CHAR = new mutable.HashMap[Char, Int]
  val CHARSET_NUM  = new mutable.HashMap[Int, Char]

  private val split = 5
  private val mask = 0x1FL
  private val key  = 0x7123456789ABCEF7L


  def InitMap (): Unit = {
    for (i <- 0 until chartCount) {
      CHARSET_NUM.put(i, CHARSET.apply(i))
      CHARSET_CHAR.put(CHARSET.apply(i), i)
    }
  }

  def fshl(value: Long, count: Int): Long = {
    val rc = count % split
    val lc = (split - rc)
    return ((value << lc) & mask) | (value >> rc & mask)
  }

  def fshr(value: Long, count: Int): Long = {
    val lc = count % split
    val rc = (split - lc)
    return ((value << lc) & mask) | (value >> rc & mask)
  }

  def encode(id: Long): String = {
    var value = id ^ key
    val length = (64 / split)
    var bit = 0L
    val data = new Array[Char](13)
    for (i <- 0 to length) {
      bit = fshl((value & 0x1F), i) ^ bit
      value = value >>> 5
      data(i) = CHARSET_NUM.apply(bit.toInt)
    }
    return data.mkString("")
  }

  def decode(id: String): Long = {
    val length = (64 / split)
    val content = id.reverse
    var value2 = 0L
    for (i <- 0 to length) {
      val bit: Long = CHARSET_CHAR(content(i)).toLong
      val msk: Long = if (i < length) CHARSET_CHAR(content(i + 1)).toLong else 0
      value2 = value2 | (fshr(msk ^ bit, length - i) << (5 * (length - i)))
    }
    return value2 ^ key
  }

  def encodeV1(id: Long): String = {
    if (id < 0) {
      return "encode id error"
    }
    val value = id ^ (key & 0x0FFFFFFFFFFFFFFFL)
    // println("value:", value)
    val length = (64 / split - 1)
    var msk = 0L
    val data = new Array[Char](12)
    for (it <- 0 to length) {
      val t1 = mask << split * it
      val cv = ((value & t1) >> split * it)
      val result = fshl(cv, Math.abs(key).toInt) ^ msk
      // println("result:", result, "cv:", cv, "msk:", msk, "t1:", t1)
      msk = result
      data(it) = CHARSET_NUM(result.toInt)
    }
    return data.mkString("")
  }

  def decodeV1(id: String): Long = {
    if (id.length != 12) {
    return  0
    }
    val length = (64 / split - 1)
    val content = id.reverse
    // println("abs key:", Math.abs(key).toInt, "key:", key.toInt)
    val res = (0 to length).map{ it =>
      val index = CHARSET_CHAR(content(it)).toLong
      val msk: Long = if (it < length) CHARSET_CHAR(content(it + 1)).toLong else 0
      val value = msk ^ index
      var keyAbs = Math.abs(key).toInt
      fshr(value, keyAbs) << (split * (length - it))
    }.sum
    // println("sum:", res)
    return res  ^ (key & 0x0FFFFFFFFFFFFFFFL)
  }

  def deleteDir(dir: String): Unit = {
    new File(new io.File(dir)).deleteRecursively()

  }


  def main(args: Array[String]): Unit = {
    InitMap()
    // println(encode(100))
    // qdtagka63phj - 3085
    println("v1:", encodeV1(3085), decodeV1(encodeV1(3085)))
    // println(decode("jp5rea56i7fen"))
  }

}
