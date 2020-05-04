import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerWithPartitionsAssignment {
    public static void main(String[] args) {

        // REF: https://kafka.apache.org/10/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html

        // SEE THE GROUPS
        // kafka-consumer-groups.sh --bootstrap-server 192.168.252.100:9092 --list

        //CONSUMER GROUP DETAILS
        //kafka-consumer-groups.sh --bootstrap-server 192.168.252.100:9092 --group GROUP_NAME --describe


        String IP_PORT_Bootstrap_Servers = "192.168.252.100:9092, 192.168.252.100:9093, 192.168.252.100:9094";

        String Topic_Name = "numbers";


        Properties props = new Properties();
        props.put("bootstrap.servers", IP_PORT_Bootstrap_Servers);
        props.put("group.id", "new_group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");


        TopicPartition partitions[] = {
                new TopicPartition(Topic_Name, 2),  // consume from partition 2
                new TopicPartition(Topic_Name,4)    // consume from partition 4
        };

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        //consumer.subscribe(Arrays.asList(Topic_Name));
        consumer.assign(Arrays.asList(partitions));     // assign method instead of subscribe
                                                        // if you start multiple consumer of the same group, every consumer will receive every message

        try{

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records){
                    String message = String.format("offset = %d, key = %s, value = %s, partition = %s%n", record.offset(), record.key(), record.value(), record.partition());
                    System.out.printf(message);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
        }

    }
}
