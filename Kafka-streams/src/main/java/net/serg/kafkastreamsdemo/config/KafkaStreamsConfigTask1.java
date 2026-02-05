package net.serg.kafkastreamsdemo.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.Properties;

@Configuration
@EnableKafka
public class KafkaStreamsConfigTask1 {

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
    
    @Bean
    public KStream<Integer, String> task1Stream(StreamsBuilder kStreamBuilder) {
        KStream<Integer, String> stream = kStreamBuilder.stream("task1-1");
        stream.foreach((key, value) -> System.out.println("Original stream, Key = " + key + ", Value = " + value));
        stream.to("task1-2");
        return stream;
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
