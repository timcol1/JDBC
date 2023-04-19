package avlyakulov.timur.practise.db_mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    //connect with maven - driver in dependencies
    public static void main(String[] args) {
        String userName = "root";
        String password = "1504";
        String connectionUrl = "jdbc:mysql://localhost:3306/example";
        try (Connection connection = DriverManager.getConnection(connectionUrl,userName,password)) {
            System.out.println("We are connected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}