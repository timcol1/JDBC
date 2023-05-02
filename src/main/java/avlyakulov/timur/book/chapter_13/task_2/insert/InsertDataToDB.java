package avlyakulov.timur.book.chapter_13.task_2.insert;

import avlyakulov.timur.book.chapter_13.task_2.conn.ConnectToVideoLibraryDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class InsertDataToDB {
    String SQL_QUERY_INSERT_ACTORS = "INSERT INTO videolibrary.actors (fullname_actor,birthday_actor,id_film) values (?,?,?)";
    String SQL_QUERY_INSERT_DIRECTORS = "INSERT INTO videolibrary.directors (fullname_director,birthday_director,id_film) values (?,?,?)";
    String SQL_QUERY_INSERT_FILMS = "INSERT INTO videolibrary.films (name_film, release_date,country_release) values (?,?,?)";

    public void insertToTableData(BufferedReader reader) {
        try (Connection connection = ConnectToVideoLibraryDB.getConnectionToDB();
             PreparedStatement insertActors = connection.prepareStatement(SQL_QUERY_INSERT_ACTORS);
             PreparedStatement insertFilm = connection.prepareStatement(SQL_QUERY_INSERT_FILMS)) {
            System.out.println("Choose table where you want insert data \n1.actors\n2.directors \n3.films");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the full name of actor");
                    String fullNameActor = reader.readLine();
                    insertActors.setString(1, fullNameActor);
                    System.out.println("Enter the date of birth actor in format yyyy-MM-dd");
                    String dateStr = reader.readLine();
                    Date date = Date.valueOf(dateStr); //converting string into sql date
                    insertActors.setDate(2, date);
                    System.out.println("Insert name of the film where this actor was filmed");
                    String nameFilm = reader.readLine();
                    PreparedStatement findIdFilm = connection.prepareStatement("Select id_film from videolibrary.films where name_film = '" + nameFilm + "'");
                    ResultSet resultSet = findIdFilm.executeQuery();
                    resultSet.next();
                    int idFilm = resultSet.getInt(1);
                    insertActors.setInt(3, idFilm);
                    insertActors.executeUpdate();
                }
                case "2" -> {

                }
                case "3" -> {
                    System.out.println("Enter the name of film");
                    String nameFilm = reader.readLine();
                    insertFilm.setString(1, nameFilm);
                    System.out.println("Enter the date of film in format yyyy-MM-dd");
                    String dateStr = reader.readLine();
                    Date date = Date.valueOf(dateStr); //converting string into sql date
                    insertFilm.setDate(2, date);
                    System.out.println("Enter the country of your film");
                    String country = reader.readLine();
                    insertFilm.setString(3, country);
                    insertFilm.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}