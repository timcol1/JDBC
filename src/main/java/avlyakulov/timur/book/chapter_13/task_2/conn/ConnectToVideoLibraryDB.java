package avlyakulov.timur.book.chapter_13.task_2.conn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
Видеотека. В БД хранится информация о домашней видеотеке: фильмы,актеры, режиссеры.
Для фильмов необходимо хранить:
• название;
• имена актеров;
• дату выхода;
• страну, в которой выпущен фильм.
• Для актеров и режиссеров необходимо хранить:
• ФИО;
• дату рождения.
Действия с БД:
• Найти все фильмы, вышедшие на экран в текущем и прошлом году.
• Вывести информацию об актерах, снимавшихся в заданном фильме.
• Вывести информацию об актерах, снимавшихся как минимум в N фильмах.
• Вывести информацию об актерах, которые были режиссерами хотя бы од-
ного из фильмов.
• Удалить все фильмы, дата выхода которых была более заданного числа лет назад.
 */
public class ConnectToVideoLibraryDB {
    private static String url;
    private static String name;
    private static String pass;

    public static Connection getConnectionToDB() throws SQLException {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/postgres.properties"))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = properties.getProperty("db.host");
        name = properties.getProperty("db.name");
        pass = properties.getProperty("db.pass");
        return DriverManager.getConnection(url,name,pass);
    }
}