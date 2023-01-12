package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ZakazniciRestauraceController implements Initializable {
    public static int id_zakaznika;
    private int id_restaurace=1;//TODO v případě více restaurací,...
    private int pocitadloRadky;
    private int pocitadloSloupce;
    private List<Zakaznik> seznamZakazniku = new ArrayList<>();
    private List<Button> seznamTlacitekZakaznici = new ArrayList<>();

    @FXML
    private Button btn_zpet;

    @FXML
    private GridPane gp_tabulka_zakaznici;

    Font fontTlacitko = new Font("Arial Black",14);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seznamZakazniku=DbUtils.getSeznamZakazniku(id_restaurace);
        pocitadloRadky=0;
        pocitadloSloupce=0;


        for (Zakaznik zakaznik:seznamZakazniku) {
            Button tlacitko = new Button();
            tlacitko.setText(zakaznik.getOznaceni()+"\n(ID:"+zakaznik.getId_zakaznika()+")");
            tlacitko.setPrefHeight(100);
            tlacitko.setPrefWidth(Region.USE_COMPUTED_SIZE);
            tlacitko.setMinWidth(180);
            tlacitko.setFont(fontTlacitko);
            tlacitko.setStyle("-fx-background-color: darkgreen;");
            tlacitko.setTextFill(Color.WHITE);

            tlacitko.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    id_zakaznika=zakaznik.getId_zakaznika();
                    ZmenaSceny.zmenScenu(actionEvent,"akceZakaznika.fxml","Akce zákazníka",600,800);
                }
            });
            seznamTlacitekZakaznici.add(tlacitko);
        }

        for (Button tlacitko: seznamTlacitekZakaznici) {
            gp_tabulka_zakaznici.add(tlacitko,pocitadloSloupce,pocitadloRadky);
            pocitadloSloupce++;
            if(pocitadloSloupce==3){
                pocitadloSloupce=0;
                pocitadloRadky++;
            }

        }
        gp_tabulka_zakaznici.setHgap(10);
        gp_tabulka_zakaznici.setVgap(10);

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");

            }
        });
    }
}
