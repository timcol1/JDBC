package avlyakulov.timur.practise.db_postgres;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ScrollableRowSet {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/postgres.properties"))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String url = properties.getProperty("db.host");
        String name = properties.getProperty("db.name");
        String pass = properties.getProperty("db.pass");
        try (Connection connection = DriverManager.getConnection(url, name, pass);
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from persons.books", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));

            }
            if (resultSet.previous()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }
            if (resultSet.absolute(2)) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }
            if (resultSet.first()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }
            if (resultSet.last()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
