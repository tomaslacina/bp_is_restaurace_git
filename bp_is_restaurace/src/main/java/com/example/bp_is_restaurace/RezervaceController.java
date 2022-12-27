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
import java.util.*;

public class RezervaceController implements Initializable {
    List <Rezervace> seznamRezervaci = new ArrayList<>();
    private final HashMap<String,Integer> hashMap_casy = new HashMap<String,Integer>();
    private String [] poleCasu = {"11:00:00","11:15:00","11:30:00","11:45:00",
            "12:00:00","12:15:00","12:30:00","12:45:00",
            "13:00:00","13:15:00","13:30:00","13:45:00",
            "14:00:00","14:15:00","14:30:00","14:45:00",
            "15:00:00","15:15:00","15:30:00","15:45:00",
            "16:00:00","16:15:00","16:30:00","16:45:00",
            "17:00:00","17:15:00","17:30:00","17:45:00",
            "18:00:00","18:15:00","18:30:00","18:45:00",
            "19:00:00","19:15:00","19:30:00","19:45:00",
            "20:00:00","20:15:00","20:30:00","20:45:00",
            "21:00:00"};



    SimpleDateFormat sdf_cas = new SimpleDateFormat("hh:mm:ss");
    @FXML
    private Button btn_zpet;
    @FXML
    private Button btn_vytvor;

    @FXML
    private Button btn_vymazat;

    @FXML
    private Button btn_vyberDatum;

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
        btn_vytvor.setVisible(false);
        btn_vymazat.setVisible(false);
        tf_jmeno.setDisable(true);
        tf_prijmeni.setDisable(true);
        tf_kontakt.setDisable(true);
        tf_poznamka.setDisable(true);

        for(int i=0; i<poleCasu.length; i++){
            hashMap_casy.put(poleCasu[i],i);
        }


        ObservableList<String> casy = FXCollections.observableArrayList(
                "11:00:00","11:15:00","11:30:00","11:45:00",
                "12:00:00","12:15:00","12:30:00","12:45:00",
                "13:00:00","13:15:00","13:30:00","13:45:00",
                "14:00:00","14:15:00","14:30:00","14:45:00",
                "15:00:00","15:15:00","15:30:00","15:45:00",
                "16:00:00","16:15:00","16:30:00","16:45:00",
                "17:00:00","17:15:00","17:30:00","17:45:00",
                "18:00:00","18:15:00","18:30:00","18:45:00",
                "19:00:00","19:15:00","19:30:00","19:45:00",
                "20:00:00","20:15:00","20:30:00","20:45:00",
                "21:00:00");


