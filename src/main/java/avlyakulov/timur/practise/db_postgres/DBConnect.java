package avlyakulov.timur.practise.db_postgres;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnect {
    public static void main(String[] args) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/postgres.properties"))) {
            Properties properties = new Properties();
            properties.load(fileReader);
            String url = properties.getProperty("db.host");
            String username = properties.getProperty("db.name");
            String password = properties.getProperty("db.pass");
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement("Select * from persons.books", Statement.RETURN_GENERATED_KEYS)) {
                ResultSet rs = preparedStatement.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                String className = rsmd.getColumnClassName(1);
                System.out.println("Class name: " + className);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
