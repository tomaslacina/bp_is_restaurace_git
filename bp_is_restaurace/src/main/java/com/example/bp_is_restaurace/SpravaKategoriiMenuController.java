package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SpravaKategoriiMenuController implements Initializable {
    @FXML
    private Button btn_zpet;
    @FXML
    private Button btn_vytvoritKategoriiMenu;
    @FXML
    private Button btn_vyhledatKategoriiMenu;
    @FXML
    private Button btn_zmenitNazevKategorie;
    @FXML
    private TextField tf_nazevNoveKategorie;
    @FXML
    private TextField tf_editovanyNazev;
    @FXML
    private ComboBox cmb_kategorie;
    @FXML
    private Label lbl_editovana_kategorie;
    private String nazevKategorie;
    private String upravenyNazev;
    private String stringKategorie;
    private int idRestaurace = 1; //TODO v případě více restaurací upravit
    private int idZvoleneKategorie;

    List<KategorieMenu> seznamKategoriiDb = DbUtils.getKategorieMenu(idRestaurace);
    String[] poleString;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_zmenitNazevKategorie.setVisible(false);
        ObservableList<KategorieMenu> seznamKategorii = FXCollections.observableArrayList(seznamKategoriiDb);
        cmb_kategorie.setItems(seznamKategorii);
        cmb_kategorie.getSelectionModel().selectFirst();



        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"nastaveniRestaurace.fxml","Nastavení restaurace",400,600);
            }
        });

        btn_vytvoritKategoriiMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nazevKategorie=tf_nazevNoveKategorie.getText();

                if(DbUtils.overExistenciNazvuKategorieMenu(nazevKategorie,idRestaurace)==true){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba ověření vstupních dat");
                    error.setContentText("Tento název kategorie v této restauraci již existuje");
                    error.show();
                    tf_nazevNoveKategorie.setText("");
                }
                else{

                    if(nazevKategorie.length()>3){

                        if(DbUtils.vytvorKategoriiMenu(nazevKategorie,idRestaurace)==true){
                            Alert info = new Alert(Alert.AlertType.INFORMATION);
                            info.setTitle("Úspěch");
                            info.setContentText("Kategorie "+nazevKategorie+" byla úspěšně vytvořena");
                            info.show();
                            ZmenaSceny.zmenScenu(actionEvent,"spravaKategoriiMenu.fxml","Správa kategorií menu",550,650);
                        }
                        else{
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setTitle("Něco se nepovedlo");
                            error.setContentText("Nastala chyba při vkládání do databáze");
                            error.show();
                        }

                    }
                    else{
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Chyba vstupních dat");
                        error.setContentText("Název kategorie musí mít alespoň 4 znaky");
                        error.show();
                    }

                }

            }
        });

        btn_vyhledatKategoriiMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btn_zmenitNazevKategorie.setVisible(true);
                stringKategorie=cmb_kategorie.getValue().toString();
                poleString=stringKategorie.split("-");

                for (KategorieMenu kategorieMenu : seznamKategorii  ) {
                    if(kategorieMenu.getId_kategorieMenu()==Integer.parseInt(poleString[0])){
                        tf_editovanyNazev.setText(kategorieMenu.getNazevKategorieMenu());
                        lbl_editovana_kategorie.setText("Id kategorie:"+poleString[0]+" název:"+poleString[1]);
                        idZvoleneKategorie=Integer.parseInt(poleString[0]);
                    }
                }
            }
        });

        btn_zmenitNazevKategorie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String novyNazev;
                novyNazev=tf_editovanyNazev.getText();
                if(DbUtils.overExistenciNazvuKategorieMenu(novyNazev,idRestaurace)==false){

                    if(novyNazev.length()>3){

                        if(DbUtils.aktualizujInformaceKategorieMenu(idZvoleneKategorie,novyNazev)==true){
                            Alert info = new Alert(Alert.AlertType.INFORMATION);
                            info.setTitle("Úspěch");
                            info.setContentText("Název kategorie byl aktualizován na: "+novyNazev);
                            info.show();
                            ZmenaSceny.zmenScenu(actionEvent,"spravaKategoriiMenu.fxml","Správa kategorií menu",550,650);
                        }
                        else{
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setTitle("Chyba při komunikaci s databází");
                            error.setContentText("Chyba při vkládání dat do databáze");
                            error.show();
                        }

                    }
                    else{
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Chyba vstupních dat");
                        error.setContentText("Nový název musí obsahovat alespoň 4 znaky! Zadej znovu");
                        error.show();
                    }

                }
                else{
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba vstupních dat");
                    error.setContentText("Název kategorie: "+novyNazev+" již v restauraci existuje, použijte jiný");
                    error.show();
                }
            }
        });



    }
}
