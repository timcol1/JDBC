package avlyakulov.timur.book.chapter_13.task_6.crud;

import avlyakulov.timur.book.chapter_13.conn.ConnectionToDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class OperateToWarehouse {

    private String SQL_INSERT_TO_PRODUCTS = "insert into warehouse.products (name_product, description, price) \n" +
            "values (?,?,?)";
    private String SQL_INSERT_TO_ORDERS = "insert into warehouse.orders (date_order)\n" +
            "values (?)";
    private String SQL_INSERT_FULL_ORDER = "insert into warehouse.products_in_order (id_order, id_product)\n" +
            "values (?,?)";

    private String SQL_GET_INFO_ORDERS = "select products_in_order.id_order, sum(price)\n" +
            "from warehouse.products_in_order\n" +
            "inner join warehouse.orders  on orders.id_order = products_in_order.id_order\n" +
            "inner join warehouse.products p on p.id_product = products_in_order.id_product\n" +
            "group by products_in_order.id_order\n" +
            "order by products_in_order.id_order\n";


    public void insertDataToDB(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement insertToProduct = connection.prepareStatement(SQL_INSERT_TO_PRODUCTS);
             PreparedStatement insertOrder = connection.prepareStatement(SQL_INSERT_TO_ORDERS);
             PreparedStatement insertFullOrder = connection.prepareStatement(SQL_INSERT_FULL_ORDER)) {
            System.out.println("Choose table to insert data\n1.Products\n2.Orders");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the name of product");
                    String nameProduct = reader.readLine();
                    insertToProduct.setString(1, nameProduct);
                    System.out.println("Enter the description of product");
                    String descriptionProduct = reader.readLine();
                    insertToProduct.setString(2, descriptionProduct);
                    System.out.println("Enter the price of product");
                    int price = Integer.parseInt(reader.readLine());
                    insertToProduct.setInt(3, price);
                    insertToProduct.executeUpdate();
                }
                case "2" -> {
                    Statement statement = connection.createStatement();
                    long millis = System.currentTimeMillis();
                    Date date = new java.sql.Date(millis);
                    insertOrder.setDate(1, date);
                    insertOrder.executeUpdate();
                    ResultSet idOrderRes = statement.executeQuery("select id_order\n" +
                            "from warehouse.orders\n" +
                            "where id_order = (select max(id_order)\n" +
                            "                    from warehouse.orders);");
                    idOrderRes.next();
                    int idOrder = idOrderRes.getInt(1);
                    System.out.println("Your number of  order is " + idOrder);
                    String answerAddOrder = "";
                    do {
                        insertFullOrder.setInt(1, idOrder);
                        System.out.println("From this list choose product which you want to order ");
                        ResultSet setProducts = statement.executeQuery("Select name_product from warehouse.products");
                        while (setProducts.next()) {
                            System.out.printf("%d.%s\n", setProducts.getRow(), setProducts.getString(1));
                        }
                        String nameProduct = reader.readLine();
                        ResultSet getIdProduct = statement.executeQuery("Select id_product from warehouse.products " +
                                "where name_product = '" + nameProduct + "'");
                        getIdProduct.next();
                        int idProduct = getIdProduct.getInt(1);
                        insertFullOrder.setInt(2, idProduct);
                        insertFullOrder.executeUpdate();
                        System.out.println("Do you want add more products to your order? (y/n)");
                        answerAddOrder = reader.readLine();
                    } while (answerAddOrder.equals("y"));
                }
            }
        } catch (SQLException | IOException e) {

            throw new RuntimeException(e);
        }
    }

    public void getInfoAboutOrders() {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getOrders = connection.prepareStatement(SQL_GET_INFO_ORDERS)) {
            ResultSet orders = getOrders.executeQuery();
            while (orders.next())
                System.out.printf("%d.ID order - %d\nPrice of this order - %d\n", orders.getRow(), orders.getInt(1), orders.getInt(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}