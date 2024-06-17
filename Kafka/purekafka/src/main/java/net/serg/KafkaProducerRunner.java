package net.serg;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaProducerRunner {

    public static void main(String[] args) {
        List<String> bootstrapServers = List.of(
            "localhost:29092",
            "localhost:39092",
            "localhost:49092"
        );

        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", bootstrapServers));
        
        try (AdminClient adminClient = AdminClient.create(properties)) {
            NewTopic newTopic = new NewTopic("test", 3, (short) 2);
            adminClient.createTopics(Collections.singletonList(newTopic));
        }
        

        MyKafkaProducer producer = new MyKafkaProducer(bootstrapServers);

        producer.send("test", "Hello new");
        producer.flush();
        producer.close();
    }
}