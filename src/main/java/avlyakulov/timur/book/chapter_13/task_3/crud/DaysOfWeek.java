package avlyakulov.timur.book.chapter_13.task_3.crud;

public enum DaysOfWeek {
    MONDAY("Monday"),

    TUESDAY("Tuesday"),

    WEDNESDAY("Wednesday"),

    THURSDAY("Thursday"),

    FRIDAY("Friday");

    private final String name;

    DaysOfWeek(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}