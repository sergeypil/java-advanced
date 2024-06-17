package net.serg;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.List;

public class KafkaConsumerRunner {

    public static void main(String[] args) {
        List<String> bootstrapServers = List.of(
            "localhost:29092",
            "localhost:39092",
            "localhost:49092"
        );

        MyKafkaConsumer consumer = new MyKafkaConsumer(bootstrapServers, "test");

        consumer.subscribe("test");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                consumer.commit();
                System.out.println("Record value: " + record.value());
                // Commit the offset to ensure the message is not read again ("At most once")
                
            }
        }
    }
}
