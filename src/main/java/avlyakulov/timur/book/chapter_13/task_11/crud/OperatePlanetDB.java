package avlyakulov.timur.book.chapter_13.task_11.crud;

import avlyakulov.timur.book.chapter_13.conn.ConnectionToDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class OperatePlanetDB {
    private String SQL_INSERT_GALAXY = "Insert into space.galaxy (name_galaxy) values (?)";
    private String SQL_INSERT_PLANET = "Insert into space.planets (name_planet, radius,core_temperature, atmosphere, life, id_galaxy)" +
            " values (?,?,?,?,?,?)";
    private String SQL_INSERT_SATELLITE = "Insert into space.satellites (name_satellite, radius, distance_to_planet, id_planet) " +
            "values (?,?,?,?)";
    private String SQL_GET_PLANETS_BY_GALAXY = "select name_planet, planets.radius,core_temperature,atmosphere,life,name_satellite,distance_to_planet\n" +
            "from space.planets\n" +
            "inner join space.satellites on satellites.id_planet = planets.id_planet\n" +
            "inner join space.galaxy on planets.id_galaxy = galaxy.id_galaxy\n" +
            "where life = true and  name_galaxy = ?";

    private String SQL_GET_GALAXY_MAX_TEMPERATURE = "select name_galaxy, sum(core_temperature)\n" +
            "from space.galaxy\n" +
            "         inner join space.planets on planets.id_galaxy = galaxy.id_galaxy\n" +
            "group by name_galaxy\n" +
            "order by sum(core_temperature) desc\n" +
            "LIMIT 1\n";

    public void insertDataToDB(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement insertGalaxy = connection.prepareStatement(SQL_INSERT_GALAXY);
             PreparedStatement insertPlanet = connection.prepareStatement(SQL_INSERT_PLANET);
             PreparedStatement insertSatellite = connection.prepareStatement(SQL_INSERT_SATELLITE)) {
            Statement statement = connection.createStatement();
            System.out.println("From this list choose table to insert data\n1.galaxy\n2.planets\n3.satellites");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the name of galaxy");
                    String name_galaxy = reader.readLine();
                    insertGalaxy.setString(1, name_galaxy);
                    insertGalaxy.executeUpdate();
                }
                case "2" -> {
                    System.out.println("The name of planet");
                    String namePlanet = reader.readLine();
                    insertPlanet.setString(1, namePlanet);
                    System.out.println("Enter radius of planet");
                    int radiusPlanet = Integer.parseInt(reader.readLine());
                    insertPlanet.setInt(2, radiusPlanet);
                    System.out.println("Enter the core temperature of planet ");
                    int coreTemperature = Integer.parseInt(reader.readLine());
                    insertPlanet.setInt(3, coreTemperature);
                    System.out.println("Does you planet has atmosphere? Enter y/n");
                    boolean atmosphere = reader.readLine().equalsIgnoreCase("y");
                    insertPlanet.setBoolean(4, atmosphere);
                    System.out.println("Does you planet has life? Enter y/n");
                    boolean life = reader.readLine().equalsIgnoreCase("y");
                    insertPlanet.setBoolean(5, life);
                    System.out.println("From this list enter the name of galaxy where this planet is placed");
                    ResultSet setGalaxies = statement.executeQuery("Select name_galaxy from space.galaxy");
                    while (setGalaxies.next())
                        System.out.printf("%d.%s\n", setGalaxies.getRow(), setGalaxies.getString(1));
                    String nameGalaxy = reader.readLine();
                    ResultSet idGalaxyRes = statement.executeQuery("select id_galaxy from space.galaxy " +
                            "where name_galaxy = '" + nameGalaxy + "'");
                    idGalaxyRes.next();
                    int idGalaxy = idGalaxyRes.getInt(1);
                    insertPlanet.setInt(6, idGalaxy);
                    insertPlanet.executeUpdate();
                }
                case "3" -> {
                    System.out.println("Enter the name of satellite");
                    String nameSatellite = reader.readLine();
                    insertSatellite.setString(1, nameSatellite);
                    System.out.println("Enter the radius of satellite ");
                    int radiusSatellite = Integer.parseInt(reader.readLine());
                    insertSatellite.setInt(2, radiusSatellite);
                    System.out.println("From this list enter the name of planet where this satellite is placed");
                    ResultSet setPlanets = statement.executeQuery("Select name_planet from space.planets");
                    while (setPlanets.next())
                        System.out.printf("%d.%s\n", setPlanets.getRow(), setPlanets.getString(1));
                    String namePlanet = reader.readLine();
                    ResultSet idPlanetRes = statement.executeQuery("select id_planet from space.planets " +
                            "where name_planet = '" + namePlanet + "'");
                    idPlanetRes.next();
                    int idPlanet = idPlanetRes.getInt(1);
                    insertSatellite.setInt(4, idPlanet);
                    System.out.println("Enter the distance to planet ");
                    int distancePlanet = Integer.parseInt(reader.readLine());
                    insertSatellite.setInt(3, distancePlanet);
                    insertSatellite.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести информацию обо всех планетах, на которых присутствует жизнь, и их спутниках в заданной галактике
    public void getInformationAboutPlanetByGalaxy(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getPlanets = connection.prepareStatement(SQL_GET_PLANETS_BY_GALAXY)) {
            Statement statement = connection.createStatement();
            System.out.println("From this list enter the name of galaxy where this planet is placed");
            ResultSet setGalaxies = statement.executeQuery("Select name_galaxy from space.galaxy");
            while (setGalaxies.next())
                System.out.printf("%d.%s\n", setGalaxies.getRow(), setGalaxies.getString(1));
            String nameGalaxy = reader.readLine();
            getPlanets.setString(1, nameGalaxy);
            ResultSet setPlanets = getPlanets.executeQuery();
            while (setPlanets.next())
                System.out.printf("%d.%s\nRadius - %d\nCore temperature - %d\nDoes it has atmosphere - %b\nDoes it has life - %b\nName satellite - %s\nDistance to planet from satellite - %d\n",
                        setPlanets.getRow(), setPlanets.getString("name_planet"), setPlanets.getInt("radius"), setPlanets.getInt("core_temperature"), setPlanets.getBoolean("atmosphere"), setPlanets.getBoolean("life"), setPlanets.getString("name_satellite"), setPlanets.getInt("distance_to_planet"));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Найти галактику, сумма ядерных температур планет которой наибольшая.
    public void findGalaxyMaxCoreTemperature() {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getGalaxy = connection.prepareStatement(SQL_GET_GALAXY_MAX_TEMPERATURE)) {
            ResultSet setGalaxy = getGalaxy.executeQuery();
            while (setGalaxy.next())
                System.out.printf("%d.%s\nSum of temperature core planets - %d \n", setGalaxy.getRow(), setGalaxy.getString(1), setGalaxy.getInt(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}