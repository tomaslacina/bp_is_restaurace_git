package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RezervaceController implements Initializable {

    private HashMap<String,Integer> hashMap_casy = new HashMap<String, Integer>();
    private String [] poleCasu = {"11:00","11:15","11:30","11:45",
            "12:00","12:15","12:30","12:45",
            "13:00","13:15","13:30","13:45",
            "14:00","14:15","14:30","14:45",
            "15:00","15:15","15:30","15:45",
            "16:00","16:15","16:30","16:45",
            "17:00","17:15","17:30","17:45",
            "18:00","18:15","18:30","18:45",
            "19:00","19:15","19:30","19:45",
            "20:00","20:15","20:30","20:45",
            "21:00"};



    SimpleDateFormat sdf_cas = new SimpleDateFormat("hh:mm:ss");
    //SimpleDateFormat sdf_casDo = new SimpleDateFormat("hh:mm:ss");
    @FXML
    private Button btn_zpet;
    @FXML
    private Button btn_vytvor;

    @FXML
    private Button btn_vymaz;

    @FXML
    private DatePicker dp_datum;

    @FXML
    private ComboBox cmb_casOd;
    @FXML
    private ComboBox cmb_casDo;
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
    private Label lbl_idStolu;
    @FXML
    private Label lbl_idUzivatele;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(int i=0; i<poleCasu.length; i++){
            hashMap_casy.put(poleCasu[i],i);
        }

        //vybrat rezervace
        //odstranit datumy




        ObservableList<String> casy = FXCollections.observableArrayList(
                "11:00","11:15","11:30","11:45",
                "12:00","12:15","12:30","12:45",
                "13:00","13:15","13:30","13:45",
                "14:00","14:15","14:30","14:45",
                "15:00","15:15","15:30","15:45",
                "16:00","16:15","16:30","16:45",
                "17:00","17:15","17:30","17:45",
                "18:00","18:15","18:30","18:45",
                "19:00","19:15","19:30","19:45",
                "20:00","20:15","20:30","20:45",
                "21:00");
        ObservableList<Integer> pocetOsob = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);

        cmb_casOd.setItems(casy);
        cmb_casOd.getSelectionModel().selectFirst();

        cmb_casDo.setItems(casy);
        cmb_casDo.getSelectionModel().selectFirst();

        cmb_pocetOsob.setItems(pocetOsob);
        cmb_pocetOsob.getSelectionModel().selectFirst();

        lbl_idStolu.setText(String.valueOf(RestauraceController.id_stolu));
        lbl_idUzivatele.setText(String.valueOf(DbUtils.getIdPrihlasenehoUzivatele()));

        btn_vytvor.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate datum = dp_datum.getValue();
                String cas_od = String.valueOf(cmb_casOd.getValue());
                String cas_do = String.valueOf(cmb_casDo.getValue());
                String jmeno = tf_jmeno.getText();
                String prijmeni = tf_prijmeni.getText();
                String kontakt = tf_kontakt.getText();
                String poznamka = tf_poznamka.getText();
                int pocet_osob = (int) cmb_pocetOsob.getValue();
                int id_stolu = RestauraceController.id_stolu;
                int id_uzivatele = DbUtils.getIdPrihlasenehoUzivatele();
               // System.out.println(cas_od);
                //System.out.println(cas_do);
                //Time time_cas_od = java.sql.Time.valueOf(cas_od);
               // Time time_cas_do = java.sql.Time.valueOf(cas_do);
                Date date_datum = java.sql.Date.valueOf(datum);

                /*if(DbUtils.overDostupnostRezervace(date_datum,cas_od,cas_do,id_stolu)>0){
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("V databázi existuje rezervace pro tento stůl v zadaný čas (nebo v průběhu zadaného rozmezí)");
                    error.show();
                }*/

                    if(DbUtils.vytvorRezervaci(date_datum,cas_od,cas_do,jmeno,prijmeni,kontakt,poznamka,pocet_osob,id_stolu,id_uzivatele)==true){
                        Alert informace = new Alert(Alert.AlertType.INFORMATION);
                        informace.setContentText("Rezervace na jméno: "+jmeno+" "+prijmeni+" pro stůl"+id_stolu+" na datum:"+date_datum+" od "+cas_od+" do"+cas_do+"byla úspěšně vytvořena");
                        informace.show();
                        ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","restaurace");
                    }
                    else{
                        System.out.println("Nastala chyba");
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setContentText("Nastala chyba při komunikaci s databází");
                        error.show();
                    }
            }
        });




        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
            }
        });

    }
}
