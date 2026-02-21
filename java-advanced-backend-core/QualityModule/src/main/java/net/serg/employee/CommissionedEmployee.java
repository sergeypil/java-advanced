package net.serg.employee;

/**
 * Represents a commissioned employee.
 */
public class CommissionedEmployee extends Employee {
    private double sales;
    private double commissionRate;

    public CommissionedEmployee(String name, double sales, double commissionRate) {
        super(name);
        this.sales = sales;
        this.commissionRate = commissionRate;
    }

    @Override
    public Money calculatePay() {
        return new Money(sales * commissionRate);
    }

    @Override
    public Money calculateBonus() {
        return new Money(sales * commissionRate * 0.1); // Example: 10% of commission as bonus
    }
}