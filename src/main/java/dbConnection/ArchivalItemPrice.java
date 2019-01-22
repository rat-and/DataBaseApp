package dbConnection;

public class ArchivalItemPrice {
    private int IDProdukt;
    private int IDMarket;
    private String nazwa;
    private int ilosc;
    private double waga;
    private String jednostka;
    private double cena;
    private String poczatek;
    private String koniec;
    private boolean promocja;

    public int getIDProdukt() {
        return IDProdukt;
    }
    public void setIDProdukt(int IDProdukt) {
        this.IDProdukt = IDProdukt;
    }

    public int getIDMarket() {
        return IDMarket;
    }
    public void setIDMarket(int IDMarket) {
        this.IDMarket = IDMarket;
    }

    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public double getWaga() {
        return waga;
    }

    public void setWaga(double waga) {
        this.waga = waga;
    }

    public String getJednostka() {
        return jednostka;
    }
    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public boolean isPromocja() {
        return promocja;
    }

    public void setPromocja(boolean promocja) {
        this.promocja = promocja;
    }

    public String getPoczatek() {
        return poczatek;
    }

    public void setPoczatek(String poczatek) {
        this.poczatek = poczatek;
    }

    public String getKoniec() {
        return koniec;
    }

    public void setKoniec(String koniec) {
        this.koniec = koniec;
    }
}

