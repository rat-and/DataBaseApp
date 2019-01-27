/**
 * Class to represent query statement return from a data base
 */
package dbConnection;

import Controllers.ItemPriceExtended;
import javafx.beans.property.*;

public class ItemPrice {
    private IntegerProperty IDProdukt = new SimpleIntegerProperty(this, "IDProdukt", 0);
    private IntegerProperty IDMarket = new SimpleIntegerProperty(this, "IDMarket", 0);
    private StringProperty name = new SimpleStringProperty(this, "name", "");
    private StringProperty market = new SimpleStringProperty(this, "market", "");
    private DoubleProperty opinion = new SimpleDoubleProperty(this, "opinion", 0.0);
    private StringProperty description = new SimpleStringProperty(this, "description", "");
    private DoubleProperty price = new SimpleDoubleProperty(this, "price", 0.0);
    private StringProperty webAddres = new SimpleStringProperty(this, "webAddres", "");
    private DoubleProperty marketOpinion = new SimpleDoubleProperty(this, "marketOpinion", 0);

    /**
     * Extends this class by adding liczba and wartosc attribute
     * so it's possible to add to Obserever.observablelist and then to a chart
     */
    public ItemPriceExtended extend() {
        ItemPriceExtended extendedOne = new ItemPriceExtended();
        extendedOne.setIDProdukt(this.getIDProdukt());
        extendedOne.setIDMarket(this.getIDMarket());
        extendedOne.setName(this.getName());
        extendedOne.setMarket(this.getMarket());
        extendedOne.setOpinion(this.getOpinion());
        extendedOne.setDescription(this.getDescription());
        extendedOne.setPrice(this.getPrice());
        extendedOne.setWebAddres(this.getWebAddres());
        extendedOne.setMarketOpinion(this.getMarketOpinion());
        extendedOne.setLiczba(1);
        extendedOne.setWartosc(this.getPrice());
        return extendedOne;
    }

    public int getIDProdukt() {
        return IDProdukt.get();
    }
    public IntegerProperty getIDProduktProperty() {
        return IDProdukt;
    }
    public void setIDProdukt(int IDProdukt) {
        this.IDProdukt.set(IDProdukt);
    }

    public int getIDMarket() {
        return IDMarket.get();
    }
    public IntegerProperty getIDMarketProperty() {
        return IDMarket;
    }
    public void setIDMarket(int IDMarket) {
        this.IDMarket.set(IDMarket);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String nazwa) {
        this.name.set(nazwa);
    }

    public String getMarket() {
        return market.get();
    }

    public StringProperty marketProperty() {
        return market;
    }

    public void setMarket(String ilosc) {
        this.market.set(ilosc);
    }

    public double getOpinion() {
        return opinion.get();
    }

    public DoubleProperty opinionProperty() {
        return opinion;
    }

    public void setOpinion(double waga) {
        this.opinion.set(waga);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String jednostka) {
        this.description.set(jednostka);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double cena) {
        this.price.set(cena);
    }

    public String getWebAddres() {
        return webAddres.get();
    }

    public void setWebAddres(String poczatek) {
        this.webAddres.set(poczatek);
    }

    public StringProperty webAddresProperty() {
        return webAddres;
    }

    public Double getMarketOpinion() {
        return marketOpinion.get();
    }

    public DoubleProperty marketOpinionProperty() {
        return marketOpinion;
    }

    public void setMarketOpinion(Double koniec) {
        this.marketOpinion.set(koniec);
    }
}
