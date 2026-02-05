package net.serg.kafkavehicle.service;


import lombok.extern.slf4j.Slf4j;
import net.serg.kafkavehicle.model.VehicleDistance;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KafkaOutputConsumerService {

    @KafkaListener(topics = "output", groupId = "group_id", containerFactory = "autoAckKafkaListenerContainerFactory")
    public void consume(List<VehicleDistance> distances) {
        distances.forEach(distance -> log.info(
            "Consumed vehicle: {} distance: {}",
            distance.getVehicleId(),
            distance.getDistance()));
    }
}