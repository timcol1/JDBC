package avlyakulov.timur.practise.db_in_memory;

import avlyakulov.timur.practise.db_in_memory.entity.Student;
import avlyakulov.timur.practise.db_in_memory.util.CRUDUtils;

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