//package com.higgs.hbase;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.com.higgs.hbase.HBaseConfiguration;
//import org.apache.hadoop.com.higgs.hbase.TableName;
//import org.apache.hadoop.com.higgs.hbase.client.*;
//import org.apache.hadoop.com.higgs.hbase.util.Bytes;
//import org.apache.hadoop.security.UserGroupInformation;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import io.vertx.core.logging.Logger;
//import io.vertx.core.logging.LoggerFactory;
//public class HTableUtil {
//  private static Logger logger = LoggerFactory.getLogger(HTableUtil.class);
//  private static Table table;
//  private static Configuration conf;
//
//  private static Connection connection;
//  static {
////        System.setProperty("java.security.krb5.conf", "/Users/xiaoyingying/Documents/workspace-higgs/demo/build/distributions/krb5.conf");
////        // 与hbase/conf/com.higgs.hbase-site.xml中hbase.zookeeper.quorum配置的值相同
////        conf = HBaseConfiguration.create();
////        conf.set("com.higgs.hbase.zookeeper.quorum", "vm-00-152.wgq.higgs.com,vm-00-102.wgq.higgs.com,vm-00-151.wgq.higgs.com");
////        conf.set("fs.defaultFS", "vm-00-104.wgq.higgs.com:8020");
////        // 与hbase/conf/com.higgs.hbase-site.xml中hbase.zookeeper.property.clientPort配置的值相同
////        conf.set("com.higgs.hbase.zookeeper.property.clientPort", "2181");
////        conf.set("hadoop.security.authentication" , "Kerberos" );
////        UserGroupInformation.setConfiguration(conf);
////        try {
////            UserGroupInformation.loginUserFromKeytab("mindcube@WGQ.HIGGS.COM",
////                    "/Users/xiaoyingying/Documents/workspace-higgs/krb5.keytab");
////        } catch (IOException e) {
////            logger.info("key tab error:" + e.getMessage());
////        }
//
//    Configuration hdfsConf = HBaseConfiguration.create();
//
//    String krb5ConfigPath = "/Users/devops/Documents/disti_setting/krb5.conf";
//    String krb5KeytabPath = "/Users/devops/Documents/disti_setting/krb5.keytab";
//    System.setProperty("java.security.krb5.conf", krb5ConfigPath);
//    hdfsConf.addResource("/Users/devops/Documents/disti_setting/com.higgs.hbase-conf/core-site.xml");
//    hdfsConf.addResource("/Users/devops/Documents/disti_setting/com.higgs.hbase-conf/hdfs-site.xml");
//    hdfsConf.addResource("/Users/devops/Documents/disti_setting/com.higgs.hbase-conf/com.higgs.hbase-site.xml");
//
//    UserGroupInformation.setConfiguration(hdfsConf);
//
//    try {
//      String username = "idmg@WGQ.HIGGS.COM";
//      UserGroupInformation.loginUserFromKeytab(username, krb5KeytabPath);
//      connection = ConnectionFactory.createConnection(hdfsConf);
//    } catch (Exception e) {
//      logger.info("key tab error:" + e.getMessage());
//    }
//  }
//  public static Configuration getConf() {
//    return conf;
//  }
//  public static Table getHTable(String tablename) throws IOException {
//    if (table == null) {
//      table = connection.getTable(TableName.valueOf("idmg", tablename));
//    }
//    return table;
//  }
//  public static byte[] gB(String name) {
//    return Bytes.toBytes(name);
//  }
//*
//   * resMap contains rowKey: Map<String, byte[]>
//
//
//  public static boolean addBatch(String tableName, String family, Map<String, Map<String,
//      byte[]>> resMap, boolean flushCommit) {
//    try {
//      Table theTable = getHTable(tableName);
//      if (resMap.size() > 0) {
//        if (null != theTable) {
//          List<Put> puts = new ArrayList<>();
//          Put p;
//          for (Map.Entry<String, Map<String, byte[]>> entry : resMap.entrySet()) {
//            p = new Put(Bytes.toBytes(entry.getKey()));
//            for (Map.Entry<String, byte[]> field : entry.getValue().entrySet()) {
//              p.addColumn(Bytes.toBytes(family), Bytes.toBytes(field.getKey()), field.getValue());
//            }
//            puts.add(p);
//          }
//          theTable.put(puts);
//          // flush HBase commit
//          if (flushCommit) {
////                        theTable.flushCommits();
//          }
//        } else {
//          logger.error("HTable connection failure!");
//          return false;
//        }
//      }
//    } catch (IOException e) {
//      logger.error("addBatch error", e);
//      return false;
//    }
//    return true;
//  }
//}
