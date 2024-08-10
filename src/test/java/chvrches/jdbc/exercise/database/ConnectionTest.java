package chvrches.jdbc.exercise.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {
    @BeforeAll
    static void beforeAll() {
        try{
            java.sql.Driver mysqldriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testConnection() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/JDBC_Exercise?ServerTimezone=Asia/Jakarta";
        String uname = "root";
        String pwd = "root";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, uname, pwd);
            System.out.println("Connection established");
        } catch (SQLException e) {
            Assertions.fail(e);
        }
    }
    @Test
    void testConnectionClose() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/JDBC_Exercise?ServerTimezone=Asia/Jakarta";
        String uname = "root";
        String pwd = "root";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, uname, pwd)) {
            System.out.println("Connection closed");
        } catch (SQLException e) {
            Assertions.fail(e);
        }
    }
}
