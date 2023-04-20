package avlyakulov.timur.book.chapter_13.examle.editing;

import avlyakulov.timur.book.chapter_13.examle.conn.Abonent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EditRowInTable {
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



        try (Connection connection = DriverManager.getConnection(dburl, dbname, dbpass);
             Statement statement = connection.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = statement.executeQuery("Select * from testphones");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                if (id == 2) {
                    resultSet.updateInt("phone", 123123122); // update row
                    resultSet.updateRow();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}