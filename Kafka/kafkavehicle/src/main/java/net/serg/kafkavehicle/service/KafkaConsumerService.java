package net.serg.kafkavehicle.service;

import lombok.extern.slf4j.Slf4j;
import net.serg.kafkavehicle.model.VehicleDistance;
import net.serg.kafkavehicle.model.VehicleSignal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    KafkaTemplate<String, VehicleDistance> transactionKafkaTemplate;

    //    @KafkaListener(topics = "input", groupId = "group_id")
    //    public void consume(VehicleSignal signal) {
    //        log.info("Consumed signal: {}", signal);
    //        VehicleDistance distance = new VehicleDistance();
    //        distance.setVehicleId(signal.getVehicleId());
    //        distance.setDistance(calculateDistance(signal.getCoordinateX(), signal.getCoordinateY()));
    //        kafkaTemplate.send("output", distance);
    //    }
    @KafkaListener(topics = "input", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void consume(List<VehicleSignal> records, Acknowledgment ack) {
        transactionKafkaTemplate.executeInTransaction(kafkaTxOperations -> {
            records.forEach(signal -> {
                log.info("Consumed signal: {}", signal);
                VehicleDistance distance = new VehicleDistance();
                distance.setVehicleId(signal.getVehicleId());
                distance.setDistance(calculateDistance(signal.getCoordinateX(), signal.getCoordinateY()));
                transactionKafkaTemplate.send("output", distance.getVehicleId(), distance);
            });
            //ack.acknowledge(); // Auto Acknowledgment in transaction
            return Boolean.TRUE;
        });
    }

    @KafkaListener(topics = "output", groupId = "group_id", containerFactory = "autoAckKafkaListenerContainerFactory")
    public void consume(List<VehicleDistance> distances) {
        distances.forEach(distance -> log.info(
            "Consumed vehicle: {} distance: {}",
            distance.getVehicleId(),
            distance.getDistance()));
    }

    private Double calculateDistance(Double coordinateX, Double coordinateY) {
        return Math.sqrt(Math.pow(coordinateX, 2) + Math.pow(coordinateY, 2));
    }
}