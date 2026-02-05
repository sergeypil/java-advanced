package net.serg;

//Import necessary packages

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class MyKafkaConsumer {

    private final KafkaConsumer<String, String> consumer;

    public MyKafkaConsumer(List<String> bootstrapServers, String groupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", String.join(",", bootstrapServers));
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //props.put("auto.offset.reset", "earliest");
        this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribe(String topic) {
        consumer.subscribe(Collections.singletonList(topic));
    }

    public ConsumerRecords<String, String> poll(long durationMillis) {
        return consumer.poll(durationMillis);
    }

    public void commit() {
        consumer.commitSync();
    }

    public void close() {
        consumer.close();
    }
}