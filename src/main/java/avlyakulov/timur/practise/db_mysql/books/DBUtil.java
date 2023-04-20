package avlyakulov.timur.practise.db_mysql.books;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    //connect with maven - driver in dependencies
    public static void main(String[] args) {
        Properties properties = new Properties();
        String connectionUrl;
        String userName;
        String password;
        try (BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/mysql.properties"))) {
            properties.load(fileReader);
            connectionUrl = properties.getProperty("db.host");
            userName = properties.getProperty("db.name");
            password = properties.getProperty("db.pass");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from books")) {
            //preparedStatement.executeUpdate(); // кодга мы хотим обновить базу данных
            //preparedStatement.executeUpdate("Update books set name = '1984' where id = 2");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.printf("%d %s\n", resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}