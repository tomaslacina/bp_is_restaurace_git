package com.example.bp_is_restaurace;

public class PolozkaMenu {
    private int idPolozkyMenu;
    private String nazev;
    private int mnozstvi;
    private String jednotky;
    private float cena;
    private float sazbaDph;
    private String alergeny;
    private String poznamka;
    private int idKategoriePolozky;


    public PolozkaMenu(int idPolozkyMenu, String nazev, int mnozstvi, String jednotky, float cena, float sazbaDph, String alergeny, String poznamka, int idKategoriePolozky) {
        this.idPolozkyMenu = idPolozkyMenu;
        this.nazev = nazev;
        this.mnozstvi = mnozstvi;
        this.jednotky = jednotky;
        this.cena = cena;
        this.sazbaDph = sazbaDph;
        this.alergeny = alergeny;
        this.poznamka = poznamka;
        this.idKategoriePolozky = idKategoriePolozky;
    }

    public int getIdPolozkyMenu() {
        return idPolozkyMenu;
    }

    public String getNazev() {
        return nazev;
    }

    public int getMnozstvi() {
        return mnozstvi;
    }

    public String getJednotky() {
        return jednotky;
    }

    public float getCena() {
        return cena;
    }

    public float getSazbaDph() {
        return sazbaDph;
    }

    public String getAlergeny() {
        return alergeny;
    }

    public String getPoznamka() {
        return poznamka;
    }

    public int getIdKategoriePolozky() {
        return idKategoriePolozky;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(idPolozkyMenu+"-"+nazev+"-"+mnozstvi+"-"+jednotky+"-"+cena);
        return sb.toString();
    }


}
