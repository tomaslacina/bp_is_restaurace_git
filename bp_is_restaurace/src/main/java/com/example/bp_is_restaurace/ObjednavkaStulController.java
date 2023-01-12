package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ObjednavkaStulController implements Initializable {
    @FXML
    private VBox vbox_menu;

    @FXML
    private Label lbl_vybranaPolozka;
    @FXML
    private Label lbl_korekce;

    @FXML
    private TextArea ta_objednavka;
    @FXML
    private TextArea ta_informace;

    @FXML
    private Button btn_vytvorObjednavku;
    @FXML
    private Button btn_korekce;
    @FXML
    private Button btn_zrusitKorekci;

    @FXML
    private GridPane gp_tabulkaTlacitek;

    @FXML
    private Button btn_zpet;


    private List<Button> seznamTlacitekKategorii = new ArrayList<>();
    private List<Button> seznamTlacitekPolozekMenu = new ArrayList<>();
    private List<KategorieMenu> seznamKategoriiMenu = new ArrayList<>();
    private List<PolozkaMenu> seznamPolozekMenu = new ArrayList<>();

    private List<ObjednavkaStul> seznamPolozekObjednavky = new ArrayList<>();

    private int id_kategorie;
    private int id_polozky;

    private boolean korekce=false;

    private int id_restaurace = 1; //TODO - defaultně nastaveno, v případě potřeby upravit!!!
    private int pocitadloSloupce = 0;
    private int pocitadloRadky = 0;

    //font
    Font fontTlacitko = new Font("Arial Black",14);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_korekce.setVisible(false);
        ta_informace.appendText(vypisInformaceModulObjednavky());

        seznamKategoriiMenu=DbUtils.getKategorieMenu(id_restaurace);

        for (KategorieMenu kategorie: seznamKategoriiMenu) {
            Button tlacitkoKategorie = new Button();

            //styl tlačítka
            tlacitkoKategorie.setPrefWidth(180);
            tlacitkoKategorie.setPrefHeight(100);
            tlacitkoKategorie.setFont(fontTlacitko);
            tlacitkoKategorie.setStyle("-fx-background-color: orange;");
            tlacitkoKategorie.setText(kategorie.getNazevKategorieMenu());
            VBox.setMargin(tlacitkoKategorie,new Insets(10,5,10,0));

            tlacitkoKategorie.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    pocitadloRadky=0;
                    pocitadloSloupce=0;
                    gp_tabulkaTlacitek.getChildren().clear();
                    seznamTlacitekPolozekMenu.clear();
                    id_kategorie=kategorie.getId_kategorieMenu();
                    seznamPolozekMenu=DbUtils.getPolozkyMenuByIdKategorieMenu(id_kategorie);

                    for (PolozkaMenu polozka : seznamPolozekMenu) {
                        Button tlacitkoPolozka = new Button();
                        tlacitkoPolozka.setText(polozka.getNazev()+"\n"+polozka.getCena()+" Kč");

                        tlacitkoPolozka.setPrefHeight(100);
                        tlacitkoPolozka.setPrefWidth(180);
                        tlacitkoPolozka.setFont(fontTlacitko);
                        tlacitkoPolozka.setStyle("-fx-background-color: gray;");
                        tlacitkoPolozka.setTextFill(Color.WHITE);
                        HBox.setMargin(tlacitkoPolozka,new Insets(5,10,5,10));

                        tlacitkoPolozka.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                ta_objednavka.setText("");
                                id_polozky=polozka.getIdPolozkyMenu();
                                lbl_vybranaPolozka.setText(polozka.getNazev());
                                lbl_korekce.setVisible(false);
                                vlozObjednavkuDoSeznamu(id_polozky,polozka.getNazev());
                                vytiskniSeznamObjednavky();
                            }
                        });
                        seznamTlacitekPolozekMenu.add(tlacitkoPolozka);
                    }
                    for (Button tlacitko: seznamTlacitekPolozekMenu) {

                        gp_tabulkaTlacitek.add(tlacitko,pocitadloSloupce,pocitadloRadky);

                        pocitadloSloupce++;

                        if(pocitadloSloupce==3){
                            pocitadloSloupce=0;
                            pocitadloRadky++;
                        }
                    }
                    gp_tabulkaTlacitek.setHgap(10);
                    gp_tabulkaTlacitek.setVgap(10);

                }
            });
            seznamTlacitekKategorii.add(tlacitkoKategorie);
        }
        vbox_menu.getChildren().addAll(seznamTlacitekKategorii);



        btn_korekce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                korekce=true;
                lbl_korekce.setVisible(true);
            }
        });

        btn_zrusitKorekci.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                korekce=false;
                lbl_korekce.setVisible(false);
            }
        });

        btn_zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ZmenaSceny.zmenScenuRestaurace(actionEvent,"restaurace.fxml","Restaurace");
            }
        });
        btn_vytvorObjednavku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int id_stolu =RestauraceController.id_stolu;
                int id_uzivatele = DbUtils.getIdPrihlasenehoUzivatele();

                    if(DbUtils.vytvorObjednavkuStolu(id_stolu,id_uzivatele,seznamPolozekObjednavky)==true){
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Úspěch");
                        info.setContentText("Objednávka pro stůl:"+id_stolu+" byla úspěšně vytvořena");
                        info.show();
                        ZmenaSceny.zmenScenu(actionEvent,"restaurace.fxml","Restaurace",750,1200);
                    }
                    else{
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Chyba");
                        error.setContentText("Nastala neočekávaná chyba");
                        error.show();

                    }
            }
        });

    }



    public void vlozObjednavkuDoSeznamu(int id_polozky, String nazev){
        boolean shoda = false;

        if(seznamPolozekObjednavky.isEmpty()==true){
            seznamPolozekObjednavky.add(new ObjednavkaStul(1,id_polozky,nazev));
            shoda=true; //pri prvnim vlozeni do seznamu

        }
        else {
            for (ObjednavkaStul objednavka: seznamPolozekObjednavky) {
                if(objednavka.getIdPolozkyMenu()==id_polozky&&korekce==false){
                    objednavka.zvysPocetKs1();
                    shoda=true;
                    break;
                }
                if(objednavka.getIdPolozkyMenu()==id_polozky&&korekce==true){
                    objednavka.zmensiPocetKs1();
                    if(objednavka.getPocetKs()<1){
                        seznamPolozekObjednavky.remove(objednavka);
                        shoda=true;
                        korekce=false;
                        break;
                    }
                    shoda=true;
                    korekce=false;
                }
            }
        }

        if(shoda==false){
            seznamPolozekObjednavky.add(new ObjednavkaStul(1,id_polozky,nazev));
        }

    }

    public void vytiskniSeznamObjednavky(){
        ta_objednavka.setText("");
        for (ObjednavkaStul objednavka: seznamPolozekObjednavky) {
            ta_objednavka.appendText(objednavka.info());
        }

    }

    public String vypisInformaceModulObjednavky(){
        StringBuffer sb_informace=new StringBuffer();
        sb_informace.append("Objednávkový modul - návod:\n");
        sb_informace.append("1. Zvolte kategorii menu\n");
        sb_informace.append("2. Vyberte z nabízených položek dané kategorie\n");
        sb_informace.append("3. Kliknutím na danou položku ji přidáte do nové objednávky (+1ks) \n");
        sb_informace.append("4. Do jedné objednávky lze přidat položky z více kategorií\n");
        sb_informace.append("5. V případě chybného zadání lze provést korekce:\n");
        sb_informace.append("6. Po stisknutí tlačítka <<Korekce>> se aktivuje funkce storno položky (-1ks)\n");
        sb_informace.append("7. Poté stačí kliknout na potřebnou položku (jako při objednání) \n a z původní objednávky se tato položka odstraní\n");
        sb_informace.append("8. Pro korekci další položky je nutné postupovat od kroku číslo 6.\n");
        sb_informace.append("9. Aktivovanou funkci <<Korekce>> lze vypnout stiskem tlačítka <<Zrušit korekci>>\n");
        sb_informace.append("10. Je-li objednávka správně vyplněna, kliknutím na tlačíko <<Vytvořit objednávku>> \n ji uložíte do databáze\n");
        return sb_informace.toString();
    }




}
