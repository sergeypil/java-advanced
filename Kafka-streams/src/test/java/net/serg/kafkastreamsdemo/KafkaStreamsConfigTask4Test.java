package net.serg.kafkastreamsdemo;

import net.serg.kafkastreamsdemo.config.KafkaStreamsConfigTask4;
import net.serg.kafkastreamsdemo.model.EmployeeDetails;
import net.serg.kafkastreamsdemo.serializer.CustomSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KafkaStreamsConfigTask4Test {

    private KafkaStreamsConfigTask4 config;

    @BeforeEach
    public void setUp() {
        config = new KafkaStreamsConfigTask4();

        String bootstrapServers = "localhost:9092";
        String appId = "TestAppId";
        ReflectionTestUtils.setField(config, "bootstrapServers", bootstrapServers);
        ReflectionTestUtils.setField(config, "appId", appId);
    }

    @Test
    public void testTask4Stream() {
        // Given
        StreamsBuilder builder = new StreamsBuilder();
        KStream<Long, EmployeeDetails> employeeDetailsKStream = config.task4Stream(builder);
        employeeDetailsKStream.to("task4-2");
        Topology topology = builder.build();
        Properties properties = config.streamsConfiguration();

        // When
        TopologyTestDriver driver = new TopologyTestDriver(topology, properties);
        TestInputTopic<Long, EmployeeDetails> inputTopic = driver.createInputTopic(
            "task4",
            Serdes
                .Long()
                .serializer(),
            new CustomSerdes.EmployeeDetailsSerde().serializer()
        );

        EmployeeDetails ed = new EmployeeDetails("testName", "testCompany", "testPosition", 1);
        inputTopic.pipeInput(1L, ed);

        TestOutputTopic<Long, EmployeeDetails> outputTopic = driver.createOutputTopic(
            "task4-2",
            Serdes
                .Long()
                .deserializer(),
            new CustomSerdes.EmployeeDetailsSerde().deserializer()
        );

        // Then
        assertEquals(1, outputTopic.readKeyValuesToList().size());
    }
}