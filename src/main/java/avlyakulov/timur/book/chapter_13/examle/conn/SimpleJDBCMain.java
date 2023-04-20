package avlyakulov.timur.book.chapter_13.examle.conn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SimpleJDBCMain {
    public static void main(String[] args) {
        List<Abonent> abonents = new ArrayList<>();
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
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from testphones")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_phone");
                String lastname = resultSet.getString("lastname");
                int phone = resultSet.getInt("phone");
                abonents.add(new Abonent(id,lastname,phone));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(abonents);
    }
}
