package com.example.bp_is_restaurace;

import java.sql.Time;
import java.util.Date;

public class Uctenka {
    int id_uctenky;
    float castka;
    float dph10;
    float dph15;
    float dph21;
    Date datum;
    Time cas;
    float spropitne;
    String poznamka;
    int id_restaurace;
    int id_uzivatele;


    public Uctenka(int id_uctenky, float castka, float dph10, float dph15, float dph21, Date datum, Time cas, float spropitne, String poznamka, int id_restaurace, int id_uzivatele) {
        this.id_uctenky = id_uctenky;
        this.castka = castka;
        this.dph10 = dph10;
        this.dph15 = dph15;
        this.dph21 = dph21;
        this.datum = datum;
        this.cas = cas;
        this.spropitne = spropitne;
        this.poznamka = poznamka;
        this.id_restaurace = id_restaurace;
        this.id_uzivatele = id_uzivatele;
    }

    public int getId_uctenky() {
        return id_uctenky;
    }

    public float getCastka() {
        return castka;
    }

    public float getDph10() {
        return dph10;
    }

    public float getDph15() {
        return dph15;
    }

    public float getDph21() {
        return dph21;
    }

    public Date getDatum() {
        return datum;
    }

    public Time getCas() {
        return cas;
    }

    public float getSpropitne() {
        return spropitne;
    }

    public String getPoznamka() {
        return poznamka;
    }

    public int getId_restaurace() {
        return id_restaurace;
    }

    public int getId_uzivatele() {
        return id_uzivatele;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Uctenka{");
        sb.append("id_uctenky=").append(id_uctenky);
        sb.append(", castka=").append(castka);
        sb.append(", dph10=").append(dph10);
        sb.append(", dph15=").append(dph15);
        sb.append(", dph21=").append(dph21);
        sb.append(", datum=").append(datum);
        sb.append(", cas=").append(cas);
        sb.append(", spropitne=").append(spropitne);
        sb.append(", poznamka='").append(poznamka).append('\'');
        sb.append(", id_restaurace=").append(id_restaurace);
        sb.append(", id_uzivatele=").append(id_uzivatele);
        sb.append('}');
        return sb.toString();
    }
}
