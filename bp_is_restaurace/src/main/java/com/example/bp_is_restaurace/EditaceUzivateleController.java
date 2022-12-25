package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditaceUzivateleController implements Initializable {
    List <Uzivatel> seznamUzivateluDb = DbUtils.getSeznamUzivatelu(1);
    String uzivatel;
    String[] poleString;

    @FXML
    private ComboBox cmb_uzivatele;
    @FXML
    private ComboBox cmb_pozice;
    @FXML
    private Button btn_vyberUzivatele;
    @FXML
    private Button btn_potvrdit;
    @FXML
    private Button btn_zpet;
    @FXML
    private TextField tf_jmeno;
    @FXML
    private TextField tf_prijmeni;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_potvrdit.setVisible(false);

        ObservableList<String> pozice_hodnoty = FXCollections.observableArrayList("Obsluha","Provozní");
        cmb_pozice.setItems(pozice_hodnoty);
        //cmb_pozice.getSelectionModel().selectFirst();


        ObservableList <Uzivatel> seznamUzivatelu = FXCollections.observableArrayList(seznamUzivateluDb);
        cmb_uzivatele.setItems(seznamUzivatelu);
        cmb_uzivatele.getSelectionModel().selectFirst();


        btn_vyberUzivatele.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                uzivatel=cmb_uzivatele.getValue().toString();
                poleString=uzivatel.split("-");

                for (Uzivatel uzivatel : seznamUzivateluDb  ) {
                    if(uzivatel.getId_uzivatele()==Integer.parseInt(poleString[0])){
                        tf_jmeno.setText(uzivatel.getJmeno());
                        tf_prijmeni.setText(uzivatel.getPrijmeni());
                        cmb_pozice.getSelectionModel().select(uzivatel.getPozice());
                        btn_potvrdit.setVisible(true);
                    }
                }
            }
        });

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"administrace.fxml","administrace",400,600);
            }
        });

        btn_potvrdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(DbUtils.aktualizujInformaceUzivatel(Integer.parseInt(poleString[0]),tf_jmeno.getText(),tf_prijmeni.getText(),cmb_pozice.getValue().toString())==true){
                    ZmenaSceny.zmenScenu(actionEvent,"administrace.fxml","administrace",400,600);
                    Alert informace = new Alert(Alert.AlertType.INFORMATION);
                    informace.setContentText("Údaje o uživateli byli změněny!");
                    informace.show();
                }
                else {
                    Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                    upozorneni.setContentText("Nastala chyba");
                    upozorneni.show();
                }
            }
        });



    }
}
