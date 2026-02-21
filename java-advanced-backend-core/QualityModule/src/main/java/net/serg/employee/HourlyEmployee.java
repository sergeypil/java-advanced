package net.serg.employee;

/**
 * Represents an hourly employee.
 */
public class HourlyEmployee extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public HourlyEmployee(String name, double hoursWorked, double hourlyRate) {
        super(name);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public Money calculatePay() {
        return new Money(hoursWorked * hourlyRate);
    }

    @Override
    public Money calculateBonus() {
        return new Money(hoursWorked > 160 ? 100 : 0); // Example: bonus for overtime
    }
}