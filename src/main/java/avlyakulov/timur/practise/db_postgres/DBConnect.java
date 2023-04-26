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
                     PreparedStatement preparedStatement = connection.prepareStatement("Select * from persons.books")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                    System.out.println(resultSet.getString(2));
                    System.out.println(resultSet.getString(3));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
