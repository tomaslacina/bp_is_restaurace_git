package com.example.bp_is_restaurace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrehledObjednavekZakaznikaController implements Initializable {
    @FXML
    private TextArea ta_prehled;

    @FXML
    private ComboBox cmb_objednavky;
    @FXML
    private Spinner<Integer> spinner_novy_pocet;
    @FXML
    private Button btn_storno;


    @FXML
    private Button btn_zvolit_objednavku;
    @FXML
    private Button btn_novy_pocet;
    @FXML
    private Button btn_info;
    @FXML
    private Button btn_zobraz_objednavky;
    @FXML
    private Button btn_zpet;

    private List<ObjednavkaZakaznik> seznam_objednavek_db;

    private String zakaznik;
    private String objednavka;

    private int id_restaurace=1; //TODO v případě změny upravit

    private int id_zakaznika=ZakazniciRestauraceController.id_zakaznika;
    private int id_zvoleneObjednavky;
    private int staryPocet;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seznam_objednavek_db=DbUtils.getObjednavkyZakaznikaByIdZakaznika(id_zakaznika);
        ta_prehled.setText("");

        for (ObjednavkaZakaznik objednavka: seznam_objednavek_db) {
            ta_prehled.appendText(objednavka.infoPrehled());
        }

        ObservableList<ObjednavkaZakaznik> seznamObjednavekZakaznika = FXCollections.observableArrayList(seznam_objednavek_db);
        cmb_objednavky.setItems(seznamObjednavekZakaznika);
        cmb_objednavky.getSelectionModel().selectFirst();




        btn_zvolit_objednavku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String[] poleString;
                objednavka=cmb_objednavky.getValue().toString();
                poleString=objednavka.split("-");
                id_zvoleneObjednavky=Integer.parseInt(poleString[0]);
                staryPocet=Integer.parseInt(poleString[2]);
                SpinnerValueFactory<Integer> hodnoty = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, staryPocet);
                spinner_novy_pocet.setValueFactory(hodnoty);
            }

        });
        btn_novy_pocet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int novyPocet = spinner_novy_pocet.getValue();

                if(DbUtils.aktualizujPocetKusuObjednavkaZakaznika(id_zvoleneObjednavky,novyPocet)==true){
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Úspěch");
                    info.setContentText("U objednávky s id: "+id_zvoleneObjednavky+" byl aktualizován původní počet:"+staryPocet+" na:"+novyPocet);
                    info.show();
                    ZmenaSceny.zmenScenu(actionEvent,"prehledObjednavekZakaznika.fxml","Přehled objednávek zákazníka",750,1200);
                }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Chyba");
                    error.setContentText("Nastala chyba při aktualizaci počtu kusů");
                    error.show();
                }
            }
        });

        btn_storno.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Smazat položku objednávky?");
                alert.setHeaderText("Chystáte se smazat celou položku z objednávky!");
                alert.setContentText("Opravdu chcete vymazat tuto objednávku?\n"+objednavka);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    if(DbUtils.smazatPolozkuObjednavkyZakaznikaById(id_zvoleneObjednavky)==true){
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Info");
                        info.setContentText("Položka byla odstraněna");
                        info.show();
                        ZmenaSceny.zmenScenu(actionEvent,"prehledObjednavekZakaznika.fxml","Přehled objednávek zákazníka",750,1200);
                    }
                } else {
                    ZmenaSceny.zmenScenu(actionEvent,"prehledObjednavekZakaznika.fxml","Přehled objednávek zákazníka",750,1200);
                }
            }
        });



        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenu(actionEvent,"restaurace.fxml","Restaurace",750,1200);
            }
        });

        btn_zobraz_objednavky.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ta_prehled.setText("");
                for (ObjednavkaZakaznik objednavka: seznam_objednavek_db) {
                    ta_prehled.appendText(objednavka.infoPrehled());
                }
            }
        });

        btn_info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ta_prehled.setText("");
                ta_prehled.appendText("V tomto modulu naleznete přehled objednávek zvoleného zákazníka.\n" +
                        "V právé části obrazovky můžete provést několik akcí:\n" +
                        "-----------------------------------------------\n" +
                        "1. Změnit počet kusů u položky z již vytvořené objednávky:\n" +
                        "->Vyberte objednávku k editaci ze seznamu.\n" +
                        "->Potvrďte volbu stiknutím tlačítka <<Zvolit objednávku>>.\n" +
                        "->Pomocí spinneru zvolte nový počet (systém přednastaví aktuální hodnotu zvolené objednávky)\n" +
                        "->Stisknutím tlačítka <<Zadat nový počet>> aktualizujete počet kusů této objednávky.\n" +
                        "-----------------------------------------------\n" +
                        "2. STORNO celé položky\n" +
                        "->Vyberte objednávku ze seznamu.\n" +
                        "->Potvrďte volbu stiknutím tlačítka <<Zvolit objednávku>>.\n" +
                        "->Stisknutím tlačítka <<STORNO CELÉ POLOŽKY Z OBJEDNÁVKY>> provedete storno\n" +
                        "->Systém se pro jistotu zeptá v dialogovém okně jestli si uživatel přeje provést tuto akci.\n" +
                        "->TATO AKCE JE NEVRATNÁ !\n" +
                        "-----------------------------------------------\n" +
                        "3. Tlačítko <<Zobraz objednávky>> zobrazí všechny objednávky zákazníka.\n" +
                        "-----------------------------------------------\n" +
                        "4. Tlačítko <<Informace k modulu>> zobrazí tento text.\n" +
                        "-----------------------------------------------\n" +
                        "5. Stisknutím tlačítka <<Zpět do restaurace>> se vrátíte na přehled restaurace.");
            }
        });





    }
}
