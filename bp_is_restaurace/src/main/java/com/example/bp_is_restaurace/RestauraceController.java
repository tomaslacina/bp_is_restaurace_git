package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class RestauraceController implements Initializable {

    public static int id_stolu=0;
    @FXML
    private Button btn_salonek;
    @FXML
    private Button btn_l1;
    @FXML
    private Button btn_l2;
    @FXML
    private Button btn_l3;
    @FXML
    private Button btn_l4;
    @FXML
    private Button btn_bar1;
    @FXML
    private Button btn_bar2;
    @FXML
    private Button btn_bar3;
    @FXML
    private Button btn_bar4;
    @FXML
    private Button btn_bar5;
    @FXML
    private Button btn_bar6;
    @FXML
    private Button btn_stred1;
    @FXML
    private Button btn_stred2;
    @FXML
    private Button btn_stred3;
    @FXML
    private Button btn_okno1;
    @FXML
    private Button btn_okno2;
    @FXML
    private Button btn_okno3;

    @FXML
    private Button btn_sprava_rezervaci;

    @FXML
    private Button btn_vytvorZakaznika;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_sprava_rezervaci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"spravaRezervaci.fxml","Správa rezervací",750,1000);
            }
        });

        btn_salonek.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=1;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_l1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=2;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_l2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=3;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_l3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=4;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_l4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=5;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_bar1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=6;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_bar2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=7;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_bar3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=8;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_bar4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=9;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_bar5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=10;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_bar6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=11;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_stred1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=12;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_stred2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=13;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_stred3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=14;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_okno1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=15;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_okno2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=16;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_okno3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id_stolu=17;
                ZmenaSceny.zmenScenu(actionEvent,"akce_stolu.fxml","akce stolu",400,600);
            }
        });

        btn_vytvorZakaznika.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"zakaznik.fxml","Zákazníci",400,600);
            }
        });


    }
}
