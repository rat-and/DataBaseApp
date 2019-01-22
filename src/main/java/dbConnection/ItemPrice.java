package dbConnection;

import javafx.beans.property.*;

public class ItemPrice {
    private IntegerProperty IDProdukt = new SimpleIntegerProperty(this, "IDProdukt", 0);
    private IntegerProperty IDMarket = new SimpleIntegerProperty(this, "IDMarket", 0);
    private StringProperty nazwa = new SimpleStringProperty(this, "nazwa", "");
    private IntegerProperty ilosc = new SimpleIntegerProperty(this, "ilosc", 0);
    private DoubleProperty waga = new SimpleDoubleProperty(this, "waga", 0.0);
    private StringProperty jednostka = new SimpleStringProperty(this, "jednostka", "");
    private DoubleProperty cena = new SimpleDoubleProperty(this, "cena", 0.0);
//    private StringProperty poczatek = new SimpleStringProperty(this, "poczatek", "");
//    private String koniec;
    private IntegerProperty promocja = new SimpleIntegerProperty(this, "promocja", 0);

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

    public String getNazwa() {
        return nazwa.get();
    }

    public StringProperty getNazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public int getIlosc() {
        return ilosc.get();
    }

    public IntegerProperty iloscProperty() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc.set(ilosc);
    }

    public double getWaga() {
        return waga.get();
    }

    public DoubleProperty wagaProperty() {
        return waga;
    }

    public void setWaga(double waga) {
        this.waga.set(waga);
    }

    public String getJednostka() {
        return jednostka.get();
    }

    public StringProperty jednostkaProperty() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka.set(jednostka);
    }

    public double getCena() {
        return cena.get();
    }

    public DoubleProperty cenaProperty() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena.set(cena);
    }

    public int isPromocja() {
        return promocja.get();
    }

    public IntegerProperty promocjaProperty() {
        return promocja;
    }

    public void setPromocja(int promocja) {
        this.promocja.set(promocja);
    }

//    public String getPoczatek() {
//        return poczatek;
//    }

//    public void setPoczatek(String poczatek) {
//        this.poczatek = poczatek;
//    }

//    public String getKoniec() {
//        return koniec;
//    }

//    public void setKoniec(String koniec) {
//        this.koniec = koniec;
//    }
}
