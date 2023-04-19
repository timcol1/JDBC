package avlyakulov.timur.practise.db_mysql;

import javax.xml.transform.Result;
import java.sql.*;

public class DBUtil {
    //connect with maven - driver in dependencies
    public static void main(String[] args) {
        String userName = "root";
        String password = "1504";
        String connectionUrl = "jdbc:mysql://localhost:3306/example";
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from books")) {
            //preparedStatement.executeUpdate(); // кодга мы хотим обновить базу данных
            //preparedStatement.executeUpdate("Update books set name = '1984' where id = 2");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.printf("%d %s\n",resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}