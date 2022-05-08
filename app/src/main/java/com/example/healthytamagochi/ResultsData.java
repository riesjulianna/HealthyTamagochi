package com.example.healthytamagochi;

public class ResultsData {
    String nev, jatek, pont;

    @Override
    public String toString() {
        return "ResultsData{" +
                "nev='" + nev + '\'' +
                ", jatek='" + jatek + '\'' +
                ", pont='" + pont + '\'' +
                '}';
    }

    public ResultsData() {
    }

    public ResultsData(String nev, String jatek, String pont) {
        this.nev = nev;
        this.jatek = jatek;
        this.pont = pont;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getJatek() {
        return jatek;
    }

    public void setJatek(String jatek) {
        this.jatek = jatek;
    }

    public String getPont() {
        return pont;
    }

    public void setPont(String pont) {
        this.pont = pont;
    }
}
