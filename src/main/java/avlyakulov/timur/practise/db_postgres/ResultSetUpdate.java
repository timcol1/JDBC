package avlyakulov.timur.practise.db_postgres;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ResultSetUpdate {
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
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from persons.books", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }
            //изменение последней записи в параметре имя
            resultSet.last();
            resultSet.updateString("name", "LilyaUA");
            resultSet.updateRow();

            //вставка новой записи в конец нашей таблицы
            resultSet.moveToInsertRow();
            resultSet.updateString("name", "Nadya");
            resultSet.updateString("book_name", "Azbuka");
            resultSet.insertRow();

            // удаление записи по номеру
            resultSet.absolute(2);
            resultSet.deleteRow();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}