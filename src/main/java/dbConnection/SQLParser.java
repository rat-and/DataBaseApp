package dbConnection;

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

    public String insertItemPriceQuery() {
        query = "INSERT INTO cenyproduktow (IDProdukt, IDMarket, Nazwa, Ilosc, Waga, Jednostka, Cena, Promocja) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        return query;
    }

    public String updateItemPriceQuery_IDMarket() {
        query = "UPDATE cenyproduktow SET IDMarket = ? WHERE IDProdukt = ?";
        return query;
    }

    public String updateItemPriceQuery_ilosc() {
        query = "UPDATE cenyproduktow SET Ilosc = ? WHERE IDProdukt = ?";
        return query;
    }
}
