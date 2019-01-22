package dbConnection;

public class SQLParser {
    private String query;

    public String fetchAllItemPriceQuery() {
        query = "SELECT * FROM cenyproduktow";
        return query;
    }

    public String fetchItemPriceQuery() {
        query = "SELECT * FROM cenyproduktow WHERE Nazwa LIKE ?";
        return query;
    }
}
