package net.serg.employee;

public class PayrollSystem {
    public static void main(String[] args) {
        Employee[] employees = {
            new CommissionedEmployee("Alice", 10000, 0.15),
            new HourlyEmployee("Bob", 170, 20),
            new SalariedEmployee("Charlie", 5000)
        };

        for (Employee e : employees) {
            System.out.println(e.getName() + " Pay: " + e.calculatePay() + ", Bonus: " + e.calculateBonus());
        }
    }
}