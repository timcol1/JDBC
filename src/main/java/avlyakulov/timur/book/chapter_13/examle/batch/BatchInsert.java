package avlyakulov.timur.book.chapter_13.examle.batch;

import avlyakulov.timur.book.chapter_13.examle.conn.Abonent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class BatchInsert {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/mysql.properties"))) {
            properties.load(fileReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String dburl = properties.getProperty("db.host");
        String dbname = properties.getProperty("db.name");
        String dbpass = properties.getProperty("db.pass");

        List<Abonent> abonents = new ArrayList<>();
        abonents.add(new Abonent("Rudenko", 13131));


        try (Connection connection = DriverManager.getConnection(dburl, dbname, dbpass);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO testphones(lastname,phone) values (?,?)")) {
            for (Abonent abonent : abonents) {
                preparedStatement.setString(1,abonent.getName());
                preparedStatement.setInt(2,abonent.getPhone());
                preparedStatement.addBatch();
            }
            int[] updateCounts = preparedStatement.executeBatch();
            System.out.println("Количество выполненых запросов " + Arrays.toString(updateCounts));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
