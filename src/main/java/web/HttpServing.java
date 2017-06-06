package web;

import com.model.TFIDF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by Jerry on 2017/5/11.
 * http服务请求处理类
 */
public class HttpServing {

  private final static Vertx vertx = Vertx.vertx();

  private static HttpServer server;

  private static Logger logger = LoggerFactory.getLogger(HttpServing.class);

  /**
   * indexing
   * @param router
   */
  private void index(Router router) {
    router.get("/").handler(routingContext -> {
      try {
        HttpServerResponse response = routingContext.response();
        response.end("Hi, this is a data server");
      } catch (Exception ex) {
        ex.printStackTrace();
        routingContext.fail(500);
      }
    });
  }

  /**
   * 自定义服务
   * @param router
   */
  public void getText(Router router) {
    router.get("/raw/:key").handler(routingContext -> {
      String key = routingContext.request().getParam("key");
      routingContext.response().setStatusCode(202).end("key");
    });
  }

  /**
   * json format
   * @param status
   * @param msg
   * @param data
   * @return
   */
  private JsonObject formatResponse(int status, String msg, JsonObject data) {
    JsonObject resp = new JsonObject();
    resp.put("status", status);
    resp.put("msg", msg);
    resp.put("data", data);
    return resp;
  }

  private void foreachMap(HashMap<String, Object> hashMap, JsonObject jsonObject) {
    JsonArray jsonArray = new JsonArray();
    Set<Map.Entry<String, Object>> entrySet = hashMap.entrySet();
    List<Map.Entry<String, Object>> list = new ArrayList<>(entrySet);
    Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
    while(iterator.hasNext()) {
      Map.Entry entry = iterator.next();
      String job = entry.getKey().toString();
      Double value = (Double) entry.getValue();
      jsonArray.add(new JsonObject().put(job, value));
    }
    jsonObject.put("data", jsonArray);
  }
  /**
   * GET json jobTitle core Extractor
   * URI:/json/:key
   * @param router
   */
  private void getJsonJobTitleCores(Router router) {
    router.get("/jobtitle/:key").handler(routingContext -> {
      try {
        HttpServerRequest request = routingContext.request();
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-Type", "application/json; charset=utf-8");
        String key = request.getParam("key");
        HashMap<String, Object> hashMap = TFIDF.getInstance().cosSimilarity(key);
        if (hashMap == null || hashMap.isEmpty()) {
          response.setStatusCode(404).end(this.formatResponse(404, "Not found", null).toString());
          return;
        }
        JsonObject data = new JsonObject();
        foreachMap(hashMap, data);
        response.setStatusCode(200).end(this.formatResponse(200, "success", data).toString());
      } catch (Exception ex) {
        ex.printStackTrace();
        routingContext.fail(500);
      }
    });
  }

  public void init () {
    server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    index(router);
    getJsonJobTitleCores(router);
    int port = 20999;
    // start server
    server.requestHandler(router::accept);
    // server.listen(port);
    logger.info("serving at port:" + port);
  }

  public HttpServer getServer() {
    return  server;
  }
}
