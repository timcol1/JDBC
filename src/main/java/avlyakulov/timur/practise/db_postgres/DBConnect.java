package avlyakulov.timur.practise.db_postgres;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {
    public static void main(String[] args) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/postgres.properties"))) {
            Properties properties = new Properties();
            properties.load(fileReader);
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.name");
            String password = properties.getProperty("db.pass");
            try (Connection connection = DriverManager.getConnection(url,username,password)) {
                System.out.println("We are connected");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
