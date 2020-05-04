import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConsumerManualCommit {
    public static void main(String[] args) throws IOException {  //throws IOException because of the FileWriter

        // REF: https://kafka.apache.org/10/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html

        // SEE THE GROUPS
        // kafka-consumer-groups.sh --bootstrap-server 192.168.252.100:9092 --list

        //CONSUMER GROUP DETAILS
        //kafka-consumer-groups.sh --bootstrap-server 192.168.252.100:9092 --group GROUP_NAME --describe

        String IP_PORT_Bootstrap_Servers = "192.168.252.100:9092, 192.168.252.100:9093, 192.168.252.100:9094";
        String Topic_Names[] = {"numbers"};

        Properties props = new Properties();
        props.put("bootstrap.servers", IP_PORT_Bootstrap_Servers);
        props.put("group.id", "second_group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(Topic_Names));

        final int minBatchSize = 20;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        //change the path !!! The following is my path on Windows machine
        FileWriter fileWriter = new FileWriter("D:\\PROVA_FILE\\numbers.txt",true);

        try{

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records){
                    buffer.add(record);
                    String message = String.format("offset = %d, key = %s, value = %s, partition = %s%n", record.offset(), record.key(), record.value(), record.partition());
                    System.out.printf(message);
                }

                if (buffer.size() >= minBatchSize) {        // Write to file only when buffer.size >= minBatchSize

                    fileWriter.append(buffer.toString());
                    consumer.commitSync();                  //commit
                    buffer.clear();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
            fileWriter.close();
        }
    }
}
