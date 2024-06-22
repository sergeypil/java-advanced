package net.serg.kafkastreamsdemo.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//@Configuration
//@EnableKafka
public class KafkaStreamsConfigTask2 {

    @Value("${spring.kafka.streams.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.streams.app-id}")
    private String appId;

    private KafkaStreams kafkaStreams;

    @Bean(name = "streamConfiguration")
    public Properties streamsConfiguration() {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, appId);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes
            .Integer()
            .getClass()
            .getName());
        streamsConfiguration.put(
            StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
            Serdes
                .String()
                .getClass()
                .getName());
        streamsConfiguration.put(
            StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG,
            WallclockTimestampExtractor.class.getName());
        return streamsConfiguration;
    }

    @Bean
    public StreamsBuilder kStreamBuilder() {
        return new StreamsBuilder();
    }

    @Bean(name = "task2Stream")
    public Map<String, KStream<Integer, String>> task2Stream(StreamsBuilder kStreamBuilder) {
        KStream<Integer, String> sourceStream = kStreamBuilder.stream("task2");

        sourceStream.foreach((key, value) -> System.out.println(
            "Original stream, Key = " + key + ", Value = " + value));

        sourceStream = sourceStream
            .filter((key, value) -> value != null)
            .flatMapValues(value -> Arrays.asList(value.split(" ")))
            .map((key, value) -> new KeyValue<>(value.length(), value));

        sourceStream.foreach((key, value) -> System.out.println("Split sentence Key = " + key + ", Value = " + value));

        KStream<Integer, String>[] branches = sourceStream.branch(
            (key, value) -> key < 10,
            (key, value) -> true
        );

        KStream<Integer, String> shortWordsStream = branches[0];
        KStream<Integer, String> longWordsStream = branches[1];

        return Map.of("shortWordsStream", shortWordsStream, "longWordsStream", longWordsStream);
    }

    @Bean(name = "filterStreams")
    public Map<String, KStream<Integer, String>> filterStreams(
        @Qualifier("task2Stream") Map<String, KStream<Integer, String>> streamsMap) {
        Map<String, KStream<Integer, String>> filteredStreams = new HashMap<>();
        streamsMap.forEach((name, stream) -> {
            KStream<Integer, String> filtered = stream.filter((key, value) -> value.contains("a"));
            filtered.foreach((key, value) -> System.out.println(name + " stream, Key: " + key + ", Value: " + value));
            filteredStreams.put(name, filtered);
        });
        return filteredStreams;
    }

    @Bean(name = "mergedStream")
    @DependsOn("filterStreams")
    public KStream<Integer, String> mergeStreams(
        @Qualifier("filterStreams") Map<String, KStream<Integer, String>> filteredStreams) {
        KStream<Integer, String> mergedStream = filteredStreams
            .get("shortWordsStream")
            .merge(filteredStreams.get("longWordsStream"));
        mergedStream.foreach((key, value) -> System.out.println("Merged stream, Key = " + key + ", Value = " + value));
        return mergedStream;
    }

    @Bean
    public KafkaStreams kafkaStreams(
        StreamsBuilder kStreamBuilder,
        @Qualifier("streamConfiguration") Properties streamsConfiguration) {
        kafkaStreams = new KafkaStreams(kStreamBuilder.build(), streamsConfiguration);
        kafkaStreams.start();
        return kafkaStreams;
    }

    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent() {
        kafkaStreams.close();
    }
}
