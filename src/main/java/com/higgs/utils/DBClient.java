package com.higgs.utils;

import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

/**
 * Created by Jerry on 2017/8/25.
 */
public class DBClient {

  public Logger logger = LoggerFactory.getLogger(DBClient.class);

  private SQLClient client;

  public void initClient() {
    /**
     * {
     "host" : <your-host>,
     "port" : <your-port>,
     "maxPoolSize" : <maximum-number-of-open-connections>,
     "username" : <your-username>,
     "password" : <your-password>,
     "database" : <name-of-your-database>,
     "charset" : <name-of-the-character-set>,
     "queryTimeout" : <timeout-in-milliseconds>
     }
     */
    Vertx vertx = Vertx.vertx();
    JsonObject mySQLClientConfig = new JsonObject()
        .put("host", "localhost")
        .put("port", 3306)
        .put("username", "root")
        .put("database", "cjyou2017")
        .put("charset", "utf-8")
        .put("database", "test");

     client = MySQLClient.createNonShared(vertx, mySQLClientConfig);
  }

  public SQLClient getClient() {
    return client;
  }

  public void queryWithSelect(SQLConnection connection, String type) {
    String queryStatme = "select job, dis from distribution_info where type = ?";
    JsonArray params = new JsonArray().add(type);

    connection.queryWithParams(queryStatme, params, query -> {
      if (query.succeeded()) {
        // Get the result set
        ResultSet resultSet = query.result();
        List<JsonArray> results = resultSet.getResults();

        for (JsonArray row : results) {
          String job = row.getString(0);
          String dis = row.getString(1);
          System.out.println(job + " -> " + dis);
        }
      } else {
        // Failed!
        logger.info("query error !!");
      }
    });

  }

  public static void main(String[] args) {

    DBClient dbClient = new DBClient();
    dbClient.initClient();
    SQLClient client = dbClient.getClient();
    client.getConnection(conn -> {
      if (conn.succeeded()) {
        SQLConnection connection =  conn.result();
        // Got a connection
        dbClient.queryWithSelect(connection, "1");
        connection.close();

      } else {
        // Failed to get connection - deal with it
        dbClient.logger.info("connection error!");
      }
    });

  }

}
