package net.serg.employee;

/**
 * Represents a salaried employee.
 */
public class SalariedEmployee extends Employee {
    private double monthlySalary;

    public SalariedEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public Money calculatePay() {
        return new Money(monthlySalary);
    }

    @Override
    public Money calculateBonus() {
        return new Money(monthlySalary * 0.2); // Example: 20% of salary as bonus
    }
}