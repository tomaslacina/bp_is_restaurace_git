package com.example.bp_is_restaurace;

public class KategorieMenu {
    private int id_kategorieMenu;
    private String nazevKategorieMenu;
    private int restaurace_id_restaurace;


    public KategorieMenu(int id_kategorieMenu, String nazevKategorieMenu, int restaurace_id_restaurace) {
        this.id_kategorieMenu = id_kategorieMenu;
        this.nazevKategorieMenu = nazevKategorieMenu;
        this.restaurace_id_restaurace = restaurace_id_restaurace;
    }

    public int getId_kategorieMenu() {
        return id_kategorieMenu;
    }

    public String getNazevKategorieMenu() {
        return nazevKategorieMenu;
    }

    public int getRestaurace_id_restaurace() {
        return restaurace_id_restaurace;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(id_kategorieMenu+"-"+nazevKategorieMenu);
        return sb.toString();
    }
}
