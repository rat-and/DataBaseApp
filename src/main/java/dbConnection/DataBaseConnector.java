package dbConnection;

import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnector {

    private final static String DBURL = "jdbc:mysql://127.0.0.1:3306/porownywarkacen?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";      //schema's name
    private final static String DBUSER = "root";    //username
    private final static String DBPASS = "mysql";   //user password
    private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";

    private Connection connection;
    private Statement statement;
//    private PreparedStatement preparedStatement;
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
                singleItemPrice.setIDProdukt(resultSet.getInt("IDProdukt"));
                singleItemPrice.setIDMarket(resultSet.getInt("IDMarket"));
                singleItemPrice.setNazwa(resultSet.getString("Nazwa"));
                singleItemPrice.setIlosc(resultSet.getInt("Ilosc"));
                singleItemPrice.setWaga(resultSet.getDouble("Waga"));
                singleItemPrice.setJednostka(resultSet.getString("Jednostka"));
                singleItemPrice.setCena(resultSet.getDouble("Cena"));

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
//            ResultSet resultSet = statement.executeQuery(query);

             PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + nazwa + "%");
//            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                singleItemPrice = new ItemPrice();
                singleItemPrice.setIDProdukt(resultSet.getInt("IDProdukt"));
                singleItemPrice.setIDMarket(resultSet.getInt("IDMarket"));
                singleItemPrice.setNazwa(resultSet.getString("Nazwa"));
                singleItemPrice.setIlosc(resultSet.getInt("Ilosc"));
                singleItemPrice.setWaga(resultSet.getDouble("Waga"));
                singleItemPrice.setJednostka(resultSet.getString("Jednostka"));
                singleItemPrice.setCena(resultSet.getDouble("Cena"));

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

}
