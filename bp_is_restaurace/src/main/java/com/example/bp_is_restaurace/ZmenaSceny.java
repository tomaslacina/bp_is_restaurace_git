package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ZmenaSceny {

    /*public static void zmenScenuHlavniMenu(ActionEvent event, String fxmlSoubor, String popisek, int vyska, int sirka, int idUzivatele, String jmenoUzivatele, int osobniCislo){
        Parent root = null;

        if(idUzivatele > 0){
            try{
                FXMLLoader loader = new FXMLLoader(DbUtils.class.getResource(fxmlSoubor));
                root = loader.load();
                UvodniObrazovkaController uvodniObrazovkaController = loader.getController();
                uvodniObrazovkaController.nastavInformace(jmenoUzivatele,idUzivatele,osobniCislo);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                root = FXMLLoader.load(DbUtils.class.getResource(fxmlSoubor));
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(popisek);
        stage.setScene(new Scene(root,sirka,vyska));
        stage.show();

    }*/

    public static void zmenScenu(ActionEvent event, String fxmlSoubor, String popisek, int vyska, int sirka){
        Parent root = null;

        try {
            root = FXMLLoader.load(DbUtils.class.getResource(fxmlSoubor));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(popisek);
        stage.setScene(new Scene(root,sirka,vyska));
        stage.show();

    }

    public static void zmenScenuUvodniObrazovka(ActionEvent event, String fxmlSoubor, String popisek, String jmenoUzivatele, Integer idUzivatele, Integer osobniCislo){
        Parent root = null;

        if(jmenoUzivatele != null && idUzivatele != null){
            try{
                FXMLLoader loader = new FXMLLoader(DbUtils.class.getResource(fxmlSoubor));
                root = loader.load();
                UvodniObrazovkaController uvodniObrazovkaController = loader.getController();
                uvodniObrazovkaController.nastavInformace(jmenoUzivatele,idUzivatele,osobniCislo);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                root = FXMLLoader.load(DbUtils.class.getResource(fxmlSoubor));
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(popisek);
        stage.setScene(new Scene(root,600,400));
        stage.show();

    }
}
