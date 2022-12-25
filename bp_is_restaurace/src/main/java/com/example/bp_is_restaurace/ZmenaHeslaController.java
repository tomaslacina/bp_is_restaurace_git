package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class ZmenaHeslaController implements Initializable {
    Uzivatel uzivatel;
    private String stareHeslo;
    private String noveHeslo;
    private String noveHesloOvereni;
    private String stareHesloDb;
    private boolean overeni;
    @FXML
    private Label label_stare;
    @FXML
    private Label label_nove;
    @FXML
    private Label label_overeni;
    @FXML
    private PasswordField psw_stare;
    @FXML
    private PasswordField psw_nove;
    @FXML
    private PasswordField psw_overeni;

    @FXML
    private Button btn_zpet;
    @FXML
    private Button btn_zmenit;
    @FXML
    private Button btn_ukazat;
    @FXML
    private Button btn_vymazat;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"administrace.fxml","administrace",400,600);
            }
        });

        btn_ukazat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                label_stare.setText(psw_stare.getText());
                label_nove.setText(psw_nove.getText());
                label_overeni.setText(psw_overeni.getText());

            }
        });

        btn_vymazat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                label_stare.setText("");
                label_nove.setText("");
                label_overeni.setText("");
                psw_stare.setText("");
                psw_nove.setText("");
                psw_overeni.setText("");
            }
        });

        btn_zmenit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                uzivatel = DbUtils.najdiUzivatele(DbUtils.getIdPrihlasenehoUzivatele());
                stareHesloDb=uzivatel.getHeslo();

                stareHeslo=Zabezpeceni.sha256(psw_stare.getText());
                noveHeslo=Zabezpeceni.sha256(psw_nove.getText());
                noveHesloOvereni=Zabezpeceni.sha256(psw_overeni.getText());

                if(stareHesloDb.equals(stareHeslo)==true){

                    if(noveHeslo.equals(noveHesloOvereni)==true){
                        System.out.println("zadané nové heslo je ověřeno");
                        overeni=true;
                    }
                    else{
                        System.out.println("Zadané nové heslo se neshoduje s heslem pro ověření");
                        overeni=false;
                        Alert upozorneni = new Alert(Alert.AlertType.INFORMATION);
                        upozorneni.setContentText("Nové heslo se neshoduje s heslem pro ověření, zadejte znovu");
                        upozorneni.show();
                    }
                }
                else {
                    System.out.println("Vaše staré heslo není zadané správě");
                    overeni=false;
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Zadali jste neplatné staré (původní) heslo");
                    error.show();
                }

                if(overeni==true){
                    DbUtils.zmenitHesloUzivateli(noveHeslo,uzivatel.getId_uzivatele());
                    ZmenaSceny.zmenScenu(actionEvent,"administrace.fxml","administrace",400,600);
                    Alert upozorneni = new Alert(Alert.AlertType.INFORMATION);
                    upozorneni.setContentText("Změna hesla proběhla úspěšně");
                    upozorneni.show();
                }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Zadané informace nejsou správné");
                    error.show();
                    label_stare.setText("");
                    label_nove.setText("");
                    label_overeni.setText("");
                    psw_stare.setText("");
                    psw_nove.setText("");
                    psw_overeni.setText("");
                }




            }
        });





    }

}
