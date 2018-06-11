package com.higgs.kafka;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <pre>
 *     Properties props = new Properties();
 *     props.put(&quot;bootstrap.servers&quot;, &quot;localhost:9092&quot;);
 *     props.put(&quot;group.id&quot;, &quot;test&quot;);
 *     props.put(&quot;enable.auto.commit&quot;, &quot;false&quot;);
 *     props.put(&quot;key.deserializer&quot;, &quot;org.apache.kafka.common.serialization.StringDeserializer&quot;);
 *     props.put(&quot;value.deserializer&quot;, &quot;org.apache.kafka.common.serialization.StringDeserializer&quot;);
 *     KafkaConsumer&lt;String, String&gt; consumer = new KafkaConsumer&lt;&gt;(props);
 *     consumer.subscribe(Arrays.asList(&quot;foo&quot;, &quot;bar&quot;));
 *     final int minBatchSize = 200;
 *     List&lt;ConsumerRecord&lt;String, String&gt;&gt; buffer = new ArrayList&lt;&gt;();
 *     while (true) {
 *         ConsumerRecords&lt;String, String&gt; records = consumer.poll(100);
 *         for (ConsumerRecord&lt;String, String&gt; record : records) {
 *             buffer.add(record);
 *         }
 *         if (buffer.size() &gt;= minBatchSize) {
 *             insertIntoDb(buffer);
 *             consumer.commitSync();
 *             buffer.clear();
 *         }
 *     }
 * </pre>
 *
 */

public class AiConsumer {

    private static Properties props = new Properties();
    private static Logger logger = LoggerFactory.getLogger(AiConsumer.class);
    private KafkaConsumer consumer;
    AiConsumer(String brokers, String topic) {
        props.setProperty("bootstrap.servers", brokers);
        props.setProperty("group.id", "AiConsumer");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(topic));
    }

    public KafkaConsumer getConsumer() {
        return consumer;
    }

    public static DateFormat getDataFormator() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     *按字符缓冲写入 BufferedWriter and BufferedOutputStream
     * @param fileName
     * @param  list 写入接受的数据
     */
    public static void writeToFile(String fileName, List<ConsumerRecord<String, String>> list) {
        File f = new File(fileName);
        BufferedOutputStream bos = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;
        try {
            // 文件名存在的话，往后面添加到文件末尾；每天生成一个新的文件
            OutputStream os = new FileOutputStream(f, true);
            bos = new BufferedOutputStream(os);
            writer = new OutputStreamWriter(bos);
            bw = new BufferedWriter(writer);
            for (int i = 0; i < list.size(); i++) {
                ConsumerRecord<String, String> item = list.get(i);
                String key = item.key();
                String value =  item.value();
                long offset = item.offset();
                logger.info("offest = " + item.offset() + ", key = " + key + ", value = " + value );
                bw.write("offest = " + item.offset() + ", key = " + key + ", value = " + value + "\n");
            }
            bw.flush();
        } catch (FileNotFoundException e) {
            logger.info("[exception] => FileNotFoundException ");
        } catch (IOException e) {
            logger.info("[exception] => IOException");
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String brokers = "host-00-02.wgq.higgs.com:9092,host-00-03.wgq.higgs.com:9092,host-00-04.wgq.higgs.com:9092";
        String topic = "llb_to_ai";
        String dir = "/Users/devops/workspace/shell/click_data/kafka";
        AiConsumer aiConsumer = new AiConsumer(brokers, topic);
        KafkaConsumer consumer = aiConsumer.getConsumer();
        final  int minBatchSize = 200;
        Calendar calendar = Calendar.getInstance();
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            calendar.setTime(new Date());
            String fileName = getDataFormator().format(calendar.getTime());
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                writeToFile(dir + "/" + fileName, buffer);
                consumer.commitSync();
                buffer.clear();
            }
        }
    }
}
