package com.example.bp_is_restaurace;

public class Stul {
    private int id_stolu;
    private String oznaceni;
    private int pocet_zidli;
    private int restaurace_id_restaurace;

    public Stul(int id_stolu, String oznaceni, int pocet_zidli, int restaurace_id_restaurace) {
        this.id_stolu = id_stolu;
        this.oznaceni = oznaceni;
        this.pocet_zidli = pocet_zidli;
        this.restaurace_id_restaurace = restaurace_id_restaurace;
    }

    public int getId_stolu() {
        return id_stolu;
    }

    public String getOznaceni() {
        return oznaceni;
    }

    public int getPocet_zidli() {
        return pocet_zidli;
    }

    public int getRestaurace_id_restaurace() {
        return restaurace_id_restaurace;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Stul{");
        sb.append("id_stolu=").append(id_stolu);
        sb.append(", oznaceni='").append(oznaceni).append('\'');
        sb.append(", pocet_zidli=").append(pocet_zidli);
        sb.append(", restaurace_id_restaurace=").append(restaurace_id_restaurace);
        sb.append('}');
        return sb.toString();
    }
}
