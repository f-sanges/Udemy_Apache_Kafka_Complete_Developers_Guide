import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerAutoCommit {
    public static void main(String[] args) {

        // REF: https://kafka.apache.org/10/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html

        // SEE THE GROUPS
        // kafka-consumer-groups.sh --bootstrap-server 192.168.252.100:9092 --list

        //CONSUMER GROUP DETAILS
        //kafka-consumer-groups.sh --bootstrap-server 192.168.252.100:9092 --group GROUP_NAME --describe


        String IP_PORT_Bootstrap_Servers = "192.168.252.100:9092, 192.168.252.100:9093, 192.168.252.100:9094";
        String Topic_Names[] = {"numbers"};

        Properties props = new Properties();
        props.put("bootstrap.servers", IP_PORT_Bootstrap_Servers);
        props.put("group.id", "first_group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(Topic_Names));

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