        ObservableList<Integer> pocetOsob = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10);

        cmb_pocetOsob.setItems(pocetOsob);
        cmb_pocetOsob.getSelectionModel().selectFirst();

        lbl_idStolu.setText(String.valueOf(RestauraceController.id_stolu));
        lbl_idUzivatele.setText(String.valueOf(DbUtils.getIdPrihlasenehoUzivatele()));

        dp_datum.setValue(LocalDate.now());


        btn_vyberDatum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btn_vytvor.setVisible(true);
                btn_vymazat.setVisible(true);
                tf_jmeno.setDisable(false);
                tf_prijmeni.setDisable(false);
                tf_kontakt.setDisable(false);
                tf_poznamka.setDisable(false);


                List<String> nedostupne_casy = new ArrayList<>();
                List <Integer> seznamCisel = new ArrayList<>();
                int nizsi;
                int vyssi;
                Time cas_od;
                Time cas_do;

                LocalDate datum = dp_datum.getValue();
                Date date_datum = java.sql.Date.valueOf(datum);
                seznamRezervaci=DbUtils.getSeznamRezervaciDatum(date_datum,RestauraceController.id_stolu);

                for (Rezervace  rezervace : seznamRezervaci) {
                    cas_od = rezervace.getCas_od();
                    cas_do = rezervace.getCas_do();
                    nizsi=hashMap_casy.get(cas_od.toString());
                    vyssi=hashMap_casy.get(cas_do.toString());

                    seznamCisel.add(nizsi);
                    seznamCisel.add(vyssi);

                    for(int i=nizsi+1;i<vyssi;i++){
                        seznamCisel.add(i);
                    }
                }

                for(int i=0;i<seznamCisel.size();i++){
                    nedostupne_casy.add(poleCasu[seznamCisel.get(i)]);
                }
                casy.removeAll(nedostupne_casy);
                cmb_casOd.setItems(casy);
                cmb_casOd.getSelectionModel().selectFirst();
                cmb_casDo.setItems(casy);
                cmb_casDo.getSelectionModel().selectFirst();

            }
        });


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
                Date date_datum = java.sql.Date.valueOf(datum);


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

        btn_vymazat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tf_jmeno.setText("");
                tf_prijmeni.setText("");
                tf_kontakt.setText("");
                tf_poznamka.setText("");
            }
        });

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
            }
        });




    }

    //TODO zkusit změnit čas rezervace
    public static ObservableList<String> getDostupneCasy(int id_rezervace){

        Rezervace rezervaceDb = DbUtils.getRezervaciById(id_rezervace);

        List <Rezervace> seznamRezervaci = new ArrayList<>();
        List<String> nedostupne_casy = new ArrayList<>();
        List <Integer> seznamCisel = new ArrayList<>();



        HashMap<String,Integer> hashMap_casy = new HashMap<String,Integer>();
        String [] poleCasu = {"11:00:00","11:15:00","11:30:00","11:45:00",
                "12:00:00","12:15:00","12:30:00","12:45:00",
                "13:00:00","13:15:00","13:30:00","13:45:00",
                "14:00:00","14:15:00","14:30:00","14:45:00",
                "15:00:00","15:15:00","15:30:00","15:45:00",
                "16:00:00","16:15:00","16:30:00","16:45:00",
                "17:00:00","17:15:00","17:30:00","17:45:00",
                "18:00:00","18:15:00","18:30:00","18:45:00",
                "19:00:00","19:15:00","19:30:00","19:45:00",
                "20:00:00","20:15:00","20:30:00","20:45:00",
                "21:00:00"};

        ObservableList<String> casy = FXCollections.observableArrayList(
                "11:00:00","11:15:00","11:30:00","11:45:00",
                "12:00:00","12:15:00","12:30:00","12:45:00",
                "13:00:00","13:15:00","13:30:00","13:45:00",
                "14:00:00","14:15:00","14:30:00","14:45:00",
                "15:00:00","15:15:00","15:30:00","15:45:00",
                "16:00:00","16:15:00","16:30:00","16:45:00",
                "17:00:00","17:15:00","17:30:00","17:45:00",
                "18:00:00","18:15:00","18:30:00","18:45:00",
                "19:00:00","19:15:00","19:30:00","19:45:00",
                "20:00:00","20:15:00","20:30:00","20:45:00",
                "21:00:00");

        int nizsi;
        int vyssi;
        Time cas_od;
        Time cas_do;

        for(int i=0; i<poleCasu.length; i++){
            hashMap_casy.put(poleCasu[i],i);
        }

        LocalDate datum = rezervaceDb.getDatum().toLocalDate();
        Date date_datum = java.sql.Date.valueOf(datum);
        seznamRezervaci=DbUtils.getSeznamRezervaciDatum(date_datum,rezervaceDb.getStoly_id_stolu());

        for (Rezervace  rezervace : seznamRezervaci) {

            if(rezervace.getId_rezervace()==id_rezervace){
                System.out.println("Tato rezervace se shoduje se zadanou rezervací");

            }
            else{
                cas_od = rezervace.getCas_od();
                cas_do = rezervace.getCas_do();
                nizsi=hashMap_casy.get(cas_od.toString());
                vyssi=hashMap_casy.get(cas_do.toString());
                seznamCisel.add(nizsi);
                seznamCisel.add(vyssi);

                for(int i=nizsi+1;i<vyssi;i++){
                    seznamCisel.add(i);
                }

            }

        }

        for(int i=0;i<seznamCisel.size();i++){
            nedostupne_casy.add(poleCasu[seznamCisel.get(i)]);
        }
        casy.removeAll(nedostupne_casy);
        return casy;
    }


}
