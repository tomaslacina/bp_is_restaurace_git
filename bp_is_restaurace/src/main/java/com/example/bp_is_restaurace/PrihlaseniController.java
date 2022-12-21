package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class PrihlaseniController implements Initializable {

    @FXML
    private ComboBox cmbox_restaurace;

    @FXML
    private TextField tf_osobniCislo;

    @FXML
    private TextField tf_heslo;

    @FXML
    private Button btn_prihlasitSe;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> restaurace = FXCollections.observableArrayList("Restaurace 1");
        cmbox_restaurace.setItems(restaurace);
        cmbox_restaurace.getSelectionModel().selectFirst();





        btn_prihlasitSe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Integer osCislo = Integer.parseInt(tf_osobniCislo.getText());
                String heslo = tf_heslo.getText();

                DbUtils.prihlasitUzivatele(actionEvent,osCislo,1,heslo);
            }
        });
    }
}