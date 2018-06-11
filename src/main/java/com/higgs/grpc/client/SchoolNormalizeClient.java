/*
package com.higgs.grpc.client;

import com.inmind.idmg.schoolnormalize.rpc.NormalFeature;
import com.inmind.idmg.schoolnormalize.rpc.NormalSuggest;
import com.inmind.idmg.schoolnormalize.rpc.NormalizeReply;
import com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest;
import com.inmind.idmg.schoolnormalize.rpc.SchoolNormalizeServiceGrpc;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.com.higgs.grpc.ManagedChannel;
import io.com.higgs.grpc.ManagedChannelBuilder;
import io.com.higgs.grpc.StatusRuntimeException;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

*/
/**
 * Created by Jerry on 2017/6/14.
 *//*

public class SchoolNormalizeClient {
  private static Logger logger = LoggerFactory.getLogger(SchoolNormalizeClient.class);
  private final ManagedChannel channel;
  private final SchoolNormalizeServiceGrpc.SchoolNormalizeServiceBlockingStub blockingStub;

  public SchoolNormalizeClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
  }

  public SchoolNormalizeClient(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    blockingStub = SchoolNormalizeServiceGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public NormalizeReply doNormalize(NormalizeRequest req) {
    NormalizeReply rep = null;
    try {
      rep = blockingStub.doNormalize(req);
    } catch (StatusRuntimeException e) {
      logger.error("Normalize rpc failed: {0}", e.getStatus());
    }
    // logger.info("reply -> " + rep);
    return rep;
  }

  public String getNormalizedShool(String title, int i) {
    if (title.length() < 20) {
      NormalFeature feature = NormalFeature.newBuilder()
          .setId(String.valueOf(i))
          .setText(title)
          .build();
      NormalizeRequest normalizeRequest = NormalizeRequest.newBuilder()
          .addFeatures(feature).build();
      NormalizeReply reply = doNormalize(normalizeRequest);
      List<NormalSuggest> suggestList = reply.getSuggestList();
      String suggestSchool = "";
      for (NormalSuggest suggest : suggestList) {
        int count = suggest.getTextCount();
        if (count >= 1) {
          suggestSchool = suggest.getText(0);
        }
      }
      return suggestSchool;
    }
     return  "";
  }

  public static void main(String[] args) throws Exception {

    SchoolNormalizeClient schoolNormalizeClient = new SchoolNormalizeClient("localhost", 20599);
    NormalFeature feature = NormalFeature.newBuilder().setId("1").setText("暨南大学 经济法学 会计学").build();
    NormalizeRequest normalizeRequest = NormalizeRequest.newBuilder().addFeatures(feature).build();
    NormalizeReply reply = schoolNormalizeClient.doNormalize(normalizeRequest);
    logger.info(reply.getSuggestList().get(0).getText(0));
    schoolNormalizeClient.shutdown();
  }
}
*/
