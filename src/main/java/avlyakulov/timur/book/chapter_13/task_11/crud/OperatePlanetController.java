package avlyakulov.timur.book.chapter_13.task_11.crud;

import avlyakulov.timur.book.chapter_13.conn.ConnectionToDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class OperatePlanetController {
    private String SQL_INSERT_GALAXY = "Insert into space.galaxy (name_galaxy) values (?)";
    private String SQL_INSERT_PLANET = "Insert into space.planets (name_planet, radius,core_temperature, atmosphere, life, id_galaxy)" +
            " values (?,?,?,?,?,?)";
    private String SQL_INSERT_SATELLITE = "Insert into space.satellites (name_satellite, radius, distance_to_planet, id_planet) " +
            "values (?,?,?,?)";

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
                    ResultSet setGalaxies = statement.executeQuery("Select name_galaxy from space.galalxy");
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
                    ResultSet setPlanets = statement.executeQuery("Select name_planet from space.planet");
                    while (setPlanets.next())
                        System.out.printf("%d.%s\n", setPlanets.getRow(), setPlanets.getString(1));
                    String namePlanet = reader.readLine();
                    ResultSet idPlanetRes = statement.executeQuery("select id_planet from space.planet " +
                            "where name_planet = '" + namePlanet + "'");
                    idPlanetRes.next();
                    int idPlanet = idPlanetRes.getInt(1);
                    insertPlanet.setInt(4, idPlanet);
                    System.out.println("Enter the distance to planet ");
                    int distancePlanet = Integer.parseInt(reader.readLine());
                    insertPlanet.setInt(3, distancePlanet);
                    insertPlanet.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}