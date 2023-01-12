package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class PokladnaController implements Initializable {
    @FXML
    private Button btn_zpet;
    @FXML
    private Button btn_zobrazTrzbu;
    @FXML
    private Button btn_storno;
    @FXML
    private DatePicker dp_datum_od;
    @FXML
    private DatePicker dp_datum_do;

    @FXML
    private Pane pane_administrator;
    @FXML
    private TextField tf_storno_uctenky;

    @FXML
    private Label lbl_trzba;
    float trzba;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        java.sql.Date datum = new Date(System.currentTimeMillis());
        trzba=DbUtils.getTrzbyZaObdobí(datum,datum);
        lbl_trzba.setText(String.valueOf(trzba)+" Kč");
        pane_administrator.setVisible(false);

        if(UvodniObrazovkaController.pozice.equals("Provozní")){
            pane_administrator.setVisible(true);
        }


        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
            }
        });

        btn_zobrazTrzbu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                trzba=0;
                Date datum_od = Date.valueOf(dp_datum_od.getValue());
                Date datum_do = Date.valueOf(dp_datum_do.getValue());
                trzba=DbUtils.getTrzbyZaObdobí(datum_od,datum_do);
                lbl_trzba.setText("Tržba za zvoelné období: "+String.valueOf(trzba)+" Kč");
            }
        });

        btn_storno.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int id_uctenky=0;
                id_uctenky=Integer.parseInt(tf_storno_uctenky.getText());
                if(id_uctenky>0){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Smazat účtenku ze systému?");
                    alert.setHeaderText("Chystáte se smazat celou účtenku ze systému restaurace!");
                    alert.setContentText("Opravdu chcete vymazat tuto účtenku s id:?\n"+id_uctenky);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        if(DbUtils.vymazatUctenkuAPolozkyUctenky(id_uctenky)==true){
                            Alert info = new Alert(Alert.AlertType.INFORMATION);
                            info.setTitle("Info");
                            info.setContentText("Účtenka a její položky byly odstraněny");
                            info.show();
                            ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
                        }
                    } else {
                        ZmenaSceny.zmenScenu(actionEvent,"pokladna.fxml","Přehled pokladna",450,600);
                    }

                }
            }
        });



    }
}
