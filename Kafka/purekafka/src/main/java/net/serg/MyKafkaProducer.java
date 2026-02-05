package net.serg;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

public class MyKafkaProducer {

    private final KafkaProducer<String, String> producer;

    public MyKafkaProducer(List<String> bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", String.join(",", bootstrapServers));
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
        props.put("retries", 3);
        props.put("retry.backoff.ms", 1000);
        this.producer = new KafkaProducer<>(props);
    }

    public Future<RecordMetadata> send(String topic, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        return this.producer.send(record, (metadata, e) -> {
            if (e == null) {
                System.out.println("Message was successfully sent to topic " + metadata.topic());
            } else {
                System.out.println("An error occurred while sending the message. " + e);
            }
        });
    }

    public void flush() {
        producer.flush();
    }

    public void close() {
        producer.close();
    }
}