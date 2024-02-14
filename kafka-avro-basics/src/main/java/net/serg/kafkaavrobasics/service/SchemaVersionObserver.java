package net.serg.kafkaavrobasics.service;

import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class SchemaVersionObserver {

    private static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";
    private static final String TOPIC = "user-topic";
    private static final String BOOTSTRAP_SERVERS = "localhost:19092,localhost:29092,localhost:39092";

    public static void main(String[] args) throws IOException, RestClientException {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", "schema-observer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));

        SchemaRegistryClient schemaRegistry = new CachedSchemaRegistryClient(SCHEMA_REGISTRY_URL, 100);

        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, byte[]> record : records) {
                ByteBuffer buffer = ByteBuffer.wrap(record.value());
                if (buffer.get() == 0) {
                    int schemaId = buffer.getInt();
                    Schema schemaMetadata = schemaRegistry.getById(schemaId);
                    log.info("Received message with schema id {} from topic {}", schemaId, record.topic());
                    StringBuilder fields = new StringBuilder();
                    for (Schema.Field field : schemaMetadata.getFields()) {
                        fields.append(field.name()).append(", ");
                    }
                    log.info("Schema fields: {}", fields);
                }
            }
        }
    }
}