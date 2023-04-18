package avlyakulov.timur.practise.crud;

import avlyakulov.timur.practise.crud.entity.Student;
import avlyakulov.timur.practise.crud.util.CRUDUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> studentList = CRUDUtils.getStudents("SELECT * FROM STUDENTS");
        System.out.println(studentList);
    }
}