package avlyakulov.timur.book.chapter_13.task_8.crud;

import avlyakulov.timur.book.chapter_13.conn.ConnectionToDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class OperateToForecastDB {
    private final String SQL_INSERT_PEOPLE = "Insert into forecast.people (name_people,language_people) values (?,?)";
    private final String SQL_GET_ALL_PEOPLE = "SELECT name_people from forecast.people";
    private final String SQL_INSERT_REGION = "Insert into forecast.region (name_region,square_region,id_people) " +
            "values (?,?,?)";
    private final String SQL_INSERT_WEATHER = "insert into forecast.weather (date_weather,temperature, id_region) " +
            "values (?,?,?)";

    private final String SQL_GET_WEATHER_REGION = "select name_region,date_weather, temperature\n" +
            "from forecast.region\n" +
            "inner join forecast.weather on region.id_region = weather.id_region " +
            "where name_region = ?";

    private final String SQL_GET_WEATHER_LANGUAGE_DATE = "select name_region,date_weather, temperature\n" +
            "from forecast.region\n" +
            "inner join forecast.weather on region.id_region = weather.id_region\n" +
            "inner join forecast.people on people.id_people = region.id_people\n" +
            "where language_people = ? and extract (week from date_weather) = (extract(week from current_date) - 1)";

    private final String GET_WEATHER_LAST_WEEK_REGIONS = "select name_region, avg(temperature)\n" +
            "from forecast.region\n" +
            "inner join forecast.weather on region.id_region = weather.id_region\n" +
            "inner join forecast.people on people.id_people = region.id_people\n" +
            "where  extract (week from date_weather) = (extract(week from current_date) - 1) \n" +
            "group by name_region";

    public void insetDataToDB(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement insertPeople = connection.prepareStatement(SQL_INSERT_PEOPLE);
             PreparedStatement insertRegion = connection.prepareStatement(SQL_INSERT_REGION);
             PreparedStatement insertWeather = connection.prepareStatement(SQL_INSERT_WEATHER)) {
            System.out.println("From this list choose number of table where you want to insert data\n1.People\n2.Region\n3.Weather\n");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the name of people ");
                    String namePeople = reader.readLine();
                    insertPeople.setString(1, namePeople);
                    System.out.println("Enter the language people ");
                    String languagePeople = reader.readLine();
                    insertPeople.setString(2, languagePeople);
                    insertPeople.executeUpdate();
                }
                case "2" -> {
                    Statement statement = connection.createStatement();
                    System.out.println("Enter the name of region ");
                    String nameRegion = reader.readLine();
                    insertRegion.setString(1, nameRegion);
                    System.out.println("Enter the square of region in km2");
                    int square = Integer.parseInt(reader.readLine());
                    insertRegion.setInt(2, square);
                    ResultSet setPeople = statement.executeQuery(SQL_GET_ALL_PEOPLE);
                    System.out.println("From this list choose name people of list people");
                    while (setPeople.next())
                        System.out.printf("%d.%s\n", setPeople.getRow(), setPeople.getString(1));
                    String namePeople = reader.readLine();
                    ResultSet getIdPeople = statement.executeQuery("select id_people from forecast.people " +
                            "where name_people = '" + namePeople + "'");
                    getIdPeople.next();
                    int idPeople = getIdPeople.getInt(1);
                    insertRegion.setInt(3, idPeople);
                    insertRegion.executeUpdate();
                }

                case "3" -> {
                    Statement statement = connection.createStatement();
                    System.out.println("Enter the date of weather in format yyyy-MM-dd");
                    Date date = Date.valueOf(reader.readLine());
                    insertWeather.setDate(1, date);
                    System.out.println("Enter the temperature ");
                    int temperature = Integer.parseInt(reader.readLine());
                    insertWeather.setInt(2, temperature);
                    ResultSet setRegion = statement.executeQuery("Select name_region from forecast.region");
                    System.out.println("From this list choose name region of list regions");
                    while (setRegion.next())
                        System.out.printf("%d.%s\n", setRegion.getRow(), setRegion.getString(1));
                    String nameRegion = reader.readLine();
                    ResultSet getIdRegion = statement.executeQuery("select id_people from forecast.region " +
                            "where name_region = '" + nameRegion + "'");
                    getIdRegion.next();
                    int idRegion = getIdRegion.getInt(1);
                    insertWeather.setInt(3, idRegion);
                    insertWeather.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Вывести сведения о погоде в заданном регионе.
    public void getWeatherByRegion(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getWeatherByRegion = connection.prepareStatement(SQL_GET_WEATHER_REGION)) {
            Statement statement = connection.createStatement();
            ResultSet setRegion = statement.executeQuery("Select name_region from forecast.region");
            System.out.println("From this list choose name region of list regions");
            while (setRegion.next())
                System.out.printf("%d.%s\n", setRegion.getRow(), setRegion.getString(1));
            String nameRegion = reader.readLine();
            getWeatherByRegion.setString(1, nameRegion);
            ResultSet weather = getWeatherByRegion.executeQuery();
            while (weather.next())
                System.out.printf("%d.%s\nDate weather - %s\nTemperature - %d\n", weather.getRow(), weather.getString(1), weather.getDate(2), weather.getInt(3));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести информацию о погоде за прошедшую неделю в регионах, жители которых общаются на заданном языке
    public void getWeatherByLanguageForLastWeek(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getWeatherByLanguage = connection.prepareStatement(SQL_GET_WEATHER_LANGUAGE_DATE)) {
            System.out.println("From this list choose language to find weather ");
            ResultSet setLanguages = connection.createStatement().executeQuery("Select language_people from forecast.people");
            while (setLanguages.next())
                System.out.printf("%d.%s\n", setLanguages.getRow(), setLanguages.getString(1));
            String language = reader.readLine();
            getWeatherByLanguage.setString(1, language);
            ResultSet setWeather = getWeatherByLanguage.executeQuery();
            while (setWeather.next())
                System.out.printf("%d.%s\nDate weather - %s\nTemperature - %d\n", setWeather.getRow(), setWeather.getString(1), setWeather.getDate(2), setWeather.getInt(3));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести среднюю температуру за прошедшую неделю в регионах
    public void getAverageWeatherForLastWeek() {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getWeatherLastWeek = connection.prepareStatement(GET_WEATHER_LAST_WEEK_REGIONS)) {
            ResultSet weatherLastWeek = getWeatherLastWeek.executeQuery();
            while (weatherLastWeek.next())
                System.out.printf("%d.%s\nAverage temperature for last week - %.1f\n", weatherLastWeek.getRow(), weatherLastWeek.getString(1), weatherLastWeek.getDouble(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}