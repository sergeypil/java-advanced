package employee;

import net.serg.employee.CommissionedEmployee;
import net.serg.employee.Employee;
import net.serg.employee.HourlyEmployee;
import net.serg.employee.SalariedEmployee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {
    @Test
    void testCommissionedEmployeePayAndBonus() {
        Employee e = new CommissionedEmployee("Test", 20000, 0.1);
        assertEquals(2000.0, e.calculatePay().amount(), 0.001);
        assertEquals(200.0, e.calculateBonus().amount(), 0.001);
    }

    @Test
    void testHourlyEmployeePayAndBonus() {
        Employee e = new HourlyEmployee("Test", 170, 15);
        assertEquals(2550.0, e.calculatePay().amount(), 0.001);
        assertEquals(100.0, e.calculateBonus().amount(), 0.001);
    }

    @Test
    void testSalariedEmployeePayAndBonus() {
        Employee e = new SalariedEmployee("Test", 4000);
        assertEquals(4000.0, e.calculatePay().amount(), 0.001);
        assertEquals(800.0, e.calculateBonus().amount(), 0.001);
    }
}