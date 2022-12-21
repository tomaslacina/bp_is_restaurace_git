package com.example.bp_is_restaurace;

public class Uzivatel {
    private int id_uzivatele;
    private String jmeno;
    private String prijmeni;
    private String pozice;
    private int osobniCislo;
    private String heslo;
    private int id_restaurace;


    public Uzivatel(int id_uzivatele, String jmeno, String prijmeni, String pozice, int osobniCislo, String heslo, int id_restaurace) {
        this.id_uzivatele = id_uzivatele;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.pozice = pozice;
        this.osobniCislo = osobniCislo;
        this.heslo = heslo;
        this.id_restaurace = id_restaurace;
    }

    public int getId_uzivatele() {
        return id_uzivatele;
    }

    public void setId_uzivatele(int id_uzivatele) {
        this.id_uzivatele = id_uzivatele;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getPozice() {
        return pozice;
    }

    public void setPozice(String pozice) {
        this.pozice = pozice;
    }

    public int getOsobniCislo() {
        return osobniCislo;
    }

    public void setOsobniCislo(int osobniCislo) {
        this.osobniCislo = osobniCislo;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public int getId_restaurace() {
        return id_restaurace;
    }

    public void setId_restaurace(int id_restaurace) {
        this.id_restaurace = id_restaurace;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Uzivatel{");
        sb.append("id_uzivatele=").append(id_uzivatele);
        sb.append(", jmeno='").append(jmeno).append('\'');
        sb.append(", prijmeni='").append(prijmeni).append('\'');
        sb.append(", pozice='").append(pozice).append('\'');
        sb.append(", osobniCislo=").append(osobniCislo);
        sb.append(", heslo='").append(heslo).append('\'');
        sb.append(", id_restaurace=").append(id_restaurace);
        sb.append('}');
        return sb.toString();
    }
}
