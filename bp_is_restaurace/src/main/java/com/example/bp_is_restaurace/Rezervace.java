package com.example.bp_is_restaurace;

import java.sql.Date;
import java.sql.Time;

public class Rezervace {
    private int id_rezervace;
    private Date datum;
    private Time cas_od;
    private Time cas_do;
    private String jmeno;
    private String prijmeni;
    private String kontakt;
    private String poznamka;
    private int pocet_osob;
    private int stoly_id_stolu;
    private int uzivatel_id_uzivatele;

    public Rezervace(int id_rezervace, Date datum, Time cas_od, Time cas_do, String jmeno, String prijmeni,String kontakt, String poznamka, int pocet_osob, int stoly_id_stolu, int uzivatel_id_uzivatele) {
        this.id_rezervace = id_rezervace;
        this.datum = datum;
        this.cas_od = cas_od;
        this.cas_do = cas_do;
        this.jmeno = jmeno;
        this.prijmeni=prijmeni;
        this.kontakt = kontakt;
        this.poznamka = poznamka;
        this.pocet_osob = pocet_osob;
        this.stoly_id_stolu = stoly_id_stolu;
        this.uzivatel_id_uzivatele = uzivatel_id_uzivatele;
    }

    public int getId_rezervace() {
        return id_rezervace;
    }

    public Date getDatum() {
        return datum;
    }

    public Time getCas_od() {
        return cas_od;
    }

    public Time getCas_do() {
        return cas_do;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni(){
        return prijmeni;
    }

    public String getKontakt() {
        return kontakt;
    }

    public String getPoznamka() {
        return poznamka;
    }

    public int getPocet_osob() {
        return pocet_osob;
    }

    public int getStoly_id_stolu() {
        return stoly_id_stolu;
    }

    public int getUzivatel_id_uzivatele() {
        return uzivatel_id_uzivatele;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(id_rezervace+"-"+jmeno+"-"+prijmeni+"-"+datum);
        /*final StringBuffer sb = new StringBuffer("Rezervace{");
        sb.append("id_rezervace=").append(id_rezervace);
        sb.append(", datum=").append(datum);
        sb.append(", cas_od=").append(cas_od);
        sb.append(", cas_do=").append(cas_do);
        sb.append(", jmeno='").append(jmeno).append('\'');
        sb.append(", prijmeni='").append(prijmeni).append('\'');
        sb.append(", kontakt='").append(kontakt).append('\'');
        sb.append(", poznamka='").append(poznamka).append('\'');
        sb.append(", pocet_osob=").append(pocet_osob);
        sb.append(", stoly_id_stolu=").append(stoly_id_stolu);
        sb.append(", uzivatel_id_uzivatele=").append(uzivatel_id_uzivatele);
        sb.append('}');*/
        return sb.toString();
    }

    public String infoRezervace() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Id rezervace:"+id_rezervace).append("\n");
        sb.append("Datum:"+datum).append("\t");
        sb.append("Stůl:"+stoly_id_stolu).append("\n");
        sb.append("Čas od:"+cas_od).append("\t");
        sb.append("Čas do"+cas_do).append("\n");
        sb.append("Jméno:"+jmeno).append("\t");
        sb.append("Prijmeni: "+prijmeni).append("\n");
        sb.append("Kontakt: "+kontakt).append("\n");
        sb.append("Poznámka: "+poznamka).append("\n");
        sb.append("Počet osob "+pocet_osob).append("\n");
        sb.append("Rezervaci vytvořil uživatel s ID:"+uzivatel_id_uzivatele).append("\n");
        return sb.toString();
    }
}
