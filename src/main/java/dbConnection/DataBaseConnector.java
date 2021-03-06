package dbConnection;

import Controllers.ItemPriceExtended;
import Controllers.Observer;

import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnector {

    private final static String DBURL = "jdbc:mysql://127.0.0.1:3306/ceneo_2.0?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";      //schema's name
    private static String DBUSER = Observer.users[3];    //default username
    private static String DBPASS = Observer.passes[3];   //default user password
    private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";

    private Connection connection;
    private Statement statement;
    private String query;
    private SQLParser sqlParser;

    public DataBaseConnector() {
        this.sqlParser = new SQLParser();
    }

    public static void setDBUSER(String dbuser) {
        DataBaseConnector.DBUSER = dbuser;
    }

    public static void setDBPASS(String dbpass) {
        DataBaseConnector.DBPASS = dbpass;
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
        query = sqlParser.insertItemPriceQuery_items();

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, singleItemPrice.getIDProdukt());
            preparedStatement.setString(2, singleItemPrice.getName());
            preparedStatement.setDouble(3, singleItemPrice.getOpinion());
            preparedStatement.setString(4, singleItemPrice.getDescription());

            preparedStatement.execute();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = sqlParser.insertItemPriceQuery_store_item();

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, singleItemPrice.getIDMarket());
            preparedStatement.setInt(2, singleItemPrice.getIDProdukt());
            preparedStatement.setDouble(3, singleItemPrice.getPrice());

            preparedStatement.execute();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItemPrice(ItemPrice singleItemPrice) {
        query = sqlParser.deleteItemPriceQuery();

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, singleItemPrice.getIDProdukt());
            preparedStatement.setInt(2, singleItemPrice.getIDMarket());

            preparedStatement.execute();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItemPrice_name(ItemPrice singleItemPrice, String newValue) {
        query = sqlParser.updateItemPriceQuery_name();

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newValue);
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

    public void updateItemPrice_price(ItemPrice singleItemPrice, Double newValue) {
        query = sqlParser.updateItemPriceQuery_cena();

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, newValue);
            preparedStatement.setInt(2, singleItemPrice.getIDProdukt());
            preparedStatement.setInt(3, singleItemPrice.getIDMarket());

            preparedStatement.execute();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItemPrice_descrip(ItemPrice singleItemPrice, String newValue) {
        query = sqlParser.updateItemPriceQuery_opis();

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newValue);
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

    public void createTableForChart(String tab_name) {
        query = sqlParser.createTableForChartQuery(tab_name);

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTableIfExists(String tab_name) {
        query = sqlParser.dropTableIfExistsQuery(tab_name);

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTmpOrder(String tab_name, ItemPriceExtended item) {
        query = sqlParser.insertTmpOrderQuery(tab_name);

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, item.getIDProdukt());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getIDMarket());
            preparedStatement.setString(4, item.getMarket());
            preparedStatement.setInt(5, item.getLiczba());
            preparedStatement.setDouble(6, item.getWartosc());

            preparedStatement.execute();

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finalizeOrder(String budget, String tab_name) {
        query = sqlParser.finalizeOrderQuery(budget, tab_name);

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dumpToCSV(String tab_name, String file_name) {
        query = sqlParser.dumpToCSVQuery(tab_name, file_name);

        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            statement = connection.createStatement();
            statement.executeQuery(query);

            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
