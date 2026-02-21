package net.serg.sonarqube;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
public class SampleController {

    // Vulnerability: SQL Injection
    @GetMapping("/unsafe")
    public String unsafeMethod(String input) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "password");
            Statement statement = connection.createStatement();
            // Intentional: SQL Injection flaw
            statement.executeQuery("SELECT * FROM user_data WHERE user_name = '" + input + "'");
            return "Data Retrieved";
        } catch (SQLException e) {
            return "Error in SQL Handling";
        }
    }

    // Bug: Division by zero
    @GetMapping("/logic-error")
    public int faultyLogic() {
        int a = 10;
        int b = 0;
        // Intentional: Division by zero
        return a / b;
    }

    // Code Smell: Long method, duplicated code
    @GetMapping("/smelly")
    public String smellyMethod() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append("Iteration ").append(i).append(": ");
            sb.append("This is a very long and complex method. ");
            sb.append("This is a very long and complex method. "); // Duplicated code
            sb.append("This is a very long and complex method. "); // Duplicated code
            sb.append("\n");
        }
        return sb.toString();
    }
}