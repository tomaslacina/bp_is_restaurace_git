package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ZakaznikController implements Initializable {
    @FXML
    private Button btn_vytvorZakaznika;
    @FXML
    private Button btn_zpet;
    @FXML
    private TextField tf_popis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int id_restaurace = 1; //TODO v případě více restaurací ošetřit!

        btn_vytvorZakaznika.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String popis = tf_popis.getText();

                if(popis.length()>3){

                    if(DbUtils.vytvorZakaznika(popis,id_restaurace)==true){
                        Alert informace = new Alert(Alert.AlertType.INFORMATION);
                        informace.setContentText("Zákazník byl vytvořen");
                        informace.show();
                        ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","restaurace");
                    }
                    else{
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setContentText("Něco se pokazilo, zkus zadat znovu");
                        error.show();
                    }

                }
                else {
                    Alert upozorneni = new Alert(Alert.AlertType.WARNING);
                    upozorneni.setContentText("V poli popis musí být alespoň 3 znaky!");
                    upozorneni.show();

                }


            }
        });



    }
}
