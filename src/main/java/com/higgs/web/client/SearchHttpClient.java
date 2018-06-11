package com.higgs.web.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;

import java.io.IOException;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Created by Jerry on 2017/6/7.
 */
public class SearchHttpClient {

  private HttpClientParams params = new HttpClientParams();
  private HttpClient client = new HttpClient(params);

  public HttpClient getClient() {
    return client;
  }

  public String postAndReturnString(HttpClient client, String url, String body) {
    PostMethod httpPost = null;
    try {
      httpPost = new PostMethod(url);
      httpPost.setRequestEntity(new StringRequestEntity(body, "application/json", "utf8"));
      int code = client.executeMethod(httpPost);
      if (code != 200) {
        return null;
      }
      String ret = httpPost.getResponseBodyAsString();
      return ret;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      try {
        httpPost.releaseConnection();
      } catch (Exception ex) {
      }
    }
  }

  public static void main(String args[]) {

    SearchHttpClient searchHttpClient = new SearchHttpClient();

    String body = "{\"query\":\"北京大学 java工程师\",\"pageSize\":10,\"page\":0}";
    String url = // "http://172.16.52.103:20202/search/api";
     "http://hg005:20698/api/tokenizer"; // online
    String string = searchHttpClient.postAndReturnString(searchHttpClient.getClient(), url, body);
    JsonObject jsonObject = new JsonObject(string);
    JsonArray jsonArray = jsonObject.getJsonArray("segments", null);
    for (int i = 0; i < jsonArray.size(); i++) {

      System.out.println(jsonArray.getString(i));
    }

  }
}
