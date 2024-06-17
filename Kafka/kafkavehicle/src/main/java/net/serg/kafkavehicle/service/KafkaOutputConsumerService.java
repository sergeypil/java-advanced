package net.serg.kafkavehicle.service;


import lombok.extern.slf4j.Slf4j;
import net.serg.kafkavehicle.model.VehicleDistance;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaOutputConsumerService {

    @KafkaListener(topics = "output", groupId = "group_id")
    public void consume(VehicleDistance distance) {
        log.info("Vehicle: {} distance: {}", distance.getVehicleId(), distance.getDistance());
    }
}