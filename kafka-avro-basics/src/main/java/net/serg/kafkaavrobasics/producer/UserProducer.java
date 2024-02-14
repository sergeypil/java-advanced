package net.serg.kafkaavrobasics.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.serg.kafkaavrobasics.config.KafkaConfigData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProducer {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaConfigData kafkaConfigData;

    public void send(Object user) {
        log.info("sending user='{}'", user.toString());
        log.info(String.format("#### -> Producing message -> %s", user));
        kafkaTemplate.send(kafkaConfigData.getTopicName(), user);
    }
}
