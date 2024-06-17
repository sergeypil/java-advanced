package net.serg;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Test;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Testcontainers
public class KafkaIntegrationTest {

    @Container
    KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.4"));

    @Test
    public void testProducerAndConsumer() throws InterruptedException, ExecutionException {
        kafka.start();
        var bootstrapServers = List.of(kafka.getBootstrapServers());

        MyKafkaConsumer consumer = new MyKafkaConsumer(bootstrapServers, "test");
        MyKafkaProducer producer = new MyKafkaProducer(bootstrapServers);
        consumer.subscribe("test");
        var records = consumer.poll(1000);
        
//        CompletableFuture<ConsumerRecords<String, String>> recordsFuture = CompletableFuture.supplyAsync(() -> {
//            ConsumerRecords<String, String> polledRecords = ConsumerRecords.empty();
//            int maxAttempts = 10;
//            int attempts = 0;
//            while (polledRecords.isEmpty() && attempts < maxAttempts) {
//                polledRecords = consumer.poll(1000);
//                attempts++;
//            }
//            return polledRecords;
//        });
//        Thread.sleep(1000);
        
        producer.send("test", "Hello").get();
        producer.flush();
        producer.close();
        
//        ConsumerRecords<String, String> records = recordsFuture.get();
        
        records = consumer.poll(1000);
        assertFalse(records.isEmpty());

        for (ConsumerRecord<String, String> record : records) {
            consumer.commit();
            System.out.println("Record value: " + record.value());
            assertEquals(record.value(), "Hello");
        }
        consumer.close();
    }
}