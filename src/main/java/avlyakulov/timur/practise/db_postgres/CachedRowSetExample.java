package avlyakulov.timur.practise.db_postgres;

import avlyakulov.timur.book.chapter_13.example.dao.GetConnectionToDB;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CachedRowSetExample {
    //пример кешированого запроса, когда уже соеденение к бд закрыто и мы получаем данные, в виде класса
    public static RowSet getData() {
        CachedRowSet cachedRowSet;
        try (Connection connection = GetConnectionToDB.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from persons.books")) {
            cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
            ResultSet resultSet = preparedStatement.executeQuery();
            cachedRowSet.populate(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cachedRowSet;
    }

    public static void main(String[] args) throws SQLException {
        ResultSet resultSet = getData();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }

        //пример выгрибания данных из уже созданной выборки, без соеденения к бд
        System.out.println("-------------------------");
        System.out.println("Cached Row Set ");
        CachedRowSet cachedRowSet = (CachedRowSet) resultSet;
        cachedRowSet.setPageSize(3);
        cachedRowSet.beforeFirst();
        while (cachedRowSet.next()) {
            System.out.println(cachedRowSet.getString(1));
            System.out.println(cachedRowSet.getString(2));
            System.out.println(cachedRowSet.getString(3));
        }
    }
}