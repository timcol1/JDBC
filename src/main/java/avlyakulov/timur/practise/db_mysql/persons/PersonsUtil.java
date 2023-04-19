package avlyakulov.timur.practise.db_mysql.persons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonsUtil {
    public static void main(String[] args) {
        String id = "1 or 1 = 1";
        try (Connection connection = PersonsDBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("Select * from persons where PersonID = ?")) {
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                System.out.printf("%d %s %s %s %s\n",resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5    ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
