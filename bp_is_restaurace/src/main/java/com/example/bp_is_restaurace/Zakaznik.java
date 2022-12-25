package com.example.bp_is_restaurace;

public class Zakaznik {
    private int id_zakaznika;
    private String oznaceni;
    private int restaurace_id_restaurace;


    public Zakaznik(int id_zakaznika, String oznaceni, int restaurace_id_restaurace) {
        this.id_zakaznika = id_zakaznika;
        this.oznaceni = oznaceni;
        this.restaurace_id_restaurace = restaurace_id_restaurace;
    }

    public int getId_zakaznika() {
        return id_zakaznika;
    }

    public String getOznaceni() {
        return oznaceni;
    }

    public int getRestaurace_id_restaurace() {
        return restaurace_id_restaurace;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Zakaznik{");
        sb.append("id_zakaznika=").append(id_zakaznika);
        sb.append(", oznaceni='").append(oznaceni).append('\'');
        sb.append(", restaurace_id_restaurace=").append(restaurace_id_restaurace);
        sb.append('}');
        return sb.toString();
    }
}
