package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlatbaJednotliveController implements Initializable {

    @FXML
    private GridPane gp_polozky;
    @FXML
    private TextArea ta_vypis;

    @FXML
    private TextField tf_spropitne;
    @FXML
    private TextField tf_poznamka;
    @FXML
    private Label lbl_celkem;

    @FXML
    private Button btn_zpet;
    @FXML
    private Button btn_vystavit_s_tiskem;
    @FXML
    private Button btn_vystavit_bez_tisku;


    private List<Button> seznamTlacitekObjednavek = new ArrayList<>();

    private List<ObjednavkaStul> seznamObjednavek = new ArrayList<>();

    private List<ObjednavkaStul> zvolenePolozky = new ArrayList<>();

    private int id_stolu = RestauraceController.id_stolu;
    private int id_uzivatele = DbUtils.getIdPrihlasenehoUzivatele();
    private int id_restaurace = 1;//TODO
    private int pocitadloSloupce = 0;
    private int pocitadloRadky = 0;

    private float cenaCelkem;

    private float vypocitaneDPH;


    private float dph10;
    private float dph15;
    private float dph21;

    private float zaklad10;
    private float zaklad15;
    private float zaklad21;

    private float koeficient21 = 0.1736f;
    private float koeficient15 = 0.1304f;
    private float koeficient10 = 0.0909f;


    Font fontTlacitko = new Font("Arial Black", 14);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seznamObjednavek = DbUtils.getObjednavkyCelehoStolu(id_stolu);

        for (ObjednavkaStul objednavka : seznamObjednavek) {
            Button tlacitkoPolozka = new Button();
            tlacitkoPolozka.setText(objednavka.getNazevPolozky() + "\n" + objednavka.getPocetKs() + "Ks");
            tlacitkoPolozka.setPrefHeight(100);
            tlacitkoPolozka.setMinWidth(180);
            tlacitkoPolozka.setPrefWidth(Region.USE_COMPUTED_SIZE);
            tlacitkoPolozka.setFont(fontTlacitko);
            tlacitkoPolozka.setStyle("-fx-background-color: gray;");
            tlacitkoPolozka.setTextFill(Color.WHITE);
            HBox.setMargin(tlacitkoPolozka, new Insets(5, 10, 5, 10));

            tlacitkoPolozka.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    vlozObjednavkuDoSeznamu(new ObjednavkaStul(objednavka.getIdObjednavky(), 1, id_stolu, objednavka.getIdPolozkyMenu(), objednavka.getNazevPolozky(), objednavka.getCena(), objednavka.getCena(), objednavka.getSazbaDPH()));
                    objednavka.zmensiPocetKs1();

                    if (objednavka.getPocetKs() < 1) {
                        tlacitkoPolozka.setDisable(true);
                        tlacitkoPolozka.setText(objednavka.getNazevPolozky() + "\n" + objednavka.getPocetKs() + "Ks");
                    }
                    tlacitkoPolozka.setText(objednavka.getNazevPolozky() + "\n" + objednavka.getPocetKs() + "Ks");
                    vytiskniSeznamObjednavky();
                }
            });
            seznamTlacitekObjednavek.add(tlacitkoPolozka);
        }

        for (Button tlacitko : seznamTlacitekObjednavek) {

            gp_polozky.add(tlacitko, pocitadloSloupce, pocitadloRadky);

            pocitadloSloupce++;

            if (pocitadloSloupce == 2) {
                pocitadloSloupce = 0;
                pocitadloRadky++;
            }
        }
        gp_polozky.setHgap(10);
        gp_polozky.setVgap(10);

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent, "restaurace.fxml", "Restaurace");
            }
        });


        btn_vystavit_bez_tisku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                float spropitne;
                int id_posledniUctenky;


                if (tf_spropitne.getText().length() > 0) {
                    spropitne = Integer.parseInt(tf_spropitne.getText());
                } else {
                    spropitne = 0;
                }

                String poznamka = tf_poznamka.getText();


                if (DbUtils.vytvorUctenku(cenaCelkem, dph10, dph15, dph21, spropitne, poznamka, id_restaurace, id_uzivatele) == true) {
                    id_posledniUctenky = DbUtils.getIdPosledniUctenky(id_restaurace);
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Úspěch");
                    info.setContentText("Účtenka s ID: " + id_posledniUctenky + " byla úspěšně vytvořena vytvořena");
                    info.show();

                    if (DbUtils.vytvorPolozkyUctenky(zvolenePolozky, id_posledniUctenky) == true) {
                        Alert informace = new Alert(Alert.AlertType.INFORMATION);
                        informace.setTitle("Úspěch");
                        informace.setContentText("Položky účtenky byly vytvořeny v databázi! \n Zaplacené objednávky stolu byly z DB odstraněny!");
                        informace.show();
                        ZmenaSceny.zmenScenuRestaurace(actionEvent, "restaurace.fxml", "Restaurace");
                    } else {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Chyba");
                        error.setContentText("Položky účtenky nebyly vytvořeny\n záznamny stolu nebyly odstraněny!");
                    }


                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba");
                    error.setContentText("Nastala chyba při vytvoření účtenky");
                    error.show();
                }
            }
        });


    }


    public void vlozObjednavkuDoSeznamu(ObjednavkaStul objednavkaStul) {
        boolean shoda = false;

        if (zvolenePolozky.isEmpty() == true) {
            zvolenePolozky.add(objednavkaStul);
            shoda = true; //pri prvnim vlozeni do seznamu

        } else {
            for (ObjednavkaStul objednavka : zvolenePolozky) {
                if (objednavka.getIdPolozkyMenu() == objednavkaStul.getIdPolozkyMenu()) {
                    objednavka.zvysPocetKs1();
                    objednavka.zvysCelkovouCenu(objednavka.getCena());
                    shoda = true;
                    break;
                }
            }
        }

        if (shoda == false) {
            zvolenePolozky.add(objednavkaStul);
        }

    }

    public void vytiskniSeznamObjednavky() {
        ta_vypis.setText("");

        cenaCelkem=0;
        dph10=0;
        dph15=0;
        dph21=0;
        zaklad10=0;
        zaklad15=0;
        zaklad21=0;

        for (ObjednavkaStul objednavka : zvolenePolozky) {
            ta_vypis.appendText(objednavka.infoNahledUctenky());
            ta_vypis.appendText("\n");
            cenaCelkem+=objednavka.getCelkemZaPolozku();

            if(objednavka.getSazbaDPH()==0.21f){
                vypocitaneDPH=0;
                vypocitaneDPH+=objednavka.getCelkemZaPolozku()*koeficient21;
                dph21+=vypocitaneDPH;
                zaklad21+=objednavka.getCelkemZaPolozku();

            }
            else if(objednavka.getSazbaDPH()==0.15f){
                vypocitaneDPH=0;
                vypocitaneDPH=objednavka.getCelkemZaPolozku()*koeficient15;
                dph15+=vypocitaneDPH;
                zaklad15+=objednavka.getCelkemZaPolozku();

            }
            else if(objednavka.getSazbaDPH()==0.10f){
                vypocitaneDPH=0;
                vypocitaneDPH=objednavka.getCelkemZaPolozku()*koeficient10;
                dph10+=vypocitaneDPH;
                zaklad10+=objednavka.getCelkemZaPolozku();
            }
            else{
                vypocitaneDPH=0;
            }

        }
        ta_vypis.appendText("Celková částka :"+cenaCelkem+" Kč \n");
        ta_vypis.appendText("-*-*-*-*-*-*-*-ROZPIS DPH-*-*-*-*-*-*-*-\n");
        ta_vypis.appendText("DPH 21%:\t"+String.format("%.3f",dph21)+" Kč"+"\t(Základ 21%:"+String.format("%.3f",zaklad21)+")\n");
        ta_vypis.appendText("DPH 15%:\t"+String.format("%.3f",dph15)+" Kč"+"\t(Základ 15%:"+String.format("%.3f",zaklad15)+")\n");
        ta_vypis.appendText("DPH 10%:\t"+String.format("%.3f",dph10)+" Kč"+"\t(Základ 10%:"+String.format("%.3f",zaklad10)+")\n");
        ta_vypis.appendText("-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-\n");
        ta_vypis.appendText("Děkujeme za návštěvu\n");
        ta_vypis.appendText("Zpracováno informačním systémem pro malé restaurace\n");
        ta_vypis.appendText("Autor bezplatného softwaru: Tomáš Lacina\n");
        ta_vypis.appendText("Kontakt: xlacina5@mendelu.cz\n");

    }



}






