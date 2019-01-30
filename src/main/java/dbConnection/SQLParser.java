package dbConnection;

import Controllers.ItemPriceExtended;

public class SQLParser {
    private String query;

    public String fetchAllItemPriceQuery() {
        query = "SELECT I.item_id, I.name, SI.item_price, I.opinion, S.name AS store_name, S.store_id, S.web_addres, S.opinion AS store_opinion, I.description FROM items I INNER JOIN store_item SI ON I.item_id = SI.item_id" +
                 " INNER JOIN stores S ON SI.store_id = S.store_id";
        return query;
    }

    public String fetchItemPriceQuery() {
        query = "SELECT I.Item_id, I.name, SI.item_price, I.opinion, S.name AS store_name, S.store_id, S.web_addres, S.opinion AS store_opinion, I.description FROM items I INNER JOIN store_item SI ON I.item_id = SI.item_id" +
                " INNER JOIN stores S ON SI.store_id = S.store_id WHERE I.name LIKE ?";
        return query;
    }

    public String insertItemPriceQuery_items() {
        query = "INSERT INTO items (item_id, name, opinion, description) VALUES(?, ?, ?, ?)";
        return query;
    }

    public String insertItemPriceQuery_store_item() {
        query = "INSERT INTO store_item(store_id, item_id, item_price) VALUES(?, ?, ?)";
        return query;
    }

    public String deleteItemPriceQuery() {
        query = "DELETE FROM store_item WHERE item_id = ? AND store_id = ?";
        return query;
    }

    public String updateItemPriceQuery_name() {
        query = "UPDATE itmes SET name = ? WHERE item_id = ?";
        return query;
    }

    public String updateItemPriceQuery_cena() {
        query = "UPDATE store_item SET item_price = ? WHERE item_id = ? AND store_id = ?";
        return query;
    }

    public String updateItemPriceQuery_opis() {
        query = "UPDATE items SET description = ? WHERE item_id = ?";
        return query;
    }

    public String updateItemPriceQuery_ilosc() {
        query = "UPDATE cenyproduktow SET Ilosc = ? WHERE IDProdukt = ?";
        return query;
    }

    public String createTableForChartQuery(String tab_name) {
        query = "CREATE TABLE " + tab_name + " (" +
                    " item_id INT NOT NULL," +
                    " item_name VARCHAR(255) NOT NULL," +
                    " store_id INT NOT NULL," +
                    " store_name VARCHAR(255)," +
                    " amount INT UNSIGNED," +
                    " cost DOUBLE NOT NULL" +
                " );";
        return query;
    }

    public String dropTableIfExistsQuery(String tab_name) {
        query = "DROP TABLE IF EXISTS " + tab_name + ";";
        return query;
    }

    public String insertTmpOrderQuery(String tab_name) {
        query = "INSERT INTO " + tab_name + " (item_id, item_name, store_id, store_name, amount, cost)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        return query;
    }

    public String finalizeOrderQuery(String budget, String tab_name) {
        query = "CALL finalize_order( " + budget + ", '" + tab_name + "' );";
        return query;
    }

    public String dumpToCSVQuery(String tab_name, String file_name) {
        //C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\
        query = "SELECT * FROM " + tab_name +
                " INTO OUTFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/" + file_name + ".csv'" +
                " FIELDS TERMINATED BY ','" +
                " ENCLOSED BY '\"'" +
                " LINES TERMINATED BY '\\n'";
        return query;
    }
}
