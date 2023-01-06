package com.example.bp_is_restaurace;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ObjednavkaStul {
    private int idObjednavky;
    private int pocetKs;
    private Timestamp datumCasObjednani;
    private int idStolu;
    private int idPolozkyMenu;
    private int idUzivatele;

    private String nazevPolozky;

    //pro potřeby objednávky před vložením do DB
    public ObjednavkaStul(int pocetKs, int idPolozkyMenu, String nazev) {
        this.pocetKs = pocetKs;
        this.idPolozkyMenu = idPolozkyMenu;
        this.nazevPolozky=nazev;
    }

    public ObjednavkaStul(int idObjednavky, int pocetKs, Timestamp datumCasObjednani, int idStolu, int idPolozkyMenu, int idUzivatele) {
        this.idObjednavky = idObjednavky;
        this.pocetKs = pocetKs;
        this.datumCasObjednani = datumCasObjednani;
        this.idStolu = idStolu;
        this.idPolozkyMenu = idPolozkyMenu;
        this.idUzivatele = idUzivatele;
    }

    public int getPocetKs() {
        return pocetKs;
    }

    public int getIdPolozkyMenu() {
        return idPolozkyMenu;
    }

    public int getIdObjednavky() {
        return idObjednavky;
    }

    public void zvysPocetKs1(){
        this.pocetKs+=1;
    }
    public void zmensiPocetKs1(){this.pocetKs-=1;}

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id položky menu:"+idPolozkyMenu+"\t"+nazevPolozky+"\t\t"+pocetKs+" Ks\t\n");
        return sb.toString();
    }
}
