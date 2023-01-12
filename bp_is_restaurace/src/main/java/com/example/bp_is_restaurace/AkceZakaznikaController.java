package com.example.bp_is_restaurace;

import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AkceZakaznikaController implements Initializable {
    @FXML
    private Button btn_objednavka;
    @FXML
    private Button btn_platba;
    @FXML
    private Button btn_prehledObjednavek;
    @FXML
    private Button btn_zpet;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_objednavka.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"objednavkaZakaznik.fxml","Objednávka zákazníka",750,1600);
            }
        });

        btn_prehledObjednavek.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"prehledObjednavekZakaznika.fxml","Přehled objednávek zákazníka",750,1500);
            }
        });

        btn_platba.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"platbaZakaznika.fxml","Platba zákazníka",750,1000);
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
