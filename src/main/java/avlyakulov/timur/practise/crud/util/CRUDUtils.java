package avlyakulov.timur.practise.crud.util;

import avlyakulov.timur.practise.crud.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static String INSERT_STUDENT = "INSERT INTO students(name,surname,course_name) VALUES (?, ? , ?);";

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

    public static List<Student> saveStudent(Student student) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setString(3, student.getCourseName());
            preparedStatement.executeUpdate();
            PreparedStatement getAllStudentsFromDB = connection.prepareStatement("SELECT * FROM STUDENTS");
            ResultSet resStudents = getAllStudentsFromDB.executeQuery();
            while (resStudents.next()) {
                int id = resStudents.getInt("id");
                String name = resStudents.getString("name");
                String surname = resStudents.getString("surname");
                String courseName = resStudents.getString("course_name");
                students.add(new Student(id, name, surname, courseName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }


}