package net.serg.kafkaavrobasics.controllers;

import lombok.RequiredArgsConstructor;
import net.serg.avro.User;
import net.serg.kafkaavrobasics.domain.UserRequest;
import net.serg.kafkaavrobasics.producer.UserProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final UserProducer userProducer;


    @PostMapping(value = "/v1/users")
    public void sendSchemaRegistryToKafkaTopic(@RequestBody UserRequest request) {
        User user = User
            .newBuilder()
            .setName(request.getName())
            //.setFavoriteNumber(request.getFavoriteNumber())
            .setFavoriteColor(request.getFavoriteColor())
            .build();
        this.userProducer.send(user);
    }
}
