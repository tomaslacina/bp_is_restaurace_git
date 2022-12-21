package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegistrujUzivateleController implements Initializable {
    @FXML
    private TextField tf_jmeno;
    @FXML
    private TextField tf_prijmeni;
    @FXML
    private TextField tf_osobniCislo;
    @FXML
    private PasswordField psw_heslo;
    @FXML
    private ComboBox cmbox_pozice;
    @FXML
    private ComboBox cmbox_restaurace;
    @FXML
    private Button btn_vytvorUzivatele;

    private String jmeno;
    private String prijmeni;
    private int osobni_cislo;
    private String heslo;
    private String pozice;
    private int id_restaurace;
    private boolean overCislo;
    private boolean overeniDat=true;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> restaurace = FXCollections.observableArrayList("Restaurace 1");
        cmbox_restaurace.setItems(restaurace);
        cmbox_restaurace.getSelectionModel().selectFirst();

        ObservableList<String> pozice_hodnoty = FXCollections.observableArrayList("Provozní","Obsluha");
        cmbox_pozice.setItems(pozice_hodnoty);
        cmbox_pozice.getSelectionModel().selectFirst();


        btn_vytvorUzivatele.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jmeno = tf_jmeno.getText();
                prijmeni=tf_prijmeni.getText();

                overCislo = Pattern.matches("\\d+", tf_osobniCislo.getText());

                if(overCislo==true){
                    osobni_cislo=Integer.parseInt(tf_osobniCislo.getText());
                }

                else{
                    Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                    upozorneni.setContentText("V poli osobní číslo musí být Číslo, zadej znovu");
                    upozorneni.show();
                    overeniDat=false;
                    System.out.println("Neplatná hodnota");
                }

                heslo=Zabezpeceni.sha256(psw_heslo.getText());
                System.out.println(heslo);

                pozice=cmbox_pozice.getValue().toString();

                //TODO v případě více restaurací, upravit!
                id_restaurace=1;
                //System.out.println(id_restaurace+" - "+osobni_cislo+" - "+heslo+" - "+jmeno+" - "+prijmeni+" - "+pozice);

                if (overeniDat==true){
                    //pokud je dotaz v pořádku, ulož do databáze a změn scénu
                    if(DbUtils.registraceNovehoUzivatele(actionEvent,id_restaurace,osobni_cislo,heslo,jmeno,prijmeni,pozice)){
                        ZmenaSceny.zmenScenu(actionEvent,"administrace.fxml","administrace",400,600);
                        Alert informace = new Alert(Alert.AlertType.INFORMATION);
                        informace.setContentText("Vytvoření uživatele: "+jmeno+" "+prijmeni+" s osobním číslem "+ osobni_cislo+" proběhlo úspěšně");
                        informace.show();
                    }
                    else{
                        Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                        upozorneni.setContentText("Osobní číslo: "+osobni_cislo+" v databázi pro zvolenou restaurci již existuje, zvolte prosím jiné!");
                        upozorneni.show();
                        overeniDat=true;
                    }
                }
                else{
                    Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                    upozorneni.setContentText("Nebyly vyplněny správě veškeré údaje");
                    upozorneni.show();
                    overeniDat=true;
                }
            }
        });














    }
}
