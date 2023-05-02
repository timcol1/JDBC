package avlyakulov.timur.book.chapter_13.example.batch;

import avlyakulov.timur.book.chapter_13.example.conn.Abonent;
import avlyakulov.timur.book.chapter_13.example.dao.GetConnectionToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatchInsert {
    public static void main(String[] args) {
        List<Abonent> abonents = new ArrayList<>();
        abonents.add(new Abonent("Rudenko", 13131));

        try (Connection connection = GetConnectionToDB.createConnection();
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
