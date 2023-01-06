package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SpravaPolozekMenuController implements Initializable {
    @FXML
    private Button btn_vytvorPolozku;
    @FXML
    private Button btn_aktualizujPolozku;
    @FXML
    private Button btn_vyhledatPolozku;
    @FXML
    private Button btn_vyberKategorii;
    @FXML
    private Button btn_zpet_nastaveni;
    @FXML
    private Button btn_zpet_administrace;

    @FXML
    private TextField tf_nazev_polozky;
    @FXML
    private TextField tf_mnozstvi;
    @FXML
    private ComboBox cmb_jednotky;
    @FXML
    private TextField tf_cena;
    @FXML
    private ComboBox cmb_sazba_dph;
    @FXML
    private TextField tf_alergeny;
    @FXML
    private TextField tf_poznamka;
    @FXML
    private ComboBox cmb_kategorie_menu;


    @FXML
    private ComboBox cmb_kategorieMenuVyhledej;

    @FXML
    private ComboBox cmb_polozky_kategorie;

    String[] poleString;
    private int idKategorie;
    private int idPolozky;






    List<KategorieMenu> seznamKategoriiMenu = DbUtils.getKategorieMenu(1); //TODO id restaurace -> konstanta
    List<PolozkaMenu> seznamPolozekMenu;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_vyhledatPolozku.setVisible(false);
        btn_aktualizujPolozku.setVisible(false);
        ObservableList<String> jednotky = FXCollections.observableArrayList("ks","l","dl","ml","cl","kg","g");
        cmb_jednotky.setItems(jednotky);
        cmb_jednotky.getSelectionModel().selectFirst();

        ObservableList<String> sazby = FXCollections.observableArrayList("0%","10%","15%","21%","neplatci");
        cmb_sazba_dph.setItems(sazby);
        cmb_sazba_dph.getSelectionModel().selectFirst();

        ObservableList<KategorieMenu> seznamKategorii = FXCollections.observableArrayList(seznamKategoriiMenu);
        cmb_kategorie_menu.setItems(seznamKategorii);
        cmb_kategorie_menu.getSelectionModel().selectFirst();

        cmb_kategorieMenuVyhledej.setItems(seznamKategorii);
        cmb_kategorieMenuVyhledej.getSelectionModel().selectFirst();




        btn_vytvorPolozku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean overeniVstupu=true;
                String nazev = tf_nazev_polozky.getText();

                if(nazev.length()<3){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba vstupních dat");
                    error.setContentText("Název musí obsahovat alespoň 4 znaky");
                    error.show();
                    overeniVstupu=false;
                }

                int mnozstvi=Integer.parseInt(tf_mnozstvi.getText());
                String jednotky=String.valueOf(cmb_jednotky.getValue());
                float cena=Float.parseFloat(tf_cena.getText());
                if(cena<0){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba vstupních dat");
                    error.setContentText("Cena musí být větší než 0 Kč!");
                    error.show();
                    overeniVstupu=false;
                }
                float sazbaDph;
                String alergeny=tf_alergeny.getText();
                String poznamka=tf_poznamka.getText();
                int idKategoriePolozky;

                String stringKategorie=cmb_kategorie_menu.getValue().toString();
                poleString=stringKategorie.split("-");
                idKategoriePolozky=Integer.parseInt(poleString[0]);

                String sazba = cmb_sazba_dph.getValue().toString();

                switch (sazba){
                    case "0%":
                        sazbaDph=0.0f;
                        break;
                    case "10%":
                        sazbaDph=0.1f;
                        break;
                    case "15%":
                        sazbaDph=0.15f;
                        break;
                    case "21%":
                        sazbaDph=0.21f;
                        break;
                    case "neplatci":
                        sazbaDph=0.0f;
                        break;
                    default:
                        sazbaDph=0.0f;

                }

                if(overeniVstupu==true){

                        if(DbUtils.vytvorPolozkuMenu(nazev,mnozstvi,jednotky,cena,sazbaDph,alergeny,poznamka,idKategoriePolozky)==true){
                            Alert info = new Alert(Alert.AlertType.INFORMATION);
                            info.setTitle("Úspěch");
                            info.setContentText("Položka menu: "+nazev+" byla vytvořena");
                            info.show();
                            ZmenaSceny.zmenScenu(actionEvent,"spravaPolozekMenu.fxml","Správa položek menu",600,1000);
                        }
                        else{
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setTitle("Chyba");
                            error.setContentText("Nastala chyba při komunikaci s databází");
                            error.show();
                        }

                    }
                else{
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba ověření vstupu");
                    error.setContentText("Data nebyla korektně vyplněna!");
                }


                }


        });

        btn_vyberKategorii.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btn_vytvorPolozku.setVisible(false);
                btn_vyhledatPolozku.setVisible(true);
                String vybranaKategorie = cmb_kategorieMenuVyhledej.getValue().toString();
                poleString=vybranaKategorie.split("-");
                System.out.println(poleString[0]);
                seznamPolozekMenu=DbUtils.getPolozkyMenuByIdKategorieMenu(Integer.parseInt(poleString[0]));

                ObservableList<PolozkaMenu> seznamPolozekDb = FXCollections.observableArrayList(seznamPolozekMenu);
                cmb_polozky_kategorie.setItems(seznamPolozekDb);
                cmb_polozky_kategorie.getSelectionModel().selectFirst();

            }
        });

        btn_vyhledatPolozku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btn_aktualizujPolozku.setVisible(true);

                PolozkaMenu polozkaMenu;
                String vybranaPolozka = cmb_polozky_kategorie.getValue().toString();
                poleString=vybranaPolozka.split("-");
                System.out.println(poleString[0]);
                int id_polozky=Integer.parseInt(poleString[0]);
                idPolozky=Integer.parseInt(poleString[0]);
                polozkaMenu=DbUtils.getPolozkuMenuByIdPolozky(id_polozky);

                cmb_kategorie_menu.setValue(cmb_kategorieMenuVyhledej.getValue());

                tf_nazev_polozky.setText(polozkaMenu.getNazev());
                tf_mnozstvi.setText(String.valueOf(polozkaMenu.getMnozstvi()));
                cmb_jednotky.setValue(polozkaMenu.getJednotky());
                tf_cena.setText(String.valueOf(polozkaMenu.getCena()));
                tf_alergeny.setText(polozkaMenu.getAlergeny());
                tf_poznamka.setText(polozkaMenu.getPoznamka());
                String sazba = String.valueOf(polozkaMenu.getSazbaDph());
                String sazbaDph;

                switch(sazba){
                    case "0.0":
                        sazbaDph="0%";
                        break;
                    case "0.1":
                        sazbaDph="10%";
                        break;
                    case "0.15":
                        sazbaDph="15%";
                        break;
                    case "0.21":
                        sazbaDph="21%";
                        break;
                    default:
                        sazbaDph="neplatci";
                }
                cmb_sazba_dph.setValue(sazbaDph);

            }
        });

        btn_aktualizujPolozku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean overeniVstupu=true;
                String nazev = tf_nazev_polozky.getText();

                if(nazev.length()<3){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba vstupních dat");
                    error.setContentText("Název musí obsahovat alespoň 4 znaky");
                    error.show();
                    overeniVstupu=false;
                }

                int mnozstvi=Integer.parseInt(tf_mnozstvi.getText());
                String jednotky=String.valueOf(cmb_jednotky.getValue());
                float cena=Float.parseFloat(tf_cena.getText());
                if(cena<0){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba vstupních dat");
                    error.setContentText("Cena musí být větší než 0 Kč!");
                    error.show();
                    overeniVstupu=false;
                }
                float sazbaDph;
                String alergeny=tf_alergeny.getText();
                String poznamka=tf_poznamka.getText();
                int idKategoriePolozky;

                String stringKategorie=cmb_kategorie_menu.getValue().toString();
                poleString=stringKategorie.split("-");
                idKategoriePolozky=Integer.parseInt(poleString[0]);

                String sazba = cmb_sazba_dph.getValue().toString();

                switch (sazba){
                    case "0%":
                        sazbaDph=0.0f;
                        break;
                    case "10%":
                        sazbaDph=0.1f;
                        break;
                    case "15%":
                        sazbaDph=0.15f;
                        break;
                    case "21%":
                        sazbaDph=0.21f;
                        break;
                    case "neplatci":
                        sazbaDph=0.0f;
                        break;
                    default:
                        sazbaDph=0.0f;

                }

                if(overeniVstupu==true){

                    if(DbUtils.aktualizujPolozkuMenu(idPolozky,nazev,mnozstvi,jednotky,cena,sazbaDph,alergeny,poznamka,idKategoriePolozky)){
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Úspěch");
                        info.setContentText("Položka menu: "+nazev+" byla aktualizována!");
                        info.show();
                        ZmenaSceny.zmenScenu(actionEvent,"spravaPolozekMenu.fxml","Správa položek menu",600,1000);
                    }
                    else{
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Chyba");
                        error.setContentText("Nastala chyba při komunikaci s databází");
                        error.show();
                    }

                }
                else{
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba ověření vstupu");
                    error.setContentText("Data nebyla korektně vyplněna!");
                }


            }

        });

        btn_zpet_nastaveni.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"nastaveniRestaurace.fxml","Nastavení restaurace",500,700);
            }
        });

        btn_zpet_administrace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"administrace.fxml","Administrace",400,600);
            }
        });


    }
}
