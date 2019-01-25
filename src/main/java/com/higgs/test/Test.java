package com.higgs.test;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * User: JerryYou
 *
 * Date: 2019-01-25
 *
 * Copyright (c) 2018 devops
 *
 * <<licensetext>>
 */
public class Test {

  Logger logger = LoggerFactory.getLogger("test");
  public boolean upload(String filePath) throws Exception {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
      try {
        //todo 压缩录音文件
        //获取文件上传token
        String[] sp = filePath.split("/");
        String key = sp[sp.length-1];
        String uuid = "em";
        String token = "0290ff6725104e53a6b1c825abbe7e52";
        File file = new File(filePath);
        //chunk size最大60M， 一般配成1M
        int chunkSize = 1 * 1024 *1024;
        byte[] buffer = new byte[chunkSize];
        fis = new FileInputStream(new File(filePath));
        bis = new BufferedInputStream(fis);
        int chunks = (int)Math.ceil(file.length() / (double)(chunkSize));
        int chunk = 0;
        Response response = null;
        while (bis.read(buffer) > 0) {
          MultipartBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
              .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse
                  ("multipart/form-data"), buffer))
              .addFormDataPart("name", key)
              .addFormDataPart("chunk", String.valueOf(chunk))
              .addFormDataPart("chunks", String.valueOf(chunks))
              .build();
          String url = "xxxx ?token="+token+"&permission=PUBLIC";
          Request request = new Request.Builder().url(url).post(formBody).build();
          response = new OkHttpClient().newCall(request).execute();
          if (response.code() != 200) {
            logger.info("status:" + response.code());
            response.close();
            return false;
          }
          chunk++;
          response.close();
        }

        return true;
      } catch (FileNotFoundException e) {
        logger.info("FileNotFoundException:" +e.getMessage());
        return false;
      } catch (Exception e) {
        logger.info("Exception:" + e.getMessage());
        return false;
      } finally {
        fis.close();
        bis.close();
      }
  }

  public static void main(String[] args) throws Exception {
      Test t = new Test();
      t.upload("/Users/devops/Desktop/em.wav");
  }
}
