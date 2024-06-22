package net.serg.kafkastreamsdemo.service;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.Properties;


//@Service
public class KafkaStreamServiceTask1 implements DisposableBean {

    private final KafkaStreams kafkaStreams;
    
    public KafkaStreamServiceTask1(@Qualifier("streamConfiguration") Properties streamsConfiguration) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> sourceStream = streamsBuilder.stream("task1-1");
        sourceStream.to("task1-2");
        this.kafkaStreams = new KafkaStreams(streamsBuilder.build(), streamsConfiguration);
        this.kafkaStreams.start();
    }

    @Override
    public void destroy() throws Exception {
        this.kafkaStreams.close();
    }
}