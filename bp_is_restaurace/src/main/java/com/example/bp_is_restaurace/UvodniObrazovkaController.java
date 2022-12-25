package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UvodniObrazovkaController implements Initializable {
    private Integer id_uzivatele;
    Uzivatel uzivatel;



    @FXML
    private Button btn_odhlasitSe;

    @FXML
    private Label label_prihlaseni;
    @FXML
    private Label label_pozice;




    @FXML
    private Label label_jmeno;
    @FXML
    private Label label_osobniCislo;
    @FXML
    private Label label_restaurace;

    @FXML
    private Button btn_restaurace;
    @FXML
    private Button btn_administrace;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_uzivatele = DbUtils.getIdPrihlasenehoUzivatele();
        uzivatel = DbUtils.najdiUzivatele(id_uzivatele);
        this.nastavInformace(uzivatel.getJmeno(),uzivatel.getId_uzivatele(),uzivatel.getOsobniCislo());
        label_pozice.setText(uzivatel.getPozice());



        btn_administrace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"administrace.fxml","administrace",400,600);
            }
        });

        btn_odhlasitSe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DbUtils.odhlasitUzivatele();
                ZmenaSceny.zmenScenu(actionEvent,"prihlasovaci-formular-view.fxml","Přihlašovací formulář",400,600);
                id_uzivatele=null;
                uzivatel=null;
            }
        });

        btn_restaurace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace - plánek");
            }
        });

    }

    public void nastavInformace(String jmenoUzivatele, Integer idUzivatele, Integer osobniCislo){
        id_uzivatele=idUzivatele;
        label_jmeno.setText(jmenoUzivatele);
        label_osobniCislo.setText(osobniCislo.toString());
        label_restaurace.setText("Restaurace 1");
    }


}
