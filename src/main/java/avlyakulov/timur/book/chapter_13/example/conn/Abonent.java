package avlyakulov.timur.book.chapter_13.example.conn;

public class Abonent extends Entity {
    private String name;
    private int phone;

    public Abonent() {
    }

    public Abonent(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Abonent{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}