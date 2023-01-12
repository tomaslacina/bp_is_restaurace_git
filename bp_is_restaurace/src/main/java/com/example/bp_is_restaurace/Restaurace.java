package com.example.bp_is_restaurace;

public class Restaurace {
    int id_restaurace;
    String nazev;
    String ulice;
    String mesto;
    String psc;
    int pocetStolu;
    String provozovatel;
    String DIC;
    String IC;

    public Restaurace(int id_restaurace, String nazev, String ulice, String mesto, String psc, int pocetStolu, String provozovatel, String DIC, String IC) {
        this.id_restaurace = id_restaurace;
        this.nazev = nazev;
        this.ulice = ulice;
        this.mesto = mesto;
        this.psc = psc;
        this.pocetStolu = pocetStolu;
        this.provozovatel = provozovatel;
        this.DIC = DIC;
        this.IC = IC;
    }

    public int getId_restaurace() {
        return id_restaurace;
    }

    public String getNazev() {
        return nazev;
    }

    public String getUlice() {
        return ulice;
    }

    public String getMesto() {
        return mesto;
    }

    public String getPsc() {
        return psc;
    }

    public int getPocetStolu() {
        return pocetStolu;
    }

    public String getProvozovatel() {
        return provozovatel;
    }

    public String getDIC() {
        return DIC;
    }

    public String getIC() {
        return IC;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Restaurace{");
        sb.append("id_restaurace=").append(id_restaurace);
        sb.append(", nazev='").append(nazev).append('\'');
        sb.append(", ulice='").append(ulice).append('\'');
        sb.append(", mesto='").append(mesto).append('\'');
        sb.append(", psc='").append(psc).append('\'');
        sb.append(", pocetStolu=").append(pocetStolu);
        sb.append(", provozovatel='").append(provozovatel).append('\'');
        sb.append(", DIC='").append(DIC).append('\'');
        sb.append(", IC='").append(IC).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
