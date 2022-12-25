package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AkceStoluController implements Initializable {
    @FXML
    private Button btn_zpet;
    @FXML
    private Button btn_vytvorRezervaci;
    @FXML
    private Label lbl_akce;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_akce.setText("AKCE STOLU s ID:"+RestauraceController.id_stolu);

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
            }
        });

        btn_vytvorRezervaci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"formularRezervace.fxml","Rezervační formulář",600,600);

            }
        });

    }
}
