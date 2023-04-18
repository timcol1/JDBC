package avlyakulov.timur.practise.crud;

import avlyakulov.timur.practise.crud.entity.Student;
import avlyakulov.timur.practise.crud.util.CRUDUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> studentList = CRUDUtils.getStudents("SELECT * FROM students");
        System.out.println(studentList);
        Student student = new Student();
        student.setName("Denis");
        student.setSurname("Shemaev");
        student.setCourseName("Dance_Course");
        System.out.println(CRUDUtils.saveStudent(student));
        System.out.println(CRUDUtils.updateStudent(1, "Gym_Course"));
        CRUDUtils.deleteStudent(1);
    }
}