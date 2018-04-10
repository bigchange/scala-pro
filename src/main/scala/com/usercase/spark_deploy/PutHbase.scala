package com.usercase.spark_deploy

import io.vertx.core.json.JsonObject
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.mapreduce.MRJobConfig
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.{SparkConf, SparkContext}

object PutHbase {

  def main(args: Array[String]): Unit = {

    val src = "hdfs://vm-00-104.wgq.higgs.com:8020/user/idmg/resume/dedup_result/index_update_20171212"
    System.setProperty("java.security.krb5.conf", "/Users/devops/Documents/disti_setting/hbase-conf/krb5.conf")
    // System.setProperty("java.security.krb5.conf", "/data/home/idmg/shell/conf/krb5.conf")
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("hbase"))
    val rdd = sc.textFile(src).foreachPartition { x =>
      val conf = HBaseConfiguration.create()
      conf.set("hadoop.security.authentication", "Kerberos")
      conf.set(MRJobConfig.TASK_TIMEOUT, "6000000")
      conf.setInt("hbase.rpc.timeout", 900000)
      conf.setInt("hbase.client.operation.timeout", 900000)
      conf.setInt("hbase.client.scanner.timeout.period", 900000)
      // conf.addResource("/data/home/idmg/shell/conf/core-site.xml")
      // conf.addResource("/data/home/idmg/shell/conf/hbase-site.xml")
      // conf.addResource("/data/home/idmg/shell/conf/hdfs-site.xml")
      conf.addResource("/Users/devops/Documents/disti_setting/hbase-conf/core-site.xml")
      conf.addResource("/Users/devops/Documents/disti_setting/hbase-conf/hbase-site.xml")
      conf.addResource("/Users/devops/Documents/disti_setting/hbase-conf/hdfs-site.xml")
      // var jobConf = new JobConf(conf)
      // 与hbase/conf/hbase-site.xml中hbase.zookeeper.quorum配置的值相同
      UserGroupInformation.setConfiguration(conf)
      // UserGroupInformation.loginUserFromKeytab("idmg@WGQ.HIGGS.COM","/data/home/idmg/shell/conf/krb5.keytab")
      UserGroupInformation.loginUserFromKeytab("idmg@WGQ.HIGGS.COM",
        "/Users/devops/Documents/disti_setting/hbase-conf/krb5.keytab")
      conf.set("hbase.zookeeper.quorum", "172.20.0.152,172.20.0.102,172.20.0.151")
      val hbaseConn = ConnectionFactory.createConnection(conf)
      val table = hbaseConn.getTable(TableName.valueOf("idmg", "resume_file"))
      x.foreach { info =>
        val strings = info.split("\t")
        if (strings.length == 2) {
          val rowKey = strings(0)
          val infoObject = new JsonObject(strings(1))
          val originResumeContent = infoObject.getString("originResumeContent", "")
          if (!"".equals(originResumeContent)) {

            val put = new Put(Bytes.toBytes(rowKey))
            put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("originResumeContent"), Bytes
              .toBytes(originResumeContent))
            table.put(put)
          }
          infoObject.remove("originResumeContent")
          val put2 = new Put(Bytes.toBytes(rowKey))
          put2.addColumn(Bytes.toBytes("data"), Bytes.toBytes("json"),
            Bytes.toBytes(infoObject.toString))
          table.put(put2)
        }
      }
    }
  }

}
