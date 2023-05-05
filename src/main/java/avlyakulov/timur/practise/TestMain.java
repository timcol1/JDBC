package avlyakulov.timur.practise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestMain {
    static String url;
    static String name;
    static String pass;

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/postgres.properties"))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = properties.getProperty("db.host");
        name = properties.getProperty("db.name");
        pass = properties.getProperty("db.pass");
        try (Connection connection = DriverManager.getConnection(url,name,pass)) {
            System.out.println("we are connected!!!!!");
        } catch (SQLException e) {

        }
    }
}
