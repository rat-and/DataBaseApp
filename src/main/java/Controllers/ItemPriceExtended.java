package Controllers;

import dbConnection.ItemPrice;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ItemPriceExtended extends ItemPrice {
    private IntegerProperty liczba = new SimpleIntegerProperty(this, "liczba", 1);
    private DoubleProperty wartosc = new SimpleDoubleProperty(this, "wartosc", 0.0);

    public void setLiczba(int liczba) {
        this.liczba.set(liczba);
    }

    public int getLiczba() {
        return liczba.get();
    }

    public IntegerProperty liczbaProperty() {
        return liczba;
    }

    public double getWartosc() {
        return wartosc.get();
    }

    public void setWartosc(double wartosc) {
        this.wartosc.set(wartosc);
    }

    public DoubleProperty wartoscProperty() {
        return wartosc;
    }
}
