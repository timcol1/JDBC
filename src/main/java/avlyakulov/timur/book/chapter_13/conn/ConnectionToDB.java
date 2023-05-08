package avlyakulov.timur.book.chapter_13.conn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDB {
    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/postgres.properties"))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String url = properties.getProperty("db.host");
        String name = properties.getProperty("db.name");
        String pass = properties.getProperty("db.pass");
        return DriverManager.getConnection(url, name, pass);
    }
}