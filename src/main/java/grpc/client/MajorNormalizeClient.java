/*
package grpc.client;

import com.inmind.idmg.major.rpc.MajorReply;
import com.inmind.idmg.major.rpc.MajorRequest;
import com.inmind.idmg.major.rpc.ResumeMajorServiceGrpc;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

*/
/**
 * Created by Jerry on 2017/6/14.
 *//*

public class MajorNormalizeClient {
  private static Logger logger = LoggerFactory.getLogger(MajorNormalizeClient.class);
  private final ManagedChannel channel;
  private final ResumeMajorServiceGrpc.ResumeMajorServiceBlockingStub blockingStub;

  public MajorNormalizeClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
  }

  public MajorNormalizeClient(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    blockingStub = ResumeMajorServiceGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  private MajorReply doNormalize(MajorRequest req) {
    MajorReply rep = MajorReply.newBuilder().build();
    try {
      rep = blockingStub.doMajor(req);
    } catch (StatusRuntimeException e) {
      logger.error("Major Normalize rpc failed: {0}", e.getStatus());
    }
    // logger.info("reply -> " + rep);
    return rep;
  }

  public String majorNormalize(String major) {
    MajorRequest request = MajorRequest.newBuilder().setMajorReq(major).build();
    MajorReply reply = doNormalize(request);
    return reply.getMajorRep();
  }

  public static void main(String[] args) throws Exception {
    MajorNormalizeClient majorNormalizeClient = new MajorNormalizeClient("hg005", 20298);
    MajorRequest request = MajorRequest.newBuilder().setMajorReq("计算机").build();
    MajorReply reply = majorNormalizeClient.doNormalize(request);
    logger.info("reply -> " + reply.getMajorRep());
    majorNormalizeClient.shutdown();
  }

}
*/
