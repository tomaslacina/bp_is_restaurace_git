package com.example.bp_is_restaurace;

public class PolozkyUctenky {
    int id_polozky_uctenky;
    int pocetKs;
    int id_uctenky;
    int id_polozky_menu;
    int cenaKs;
    float skupinaDPH;


    public PolozkyUctenky(int id_polozky_uctenky, int pocetKs, int id_uctenky, int id_polozky_menu, int cenaKs, float skupinaDPH) {
        this.id_polozky_uctenky = id_polozky_uctenky;
        this.pocetKs = pocetKs;
        this.id_uctenky = id_uctenky;
        this.id_polozky_menu = id_polozky_menu;
        this.cenaKs = cenaKs;
        this.skupinaDPH = skupinaDPH;
    }


    public int getId_polozky_uctenky() {
        return id_polozky_uctenky;
    }

    public int getPocetKs() {
        return pocetKs;
    }

    public int getId_uctenky() {
        return id_uctenky;
    }

    public int getId_polozky_menu() {
        return id_polozky_menu;
    }

    public int getCenaKs() {
        return cenaKs;
    }

    public float getSkupinaDPH() {
        return skupinaDPH;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PolozkyUctenky{");
        sb.append("id_polozky_uctenky=").append(id_polozky_uctenky);
        sb.append(", pocetKs=").append(pocetKs);
        sb.append(", id_uctenky=").append(id_uctenky);
        sb.append(", id_polozky_menu=").append(id_polozky_menu);
        sb.append(", cenaKs=").append(cenaKs);
        sb.append(", skupinaDPH=").append(skupinaDPH);
        sb.append('}');
        return sb.toString();
    }
}
