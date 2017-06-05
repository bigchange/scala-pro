package grpc.client;

/*

import com.bgfurfeature.hello.rpc.GreeterGrpc;
import com.bgfurfeature.hello.rpc.HelloReply;
import com.bgfurfeature.hello.rpc.HelloRequest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

*/
/**
 * Created by Jerry on 2017/5/24.
 * java client to connect scala server
 * works
 *//*

public class JGreeterClient {

  private static Logger logger = LoggerFactory.getLogger(JGreeterClient.class);
  private final ManagedChannel channel;
  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  JGreeterClient(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    blockingStub = GreeterGrpc.blockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public JGreeterClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
  }

  */
/**
   * sayHello
   * @param message
   * @return
   *//*

  public HelloReply greeter(String message) {
    HelloRequest request = HelloRequest.apply(message);
    HelloReply reply;
    try {
      reply = blockingStub.sayHello(request);
    } catch (Exception e) {
      logger.info(Level.WARNING, "RPC failed: {0}", e.getMessage());
      logger.info(Level.WARNING  + "RPC failed -> " + e.getMessage());
      return null;
    }
    logger.info("Greeting: " + reply.message());
    return reply;
  }

  public static void main(String[] args) throws Exception {
    JGreeterClient client = new JGreeterClient("localhost", 50051);
    try {
      client.greeter("asp.net高级开发工程师");
    } finally {
      client.shutdown();
    }
  }
}
*/
