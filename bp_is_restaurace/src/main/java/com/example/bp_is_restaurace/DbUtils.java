package com.example.bp_is_restaurace;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DbUtils {

    public static boolean registraceNovehoUzivatele(ActionEvent event, Integer idRestaurace, Integer osobniCislo, String heslo, String jmeno, String prijmeni, String pozice){
        Connection spojeni = null;
        PreparedStatement psVloz = null;
        PreparedStatement psOverExistenci = null;
        ResultSet vysledekDotazu = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psOverExistenci = spojeni.prepareStatement("SELECT * FROM uzivatele WHERE restaurace_id_restaurace = ? and osobni_Cislo = ?");
            psOverExistenci.setInt(1,idRestaurace);
            psOverExistenci.setInt(2,osobniCislo);
            vysledekDotazu = psOverExistenci.executeQuery();


            if(vysledekDotazu.isBeforeFirst()){
                System.out.println("Uzivatel v této restauraci s tímto osobním číslem již existuje");
                Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                upozorneni.setContentText("Toto osobní číslo je již zabráno jiným uživatelem, zvolte prosím jiné");
                upozorneni.show();
                return false;

            }
            else {
                psVloz = spojeni.prepareStatement("INSERT INTO bp_restaurace.uzivatele (jmeno, prijmeni, pozice, osobni_cislo, heslo, restaurace_id_restaurace) VALUES (?,?,?,?,?,?)");
                psVloz.setString(1,jmeno);
                psVloz.setString(2,prijmeni);
                psVloz.setString(3,pozice);
                psVloz.setInt(4,osobniCislo);
                psVloz.setString(5,heslo);
                psVloz.setInt(6,idRestaurace);
                System.out.println(psVloz);
                psVloz.executeUpdate();
                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        finally {
            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psVloz != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psOverExistenci != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(vysledekDotazu != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }


    }

    public static void prihlasitUzivatele(ActionEvent event, Integer osobniCislo, Integer idRestaurace, String heslo){

        Connection spojeni = null;
        PreparedStatement psPrihlasit = null;
        PreparedStatement psVlozPrihlaseni = null;
        ResultSet vysledekDotazu = null;
        Timestamp datumCasPrihlaseni = new Timestamp(System.currentTimeMillis());


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psPrihlasit = spojeni.prepareStatement("SELECT * from uzivatele WHERE osobni_cislo = ? and restaurace_id_restaurace = ?");
            psPrihlasit.setInt(1,osobniCislo);
            psPrihlasit.setInt(2, idRestaurace);
            System.out.println(psPrihlasit);
            vysledekDotazu = psPrihlasit.executeQuery();

            if(!vysledekDotazu.isBeforeFirst()){
                System.out.println("Uživatel nebyl v databázi nalezen");
                Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                upozorneni.setContentText("Uživatel v této restauraci a s tímto osobním číslem v databázi neexistuje");
                upozorneni.show();
            }
            else {
                while (vysledekDotazu.next()){
                    String hesloDb = vysledekDotazu.getString("heslo");
                    String jmenoDb = vysledekDotazu.getString("jmeno");
                    Integer idUzivateleDb = vysledekDotazu.getInt("id_uzivatele");


                    if(hesloDb.equals(heslo)){
                        ZmenaSceny.zmenScenuUvodniObrazovka(event,"uvodniObrazovka.fxml","Hlavní menu",jmenoDb,idUzivateleDb,osobniCislo);
                        //zmenScenu(event, "uvodniObrazovka.fxml","Restaurace",jmenoDb, idUzivateleDb, osCisloDb);
                        psVlozPrihlaseni = spojeni.prepareStatement("INSERT INTO prihlaseni (datum_cas_prihlaseni,uzivatele_id_uzivatele) VALUES (?,?)");
                        psVlozPrihlaseni.setTimestamp(1,datumCasPrihlaseni);
                        psVlozPrihlaseni.setInt(2,idUzivateleDb);
                        psVlozPrihlaseni.executeUpdate();
                    }
                    else {
                        System.out.println("Heslo se neshoduje");
                        Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                        upozorneni.setContentText("Zadané přihlašovací údaje nejsou správné");
                        upozorneni.show();
                    }


                }
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psPrihlasit != null){
                try{
                    psPrihlasit.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psVlozPrihlaseni !=null){
                try{
                    psVlozPrihlaseni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static Uzivatel najdiUzivatele(int id_uzivatele){
        Uzivatel uzivatel = new Uzivatel(0,null,null,null,0,null,0);

        Connection spojeni = null;
        PreparedStatement psNajdiUzivatele = null;
        ResultSet vysledekDotazu = null;


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiUzivatele = spojeni.prepareStatement("SELECT * from uzivatele WHERE id_uzivatele = ?");
            psNajdiUzivatele.setInt(1,id_uzivatele);

            System.out.println(psNajdiUzivatele);

            vysledekDotazu = psNajdiUzivatele.executeQuery();

            if(!vysledekDotazu.isBeforeFirst()){
                System.out.println("Uživatel nebyl v databázi nalezen");
                Alert upozorneni = new Alert(Alert.AlertType.ERROR);
                upozorneni.setContentText("Uživatel s tímto ID nebyl nalezen");
                upozorneni.show();
                return uzivatel;
            }
            else {
                while (vysledekDotazu.next()){
                    String hesloDb = vysledekDotazu.getString("heslo");
                    String jmenoDb = vysledekDotazu.getString("jmeno");
                    String prijmeniDb = vysledekDotazu.getString("prijmeni");
                    String poziceDb = vysledekDotazu.getString("pozice");
                    Integer osCisloDb = vysledekDotazu.getInt("osobni_cislo");
                    Integer idUzivateleDb = vysledekDotazu.getInt("id_uzivatele");
                    Integer idRestauraceDb = vysledekDotazu.getInt("restaurace_id_restaurace");

                    uzivatel = new Uzivatel(idUzivateleDb,jmenoDb,prijmeniDb,poziceDb,osCisloDb,hesloDb,idRestauraceDb);
                    //return uzivatel;



                }
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiUzivatele != null){
                try{
                    psNajdiUzivatele.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return uzivatel;
    }

    public static int getIdPrihlasenehoUzivatele(){
        int idUzivatele=0;
        Connection spojeni = null;
        PreparedStatement psGetIdPrihlaseni = null;
        ResultSet vysledekDotazu = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psGetIdPrihlaseni = spojeni.prepareStatement("SELECT * FROM bp_restaurace.prihlaseni ORDER BY id_prihlaseni DESC LIMIT 1");
            vysledekDotazu = psGetIdPrihlaseni.executeQuery();

            while (vysledekDotazu.next()){
                idUzivatele = vysledekDotazu.getInt("uzivatele_id_uzivatele");
                System.out.println("Posledni prihlaseny uzivatel má id:"+idUzivatele);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(vysledekDotazu != null){

                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psGetIdPrihlaseni != null){
                try{
                    psGetIdPrihlaseni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return idUzivatele;

    }

    public static void odhlasitUzivatele(){
        Connection spojeni = null;
        PreparedStatement psOdhlasitSe = null;
        Timestamp datumCasOdhlaseni = new Timestamp(System.currentTimeMillis());
        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psOdhlasitSe = spojeni.prepareStatement("UPDATE bp_restaurace.prihlaseni SET datum_cas_odhlaseni = ? WHERE id_prihlaseni=(SELECT MAX(id_prihlaseni) from bp_restaurace.prihlaseni)");
            psOdhlasitSe.setTimestamp(1,datumCasOdhlaseni);
            psOdhlasitSe.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void zmenitHesloUzivateli(String noveHeslo, int id_uzivatele){

        Connection spojeni = null;
        PreparedStatement psZmenaHesla = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psZmenaHesla = spojeni.prepareStatement("UPDATE bp_restaurace.uzivatele SET heslo = ? WHERE id_uzivatele = ?");
            psZmenaHesla.setString(1,noveHeslo);
            psZmenaHesla.setInt(2,id_uzivatele);
            System.out.println(psZmenaHesla);
            psZmenaHesla.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static List <Uzivatel> getSeznamUzivatelu(int id_restaurace){
        List <Uzivatel> seznamUzivatelu = new ArrayList<>();

        Uzivatel uzivatel;
        Connection spojeni = null;
        PreparedStatement psNajdiUzivatele = null;
        ResultSet vysledekDotazu = null;


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiUzivatele = spojeni.prepareStatement("SELECT * from bp_restaurace.uzivatele WHERE restaurace_id_restaurace = ?");
            psNajdiUzivatele.setInt(1,id_restaurace);

            System.out.println(psNajdiUzivatele);

            vysledekDotazu = psNajdiUzivatele.executeQuery();

                while (vysledekDotazu.next()){
                    String hesloDb = vysledekDotazu.getString("heslo");
                    String jmenoDb = vysledekDotazu.getString("jmeno");
                    String prijmeniDb = vysledekDotazu.getString("prijmeni");
                    String poziceDb = vysledekDotazu.getString("pozice");
                    Integer osCisloDb = vysledekDotazu.getInt("osobni_cislo");
                    Integer idUzivateleDb = vysledekDotazu.getInt("id_uzivatele");
                    Integer idRestauraceDb = vysledekDotazu.getInt("restaurace_id_restaurace");
                    uzivatel = new Uzivatel(idUzivateleDb,jmenoDb,prijmeniDb,poziceDb,osCisloDb,hesloDb,idRestauraceDb);
                    seznamUzivatelu.add(uzivatel);

                }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiUzivatele != null){
                try{
                    psNajdiUzivatele.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return seznamUzivatelu;
    }

    public static boolean aktualizujInformaceUzivatel(int id_uzivatele, String jmeno, String prijmeni, String pozice){
        boolean potvrzeni=false;
        Connection spojeni = null;
        PreparedStatement psAktualizujUzivatele = null;
        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psAktualizujUzivatele = spojeni.prepareStatement("UPDATE bp_restaurace.uzivatele SET jmeno = ?, prijmeni = ?, pozice=? WHERE id_uzivatele=?");
            psAktualizujUzivatele.setString(1,jmeno);
            psAktualizujUzivatele.setString(2,prijmeni);
            psAktualizujUzivatele.setString(3,pozice);
            psAktualizujUzivatele.setInt(4,id_uzivatele);
            System.out.println(psAktualizujUzivatele);
            psAktualizujUzivatele.executeUpdate();
            potvrzeni=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return potvrzeni;
    }

    public static List <Rezervace> getSeznamRezervaciDatum(Date datum,int id_stolu){

        List <Rezervace> seznamRezervaci = new ArrayList<>();

        Rezervace rezervace;
        Connection spojeni = null;
        PreparedStatement psNajdiRezervace = null;
        ResultSet vysledekDotazu = null;


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiRezervace = spojeni.prepareStatement("SELECT * from bp_restaurace.rezervace WHERE datum = ? AND stoly_id_stolu = ?");
            psNajdiRezervace.setDate(1,datum);
            psNajdiRezervace.setInt(2,id_stolu);

            System.out.println(psNajdiRezervace);

            vysledekDotazu = psNajdiRezervace.executeQuery();

            while (vysledekDotazu.next()){
                int id_rezervace = vysledekDotazu.getInt("id_rezervace");
                Date datum_db = vysledekDotazu.getDate("datum");
                Time cas_od = vysledekDotazu.getTime("cas_od");
                Time cas_do = vysledekDotazu.getTime("cas_do");
                String jmeno = vysledekDotazu.getString("jmeno");
                String prijmeni = vysledekDotazu.getString("prijmeni");
                String kontakt = vysledekDotazu.getString("kontakt");
                String poznamka = vysledekDotazu.getString("poznamka");
                int pocet_osob = vysledekDotazu.getInt("pocet_osob");
                int id_stolu_db = vysledekDotazu.getInt("stoly_id_stolu");
                int id_uzivatele = vysledekDotazu.getInt("uzivatele_id_uzivatele");
                rezervace = new Rezervace(id_rezervace,datum_db,cas_od,cas_do,jmeno,prijmeni,kontakt,poznamka,pocet_osob,id_stolu_db,id_uzivatele);
                seznamRezervaci.add(rezervace);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiRezervace != null){
                try{
                    psNajdiRezervace.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return seznamRezervaci;
    }

    public static List <Rezervace> getSeznamVsechRezervaci(){
        List <Rezervace> seznamRezervaci = new ArrayList<>();

        Rezervace rezervace;
        Connection spojeni = null;
        PreparedStatement psNajdiRezervace = null;
        ResultSet vysledekDotazu = null;


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiRezervace = spojeni.prepareStatement("SELECT * from bp_restaurace.rezervace");


            System.out.println(psNajdiRezervace);

            vysledekDotazu = psNajdiRezervace.executeQuery();

            while (vysledekDotazu.next()){
                int id_rezervace = vysledekDotazu.getInt("id_rezervace");
                Date datum_db = vysledekDotazu.getDate("datum");
                Time cas_od = vysledekDotazu.getTime("cas_od");
                Time cas_do = vysledekDotazu.getTime("cas_do");
                String jmeno = vysledekDotazu.getString("jmeno");
                String prijmeni = vysledekDotazu.getString("prijmeni");
                String kontakt = vysledekDotazu.getString("kontakt");
                String poznamka = vysledekDotazu.getString("poznamka");
                int pocet_osob = vysledekDotazu.getInt("pocet_osob");
                int id_stolu_db = vysledekDotazu.getInt("stoly_id_stolu");
                int id_uzivatele = vysledekDotazu.getInt("uzivatele_id_uzivatele");
                rezervace = new Rezervace(id_rezervace,datum_db,cas_od,cas_do,jmeno,prijmeni,kontakt,poznamka,pocet_osob,id_stolu_db,id_uzivatele);
                seznamRezervaci.add(rezervace);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiRezervace != null){
                try{
                    psNajdiRezervace.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return seznamRezervaci;

    }

    public static Rezervace getRezervaciById(int idRezervace){

        Rezervace rezervace = new Rezervace(idRezervace,null,null,null,null,null,null,null,0,0,0);
        Connection spojeni = null;
        PreparedStatement psNajdiRezervaci = null;
        ResultSet vysledekDotazu = null;


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiRezervaci = spojeni.prepareStatement("SELECT * from bp_restaurace.rezervace WHERE id_rezervace = ?");
            psNajdiRezervaci.setInt(1,idRezervace);


            System.out.println(psNajdiRezervaci);

            vysledekDotazu = psNajdiRezervaci.executeQuery();

            while (vysledekDotazu.next()){
                int id_rezervace = vysledekDotazu.getInt("id_rezervace");
                Date datum_db = vysledekDotazu.getDate("datum");
                Time cas_od = vysledekDotazu.getTime("cas_od");
                Time cas_do = vysledekDotazu.getTime("cas_do");
                String jmeno = vysledekDotazu.getString("jmeno");
                String prijmeni = vysledekDotazu.getString("prijmeni");
                String kontakt = vysledekDotazu.getString("kontakt");
                String poznamka = vysledekDotazu.getString("poznamka");
                int pocet_osob = vysledekDotazu.getInt("pocet_osob");
                int id_stolu_db = vysledekDotazu.getInt("stoly_id_stolu");
                int id_uzivatele = vysledekDotazu.getInt("uzivatele_id_uzivatele");
                rezervace = new Rezervace(id_rezervace,datum_db,cas_od,cas_do,jmeno,prijmeni,kontakt,poznamka,pocet_osob,id_stolu_db,id_uzivatele);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiRezervaci != null){
                try{
                    psNajdiRezervaci.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return rezervace;


    }

    public static boolean aktualizujRezervaci(int idRezervace, Date datum, String cas_od, String cas_do, String jmeno, String prijmeni, String kontakt, String poznamka, int pocet_osob, int id_uzivatele){
        boolean potvrzeni=false;
        Connection spojeni = null;
        PreparedStatement psAktualizujRezervaci = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psAktualizujRezervaci = spojeni.prepareStatement("UPDATE bp_restaurace.rezervace SET datum = ?, cas_od = ?, cas_do = ?, jmeno =?, prijmeni = ?, kontakt = ?, poznamka = ?, pocet_osob = ?, uzivatele_id_uzivatele = ? WHERE (id_rezervace =?)");
            psAktualizujRezervaci.setDate(1,datum);
            psAktualizujRezervaci.setString(2,cas_od);
            psAktualizujRezervaci.setString(3,cas_do);
            psAktualizujRezervaci.setString(4,jmeno);
            psAktualizujRezervaci.setString(5,prijmeni);
            psAktualizujRezervaci.setString(6,kontakt);
            psAktualizujRezervaci.setString(7,poznamka);
            psAktualizujRezervaci.setInt(8,pocet_osob);
            psAktualizujRezervaci.setInt(9,id_uzivatele);
            psAktualizujRezervaci.setInt(10,idRezervace);
            System.out.println(psAktualizujRezervaci);
            psAktualizujRezervaci.executeUpdate();
            potvrzeni=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return potvrzeni;
    }

    public static List <Rezervace> getRezervaceByJmenoPrijemni(String jmeno, String prijmeni){
        List <Rezervace> seznamRezervaci = new ArrayList<>();

        Rezervace rezervace;
        Connection spojeni = null;
        PreparedStatement psNajdiRezervace = null;
        ResultSet vysledekDotazu = null;

        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiRezervace = spojeni.prepareStatement("SELECT * from bp_restaurace.rezervace WHERE jmeno = ? AND prijmeni = ?");
            psNajdiRezervace.setString(1,jmeno);
            psNajdiRezervace.setString(2,prijmeni);
            System.out.println(psNajdiRezervace);

            vysledekDotazu = psNajdiRezervace.executeQuery();

            while (vysledekDotazu.next()){
                int id_rezervace = vysledekDotazu.getInt("id_rezervace");
                Date datum_db = vysledekDotazu.getDate("datum");
                Time cas_od = vysledekDotazu.getTime("cas_od");
                Time cas_do = vysledekDotazu.getTime("cas_do");
                String jmenoDb = vysledekDotazu.getString("jmeno");
                String prijmeniDb = vysledekDotazu.getString("prijmeni");
                String kontakt = vysledekDotazu.getString("kontakt");
                String poznamka = vysledekDotazu.getString("poznamka");
                int pocet_osob = vysledekDotazu.getInt("pocet_osob");
                int id_stolu_db = vysledekDotazu.getInt("stoly_id_stolu");
                int id_uzivatele = vysledekDotazu.getInt("uzivatele_id_uzivatele");
                rezervace = new Rezervace(id_rezervace,datum_db,cas_od,cas_do,jmenoDb,prijmeniDb,kontakt,poznamka,pocet_osob,id_stolu_db,id_uzivatele);
                seznamRezervaci.add(rezervace);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiRezervace != null){
                try{
                    psNajdiRezervace.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return seznamRezervaci;

    }
    public static List <Rezervace> getRezervaceByDatumOdDo(Date datum_od, Date datum_do){
        List <Rezervace> seznamRezervaci = new ArrayList<>();

        Rezervace rezervace;
        Connection spojeni = null;
        PreparedStatement psNajdiRezervace = null;
        ResultSet vysledekDotazu = null;

        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiRezervace = spojeni.prepareStatement("SELECT * from bp_restaurace.rezervace WHERE datum >= ? AND datum <=?");
            psNajdiRezervace.setDate(1,datum_od);
            psNajdiRezervace.setDate(2,datum_do);
            System.out.println(psNajdiRezervace);

            vysledekDotazu = psNajdiRezervace.executeQuery();

            while (vysledekDotazu.next()){
                int id_rezervace = vysledekDotazu.getInt("id_rezervace");
                Date datum_db = vysledekDotazu.getDate("datum");
                Time cas_od = vysledekDotazu.getTime("cas_od");
                Time cas_do = vysledekDotazu.getTime("cas_do");
                String jmeno = vysledekDotazu.getString("jmeno");
                String prijmeni = vysledekDotazu.getString("prijmeni");
                String kontakt = vysledekDotazu.getString("kontakt");
                String poznamka = vysledekDotazu.getString("poznamka");
                int pocet_osob = vysledekDotazu.getInt("pocet_osob");
                int id_stolu_db = vysledekDotazu.getInt("stoly_id_stolu");
                int id_uzivatele = vysledekDotazu.getInt("uzivatele_id_uzivatele");
                rezervace = new Rezervace(id_rezervace,datum_db,cas_od,cas_do,jmeno,prijmeni,kontakt,poznamka,pocet_osob,id_stolu_db,id_uzivatele);
                seznamRezervaci.add(rezervace);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiRezervace != null){
                try{
                    psNajdiRezervace.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return seznamRezervaci;

    }

    public static List <Rezervace> getRezervaceByStul(String stul){

        List <Rezervace> seznamRezervaci = new ArrayList<>();

        Rezervace rezervace;
        Connection spojeni = null;
        PreparedStatement psNajdiRezervace = null;
        ResultSet vysledekDotazu = null;

        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiRezervace = spojeni.prepareStatement("SELECT * from bp_restaurace.rezervace JOIN bp_restaurace.stoly ON stoly_id_stolu=id_stolu WHERE oznaceni = ? ");
            psNajdiRezervace.setString(1,stul);
            System.out.println(psNajdiRezervace);

            vysledekDotazu = psNajdiRezervace.executeQuery();

            while (vysledekDotazu.next()){
                int id_rezervace = vysledekDotazu.getInt("id_rezervace");
                Date datum_db = vysledekDotazu.getDate("datum");
                Time cas_od = vysledekDotazu.getTime("cas_od");
                Time cas_do = vysledekDotazu.getTime("cas_do");
                String jmenoDb = vysledekDotazu.getString("jmeno");
                String prijmeniDb = vysledekDotazu.getString("prijmeni");
                String kontakt = vysledekDotazu.getString("kontakt");
                String poznamka = vysledekDotazu.getString("poznamka");
                int pocet_osob = vysledekDotazu.getInt("pocet_osob");
                int id_stolu_db = vysledekDotazu.getInt("stoly_id_stolu");
                int id_uzivatele = vysledekDotazu.getInt("uzivatele_id_uzivatele");
                rezervace = new Rezervace(id_rezervace,datum_db,cas_od,cas_do,jmenoDb,prijmeniDb,kontakt,poznamka,pocet_osob,id_stolu_db,id_uzivatele);
                seznamRezervaci.add(rezervace);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiRezervace != null){
                try{
                    psNajdiRezervace.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return seznamRezervaci;

    }
    public static boolean vytvorRezervaci(Date datum, String cas_od, String cas_do, String jmeno, String prijmeni, String kontakt, String poznamka, int pocet_osob, int id_stolu, int id_uzivatele){
        boolean vlozeno=false;
        Connection spojeni = null;
        PreparedStatement psVlozRezervaci = null;
        ResultSet vysledekDotazu = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psVlozRezervaci = spojeni.prepareStatement("INSERT INTO bp_restaurace.rezervace (datum, cas_od, cas_do, jmeno, prijmeni, kontakt,poznamka, pocet_osob, stoly_id_stolu,uzivatele_id_uzivatele) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            psVlozRezervaci.setDate(1,datum);
            psVlozRezervaci.setString(2,cas_od);
            psVlozRezervaci.setString(3,cas_do);
            psVlozRezervaci.setString(4,jmeno);
            psVlozRezervaci.setString(5,prijmeni);
            psVlozRezervaci.setString(6,kontakt);
            psVlozRezervaci.setString(7,poznamka);
            psVlozRezervaci.setInt(8,pocet_osob);
            psVlozRezervaci.setInt(9,id_stolu);
            psVlozRezervaci.setInt(10,id_uzivatele);
            System.out.println(psVlozRezervaci);
            psVlozRezervaci.executeUpdate();
            vlozeno=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psVlozRezervaci != null){
                try{
                    psVlozRezervaci.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return vlozeno;
    }

    public static boolean smazatRezervaci(int id_rezervace){
        boolean potvrzeni=false;
        Connection spojeni = null;
        PreparedStatement psSmazatRezervaci = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psSmazatRezervaci = spojeni.prepareStatement("DELETE FROM bp_restaurace.rezervace WHERE (id_rezervace = ?)");
            psSmazatRezervaci.setInt(1,id_rezervace);
            System.out.println(psSmazatRezervaci);
            psSmazatRezervaci.executeUpdate();
            potvrzeni=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return potvrzeni;

    }

    public static boolean vytvorZakaznika(String popis, int id_restaurace){
        boolean vytvoreno=false;
        Connection spojeni = null;
        PreparedStatement psVytvorZakaznika = null;
        ResultSet vysledekDotazu = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psVytvorZakaznika = spojeni.prepareStatement("INSERT INTO bp_restaurace.zakaznici (oznaceni, restaurace_id_restaurace ) VALUES (?, ?)");
            psVytvorZakaznika.setString(1,popis);
            psVytvorZakaznika.setInt(2,id_restaurace);
            System.out.println(psVytvorZakaznika);
            psVytvorZakaznika.executeUpdate();
            vytvoreno=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psVytvorZakaznika != null){
                try{
                    psVytvorZakaznika.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return vytvoreno;
    }


    public static boolean vytvorKategoriiMenu(String nazev, int id_restaurace){
        boolean vytvoreno=false;
        Connection spojeni = null;
        PreparedStatement psVytvorKategoriiMenu = null;
        ResultSet vysledekDotazu = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psVytvorKategoriiMenu = spojeni.prepareStatement("INSERT INTO bp_restaurace.kategorie_polozky (nazev, restaurace_id_restaurace) VALUES (?,?)");
            psVytvorKategoriiMenu.setString(1,nazev);
            psVytvorKategoriiMenu.setInt(2,id_restaurace);
            System.out.println(psVytvorKategoriiMenu);
            psVytvorKategoriiMenu.executeUpdate();
            vytvoreno=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psVytvorKategoriiMenu != null){
                try{
                    psVytvorKategoriiMenu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return vytvoreno;
    }

    public static boolean overExistenciNazvuKategorieMenu(String nazev, int id_restaurace){
        boolean shodaNazvu;
        Connection spojeni = null;
        PreparedStatement psOverNazev = null;
        ResultSet vysledekDotazu = null;
        int pocetVysledku=0;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psOverNazev = spojeni.prepareStatement("SELECT COUNT(id_kategorie) AS pocet FROM bp_restaurace.kategorie_polozky WHERE nazev=? and restaurace_id_restaurace=?");
            psOverNazev.setString(1,nazev);
            psOverNazev.setInt(2,id_restaurace);
            System.out.println(psOverNazev);
            vysledekDotazu=psOverNazev.executeQuery();

            while (vysledekDotazu.next()){
                pocetVysledku = vysledekDotazu.getInt("pocet");
                System.out.println("Počet výsledků:"+pocetVysledku);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psOverNazev != null){
                try{
                    psOverNazev.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }


        if(pocetVysledku==0){
            shodaNazvu=false; //nenašla se shoda -> false
        }
        else{
            shodaNazvu = true; //našla se shoda názvu ->true
        }

        return shodaNazvu;





    }

    public static List <KategorieMenu> getKategorieMenu(int id_restaurace){
        List <KategorieMenu> seznamKategoriiMenu = new ArrayList<>();
        KategorieMenu kategorieMenu;
        Connection spojeni = null;
        PreparedStatement psNajdiKategorie = null;
        ResultSet vysledekDotazu = null;


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiKategorie = spojeni.prepareStatement("SELECT * from bp_restaurace.kategorie_polozky WHERE restaurace_id_restaurace=?");
            psNajdiKategorie.setInt(1,id_restaurace);
            System.out.println(psNajdiKategorie);

            vysledekDotazu = psNajdiKategorie.executeQuery();

            while (vysledekDotazu.next()){
                int id_kategorie = vysledekDotazu.getInt("id_kategorie");
                String nazev = vysledekDotazu.getString("nazev");
                int id_restaurace_db = vysledekDotazu.getInt("restaurace_id_restaurace");
                kategorieMenu = new KategorieMenu(id_kategorie,nazev,id_restaurace_db);
                seznamKategoriiMenu.add(kategorieMenu);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiKategorie != null){
                try{
                    psNajdiKategorie.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return seznamKategoriiMenu;

    }

    public static boolean aktualizujInformaceKategorieMenu(int id_kategorie, String novyNazev){
        boolean aktualizovano = false;
        Connection spojeni = null;
        PreparedStatement psAktualizujKategorii = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psAktualizujKategorii = spojeni.prepareStatement("UPDATE bp_restaurace.kategorie_polozky SET nazev = ? WHERE (id_kategorie=?)");
            psAktualizujKategorii.setString(1,novyNazev);
            psAktualizujKategorii.setInt(2,id_kategorie);
            System.out.println(psAktualizujKategorii);
            psAktualizujKategorii.executeUpdate();
            aktualizovano=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return aktualizovano;

    }

    public static boolean vytvorPolozkuMenu(String nazev, int mnozstvi, String jednotky, float cena, float sazbaDph, String alergeny, String poznamka, int id_kategorie_polozky){
        boolean vytvoreno=false;

        Connection spojeni = null;
        PreparedStatement psVytvorPolozkuMenu = null;
        ResultSet vysledekDotazu = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psVytvorPolozkuMenu = spojeni.prepareStatement("INSERT INTO bp_restaurace.polozky_menu (nazev, mnozstvi, jednotky, cena, sazba_dph, alergeny, poznamka, kategorie_polozky_id_kategorie) VALUES (?,?,?,?,?,?,?,?)");
            psVytvorPolozkuMenu.setString(1,nazev);
            psVytvorPolozkuMenu.setInt(2,mnozstvi);
            psVytvorPolozkuMenu.setString(3,jednotky);
            psVytvorPolozkuMenu.setFloat(4,cena);
            psVytvorPolozkuMenu.setFloat(5,sazbaDph);
            psVytvorPolozkuMenu.setString(6,alergeny);
            psVytvorPolozkuMenu.setString(7,poznamka);
            psVytvorPolozkuMenu.setInt(8,id_kategorie_polozky);
            System.out.println(psVytvorPolozkuMenu);
            psVytvorPolozkuMenu.executeUpdate();
            vytvoreno=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psVytvorPolozkuMenu != null){
                try{
                   psVytvorPolozkuMenu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return vytvoreno;
    }

    public static boolean aktualizujPolozkuMenu(int id_polozky, String nazev, int mnozstvi, String jednotky, float cena, float sazbaDph, String alergeny, String poznamka, int id_kategorie_polozky){
        boolean aktualizovano = false;
        Connection spojeni = null;
        PreparedStatement psAktualizujPolozkuMenu = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psAktualizujPolozkuMenu = spojeni.prepareStatement("UPDATE bp_restaurace.polozky_menu SET nazev = ?, mnozstvi=?, jednotky = ?, cena = ?, sazba_dph = ?, alergeny = ?, poznamka = ?, kategorie_polozky_id_kategorie = ? WHERE (id_polozky =?)");
            psAktualizujPolozkuMenu.setString(1,nazev);
            psAktualizujPolozkuMenu.setInt(2,mnozstvi);
            psAktualizujPolozkuMenu.setString(3,jednotky);
            psAktualizujPolozkuMenu.setFloat(4,cena);
            psAktualizujPolozkuMenu.setFloat(5,sazbaDph);
            psAktualizujPolozkuMenu.setString(6,alergeny);
            psAktualizujPolozkuMenu.setString(7,poznamka);
            psAktualizujPolozkuMenu.setInt(8,id_kategorie_polozky);
            psAktualizujPolozkuMenu.setInt(9,id_polozky);
            System.out.println(psAktualizujPolozkuMenu);
            psAktualizujPolozkuMenu.executeUpdate();
            aktualizovano=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aktualizovano;
    }

    public static List <PolozkaMenu> getPolozkyMenuByIdKategorieMenu(int id_kategorieMenu){
        List<PolozkaMenu> polozkyMenu = new ArrayList<>();

        PolozkaMenu polozkaMenu;
        Connection spojeni = null;
        PreparedStatement psNajdePolozkyMenu = null;
        ResultSet vysledekDotazu = null;

        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdePolozkyMenu = spojeni.prepareStatement("SELECT * FROM bp_restaurace.polozky_menu where kategorie_polozky_id_kategorie=?");
            psNajdePolozkyMenu.setInt(1,id_kategorieMenu);
            System.out.println(psNajdePolozkyMenu);

            vysledekDotazu = psNajdePolozkyMenu.executeQuery();

            while (vysledekDotazu.next()){
                int idPolozkyMenu=vysledekDotazu.getInt("id_polozky");
                String nazev = vysledekDotazu.getString("nazev");
                int mnozstvi = vysledekDotazu.getInt("mnozstvi");
                String jednotky = vysledekDotazu.getString("jednotky");
                float cena = vysledekDotazu.getFloat("cena");
                float sazbaDph = vysledekDotazu.getFloat("sazba_dph");
                String alergeny = vysledekDotazu.getString("alergeny");
                String poznamka = vysledekDotazu.getString("poznamka");
                int idKategoriePolozky = vysledekDotazu.getInt("kategorie_polozky_id_kategorie");

                polozkaMenu = new PolozkaMenu(idPolozkyMenu,nazev,mnozstvi,jednotky,cena,sazbaDph,alergeny, poznamka,idKategoriePolozky);
                polozkyMenu.add(polozkaMenu);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdePolozkyMenu != null){
                try{
                    psNajdePolozkyMenu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return polozkyMenu;

    }

    public static PolozkaMenu getPolozkuMenuByIdPolozky(int id_polozky){
        PolozkaMenu polozkaMenu=null;

        Connection spojeni = null;
        PreparedStatement psNajdiPolozkuMenu = null;
        ResultSet vysledekDotazu = null;


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiPolozkuMenu = spojeni.prepareStatement("SELECT * from bp_restaurace.polozky_menu where id_polozky = ?");
            psNajdiPolozkuMenu.setInt(1,id_polozky);

            System.out.println(psNajdiPolozkuMenu);

            vysledekDotazu = psNajdiPolozkuMenu.executeQuery();

            if(!vysledekDotazu.isBeforeFirst()){
                System.out.println("Položka menu nebyla nalezena");
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Chyba");
                error.setContentText("Položka menu nebyla nalezena");
                error.show();
                return polozkaMenu;
            }
            else {
                while (vysledekDotazu.next()){
                   int id=vysledekDotazu.getInt("id_polozky");
                   String nazev = vysledekDotazu.getString("nazev");
                   int mnozstvi = vysledekDotazu.getInt("mnozstvi");
                   String jednotky = vysledekDotazu.getString("jednotky");
                   float cena = vysledekDotazu.getFloat("cena");
                   float sazbaDph=vysledekDotazu.getFloat("sazba_dph");
                   String alergeny = vysledekDotazu.getString("alergeny");
                   String poznamka = vysledekDotazu.getString("Poznamka");
                   int idKategorie = vysledekDotazu.getInt("kategorie_polozky_id_kategorie");
                   polozkaMenu = new PolozkaMenu(id,nazev,mnozstvi,jednotky,cena,sazbaDph,alergeny,poznamka,idKategorie);

                }
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiPolozkuMenu != null){
                try{
                    psNajdiPolozkuMenu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }




        return polozkaMenu;
    }


    public static boolean overExistenciPolozkyObjednavky(int id_stolu, int id_polozky_menu){
        boolean existuje = false;

        Connection spojeni = null;
        PreparedStatement psOverExistenciObjednavky = null;
        ResultSet vysledekDotazu = null;
        int pocetVysledku=0;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psOverExistenciObjednavky = spojeni.prepareStatement("SELECT COUNT(polozky_menu_id_polozky) AS pocet FROM bp_restaurace.objednavky_stul WHERE stoly_id_stolu=? and polozky_menu_id_polozky=?");
            psOverExistenciObjednavky.setInt(1,id_stolu);
            psOverExistenciObjednavky.setInt(2,id_polozky_menu);
            System.out.println(psOverExistenciObjednavky);
            vysledekDotazu=psOverExistenciObjednavky.executeQuery();

            while (vysledekDotazu.next()){
                pocetVysledku = vysledekDotazu.getInt("pocet");
                System.out.println("Počet výsledků - objednávka (existuje stejný počet):"+pocetVysledku);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psOverExistenciObjednavky != null){
                try{
                    psOverExistenciObjednavky.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }


        if(pocetVysledku==0){
           existuje =false; //nenašla se shoda -> false
        }
        else{
            existuje = true; //našla se shoda názvu ->true
        }

        return existuje;
    }

    public static ObjednavkaStul getObjednavkaStolu(int id_stolu, int id_polozky_menu){
        ObjednavkaStul objednavka = null;

        Connection spojeni = null;
        PreparedStatement psNajdiObjednavku = null;
        ResultSet vysledekDotazu = null;


        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiObjednavku = spojeni.prepareStatement("SELECT * from bp_restaurace.objednavky_stul where stoly_id_stolu = ? and polozky_menu_id_polozky=?");
            psNajdiObjednavku.setInt(1,id_stolu);
            psNajdiObjednavku.setInt(2,id_polozky_menu);

            System.out.println(psNajdiObjednavku);

            vysledekDotazu = psNajdiObjednavku.executeQuery();

            if(!vysledekDotazu.isBeforeFirst()){
                System.out.println("Objednávka nebyla nalezena");
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Chyba");
                error.setContentText("Objednávka nebyla nalezena");
                error.show();
                return objednavka;
            }
            else {
                while (vysledekDotazu.next()){
                    int idObjednavky=vysledekDotazu.getInt("id_objednavky");
                    int pocetKs = vysledekDotazu.getInt("pocet_ks");
                    Timestamp datumCasObjednani = vysledekDotazu.getTimestamp("datum_cas_objednani");
                    int idUzivatele = vysledekDotazu.getInt("uzivatele_id_uzivatele");

                    objednavka = new ObjednavkaStul(idObjednavky,pocetKs,datumCasObjednani,id_stolu,id_polozky_menu,idUzivatele);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {

            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiObjednavku != null){
                try{
                    psNajdiObjednavku.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return objednavka;
    }

    public static List<ObjednavkaStul> getObjednavkyStoluByIdStolu(int id_stolu){
        List<ObjednavkaStul> seznamObjednavek = new ArrayList<>();

        ObjednavkaStul objednavka;
        Connection spojeni = null;
        PreparedStatement psNajdiObjednavkyStolu = null;
        ResultSet vysledekDotazu = null;

        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psNajdiObjednavkyStolu = spojeni.prepareStatement("SELECT id_objednavky, nazev, pocet_ks FROM bp_restaurace.objednavky_stul JOIN bp_restaurace.polozky_menu ON polozky_menu_id_polozky = id_polozky WHERE stoly_id_stolu=?");
            psNajdiObjednavkyStolu.setInt(1,id_stolu);
            System.out.println(psNajdiObjednavkyStolu);

            vysledekDotazu = psNajdiObjednavkyStolu.executeQuery();

            while (vysledekDotazu.next()){
                int idObjednavky = vysledekDotazu.getInt("id_objednavky");
                int pocetKs = vysledekDotazu.getInt("pocet_ks");
                String nazevPolozky=vysledekDotazu.getString("nazev");
                objednavka = new ObjednavkaStul(idObjednavky,nazevPolozky,pocetKs);
                seznamObjednavek.add(objednavka);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psNajdiObjednavkyStolu != null){
                try{
                    psNajdiObjednavkyStolu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return seznamObjednavek;
    }


    public static boolean aktualizujPocetKusuObjednavka(int id_objednavky, int novy_pocet){
        boolean aktualizovano=false;

        Connection spojeni = null;
        PreparedStatement psAktualizujPocetKs = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psAktualizujPocetKs = spojeni.prepareStatement("UPDATE bp_restaurace.objednavky_stul SET pocet_ks = ? WHERE (id_objednavky =?)");
            psAktualizujPocetKs.setInt(1,novy_pocet);
            psAktualizujPocetKs.setInt(2,id_objednavky);
            System.out.println(psAktualizujPocetKs);
            psAktualizujPocetKs.executeUpdate();
            aktualizovano=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aktualizovano;
    }


    public static boolean smazatPolozkuObjednavkyById(int id_objednavky){
        boolean smazano=false;
        Connection spojeni = null;
        PreparedStatement psSmazatPolozkuObjednavky = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psSmazatPolozkuObjednavky = spojeni.prepareStatement("DELETE FROM bp_restaurace.objednavky_stul WHERE (id_objednavky = ?)");
            psSmazatPolozkuObjednavky.setInt(1,id_objednavky);
            System.out.println(psSmazatPolozkuObjednavky);
            psSmazatPolozkuObjednavky.executeUpdate();
            smazano=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return smazano;
    }


    public static boolean zamenObjednavkuStolu(int id_stolu_stary, int id_stolu_novy){
        boolean zameneno = false;

        Connection spojeni = null;
        PreparedStatement psZamenaStolu = null;

        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psZamenaStolu = spojeni.prepareStatement("UPDATE bp_restaurace.objednavky_stul SET stoly_id_stolu = ? WHERE (stoly_id_stolu = ?)");
            psZamenaStolu.setInt(1,id_stolu_novy);
            psZamenaStolu.setInt(2,id_stolu_stary);
            System.out.println(psZamenaStolu);
            psZamenaStolu.executeUpdate();
            zameneno=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return zameneno;
    }

    public static boolean vytvorObjednavkuStolu(int id_stolu, int id_uzivatele, List<ObjednavkaStul> polozkyObjednavky){
        boolean vytvoreno = false;
        ObjednavkaStul objednavkaStul;
        int staryPocet;
        int novyPocet;
        Timestamp datumCasVytvoreniObjednavky = new Timestamp(System.currentTimeMillis());

        Connection spojeni = null;
        PreparedStatement psVytvorObjednavku = null;
        PreparedStatement psAktualizujObjednavku=null;
        try {
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psVytvorObjednavku = spojeni.prepareStatement("INSERT INTO bp_restaurace.objednavky_stul (pocet_ks,datum_cas_objednani,stoly_id_stolu,polozky_menu_id_polozky,uzivatele_id_uzivatele) VALUES (?,?,?,?,?)");
            psAktualizujObjednavku = spojeni.prepareStatement("UPDATE bp_restaurace.objednavky_stul SET pocet_ks=? WHERE (id_objednavky =?)");
            for (ObjednavkaStul objednavka: polozkyObjednavky) {

                //pokud neexistuje - vytvor novou
                if(DbUtils.overExistenciPolozkyObjednavky(id_stolu,objednavka.getIdPolozkyMenu())==false){
                    psVytvorObjednavku.setInt(1,objednavka.getPocetKs());
                    psVytvorObjednavku.setTimestamp(2,datumCasVytvoreniObjednavky);
                    psVytvorObjednavku.setInt(3,id_stolu);
                    psVytvorObjednavku.setInt(4,objednavka.getIdPolozkyMenu());
                    psVytvorObjednavku.setInt(5,id_uzivatele);
                    System.out.println(psVytvorObjednavku);
                    psVytvorObjednavku.executeUpdate();
                }
                //jinak aktualizuj pocet
                else{
                    objednavkaStul=DbUtils.getObjednavkaStolu(id_stolu,objednavka.getIdPolozkyMenu());
                    staryPocet=objednavkaStul.getPocetKs();
                    novyPocet=staryPocet+objednavka.getPocetKs();
                    psAktualizujObjednavku.setInt(1,novyPocet);
                    psAktualizujObjednavku.setInt(2,objednavkaStul.getIdObjednavky());
                    System.out.println(psAktualizujObjednavku);
                    psAktualizujObjednavku.executeUpdate();

                }

            }
            vytvoreno=true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if(psVytvorObjednavku != null){
                try{
                    psVytvorObjednavku.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(psAktualizujObjednavku != null){
                try{
                    psAktualizujObjednavku.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return vytvoreno;
    }

    public static List<Stul> getSeznamStolu(int id_restaurace){
        ArrayList<Stul> seznamStolu = new ArrayList<>();

        Stul stul;
        Connection spojeni = null;
        PreparedStatement psSeznamStolu = null;
        ResultSet vysledekDotazu = null;

        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psSeznamStolu = spojeni.prepareStatement("SELECT * FROM bp_restaurace.stoly  WHERE restaurace_id_restaurace=?");
            psSeznamStolu.setInt(1,id_restaurace);
            System.out.println(psSeznamStolu);

            vysledekDotazu = psSeznamStolu.executeQuery();

            while (vysledekDotazu.next()){
                int idStolu = vysledekDotazu.getInt("id_stolu");
                String oznaceni = vysledekDotazu.getString("oznaceni");
                int pocetZidli = vysledekDotazu.getInt("pocet_zidli");
                stul = new Stul(idStolu,oznaceni,pocetZidli,id_restaurace);
                seznamStolu.add(stul);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psSeznamStolu != null){
                try{
                    psSeznamStolu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return seznamStolu;
    }

    public static List<Zakaznik> getSeznamZakazniku(int id_restaurace){
        List<Zakaznik> seznamZakazniku = new ArrayList<>();

        Zakaznik zakaznik;
        Connection spojeni = null;
        PreparedStatement psSeznamZakazniku = null;
        ResultSet vysledekDotazu = null;

        try{
            spojeni = DriverManager.getConnection("jdbc:mysql://localhost:3308/bp_restaurace","root","Root1234");
            psSeznamZakazniku = spojeni.prepareStatement("SELECT * FROM bp_restaurace.zakaznici  WHERE restaurace_id_restaurace=?");
            psSeznamZakazniku.setInt(1,id_restaurace);
            System.out.println(psSeznamZakazniku);

            vysledekDotazu = psSeznamZakazniku.executeQuery();

            while (vysledekDotazu.next()){
                int id_zakaznika = vysledekDotazu.getInt("id_zakaznika");
                String oznaceni = vysledekDotazu.getString("oznaceni");
                zakaznik = new Zakaznik(id_zakaznika,oznaceni,id_restaurace);
                seznamZakazniku.add(zakaznik);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {
            if(vysledekDotazu != null){
                try{
                    vysledekDotazu.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psSeznamZakazniku != null){
                try{
                    psSeznamZakazniku.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(spojeni != null){
                try{
                    spojeni.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return seznamZakazniku;
    }
}
