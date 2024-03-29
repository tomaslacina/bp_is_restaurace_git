package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SpravaRezervaciController implements Initializable {

    List<Rezervace> seznamRezervaciDb = DbUtils.getSeznamVsechRezervaci();
    String rezervace;
    String[] poleString;
    @FXML
    private Button btn_vyhledej_ze_seznamu;
    @FXML
    private Button btn_vyhledej_datum;
    @FXML
    private Button btn_vyhledej_jmeno_primeni;
    @FXML
    private Button btn_vyhledej_rezervace_stul;
    @FXML
    private Button btn_smazat_rezervaci;
    @FXML
    private Button btn_aktualizuj_rezervaci;
    @FXML
    private Button btn_vytiskni_rezervace;
    @FXML
    private Button btn_zpet;
    @FXML
    private ComboBox cmb_seznamRezervaci;
    @FXML
    private DatePicker dp_datum;
    @FXML
    private DatePicker dp_vyhledej_datum_do;
    @FXML
    private DatePicker dp_vyhledej_datum_od;
    @FXML
    private ComboBox cmb_cas_od;
    @FXML
    private ComboBox cmb_cas_do;
    @FXML
    private ComboBox cmb_stoly;
    @FXML
    private TextField tf_vyhledej_jmeno;
    @FXML
    private TextField tf_vyhledej_prijmeni;
    @FXML
    private TextField tf_jmeno;
    @FXML
    private TextField tf_prijmeni;
    @FXML
    private TextField tf_kontakt;
    @FXML
    private TextField tf_poznamka;
    @FXML
    private ComboBox cmb_pocetOsob;
    @FXML
    private Label lbl_id_stolu;
    @FXML
    private Label lbl_id_uzivatele;

    @FXML
    private Label lbl_id_rezervace;
    @FXML
    private TextArea text_area_rezervace;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO stoly jsou vloženy ručně, v případě změny upravit aby se vyplňovaly automaticky!!!
        ObservableList<String> stoly = FXCollections.observableArrayList("SALONEK","L1","L2","L3","L4","BAR1","BAR2","BAR3","BAR4","BAR5","BAR6","STRED1","STRED2","STRED3","OKNO1","OKNO2","OKNO3");
        cmb_stoly.setItems(stoly);
        ObservableList<Integer> pocetOsob = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);
        cmb_pocetOsob.setItems(pocetOsob);
        ObservableList<Rezervace> seznamRezervaci = FXCollections.observableArrayList(seznamRezervaciDb);
        cmb_seznamRezervaci.setItems(seznamRezervaci);
        cmb_seznamRezervaci.getSelectionModel().selectFirst();
        btn_aktualizuj_rezervaci.setVisible(false);
        btn_smazat_rezervaci.setVisible(false);
        dp_vyhledej_datum_od.setValue(LocalDate.now());
        dp_vyhledej_datum_do.setValue(LocalDate.now());

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
            }
        });

        btn_vyhledej_datum.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate datum_od = dp_vyhledej_datum_od.getValue();
                LocalDate datum_do = dp_vyhledej_datum_do.getValue();
                Date date_datum_od = java.sql.Date.valueOf(datum_od);
                Date date_datum_do = java.sql.Date.valueOf(datum_do);
                List<Rezervace> seznamRezervaci = new ArrayList<Rezervace>();
                text_area_rezervace.setText("");
                seznamRezervaci=DbUtils.getRezervaceByDatumOdDo(date_datum_od,date_datum_do);

                for (Rezervace rezervace : seznamRezervaci) {
                    text_area_rezervace.appendText(rezervace.infoRezervace()+"\n");
                    text_area_rezervace.appendText("------------------------------------------------------------------\n");
                }
            }
        });

        btn_vyhledej_jmeno_primeni.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String jmeno = tf_vyhledej_jmeno.getText();
                String prijmeni = tf_vyhledej_prijmeni.getText();
                List<Rezervace> seznamRezervaci = new ArrayList<Rezervace>();
                text_area_rezervace.setText("");
                seznamRezervaci=DbUtils.getRezervaceByJmenoPrijemni(jmeno,prijmeni);
                //text_area_rezervace.appendText("Id\t Datum\t Čas od\t Čas do\t Jméno\t Příjmení \t Kontatk\t Poznámka\t Počet osob\t Stůl\t Uživatel\n");
                for (Rezervace rezervace : seznamRezervaci) {
                    text_area_rezervace.appendText(rezervace.infoRezervace()+"\n");
                    text_area_rezervace.appendText("------------------------------------------------------------------\n");
                }

            }
        });

        btn_vyhledej_rezervace_stul.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                String stul = cmb_stoly.getValue().toString();
                List<Rezervace> seznamRezervaci = new ArrayList<Rezervace>();
                text_area_rezervace.setText("");
                seznamRezervaci=DbUtils.getRezervaceByStul(stul);
                //text_area_rezervace.appendText("Id\t Datum\t Čas od\t Čas do\t Jméno\t Příjmení \t Kontatk\t Poznámka\t Počet osob\t Stůl\t Uživatel\n");
                for (Rezervace rezervace : seznamRezervaci) {
                    text_area_rezervace.appendText(rezervace.infoRezervace()+"\n");
                    text_area_rezervace.appendText("------------------------------------------------------------------\n");
                }



            }
        });

        btn_aktualizuj_rezervaci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int id_rezervace = Integer.parseInt(lbl_id_rezervace.getText());
                LocalDate datum = dp_datum.getValue();
                String cas_od = cmb_cas_od.getValue().toString();
                String cas_do = cmb_cas_do.getValue().toString();
                String jmeno = tf_jmeno.getText();
                String prijmeni = tf_prijmeni.getText();
                String kontakt = tf_kontakt.getText();
                String poznamka = tf_poznamka.getText();
                int pocet_osob = Integer.parseInt(cmb_pocetOsob.getValue().toString());
                int id_uzivatele = Integer.parseInt(lbl_id_uzivatele.getText());


                Date date_datum = java.sql.Date.valueOf(datum);

                if(DbUtils.aktualizujRezervaci(id_rezervace,date_datum,cas_od,cas_do,jmeno,prijmeni,kontakt,poznamka,pocet_osob,id_uzivatele)){
                    System.out.println("Záznam byl aktualizován");
                    Alert informace = new Alert(Alert.AlertType.INFORMATION);
                    informace.setContentText("Rezervace byla aktualizována!");
                    informace.show();
                    ZmenaSceny.zmenScenu(actionEvent,"restaurace.fxml","restaurace",750,1200);

                }
                else {
                    System.out.println("nastala chyba");
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Nastala chyba");
                    error.show();
                }

            }
        });

        btn_smazat_rezervaci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int id_rezervace = Integer.parseInt(lbl_id_rezervace.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Opravdu chcete smazat tuto rezervaci?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    if(DbUtils.smazatRezervaci(id_rezervace)==true){
                        Alert informace = new Alert(Alert.AlertType.WARNING);
                        informace.setContentText("Záznam byl odstraněn z databáze");
                        informace.show();
                        ZmenaSceny.zmenScenu(actionEvent,"restaurace.fxml","restaurace",750,1200);
                        }

                    else{
                        System.out.println("nastala chyba");
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setContentText("Nastala chyba");
                        error.show();
                    }

                    }

            }
        });

        btn_vyhledej_ze_seznamu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btn_aktualizuj_rezervaci.setVisible(true);
                btn_smazat_rezervaci.setVisible(true);

                rezervace=cmb_seznamRezervaci.getValue().toString();
                poleString=rezervace.split("-");

                for (Rezervace rezervace : seznamRezervaciDb  ) {
                    if(rezervace.getId_rezervace()==Integer.parseInt(poleString[0])){
                        dp_datum.setValue(rezervace.getDatum().toLocalDate());
                        cmb_cas_od.setValue(rezervace.getCas_od());
                        cmb_cas_do.setValue(rezervace.getCas_do());
                        tf_jmeno.setText(rezervace.getJmeno());
                        tf_prijmeni.setText(rezervace.getPrijmeni());
                        tf_kontakt.setText(rezervace.getKontakt());
                        tf_poznamka.setText(rezervace.getPoznamka());
                        cmb_pocetOsob.setValue(rezervace.getPocet_osob());
                        lbl_id_stolu.setText(String.valueOf(rezervace.getStoly_id_stolu()));
                        lbl_id_uzivatele.setText(String.valueOf(rezervace.getUzivatel_id_uzivatele()));
                        lbl_id_rezervace.setText(String.valueOf(rezervace.getId_rezervace()));
                        cmb_cas_od.setItems(RezervaceController.getDostupneCasy(rezervace.getId_rezervace()));
                        cmb_cas_do.setItems(RezervaceController.getDostupneCasy(rezervace.getId_rezervace()));
                    }
                }


            }
        });

        btn_vytiskni_rezervace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


            }
        });


}}
