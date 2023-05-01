package avlyakulov.timur.book.chapter_13.example.dao;

import avlyakulov.timur.book.chapter_13.example.conn.Entity;

public class PersonBook extends Entity {
    private String name;
    private String book_name;

    public PersonBook() {
    }

    public PersonBook(String name, String book_name) {
        this.name = name;
        this.book_name =  book_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookName() {
        return book_name;
    }
    public void setBookName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "PersonBook{" +
                "name='" + name + '\'' +
                ", phone=" + book_name +
                '}';
    }
}