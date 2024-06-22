package net.serg.kafkastreamsdemo;

import net.serg.kafkastreamsdemo.config.KafkaStreamsConfigTask2;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KafkaStreamsConfigTask2Test {
    private KafkaStreamsConfigTask2 kafkaStreamsConfigTask2;
    private StreamsBuilder builder;
    private TopologyTestDriver testDriver;
    private Properties props;

    
    @BeforeEach
    void setUp() {
        props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        kafkaStreamsConfigTask2 = new KafkaStreamsConfigTask2();
        builder = new StreamsBuilder();
    }
    
    @AfterEach
    void tearDown() {
        testDriver.close();
    }

    @Test
    void shouldSplitToTopicAndFilterLength() {
        // Given
        Map<String, KStream<Integer, String>> streamMap = kafkaStreamsConfigTask2.task2Stream(builder);
        streamMap.get("shortWordsStream").to("testOutputShortWords");
        streamMap.get("longWordsStream").to("testOutputLongWords");

        testDriver = new TopologyTestDriver(builder.build(), props);

        TestInputTopic<Integer, String>  sourceInputTopic = testDriver.createInputTopic("task2", Serdes.Integer().serializer(), new StringSerializer());
        TestOutputTopic<Integer, String>shortWordsTopic = testDriver.createOutputTopic("testOutputShortWords", new IntegerDeserializer(), new StringDeserializer());
        TestOutputTopic<Integer, String>longWordsTopic = testDriver.createOutputTopic("testOutputLongWords", new IntegerDeserializer(), new StringDeserializer());

        // When
        sourceInputTopic.pipeInput(1, "Hello World");
        sourceInputTopic.pipeInput(2, "dadadadadada");

        // Then
        assertEquals(new KeyValue<>(5, "Hello"), shortWordsTopic.readKeyValue());
        assertEquals(new KeyValue<>(5, "World"), shortWordsTopic.readKeyValue());
        
        assertEquals(new KeyValue<>(12, "dadadadadada"), longWordsTopic.readKeyValue());
    }

    @Test
    void shouldFilterLetterA() {
        // Given
        KStream<Integer, String> shortWordsStream = builder.stream("shortWordsInputTopic");
        KStream<Integer, String> longWordsStream = builder.stream("longWordsInputTopic");

        Map<String, KStream<Integer, String>> streamMap = kafkaStreamsConfigTask2.filterStreams(Map.of("shortWordsStream", shortWordsStream, "longWordsStream", longWordsStream));
        streamMap.get("shortWordsStream").to("testOutputShortWords");
        streamMap.get("longWordsStream").to("testOutputLongWords");
        
        testDriver = new TopologyTestDriver(builder.build(), props);
        
        TestInputTopic<Integer, String> shortWordsInputTopic = testDriver.createInputTopic("shortWordsInputTopic", Serdes.Integer().serializer(), new StringSerializer());
        TestInputTopic<Integer, String> longWordsInputTopic = testDriver.createInputTopic("longWordsInputTopic", Serdes.Integer().serializer(), new StringSerializer());

        TestOutputTopic<Integer, String> shortWordsTopic = testDriver.createOutputTopic("testOutputShortWords", new IntegerDeserializer(), new StringDeserializer());
        TestOutputTopic<Integer, String> longWordsTopic = testDriver.createOutputTopic("testOutputLongWords", new IntegerDeserializer(), new StringDeserializer());
        
        // When
        shortWordsInputTopic.pipeInput(1, "Hello");
        longWordsInputTopic.pipeInput(12, "dadadadadada");
        
        // Then
        assertEquals(0, shortWordsTopic.readKeyValuesToList().size());
        assertEquals(1, longWordsTopic.readKeyValuesToList().size());
    }
    
    @Test
    void shouldMerge() {
        // Given
        KStream<Integer, String> shortWordsStream = builder.stream("shortWordsInputTopic");
        KStream<Integer, String> longWordsStream = builder.stream("longWordsInputTopic");

        KStream<Integer, String> mergeStream = kafkaStreamsConfigTask2.mergeStreams(Map.of("shortWordsStream", shortWordsStream, "longWordsStream", longWordsStream));
        mergeStream.to("mergedTopic");

        testDriver = new TopologyTestDriver(builder.build(), props);

        TestInputTopic<Integer, String> shortWordsInputTopic = testDriver.createInputTopic("shortWordsInputTopic", Serdes.Integer().serializer(), new StringSerializer());
        TestInputTopic<Integer, String> longWordsInputTopic = testDriver.createInputTopic("longWordsInputTopic", Serdes.Integer().serializer(), new StringSerializer());

        TestOutputTopic<Integer, String> mergedTopic = testDriver.createOutputTopic("mergedTopic", new IntegerDeserializer(), new StringDeserializer());

        // When
        shortWordsInputTopic.pipeInput(3, "asd");
        longWordsInputTopic.pipeInput(12, "dadadadadada");

        // Then
        assertEquals(2, mergedTopic.readKeyValuesToList().size());
    }
}