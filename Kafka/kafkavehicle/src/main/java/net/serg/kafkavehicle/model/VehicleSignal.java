package net.serg.kafkavehicle.model;

import lombok.Data;

@Data
public class VehicleSignal {
    private String vehicleId;
    private Double coordinateX;
    private Double coordinateY;
}