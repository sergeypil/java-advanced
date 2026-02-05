package net.serg.kafkastreamsdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetails {
    private String name;
    private String company;
    private String position;
    private int experience;
}