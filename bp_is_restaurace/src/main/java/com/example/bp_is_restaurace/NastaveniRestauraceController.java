package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class NastaveniRestauraceController implements Initializable {

    @FXML
    private Button btn_vytvoritUzivatele;

    @FXML
    private Button btn_editaceUzivatele;

    @FXML
    private Button btn_spravaPolozek;

    @FXML
    private Button btn_spravaKategorii;

    @FXML
    private Button btn_zpet;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"uvodniObrazovka.fxml","hlavni nabídka",400,600);
            }
        });

        btn_vytvoritUzivatele.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"registraceUzivatele.fxml","Nový uživatel",500,600);
            }
        });

        btn_editaceUzivatele.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"editaceUzivatele.fxml","Editace uzivatele",400,600);
            }
        });

        btn_spravaKategorii.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"spravaKategoriiMenu.fxml","Správa kategorií menu",550,650);
            }
        });

        btn_spravaPolozek.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"spravaPolozekMenu.fxml","Správa položek menu",600,1000);
            }
        });



    }
}
