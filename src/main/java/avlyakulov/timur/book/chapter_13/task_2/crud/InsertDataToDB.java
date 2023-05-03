package avlyakulov.timur.book.chapter_13.task_2.crud;

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
             PreparedStatement queryActor = connection.prepareStatement(SQL_QUERY_INSERT_ACTORS);
             PreparedStatement queryFilm = connection.prepareStatement(SQL_QUERY_INSERT_FILMS);
             PreparedStatement queryDirector = connection.prepareStatement(SQL_QUERY_INSERT_DIRECTORS)) {
            System.out.println("Choose table where you want insert data \n1.actors\n2.directors \n3.films");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the full name of actor");
                    String fullNameActor = reader.readLine();
                    queryActor.setString(1, fullNameActor);
                    System.out.println("Enter the date of birth actor in format yyyy-MM-dd");
                    String dateStr = reader.readLine();
                    Date date = Date.valueOf(dateStr); //converting string into sql date
                    queryActor.setDate(2, date);
                    System.out.println("Insert name of the film from this list where this actor was filmed");
                    Statement statement = connection.createStatement();
                    ResultSet setOfFilms = statement.executeQuery("Select name_film from videolibrary.films");
                    int i = 1;
                    while (setOfFilms.next()) {
                        System.out.printf("%d. %s \n", i, setOfFilms.getString(1));
                        ++i;
                    }
                    String nameFilm = reader.readLine();
                    ResultSet getIdFilm = statement.executeQuery("Select id_film from videolibrary.films where name_film = '" + nameFilm + "'");
                    getIdFilm.next();
                    int idFilm = getIdFilm.getInt(1);
                    queryActor.setInt(3, idFilm);
                    queryActor.executeUpdate();
                }
                case "2" -> {
                    System.out.println("Enter the full name of director");
                    String fullNameDirector = reader.readLine();
                    queryDirector.setString(1, fullNameDirector);
                    System.out.println("Enter the date of birth director in format yyyy-MM-dd");
                    String dateStr = reader.readLine();
                    Date date = Date.valueOf(dateStr); //converting string into sql date
                    queryDirector.setDate(2, date);
                    System.out.println("Enter the name of the film from this list which this director produced");
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("Select name_film from videolibrary.films");
                    int i = 1;
                    while (resultSet.next()) {
                        System.out.printf("%d. %s \n", i, resultSet.getString(1));
                        ++i;
                    }
                    String nameFilm = reader.readLine();
                    resultSet = statement.executeQuery("Select id_film from videolibrary.films where name_film = '" + nameFilm + "'");
                    resultSet.next();
                    int idFilm = resultSet.getInt(1);
                    queryDirector.setInt(3, idFilm);
                    queryDirector.executeUpdate();
                }
                case "3" -> {
                    System.out.println("Enter the name of film");
                    String nameFilm = reader.readLine();
                    queryFilm.setString(1, nameFilm);
                    System.out.println("Enter the date of film in format yyyy-MM-dd");
                    String dateStr = reader.readLine();
                    Date date = Date.valueOf(dateStr); //converting string into sql date
                    queryFilm.setDate(2, date);
                    System.out.println("Enter the country of your film");
                    String country = reader.readLine();
                    queryFilm.setString(3, country);
                    queryFilm.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}