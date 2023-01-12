package com.example.bp_is_restaurace;

import java.sql.Timestamp;

public class ObjednavkaZakaznik {
    private int idObjednavky;
    private int pocetKs;
    private Timestamp datumCasObjednani;
    private int idPolozkaMenu;
    private int idZakaznika;
    private int idUzivatele;

    private float cena;
    private float sazbaDPH;
    private float celkemZaPolozku;

    private String nazevPolozky;

    public ObjednavkaZakaznik(int idObjednavky, int pocetKs, Timestamp datumCasObjednani, int idPolozkaMenu, int idZakaznika, int idUzivatele) {
        this.idObjednavky = idObjednavky;
        this.pocetKs = pocetKs;
        this.datumCasObjednani = datumCasObjednani;
        this.idPolozkaMenu = idPolozkaMenu;
        this.idZakaznika = idZakaznika;
        this.idUzivatele = idUzivatele;
    }

    public ObjednavkaZakaznik(int idObjednavky, int pocetKs, int idZakaznika, int idPolozkyMenu, String nazevPolozky, float cena, float celkemZaPolozku, float sazbaDPH) {
        this.idObjednavky = idObjednavky;
        this.pocetKs = pocetKs;
        this.idZakaznika = idZakaznika;
        this.idPolozkaMenu = idPolozkyMenu;
        this.nazevPolozky = nazevPolozky;
        this.cena = cena;
        this.sazbaDPH = sazbaDPH;
        this.celkemZaPolozku=celkemZaPolozku;
    }



    public ObjednavkaZakaznik(int idPolozkaMenu, int pocetKs, String nazevPolozky) {
        this.idPolozkaMenu = idPolozkaMenu;
        this.pocetKs = pocetKs;
        this.nazevPolozky = nazevPolozky;
    }

    public ObjednavkaZakaznik(int idObjednavky, String nazevPolozky, int pocetKs){
        this.idObjednavky=idObjednavky;
        this.nazevPolozky=nazevPolozky;
        this.pocetKs=pocetKs;
    }


    public int getPocetKs() {
        return pocetKs;
    }

    public int getIdPolozkyMenu() {
        return idPolozkaMenu;
    }

    public int getIdObjednavky() {
        return idObjednavky;
    }

    public float getCelkemZaPolozku() {
        return celkemZaPolozku;
    }

    public float getSazbaDPH() {
        return sazbaDPH;
    }

    public float getCena() {
        return cena;
    }

    public void zvysPocetKs1(){
        this.pocetKs+=1;
    }
    public void zmensiPocetKs1(){this.pocetKs-=1;}


    //pro potřeby objednávky před vložením do DB
    public String info() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id položky menu:"+idPolozkaMenu+"\t"+nazevPolozky+"\t\t"+pocetKs+" Ks\t\n");
        return sb.toString();
    }

    public String infoPrehled() {
        StringBuffer sb = new StringBuffer();
        sb.append("Počet kusů:"+pocetKs+" Ks\t"+nazevPolozky+"\t\t(objednávka id:"+idObjednavky+")\n");
        return sb.toString();
    }

    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer(idObjednavky+"-"+nazevPolozky+"-"+pocetKs);
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



}
