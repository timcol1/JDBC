package avlyakulov.timur.book.chapter_13.task_5.crud;

import avlyakulov.timur.book.chapter_13.task_5.conn.ConnectionToSouvenirsDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class OperateDataSouvenirs {

    ConnectionToSouvenirsDB connectionToSouvenirsDB = new ConnectionToSouvenirsDB();

    private String SQL_INSERT_TO_PRODUCERS = "insert into souvenirs.producers (name_producer, name_country) values (?,?)";
    private String SQL_INSERT_TO_PRODUCTS = "insert into souvenirs.products (name_product, date_release, price, id_producer) values (?,?,?,?)";

    public void insertDataToDB(BufferedReader reader) {
        try (Connection connection = connectionToSouvenirsDB.getConnection();
             PreparedStatement insertToProducts = connection.prepareStatement(SQL_INSERT_TO_PRODUCTS);
             PreparedStatement insertToProducers = connection.prepareStatement(SQL_INSERT_TO_PRODUCERS)) {
            System.out.println("Choose number table where you want to insert data\n1.Producers\n2.Products");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the name producer");
                    String nameProducer = reader.readLine();
                    System.out.println("Enter the country where this souvenir was produced");
                    String countryName = reader.readLine();
                    insertToProducers.setString(1, nameProducer);
                    insertToProducers.setString(2, countryName);
                    insertToProducers.executeUpdate();
                }
                case "2" -> {
                    System.out.println("Enter the name of product");
                    String nameProduct = reader.readLine();
                    System.out.println("Enter the date of release in format yyyy-MM-dd");
                    insertToProducts.setString(1, nameProduct);
                    Date date = Date.valueOf(reader.readLine());
                    insertToProducts.setDate(2, date);
                    System.out.println("Enter the price of product");
                    int price = Integer.parseInt(reader.readLine());
                    insertToProducts.setInt(3, price);
                    System.out.println("From this list enter the name of Producer");
                    Statement getProducers = connection.createStatement();
                    ResultSet setOfProducers = getProducers.executeQuery("Select name_producer from souvenirs.producers");
                    while (setOfProducers.next())
                        System.out.printf("%d.%s\n", setOfProducers.getRow(), setOfProducers.getString(1));
                    String nameProducer = reader.readLine();
                    ResultSet getIdProducer = getProducers.executeQuery("select id_producer from souvenirs.producers " +
                            "where name_producer = '" + nameProducer + "'");
                    getIdProducer.next();
                    int idProducer = getIdProducer.getInt(1);
                    insertToProducts.setInt(4, idProducer);
                    insertToProducts.executeUpdate();
                }
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}