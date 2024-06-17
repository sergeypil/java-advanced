package net.serg.kafkavehicle.controller;

import net.serg.kafkavehicle.model.VehicleSignal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class VehicleController {

    @Autowired
    KafkaTemplate<String, VehicleSignal> kafkaTemplate;

        @PostMapping("/vehicleSignal")
        public String signal(@RequestBody VehicleSignal signal) {
            if (signal.getVehicleId() == null || signal.getCoordinateX() == null || signal.getCoordinateY() == null) {
                return "Invalid signal data";
            }
            kafkaTemplate.send("input", signal.getVehicleId(), signal).whenComplete((result, ex) -> {
                if (ex != null) {
                    System.out.println("Failed to send message=[" + signal + "] due to : " + ex.getMessage());
                } else {
                    System.out.println("Sent message=[" + signal +
                                           "] at timestamp " + Instant
                        .ofEpochMilli(result.getRecordMetadata().timestamp()).toString() +
                                           " with offset " + result.getRecordMetadata().offset() +
                                           " to partition " + result.getRecordMetadata().partition());
                }
            });
            return "Signal data sent";
        }
    
//    @PostMapping("/vehicleSignal")
//    public String signal(@RequestBody VehicleSignal signal) {
//        if (signal.getVehicleId() == null || signal.getCoordinateX() == null || signal.getCoordinateY() == null) {
//            return "Invalid signal data";
//        }
//        kafkaTemplate.send("input", signal.getVehicleId(), signal).whenComplete((result, ex) -> {
//            if (ex != null) {
//                System.out.println("Failed to send message=[" + signal + "] due to : " + ex.getMessage());
//            } else {
//                System.out.println("Sent message=[" + signal +
//                                       "] at timestamp " + Instant
//                    .ofEpochMilli(result.getRecordMetadata().timestamp()).toString() +
//                                       " with offset " + result.getRecordMetadata().offset() +
//                                       " to partition " + result.getRecordMetadata().partition());
//            }
//        });
//        return "Signal data sent";
//    }
}