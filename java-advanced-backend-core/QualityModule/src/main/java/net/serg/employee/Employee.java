package net.serg.employee;

/**
 * Abstract base class for all employees.
 */
public abstract class Employee {
    protected String name;
    
    public Employee(String name) {
        this.name = name;
    }
    
    public abstract Money calculatePay();
    
    public abstract Money calculateBonus();
    
    public String getName() {
        return name;
    }
}