package avlyakulov.timur.book.chapter_13.task_3.crud;

import avlyakulov.timur.book.chapter_13.task_3.conn.ConnectToSchoolDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

//Вывести информацию о преподавателях, работающих в заданный день недели

public class OperateDataDB {

    private final String SQL_INSERT_TEACHER = "INSERT INTO school.teachers (fullname) values (?)";
    private final String SQL_INSERT_SUBJECT = "INSERT INTO school.subjects (subject_name,day_of_week,audience_number,number_classes,number_students,id_teacher)\n" +
            "values (?,?,?,?,?,?);";

    private final String SQL_GET_INFORMATION_TEACHER = "select fullname\n" +
            "from school.teachers\n" +
            "inner join school.subjects on teachers.id_teacher = subjects.id_teacher\n" +
            "where day_of_week = ?";

    private final String SQL_GET_INFORMATION_TEACHER_NOT_WORK_DAY = "select distinct fullname\n" +
            "from school.teachers\n" +
            "inner join school.subjects on teachers.id_teacher = subjects.id_teacher\n" +
            "where day_of_week NOT LIKE ?";

    private final String SQL_GET_SUBJECT_BY_NUMBER_CLASSES = "select day_of_week, sum(number_classes)\n" +
            "from school.subjects\n" +
            "group by day_of_week\n" +
            "having sum(number_classes) = ?";

    private final String SQL_GET_DAY_OF_WEEK_BY_NUMBER_BUSY_AUDIENCES = "select day_of_week,count(*)\n" +
            "from school.subjects\n" +
            "group by day_of_week\n" +
            "having count(*) = ?";
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

    public void getInformationAboutTeachersByDayOfWeek(BufferedReader reader) {
        try (Connection connection = ConnectToSchoolDB.getConnectionToSchoolDB();
             PreparedStatement getInformationAboutTeacher = connection.prepareStatement(SQL_GET_INFORMATION_TEACHER)) {
            System.out.println("From this list choose day of week to print information about teachers");
            System.out.println(Arrays.toString(DaysOfWeek.values()));
            String dayOfWeek = reader.readLine();
            getInformationAboutTeacher.setString(1, dayOfWeek);
            ResultSet resultSet = getInformationAboutTeacher.executeQuery();
            System.out.println("Teachers who work on " + dayOfWeek);
            while (resultSet.next())
                System.out.printf("%d.%s\n", resultSet.getRow(), resultSet.getString(1));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getInformationAboutTeachersNotWorkDay(BufferedReader reader) {
        try (Connection connection = ConnectToSchoolDB.getConnectionToSchoolDB();
             PreparedStatement getInformationAboutTeachers = connection.prepareStatement(SQL_GET_INFORMATION_TEACHER_NOT_WORK_DAY)) {
            System.out.println("From this list choose day of week to print information about teachers");
            System.out.println(Arrays.toString(DaysOfWeek.values()));
            String dayOfWeek = reader.readLine();
            getInformationAboutTeachers.setString(1, dayOfWeek);
            ResultSet resultSet = getInformationAboutTeachers.executeQuery();
            System.out.println("Teachers who doesn't work on " + dayOfWeek);
            while (resultSet.next())
                System.out.printf("%d.%s\n", resultSet.getRow(), resultSet.getString(1));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getDayOfWeekByNumberClasses(BufferedReader reader) {
        try (Connection connection = ConnectToSchoolDB.getConnectionToSchoolDB();
             PreparedStatement getDayOfWeek = connection.prepareStatement(SQL_GET_SUBJECT_BY_NUMBER_CLASSES)) {
            System.out.println("Enter the number of classes to get days");
            int numberClasses = Integer.parseInt(reader.readLine());
            getDayOfWeek.setInt(1, numberClasses);
            ResultSet resultSet = getDayOfWeek.executeQuery();
            while (resultSet.next())
                System.out.printf("%d.%s \n", resultSet.getRow(), resultSet.getString(1));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getDayOfWeekByNumberByNumberAudiences (BufferedReader reader) {
        try (Connection connection = ConnectToSchoolDB.getConnectionToSchoolDB();
             PreparedStatement getDayOfWeek = connection.prepareStatement(SQL_GET_DAY_OF_WEEK_BY_NUMBER_BUSY_AUDIENCES)) {
            System.out.println("Enter the number of busy audiences to get days");
            int numberBusyAudiences = Integer.parseInt(reader.readLine());
            getDayOfWeek.setInt(1, numberBusyAudiences);
            ResultSet resultSet = getDayOfWeek.executeQuery();
            while (resultSet.next())
                System.out.printf("%d.%s \n", resultSet.getRow(), resultSet.getString(1));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}