package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministraceController implements Initializable {

    private Uzivatel uzivatel;
    private int id_uzivatele;
    @FXML
    private Button btn_vytvoritUzivatele;

    @FXML
    private Button btn_zmenitHeslo;

    @FXML
    private Button btn_editaceUzivatele;

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
    }

    public void nastavUzivatele(int idUzivatele){
        uzivatel=DbUtils.najdiUzivatele(idUzivatele);
        System.out.println("Administrace:" + uzivatel.toString());

    }
    public void nastavId(int idUzivatele){
        id_uzivatele=idUzivatele;
    }







}
