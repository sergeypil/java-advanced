package net.serg.kafkastreamsdemo.service;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Properties;

//@Service
public class KafkaStreamServiceTask2 implements DisposableBean {

    private final KafkaStreams kafkaStreams;

    public KafkaStreamServiceTask2(@Qualifier("streamConfiguration") Properties streamsConfiguration) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> sourceStream = streamsBuilder.stream("task2");
        sourceStream = sourceStream.filter((key, value) -> value != null);
        sourceStream = sourceStream.flatMapValues(value -> Arrays.asList(value.split("\\s+")));
        sourceStream.foreach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        KStream<String, String>[] branches = sourceStream.branch(
            (key, value) -> value.length() < 10,
            (key, value) -> value.length() >= 10
        );
        KStream<String, String> shortWords = branches[0];
        KStream<String, String> longWords = branches[1];
        
        
        shortWords = shortWords.filter((key, value) -> value.contains("a"));
        longWords = longWords.filter((key, value) -> value.contains("a"));

        KStream<String, String> merged = shortWords.merge(longWords);
        merged.foreach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
        
        this.kafkaStreams = new KafkaStreams(streamsBuilder.build(), streamsConfiguration);
        this.kafkaStreams.start();
    }

    @Override
    public void destroy() throws Exception {
        this.kafkaStreams.close();
    }
}