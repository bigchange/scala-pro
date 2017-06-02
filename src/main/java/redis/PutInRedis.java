package redis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

/**
 * Created by Jerry on 2017/5/26.
 */
public class PutInRedis {


  private static HashMap<String, String> docIds = new HashMap<>();

  private static HashSet<String> docIdSet = new HashSet<>();

  private static HashMap<String, Long> disctDocIds = new HashMap<>();

  private Long docIdCounts = 0L;

  // into redis
  private  static JedisPoolConfig config = new JedisPoolConfig();

  static {
    config.setMaxWaitMillis(10000);
    config.setMaxIdle(100);
    config.setMaxTotal(1024);
    config.setTestOnBorrow(false);
  }
  private static JedisPool jedisPool =  new JedisPool(config,"172.16.52.91", 6379, 20000,
      "DT:FA66AC61-C2F9-49F1-8A21-A14FCFD521E3", 14);
  private static Jedis jedis = jedisPool.getResource();
  private static Pipeline pipeline = jedis.pipelined();

  private void shutRedis() {
    jedisPool.close();
  }

  /**
   * 往redis中写数据
   * @param pipeline
   */
  public void writerFeatureMapIntoRedis(Pipeline pipeline, HashMap<String, String> blcokMap,
    int i) {

      System.out.println("start write redis!! feature -> " + i);

      int pipeCounter = 0;
      String redisKey = "map:feature:" + i;
      System.out.println("map -> " + redisKey);
      Set<Map.Entry<String, String>> entrySet = blcokMap.entrySet();
      Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
      while (iterator.hasNext()) {
        Map.Entry entry = iterator.next();
        String key = entry.getKey().toString();
        String value = entry.getValue().toString();
        String docId = docIds.get(value).toString();
        Long newIndexOfKey = Long.parseLong(disctDocIds.get(docId).toString());
        pipeline.hset(redisKey, key, newIndexOfKey.toString());
        if (pipeCounter == 2048) {
          pipeCounter=0;
          pipeline.syncAndReturnAll();
        } else  {
          pipeCounter += 1;
        }

      }

      if (pipeCounter > 0) {
        List list = pipeline.syncAndReturnAll();
        System.out.println("last respond size: " + list.size());
      }

    System.out.println("writerFeatureMapIntoRedis finished!!");

  }

  /**
   * write doc id list to redis
   * @param pipeline
   */
  public void writerDocIds(Pipeline pipeline) {

    int pipeCounter = 0;
    // write list doc id
    String redisKey = "list:docId";
    Set<Map.Entry<String, Long>> entrySet = disctDocIds.entrySet();
    ArrayList<Map.Entry<String, Long>> list = new ArrayList(entrySet);
    Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
      public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
        return (int) (o1.getValue() - o2.getValue());
      }
    });

    int size = list.size();
    for (int i = 0; i < size; i++) {
      Map.Entry entry = list.get(i);
      String key = entry.getKey().toString();
      // value sort asc
      String value = entry.getValue().toString();
      pipeline.rpush(redisKey, key);
      if (pipeCounter == 2048) {
        pipeCounter=0;
        pipeline.syncAndReturnAll();
      } else  {
        pipeCounter += 1;
      }
    }

    if (pipeCounter > 0) {
      List respList = pipeline.syncAndReturnAll();
      System.out.println("writerDocIds last respond size: " + respList.size());
    }

  }

  /**
   * read file
   * @param path
   */
  public void readFiles(String path) {

    BufferedReader bf = null;

    int featureIndex = -2;

    HashMap<String, String> blcokMap = new HashMap<>();

    try {

      bf = new BufferedReader(new FileReader(path));
      String line = bf.readLine();
      Long index = 0L;
      Long mapIndex = 1L;
      while (line != null) {
        if (index == 0) {
          docIdCounts = Long.parseLong(line);
          System.out.println("docIdCounts:" + docIdCounts);
        } else if (index <= docIdCounts) {
          docIds.put(index.toString(), line);
          if (index % 10000 == 0) {
            System.out.println("docIdCounts index:" + index);
          }
          if (!disctDocIds.containsKey(line)) {
            disctDocIds.put(line, mapIndex);
            mapIndex += 1;
          }

        } else {
          // get line sp \t length = 2
          String[] sp = line.split("\t");
          if (sp.length == 1 ) {

            System.out.println("item:" + line + ", featureIndex:" + featureIndex);

            if (featureIndex >= 0) {
              System.out.println(featureIndex + " -> map size:" +  blcokMap.size());
              writerFeatureMapIntoRedis(pipeline, blcokMap, featureIndex);
              blcokMap.clear();
              System.out.println(featureIndex + " -> map size:" +  blcokMap.size());

             /* List list = pipeline.syncAndReturnAll();
              System.out.println("last respond size: " + list.size());*/

            }
            featureIndex += 1;

          }
          if (sp.length == 2) {
            blcokMap.put(sp[0], sp[1]);
            /**
             * director writer
             */
           /* String redisKey = "map:feature:" + featureIndex;
            String docId = docIds.get(sp[1]).toString();
            Long newIndexOfKey = Long.parseLong(disctDocIds.get(docId).toString());
            pipeline.hset(redisKey, sp[0], newIndexOfKey.toString());
            if (index % 2048 == 0) {
              pipeline.syncAndReturnAll();
            }*/
          }
          if (index % 10000 == 0) {
            System.out.println("index:" + index);
          }
        }

        line = bf.readLine();
        index ++;

      }

    } catch (Exception e) {
      System.out.println("EXCEPTION:" + e.getMessage());
    } finally {
      try {
        bf.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


  }

  public String whiteSpaceAbandon(String src) {
    return src.replaceAll("\\s*", "");
  }

  private static Pattern pattern = Pattern.compile("[a-zA-Z]*");

  public static void main(String[] args) {

    PutInRedis ins = new PutInRedis();

    // String filePath = args[0];
    // "/Users/devops/workspace/shell/distinct_fingerprinter/dedup_store.0";
    // args[0];
    // ins.readFiles(filePath);
    // ins.writerDocIds(pipeline);

    String name = "  ";

    if (pattern.matcher(name).matches()) {
      System.out.println("matcher");
    } else {
      System.out.println("pass");
      String format = ins.whiteSpaceAbandon(name);
      System.out.println(format.length());
    }

    // System.out.println("writerDocIds finished!!");

    ins.shutRedis();

  }

}
