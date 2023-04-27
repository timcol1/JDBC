package avlyakulov.timur.practise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class DatePractise {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/mysql.properties"))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String dburl = properties.getProperty("db.host");
        String dbname = properties.getProperty("db.name");
        String dbpass = properties.getProperty("db.pass");
        try (Connection connection = DriverManager.getConnection(dburl, dbname, dbpass);
             PreparedStatement preparedStatement = connection.prepareStatement("Insert Into dates (date) values (?)")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String date = "20190623";
            LocalDate localDate = LocalDate.parse(date, formatter);
            System.out.println(localDate);
            preparedStatement.setString(1, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}