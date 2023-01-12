package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlatbaZaCelyStulController implements Initializable {
    private float koeficient21=0.1736f;
    private float koeficient15=0.1304f;
    private float koeficient10=0.0909f;
    public int id_stolu = RestauraceController.id_stolu;
    public int id_uzivatele=DbUtils.getIdPrihlasenehoUzivatele();
    public int id_restaurace=1;//TODO

    @FXML
    private TextArea ta_prehledObjednavek;
    @FXML
    private Label lbl_celkem;

    @FXML
    private Button btn_vystavit_s_tiskem;
    @FXML
    private Button btn_vystavit_bez_tisku;

    @FXML
    private Button btn_zpet;

    @FXML
    private TextField tf_spropitne;
    @FXML
    private TextField tf_poznamka;

    private List<ObjednavkaStul> seznamPolozek = new ArrayList<>();

    private float cenaCelkem;

    private float vypocitaneDPH;


    private float dph10;
    private float dph15;
    private float dph21;

    private float zaklad10;
    private float zaklad15;
    private float zaklad21;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ta_prehledObjednavek.setText("");
        id_stolu=RestauraceController.id_stolu;
        seznamPolozek=DbUtils.getObjednavkyCelehoStolu(id_stolu);
        cenaCelkem=0;
        dph10=0;
        dph15=0;
        dph21=0;
        zaklad10=0;
        zaklad15=0;
        zaklad21=0;


        ta_prehledObjednavek.appendText("Hlavicka\n");
        ta_prehledObjednavek.appendText("############################\n");
        for (ObjednavkaStul objednavka: seznamPolozek) {
            ta_prehledObjednavek.appendText(objednavka.infoNahledUctenky());
            ta_prehledObjednavek.appendText("\n");

            cenaCelkem+=objednavka.getCelkemZaPolozku();

            if(objednavka.getSazbaDPH()==0.21f){
                vypocitaneDPH=0;
                vypocitaneDPH=objednavka.getCelkemZaPolozku()*koeficient21;
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
        ta_prehledObjednavek.appendText("Celková částka :"+cenaCelkem+" Kč \n");
        ta_prehledObjednavek.appendText("-*-*-*-*-*-*-*-ROZPIS DPH-*-*-*-*-*-*-*-\n");
        ta_prehledObjednavek.appendText("DPH 21%:\t"+String.format("%.3f",dph21)+" Kč"+"\t(Základ 21%:"+String.format("%.3f",zaklad21)+")\n");
        ta_prehledObjednavek.appendText("DPH 15%:\t"+String.format("%.3f",dph15)+" Kč"+"\t(Základ 15%:"+String.format("%.3f",zaklad15)+")\n");
        ta_prehledObjednavek.appendText("DPH 10%:\t"+String.format("%.3f",dph10)+" Kč"+"\t(Základ 10%:"+String.format("%.3f",zaklad10)+")\n");
        ta_prehledObjednavek.appendText("-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-\n");
        ta_prehledObjednavek.appendText("Děkujeme za návštěvu\n");
        ta_prehledObjednavek.appendText("Zpracováno informačním systémem pro malé restaurace\n");
        ta_prehledObjednavek.appendText("Autor bezplatného softwaru: Tomáš Lacina\n");
        ta_prehledObjednavek.appendText("Kontakt: xlacina5@mendelu.cz\n");


        lbl_celkem.setText(String.valueOf(cenaCelkem));


        btn_vystavit_bez_tisku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                float spropitne;
                int id_posledniUctenky;

                if(tf_spropitne.getText().length()>0){
                    spropitne=Integer.parseInt(tf_spropitne.getText());
                }
                else {
                    spropitne=0;
                }

                String poznamka = tf_poznamka.getText();


                if(DbUtils.vytvorUctenku(cenaCelkem,dph10,dph15,dph21,spropitne,poznamka,id_restaurace,id_uzivatele)==true){
                    id_posledniUctenky=DbUtils.getIdPosledniUctenky(id_restaurace);
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Úspěch");
                    info.setContentText("Účtenka s ID: "+id_posledniUctenky+" byla úspěšně vytvořena vytvořena");
                    info.show();

                    if(DbUtils.vytvorPolozkyUctenky(seznamPolozek,id_posledniUctenky)==true){
                        Alert informace = new Alert(Alert.AlertType.INFORMATION);
                        informace.setTitle("Úspěch");
                        informace.setContentText("Položky účtenky byly vytvořeny v databázi! \n Zaplacené objednávky stolu byly z DB odstraněny!");
                        informace.show();
                        ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
                    }
                    else{
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Chyba");
                        error.setContentText("Položky účtenky nebyly vytvořeny\n záznamny stolu nebyly odstraněny!");
                    }


                }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba");
                    error.setContentText("Nastala chyba při vytvoření účtenky");
                    error.show();
                }


            }
        }
        );

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
            }
        });



    }
}
