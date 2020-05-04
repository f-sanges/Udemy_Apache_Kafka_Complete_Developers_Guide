import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;

public class Producer {
    public static void main(String[] args) {

        String IP_PORT_Bootstrap_Servers = "192.168.252.100:9092, 192.168.252.100:9093, 192.168.252.100:9094";
        String Topic_Name = "numbers";

        String client_id = "My_Producer";

        // REF: https://kafka.apache.org/25/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html
        Properties props = new Properties();
        props.put("bootstrap.servers", IP_PORT_Bootstrap_Servers);
        //props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        int Number_of_Records = 10;

        //EXAMPLE: Formatted string as message and messages are sent with 300ms delay (3 messages / second)
        try {
            for (int i = 0; i < Number_of_Records; i++) {
                String message = String.format("Producer %s has sent message %s at %s", client_id, i, new Date());
                System.out.println(message);
                producer.send(new ProducerRecord<>(Topic_Name, Integer.toString(i), message));
                Thread.sleep(300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
