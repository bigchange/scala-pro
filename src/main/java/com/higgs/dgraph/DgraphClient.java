package com.higgs.dgraph;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.dgraph.DgraphGrpc;
import io.dgraph.DgraphProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

class  Person {
  String name;
  Person() {}
}

class People {
  List<Person> all;

  People() {}
}

public class DgraphClient {

  private static final String TEST_HOSTNAME = "172.20.0.68";
  private static final int TEST_PORT = 9080;

  private io.dgraph.DgraphClient dgraphClient;

  public DgraphClient(String host, int port) {
    ManagedChannel channel =
        ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    DgraphGrpc.DgraphBlockingStub blockingStub = DgraphGrpc.newBlockingStub(channel);
    dgraphClient = new io.dgraph.DgraphClient(Collections.singletonList(blockingStub));
    // Initialize
    dgraphClient.alter(DgraphProto.Operation.newBuilder().setDropAll(true).build());
  }


  public void createSchema(String schema) {
    DgraphProto.Operation op = DgraphProto.Operation.newBuilder()
        .setSchema(schema).build();
    dgraphClient.alter(op);
  }

  public void mutation(List<String> jsons) {
    io.dgraph.DgraphClient.Transaction txn = dgraphClient.newTransaction();
    try {
      for (String json : jsons) {
        // Run mutation
        DgraphProto.Mutation mu = DgraphProto.Mutation.newBuilder()
            .setSetJson(ByteString.copyFromUtf8(json.toString()))
            .build();
        txn.mutate(mu);
      }
      txn.commit();

    } finally {
      txn.discard();
    }
  }
  public void Query(String query,  Map<String, String> vars) {
    DgraphProto.Response res = dgraphClient.newTransaction().queryWithVars(query, vars);
    // Deserialize
    People ppl = new Gson().fromJson(res.getJson().toStringUtf8(), People.class);
    for (Person p : ppl.all) {
      System.out.println(p.name);
    }
  }

  public static void main(final String[] args) {
    // Set schema
    String schema = "name: string @index(exact) .";
    DgraphClient application = new DgraphClient(TEST_HOSTNAME, TEST_PORT);
    application.createSchema(schema);
    Gson gson = new Gson(); // For JSON encode/decode
    Person p = new Person();
    p.name = "ycj";
    // Serialize it
    String json = gson.toJson(p);
    ArrayList list = new ArrayList();
    list.add(json);
    application.mutation(list);
    // Query
    String query =
        "query all($a: string){\n" + "all(func: eq(name, $a)) {\n" + "    name\n" + "  }\n" + "}";
    System.out.println("Query \n:" + query);
    Map<String, String> vars = Collections.singletonMap("$a", "ycj");
    application.Query(query, vars);
  }
}
