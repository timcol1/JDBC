package avlyakulov.timur.book.chapter_13.task_2.insert;

import avlyakulov.timur.book.chapter_13.task_2.conn.ConnectToVideoLibraryDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class InsertDataToDB {
    String SQL_QUERY_INSERT = "INSERT INTO ? (?,?,?) values (?,?,?)";
    String SQL_QUERY_INSERT_FILMS = "INSERT INTO videolibrary.films (name_film, release_date,country_release) values (?,?,?)";

    public void insertToTableData() {
        try (Connection connection = ConnectToVideoLibraryDB.getConnectionToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_INSERT);
             PreparedStatement insertFilm = connection.prepareStatement(SQL_QUERY_INSERT_FILMS);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Choose table where you want insert data \n1.actors\n2.directors \n3.films");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {

                }
                case "2" -> {

                }
                case "3" -> {
                    System.out.println("Enter the name of film");
                    String nameFilm = reader.readLine();
                    insertFilm.setString(1, nameFilm);
                    System.out.println("Enter the date of film in format yyyy-MM-dd");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String dateStr = reader.readLine();
                    Date date = Date.valueOf(dateStr); //converting string into sql date
                    insertFilm.setDate(2, date);
                    System.out.println("Enter the country of your film");
                    String country = reader.readLine();
                    insertFilm.setString(3,country);
                    insertFilm.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}