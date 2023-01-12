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

    private float cena;

    private float sazbaDPH;

    private float celkemZaPolozku;



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

    public ObjednavkaStul(int idObjednavky, String nazevPolozky, int pocetKs){
        this.idObjednavky=idObjednavky;
        this.nazevPolozky=nazevPolozky;
        this.pocetKs=pocetKs;
    }

    public ObjednavkaStul(int idObjednavky, int pocetKs, int idStolu, int idPolozkyMenu, String nazevPolozky, float cena, float celkemZaPolozku, float sazbaDPH) {
        this.idObjednavky = idObjednavky;
        this.pocetKs = pocetKs;
        this.idStolu = idStolu;
        this.idPolozkyMenu = idPolozkyMenu;
        this.nazevPolozky = nazevPolozky;
        this.cena = cena;
        this.sazbaDPH = sazbaDPH;
        this.celkemZaPolozku=celkemZaPolozku;
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

    public float getCena() {
        return cena;
    }

    public float getSazbaDPH() {
        return sazbaDPH;
    }


    public float getCelkemZaPolozku() {
        return celkemZaPolozku;
    }

    public void zvysPocetKs1(){
        this.pocetKs+=1;
    }
    public void zmensiPocetKs1(){this.pocetKs-=1;}


    public String info() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id položky menu:"+idPolozkyMenu+"\t"+nazevPolozky+"\t\t"+pocetKs+" Ks\t\n");
        return sb.toString();
    }

    public String infoPrehled() {
        StringBuffer sb = new StringBuffer();
        sb.append("Počet kusů:"+pocetKs+" Ks\t"+nazevPolozky+"\t\t(objednávka id:"+idObjednavky+")\n");
        return sb.toString();
    }


    public String infoNahledUctenky(){
        float sazba = sazbaDPH*100;
        String sazbaProcenta = String.format("%.2f",sazba);
        StringBuffer sb = new StringBuffer();
        sb.append(nazevPolozky+"\n");
        sb.append("Počet:"+pocetKs+" KS\t"+cena+" Kč/Ks\t"+"=\t"+celkemZaPolozku+" Kč\t"+"DPH: "+sazbaProcenta+" %\n");
        return sb.toString();
    }

    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer(idObjednavky+"-"+nazevPolozky+"-"+pocetKs);
        return sb.toString();
    }
}
