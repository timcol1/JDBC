package avlyakulov.timur.practise.crud.entity;

public class Student {
    private int id;
    private String name;
    private String surname;
    private String courseName;

    public Student() {

    }
    public Student(int id, String name, String surname, String courseName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}