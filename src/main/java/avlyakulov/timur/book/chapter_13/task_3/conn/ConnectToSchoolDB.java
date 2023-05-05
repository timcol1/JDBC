package avlyakulov.timur.book.chapter_13.task_3.conn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectToSchoolDB {
    public static Connection getConnectionToSchoolDB() throws SQLException {
        String url;
        String name;
        String pass;
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/postgres.properties"))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = properties.getProperty("db.host");
        name = properties.getProperty("db.name");
        pass = properties.getProperty("db.pass");

        return DriverManager.getConnection(url, name, pass);
    }
}