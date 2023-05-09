package avlyakulov.timur.book.chapter_13.task_10.crud;

import avlyakulov.timur.book.chapter_13.conn.ConnectionToDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class OperateToWatchStore {
    private final String SQL_INSERT_PRODUCER = "insert into watchstore.producer (name_producer, country_producer) " +
            "values (?,?)";
    private final String SQL_INSERT_WATCH = "insert into watchstore.watch (brand_watch, type_watch, price_watch, quantity_watch, id_producer) " +
            "values (?,?,?,?,?)";

    private final String SQL_GET_WATCH_BY_TYPE_WATCH = "select brand_watch\n" +
            "    from watchstore.watch\n" +
            "where type_watch = ?";

    private final String SQL_GET_WATCH_BY_COUNTRY = "select brand_watch, type_watch,price_watch\n" +
            "from watchstore.watch\n" +
            "inner join watchstore.producer on watch.id_producer = producer.id_producer\n" +
            "where country_producer = ?";

    private final String SQL_GET_WATCH_BY_QUANTITY = "select name_producer\n" +
            "from watchstore.producer\n" +
            "inner join watchstore.watch on producer.id_producer = watch.id_producer\n" +
            "group by name_producer\n" +
            "having sum(quantity_watch) <= ?";

    public void insertDataToDb(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement insertProducer = connection.prepareStatement(SQL_INSERT_PRODUCER);
             PreparedStatement insertWatch = connection.prepareStatement(SQL_INSERT_WATCH)) {
            Statement statement = connection.createStatement();
            System.out.println("Choose table from this list where you want to insert data\n1.Producer\n2.Watch\n");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the name of producer");
                    String nameProducer = reader.readLine();
                    insertProducer.setString(1, nameProducer);
                    System.out.println("Enter country of producer");
                    String countryProducer = reader.readLine();
                    insertProducer.setString(2, countryProducer);
                    insertProducer.executeUpdate();
                }
                case "2" -> {
                    System.out.println("Enter the brand watch ");
                    String brandWatch = reader.readLine();
                    insertWatch.setString(1, brandWatch);
                    System.out.println("Enter the type of watch quartz or mechanical");
                    String typeWatch = reader.readLine();
                    insertWatch.setString(2, typeWatch);
                    System.out.println("Enter the price of watch ");
                    int price = Integer.parseInt(reader.readLine());
                    insertWatch.setInt(3, price);
                    System.out.println("Enter the number of watch in stock ");
                    int quantity = Integer.parseInt(reader.readLine());
                    insertWatch.setInt(4, quantity);
                    System.out.println("From this list choose producer of watch ");
                    ResultSet listProducers = statement.executeQuery("Select name_producer from watchstore.producer");
                    while (listProducers.next())
                        System.out.printf("%d.%s\n", listProducers.getRow(), listProducers.getString(1));
                    String nameProducer = reader.readLine();
                    ResultSet idProducerSet = statement.executeQuery("Select id_producer from watchstore.producer " +
                            "where name_producer = '" + nameProducer + "'");
                    idProducerSet.next();
                    int idProducer = idProducerSet.getInt(1);
                    insertWatch.setInt(5, idProducer);
                    insertWatch.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести марки заданного типа часов.
    public void getBrandWatchByType(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getWatch = connection.prepareStatement(SQL_GET_WATCH_BY_TYPE_WATCH)) {
            Statement statement = connection.createStatement();
            System.out.println("From this list choose type of watch ");
            ResultSet typeWatchSet = statement.executeQuery("Select distinct type_watch from watchstore.watch");
            while (typeWatchSet.next())
                System.out.printf("%d.%s\n", typeWatchSet.getRow(), typeWatchSet.getString(1));
            String typeWatch = reader.readLine();
            getWatch.setString(1, typeWatch);
            ResultSet setWatch = getWatch.executeQuery();
            while (setWatch.next())
                System.out.printf("%d.%s\n", setWatch.getRow(), setWatch.getString(1));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести марки часов, изготовленных в заданной стране.
    public void getWatchByCountryRelease(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getWatch = connection.prepareStatement(SQL_GET_WATCH_BY_COUNTRY)) {
            System.out.println("Enter the country to get watch ");
            String countryWatch = reader.readLine();
            getWatch.setString(1, countryWatch);
            ResultSet setWatch = getWatch.executeQuery();
            while (setWatch.next())
                System.out.printf("%d.%s\nType watch - %s\nPrice watch - %d\n", setWatch.getRow(), setWatch.getString(1), setWatch.getString(2), setWatch.getInt(3));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести производителей, общая сумма часов которых в магазине не превышает заданную.
    public void getProducersByQuantityAtStore(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getProducers = connection.prepareStatement(SQL_GET_WATCH_BY_QUANTITY)) {
            System.out.println("Enter the number of watch to find producers");
            int quantity = Integer.parseInt(reader.readLine());
            getProducers.setInt(1, quantity);
            ResultSet producers = getProducers.executeQuery();
            System.out.println("The producers who has at store lower or equal than " + quantity);
            while (producers.next())
                System.out.printf("%d.%s\n", producers.getRow(), producers.getString(1));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}