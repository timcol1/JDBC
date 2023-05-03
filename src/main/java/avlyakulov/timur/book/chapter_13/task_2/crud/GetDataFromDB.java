package avlyakulov.timur.book.chapter_13.task_2.crud;

import avlyakulov.timur.book.chapter_13.task_2.conn.ConnectToVideoLibraryDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
Действия с БД:
• Найти все фильмы, вышедшие на экран в текущем и прошлом году.
• Вывести информацию об актерах, снимавшихся в заданном фильме.
• Вывести информацию об фильмах, в котором снимальнись как минимум в N актеров.
• Удалить все фильмы, дата выхода которых была более заданного числа лет назад.
 */
public class GetDataFromDB {
    private final String SQL_GET_FILMS_BY_YEAR = "SELECT name_film,EXTRACT(YEAR FROM release_date) AS year\n" +
            "from videolibrary.films\n" +
            "where EXTRACT(YEAR FROM release_date) = EXTRACT(YEAR FROM current_date) OR EXTRACT(YEAR FROM release_date) = EXTRACT(YEAR FROM current_date) - 1\n" +
            "ORDER BY year desc, name_film;";
    private final String SQL_SELECT_INFORMATION_ACTORS_BY_NAME_FILM = "Select fullname_actor,birthday_actor\n" +
            "from videolibrary.actors\n" +
            "where id_film = (select id_film from videolibrary.films where name_film = ?)";
    private final String SQL_SELECT_INFORMATION_FILMS_BY_NUMBERS_ACTORS = "Select name_film,release_date,country_release,count(*) number_actors\n" +
            "from videolibrary.films,videolibrary.actors\n" +
            "where films.id_film = actors.id_film\n" +
            "group by name_film, release_date, country_release\n" +
            "HAVING count(*) >= ?;";

    private final String SQL_DELETE_FILM_BY_YEAR = "Delete from videolibrary.films\n" +
            "where extract(year from current_date) - extract(year from release_date) >= ?";

    public void findAllFilmsByYear() {
        try (Connection connection = ConnectToVideoLibraryDB.getConnectionToDB();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_FILMS_BY_YEAR)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 1;
            while (resultSet.next()) {
                System.out.printf("%d.Name of film - %s\n  Year of released - %s\n", i, resultSet.getString("name_film"), resultSet.getString("year"));
                ++i;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findActorsByFilm(BufferedReader reader) {
        try (Connection connection = ConnectToVideoLibraryDB.getConnectionToDB();
             PreparedStatement getInformationAboutActors = connection.prepareStatement(SQL_SELECT_INFORMATION_ACTORS_BY_NAME_FILM)) {
            System.out.println("From this list enter the name of film to get information about actors");
            ResultSet listOfFilms = connection.prepareStatement("Select name_film from videolibrary.films ").executeQuery();
            while (listOfFilms.next())
                System.out.printf("%d.%s\n", listOfFilms.getRow(), listOfFilms.getString("name_film"));
            String nameFilm = reader.readLine();
            getInformationAboutActors.setString(1, nameFilm);
            ResultSet informationAboutActorsByFilm = getInformationAboutActors.executeQuery();
            System.out.println("Film " + nameFilm);
            while (informationAboutActorsByFilm.next())
                System.out.printf("%d.Fullname actor %s\n  His birthday %s\n", informationAboutActorsByFilm.getRow(), informationAboutActorsByFilm.getString("fullname_actor"), informationAboutActorsByFilm.getDate("birthday_actor"));

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void findFilmsByNumberOfActors(BufferedReader reader) {
        try (Connection connection = ConnectToVideoLibraryDB.getConnectionToDB();
             PreparedStatement getInformationFilmsByNumberOfActors = connection.prepareStatement(SQL_SELECT_INFORMATION_FILMS_BY_NUMBERS_ACTORS)) {
            System.out.println("Enter the number of actors in the film to find information about film");
            int numberActors = Integer.parseInt(reader.readLine());
            getInformationFilmsByNumberOfActors.setInt(1, numberActors);
            ResultSet setFilms = getInformationFilmsByNumberOfActors.executeQuery();
            while (setFilms.next())
                System.out.printf("%d.%s\n  Data of release - %s\n  Country of release - %s\n  Number of actors in this film - %d\n", setFilms.getRow(), setFilms.getString("name_film"), setFilms.getDate("release_date"), setFilms.getString("country_release"), setFilms.getInt("number_actors"));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFilmsByDate(BufferedReader reader) {
        try (Connection connection = ConnectToVideoLibraryDB.getConnectionToDB();
             PreparedStatement deleteFilm = connection.prepareStatement(SQL_DELETE_FILM_BY_YEAR)) {
            System.out.println("Enter the number of years to delete all films that are older than this number");
            int numberYears = Integer.parseInt(reader.readLine());
            deleteFilm.setInt(1, numberYears);
            deleteFilm.executeUpdate();
        } catch (SQLException | IOException e) {

        }
    }
}