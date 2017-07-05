package web;

import com.model.TFIDF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
 * 单独用来处理shell输出
 */
class StreamGobbler extends Thread
{
  InputStream is;
  String type;
  Logger logger = LoggerFactory.getLogger(StreamGobbler.class);

  StreamGobbler(InputStream is, String type)
  {
    this.is = is;
    this.type = type;
  }

  public void run()
  {
    try
    {
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line = null;
      logger.info(type + " --> ");
      while ( (line = br.readLine()) != null)
        logger.info(line);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
/**
 * Created by Jerry on 2017/5/11.
 * http服务请求处理类
 */
public class HttpServing {

  private final static Vertx vertx = Vertx.vertx();

  private static HttpServer server;

  private static Logger logger = LoggerFactory.getLogger(HttpServing.class);

  /**
   * help doc
   * @param router
   */
  private void help(Router router) {
    router.get("/help").handler(routingContext -> {
      try {
        HttpServerResponse response = routingContext.response();
        response.putHeader("Content-Type", "application/json; charset=utf-8");
        String respondString = "脚本参数：\n" +
            "Usage: < CVs_dedup-subDirName sourceValue run-stage[all,1-DoExtractor ....] " +
             "true/false>\n" +
            "CVs_dedup-subDirName: 传入的数据源文件目录名(一次性运行多个目录使用冒号隔开，例如，dir1:dir2: ....)\n" +
            "sourceValue： 简历解析阶段 会修改source 来源的值，但在去重阶段，可以修正source\n" +
            "run-stage：运行阶段的指定(一次性运行多个stage的时候，每个stage使用冒号隔开，如使用all仅仅需要指定一个stage无需多个。例如，1:2:3:4: " +
             "...)\n" +
            "           all：所有的阶段一次性跑完\n" +
            "            1 ：DoExtractor - 简历解析，\n" +
            "            2 :  DoFilter - 按照docId去重，\n" +
            "            3：DoDistinct - 去重服务，\n" +
            "            4 : DoNormalizeMapred - 公司名归一化,\n" +
            "            5 : tags_run - tag操作（985，211),\n" +
            "            6:  CandidateIntegrityMapred - 简历完整度分数（json format + 学校归一化  + 专业归一化 + " +
             "工作等级修正）,\n" +
            "            7:  DoESindex  - 建立索引，\n" +
            "            8 : DoFixResumeJsonMapred,\n" +
            "            9:  DoResumeOriginContent  - hbase存入（json + originContent ），\n" +
            "            ...\n" +
            "first ：是否是自由简历 : true or false - true for free resume extractor" +
            "http请求时各个参数用逗号（,）分隔。使用示例：http://hg001:20899/pipeline/p11,-1,1,true";
        response.setStatusCode(200).end(respondString);
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
    router.get("/raw/:key")
    .handler(routingContext -> {
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

  private void callPipeLine(Router router) {
    router.get("/pipeline/:key").handler(routingContext -> {
      HttpServerRequest request = routingContext.request();
      HttpServerResponse response = routingContext.response();
      response.putHeader("Content-Type", "application/json;charset=utf-8");
      String key = request.getParam("key");
      String[] sp = key.split(",");
      // request.params()
      JsonObject data = new JsonObject();
      data.put("dir", sp[0]);
      data.put("source", sp[1]);
      String cmd = "nohup /Users/devops/workspace/shell/check_server.sh 2>&1 &";
      Process proc = null;
      try {
        proc = Runtime.getRuntime().exec(cmd);
        // any output?
        StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
        outputGobbler.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
      response.setStatusCode(200).end(this.formatResponse(200, "success", data).toString());
    });
  }

  public void init () {
    server = vertx.createHttpServer();
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    help(router);
    getJsonJobTitleCores(router);
    callPipeLine(router);
    int port = 20999;
    // start server
    server.requestHandler(router::accept);
    server.listen(port);
    logger.info("serving at port:" + port);
  }

  public HttpServer getServer() {
    return  server;
  }

  public static void main(String[] args) {
    HttpServing httpServing = new HttpServing();
    httpServing.init();
  }
}
