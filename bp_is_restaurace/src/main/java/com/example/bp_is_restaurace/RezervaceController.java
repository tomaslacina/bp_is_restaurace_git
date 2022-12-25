package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class RezervaceController implements Initializable {

    @FXML
    private Button btn_zpet;

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


        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
            }
        });

    }
}
