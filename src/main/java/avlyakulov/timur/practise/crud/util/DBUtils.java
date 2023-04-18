package avlyakulov.timur.practise.crud.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
    //здесь бы будем создавать connection
    //private static String dbURL = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql'";

    /*private static String dbUsername = "sa";
    private static String dbPassword = "";*/

    public static Connection getConnection() {
        //пример взаимодействия с папкой resources для бд
        String dbURL = null;
        String dbUsername = "sa";
        String dbPassword = "";

        Properties properties = new Properties();
        try (BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/config.properties"))) {
            properties.load(fileReader);
            dbURL = properties.getProperty("db.host");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}