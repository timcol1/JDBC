package avlyakulov.timur.practise;

import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
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
        try (Connection connection = DriverManager.getConnection(url, name, pass);
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from school.teachers")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
                System.out.println(resultSet.getString(2));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
