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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class PrihlaseniController implements Initializable {

    @FXML
    private ComboBox cmbox_restaurace;

    @FXML
    private TextField tf_osobniCislo;

    @FXML
    private TextField tf_heslo;

    @FXML
    private Button btn_prihlasitSe;


    private boolean overCislo=true;
    private boolean overeniDat;

    private int osCislo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> restaurace = FXCollections.observableArrayList("Restaurace 1");
        cmbox_restaurace.setItems(restaurace);
        cmbox_restaurace.getSelectionModel().selectFirst();




        btn_prihlasitSe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                overCislo = Pattern.matches("\\d+", tf_osobniCislo.getText());

                if(overCislo==true){
                    osCislo=Integer.parseInt(tf_osobniCislo.getText());
                    overeniDat=true;
                }

                else{
                    Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                    upozorneni.setContentText("V poli osobní číslo musí být zadáno ČÍSLO, zadejte znovu!!!");
                    upozorneni.show();
                    overeniDat=false;
                }

                String heslo = tf_heslo.getText();
                heslo = Zabezpeceni.sha256(heslo);

                if(overeniDat==true){
                    DbUtils.prihlasitUzivatele(actionEvent,osCislo,1,heslo);
                }
                else{
                    Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                    upozorneni.setContentText("Byly chybně zadány hodnoty přihlášení, zadejte znovu");
                    upozorneni.show();
                }

            }
        });
    }
}