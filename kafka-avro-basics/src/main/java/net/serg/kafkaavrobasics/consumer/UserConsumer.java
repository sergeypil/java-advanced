package net.serg.kafkaavrobasics.consumer;

import lombok.extern.slf4j.Slf4j;
import net.serg.avro.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class UserConsumer {

    @KafkaListener(topics = "${kafka-config.topic-name}", groupId = "${kafka-consumer-config.consumer-group-id}}")
    public void consume(User message) {
        log.info(String.format("#### -> Consumed User message -> %s", message));
    }
}
