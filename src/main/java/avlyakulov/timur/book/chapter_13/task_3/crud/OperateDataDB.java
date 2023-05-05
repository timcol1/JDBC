package avlyakulov.timur.book.chapter_13.task_3.crud;

import avlyakulov.timur.book.chapter_13.task_3.conn.ConnectToSchoolDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class OperateDataDB {
    private final String SQL_INSERT_TEACHER = "INSERT INTO school.teachers (fullname) values (?)";
    private final String SQL_INSERT_SUBJECT = "INSERT INTO school.subjects (subject_name,day_of_week,audience_number,number_classes,number_students,id_teacher)\n" +
            "values (?,?,?,?,?,?);";

    public void insertDataToDB(BufferedReader reader) {
        try (Connection connection = ConnectToSchoolDB.getConnectionToSchoolDB();
             PreparedStatement insertTeacher = connection.prepareStatement(SQL_INSERT_TEACHER);
             PreparedStatement insertSubject = connection.prepareStatement(SQL_INSERT_SUBJECT)) {
            System.out.println("Choose table where you want to insert data\n1.teachers\n2.subjects");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the full name of teacher");
                    String fullName = reader.readLine();
                    insertTeacher.setString(1, fullName);
                    insertTeacher.executeUpdate();
                }
                case "2" -> {
                    System.out.println("Enter the name of subject");
                    String subjectName = reader.readLine();
                    insertSubject.setString(1, subjectName);
                    System.out.println("From this list enter day of week for this subject");
                    System.out.println(Arrays.toString(DaysOfWeek.values()));
                    DaysOfWeek dayOfWeek = DaysOfWeek.valueOf(reader.readLine().toUpperCase());
                    insertSubject.setString(2, dayOfWeek.getName());
                    System.out.println("Enter the audience number of subject");
                    int audienceNumber = Integer.parseInt(reader.readLine());
                    insertSubject.setInt(3, audienceNumber);
                    System.out.println("Enter the number classes in week");
                    int numberClasses = Integer.parseInt(reader.readLine());
                    insertSubject.setInt(4, numberClasses);
                    System.out.println("Enter the number of students who have to be on subject");
                    int numberStudents = Integer.parseInt(reader.readLine());
                    insertSubject.setInt(5, numberStudents);
                    System.out.println("From this list enter fullname of teacher who teaches this lesson");
                    Statement subjectQuery = connection.createStatement();
                    ResultSet setOfTeachers = subjectQuery.executeQuery("Select fullname from school.teachers");
                    while (setOfTeachers.next())
                        System.out.printf("%d.%s\n", setOfTeachers.getRow(), setOfTeachers.getString("fullname"));
                    String nameTeacher = reader.readLine();
                    ResultSet idTeacherRes = subjectQuery.executeQuery("Select id_teacher from school.teachers where fullname = '" + nameTeacher + "'");
                    idTeacherRes.next();
                    int idTeacher = idTeacherRes.getInt(1);
                    insertSubject.setInt(6, idTeacher);
                    insertSubject.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}