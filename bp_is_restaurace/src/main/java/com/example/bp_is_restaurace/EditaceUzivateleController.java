package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditaceUzivateleController implements Initializable {
    List <Uzivatel> seznamUzivateluDb = DbUtils.getSeznamUzivatelu(1);


    @FXML
    private ComboBox cmb_uzivatele;
    @FXML
    private Button btn_vyberUzivatele;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ObservableList <Uzivatel> seznamUzivatelu = FXCollections.observableArrayList(seznamUzivateluDb);
        cmb_uzivatele.setItems(seznamUzivatelu);
        cmb_uzivatele.getSelectionModel().selectFirst();

       /* btn_vyberUzivatele.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });*/



    }
}
