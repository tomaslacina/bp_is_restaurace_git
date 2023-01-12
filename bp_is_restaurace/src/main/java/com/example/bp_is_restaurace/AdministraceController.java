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
    private Button btn_zmenitHeslo;

    @FXML
    private Button btn_zpet;

    @FXML
    private Button btn_nastaveniRestaurace;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(UvodniObrazovkaController.pozice.equals("Obsluha")){
            btn_nastaveniRestaurace.setVisible(false);
        }

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"uvodniObrazovka.fxml","hlavni nabídka",400,600);
            }
        });


        btn_zmenitHeslo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"zmenaHesla.fxml","Změna hesla",400,600);
            }
        });

        btn_nastaveniRestaurace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"nastaveniRestaurace.fxml","Nastavení restaurace",500,700);
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
