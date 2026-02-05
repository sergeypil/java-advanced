package net.serg.kafkavehicle.model;

import lombok.Data;

@Data
public class VehicleDistance {
    private String vehicleId;
    private Double distance;
}