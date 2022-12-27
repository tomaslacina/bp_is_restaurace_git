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




}
