package net.serg.kafkastreamsdemo.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;

//@Configuration
//@EnableKafka
public class KafkaStreamsConfigTask3 {

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
            .Long()
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

    @Bean(name = "task3Stream")
    public Map<String, KStream<Long, String>> task3Stream(StreamsBuilder kStreamBuilder) {
        KStream<Long, String> task3Stream1 = kStreamBuilder.stream("task3-1");
        KStream<Long, String> task3Stream2 = kStreamBuilder.stream("task3-2");

        task3Stream1 = task3Stream1.filter((key, value) -> value != null && value.contains(":"))
                                   .map((key, value) -> KeyValue.pair(Long.valueOf(value.split(":")[0]), value.split(":")[1]))
                                   .peek((key, value) -> System.out.println("First stream, Key:" + key + ", Value:" + value));
        task3Stream2 = task3Stream2.filter((key, value) -> value != null && value.contains(":"))
                                   .map((key, value) -> KeyValue.pair(Long.valueOf(value.split(":")[0]), value.split(":")[1]))
                                   .peek((key, value) -> System.out.println("Second stream, Key:" + key + ", Value:" + value));

        return Map.of("task3Stream1", task3Stream1, "task3Stream2", task3Stream2);
    }

    @Bean(name = "joinStreams")
    public KStream<Long, String> joinStream(
        @Qualifier("task3Stream") Map<String, KStream<Long, String>> streamsMap) {
        KStream<Long, String> task3Stream1 = streamsMap.get("task3Stream1");
        KStream<Long, String> task3Stream2 = streamsMap.get("task3Stream2");
        JoinWindows joinWindows = JoinWindows.of(Duration.ofSeconds(20))
                                             .grace(Duration.ofSeconds(10));
        KStream<Long, String> joinedStream = task3Stream1.join(task3Stream2, (leftValue, rightValue) -> leftValue + ":" + rightValue, joinWindows);
        joinedStream.foreach((key, value) -> System.out.println("Joined stream, Key = " + key + ", Value = " + value));
        return joinedStream;
    }

    @Bean
    public KafkaStreams kafkaStreams(
        StreamsBuilder kStreamBuilder,
        @Qualifier("streamConfiguration") Properties streamsConfiguration) {
        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder.build(), streamsConfiguration);
        kafkaStreams.start();
        return kafkaStreams;
    }

    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent() {
        kafkaStreams.close();
    }
}
