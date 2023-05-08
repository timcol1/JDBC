package avlyakulov.timur.book.chapter_13.task_5.crud;

import avlyakulov.timur.book.chapter_13.task_5.conn.ConnectionToSouvenirsDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class OperateDataSouvenirs {

    ConnectionToSouvenirsDB connectionToSouvenirsDB = new ConnectionToSouvenirsDB();

    private String SQL_INSERT_TO_PRODUCERS = "insert into souvenirs.producers (name_producer, name_country) values (?,?)";
    private String SQL_INSERT_TO_PRODUCTS = "insert into souvenirs.products (name_product, date_release, price, id_producer) values (?,?,?,?)";

    private String SQL_GET_INFORMATION_PRODUCTS = "select name_product,date_release,price\n" +
            "from souvenirs.products\n" +
            "inner join souvenirs.producers on products.id_producer = producers.id_producer\n" +
            "where name_producer = ?";

    private String SQL_GET_INFORMATION_PRODUCTS_BY_COUNTRY = "select name_product,date_release,price\n" +
            "from souvenirs.products\n" +
            "inner join souvenirs.producers on products.id_producer = producers.id_producer\n" +
            "where name_country = ?";

    private String SQL_GET_INFORMATION_PRODUCERS_BY_YEAR = "select distinct name_producer,name_country\n" +
            "from souvenirs.products\n" +
            "inner join souvenirs.producers on products.id_producer = producers.id_producer\n" +
            "where extract(year from date_release) = ?";
    private String SQL_DELETE_COLUMN = "delete from souvenirs.producers " +
            "where name_producer = ?;";
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

    //Вывести информацию о сувенирах заданного производителя.
    public void getInformationOfProductsByProducer(BufferedReader reader) {
        try (Connection connection = connectionToSouvenirsDB.getConnection();
             PreparedStatement getInformationProducts = connection.prepareStatement(SQL_GET_INFORMATION_PRODUCTS)) {
            System.out.println("From this list of Producers choose producer that you need");
            Statement getProducers = connection.createStatement();
            ResultSet setOfProducers = getProducers.executeQuery("Select name_producer from souvenirs.producers");
            while (setOfProducers.next())
                System.out.printf("%d.%s\n", setOfProducers.getRow(), setOfProducers.getString(1));
            String nameProducer = reader.readLine();
            getInformationProducts.setString(1, nameProducer);
            ResultSet informationProducts = getInformationProducts.executeQuery();
            while (informationProducts.next()) {
                System.out.printf("%d.%s\nDate - %s\nPrice - %d\n", informationProducts.getRow(), informationProducts.getString(1), informationProducts.getString(2), informationProducts.getInt(3));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести информацию о сувенирах, произведенных в заданной стране.
    public void getInformationOfProductsByCountry(BufferedReader reader) {
        try (Connection connection = connectionToSouvenirsDB.getConnection();
             PreparedStatement getInformationProductsByCountry = connection.prepareStatement(SQL_GET_INFORMATION_PRODUCTS_BY_COUNTRY)) {
            System.out.println("From this list choose country to find products");
            Statement listCountries = connection.createStatement();
            ResultSet setOfCountries = listCountries.executeQuery("Select distinct name_country from souvenirs.producers");
            while (setOfCountries.next())
                System.out.printf("%d.%s\n", setOfCountries.getRow(), setOfCountries.getString(1));
            String nameCountry = reader.readLine();
            getInformationProductsByCountry.setString(1, nameCountry);
            ResultSet informationProducts = getInformationProductsByCountry.executeQuery();
            while (informationProducts.next())
                System.out.printf("%d.%s\nDate - %s\nPrice - %d\n", informationProducts.getRow(), informationProducts.getString(1), informationProducts.getString(2), informationProducts.getInt(3));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести информацию о производителях заданного сувенира, произведенного в заданном году.
    public void getProducersInformationByYear(BufferedReader reader) {
        try (Connection connection = connectionToSouvenirsDB.getConnection();
             PreparedStatement getInformationByYear = connection.prepareStatement(SQL_GET_INFORMATION_PRODUCERS_BY_YEAR)) {
            System.out.println("Enter year of product release");
            int yearRelease = Integer.parseInt(reader.readLine());
            getInformationByYear.setInt(1, yearRelease);
            ResultSet setOfProducers = getInformationByYear.executeQuery();
            while (setOfProducers.next())
                System.out.printf("%d.%s\nCountry - %s", setOfProducers.getRow(),setOfProducers.getString(1),setOfProducers.getString(2));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Удалить заданного производителя и его сувениры.
    public void deleteProducerAndHisProducts (BufferedReader reader) {
        try (Connection connection = connectionToSouvenirsDB.getConnection();
             PreparedStatement deleteInformation = connection.prepareStatement(SQL_DELETE_COLUMN)) {
            System.out.println("Enter the name of producer to delete him from table");
            String nameProducer = reader.readLine();
            deleteInformation.setString(1,nameProducer);
            deleteInformation.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}