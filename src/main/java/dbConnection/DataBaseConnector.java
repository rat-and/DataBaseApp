package dbConnection;

import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnector {

    private final static String DBURL = "jdbc:mysql://127.0.0.1:3306/ceneo_2.0?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";      //schema's name
    private final static String DBUSER = "root";    //username
    private final static String DBPASS = "mysql";   //user password
    private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";

    private Connection connection;
    private Statement statement;
    private String query;
    private SQLParser sqlParser;

    public DataBaseConnector() {
        this.sqlParser = new SQLParser();
    }

    public List<ItemPrice> getItemPrces() {
        query = sqlParser.fetchAllItemPriceQuery();
        List<ItemPrice> itemPrices = new ArrayList<ItemPrice>();
        ItemPrice singleItemPrice;

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                singleItemPrice = new ItemPrice();
                singleItemPrice.setIDProdukt(resultSet.getInt("item_id"));
                singleItemPrice.setIDMarket(resultSet.getInt("store_id"));
                singleItemPrice.setName(resultSet.getString("name"));
                singleItemPrice.setMarket(resultSet.getString("store_name"));
                singleItemPrice.setPrice(resultSet.getDouble("item_price"));
                singleItemPrice.setOpinion(resultSet.getDouble("opinion"));
                singleItemPrice.setWebAddres(resultSet.getString("web_addres"));
                singleItemPrice.setDescription(resultSet.getString("description"));
                singleItemPrice.setMarketOpinion(resultSet.getDouble("store_opinion"));

                itemPrices.add(singleItemPrice);
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemPrices;
    }

    public List<ItemPrice> getItemPrcesOf(String nazwa) {
        query = sqlParser.fetchItemPriceQuery();
        List<ItemPrice> itemPrices = new ArrayList<ItemPrice>();
        ItemPrice singleItemPrice;

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + nazwa + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                singleItemPrice = new ItemPrice();
                singleItemPrice.setIDProdukt(resultSet.getInt("item_id"));
                singleItemPrice.setIDMarket(resultSet.getInt("store_id"));
                singleItemPrice.setName(resultSet.getString("name"));
                singleItemPrice.setMarket(resultSet.getString("store_name"));
                singleItemPrice.setPrice(resultSet.getDouble("item_price"));
                singleItemPrice.setOpinion(resultSet.getDouble("opinion"));
                singleItemPrice.setWebAddres(resultSet.getString("web_addres"));
                singleItemPrice.setDescription(resultSet.getString("description"));
                singleItemPrice.setMarketOpinion(resultSet.getDouble("store_opinion"));

                itemPrices.add(singleItemPrice);
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemPrices;
    }

    public void insertItemPrice(ItemPrice singleItemPrice) {
//        query = sqlParser.insertItemPriceQuery();
//
//        try {
//            Class.forName(DBDRIVER);
//            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
//            statement = connection.createStatement();
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, singleItemPrice.getIDProdukt());
//            preparedStatement.setInt(2, singleItemPrice.getIDMarket());
//            preparedStatement.setString(3, singleItemPrice.getName());
//            preparedStatement.setInt(4, singleItemPrice.getIlosc());
//            preparedStatement.setDouble(5, singleItemPrice.getOpinion());
//            preparedStatement.setString(6, singleItemPrice.getDescription());
//            preparedStatement.setDouble(7, singleItemPrice.getCena());
//            preparedStatement.setInt(8, 0);
//
//            preparedStatement.execute();
//
//            statement.close();
//            connection.close();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

    public void updateItemPrice_IDMarket(ItemPrice singleItemPrice, int newValue) {
        query = sqlParser.updateItemPriceQuery_IDMarket();

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newValue);
            preparedStatement.setInt(2, singleItemPrice.getIDProdukt());

            preparedStatement.execute();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateItemPrice_ilosc(ItemPrice singleItemPrice, int newValue) {
        query = sqlParser.updateItemPriceQuery_ilosc();

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newValue);
            preparedStatement.setInt(2, singleItemPrice.getIDProdukt());

            preparedStatement.execute();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
