package net.serg.kafkastreamsdemo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topic1() {
        return new NewTopic("task1-1", 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("task1-2", 1, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic("task2", 1, (short) 1);
    }

    @Bean
    public NewTopic topic4() {
        return new NewTopic("task3-1", 1, (short) 1);
    }

    @Bean
    public NewTopic topic5() {
        return new NewTopic("task3-2", 1, (short) 1);
    }

    @Bean
    public NewTopic topic6() {
        return new NewTopic("task4", 1, (short) 1);
    }
}