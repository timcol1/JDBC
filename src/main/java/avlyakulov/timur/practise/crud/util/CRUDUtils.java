package avlyakulov.timur.practise.crud.util;

import avlyakulov.timur.practise.crud.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {

    public static List<Student> getStudents(String query) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                String surname = res.getString("surname");
                String courseName = res.getString("course_name");
                students.add(new Student(id, name, surname, courseName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}