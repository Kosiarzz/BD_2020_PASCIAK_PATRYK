/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenemenagerproject;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import klasy.LOTY;
import klasy.Zarezerwowane;

public class Uzytkownik implements Initializable {

    int KONTOID = 2;
    String IMIE = "blad";
    String NAZWISKO = "blad";
    String ADRES = "blad";
    String PESEL = "blad";
    String TELEFON = "blad";
    String LOGIN = "blad";

    Date nowDate = new Date();
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");

    @FXML
    Tab DANEOSOBOWE, HISTORIAREZERWACJI, ZAREZERWOWANE, REZERWACJE;

    ///Zmiana danych
    @FXML
    TextField Dimie, Dnazwisko, Dadres, Dpesel, Dtelefon, Dlogin, Dhaslo, Dphaslo;

    @FXML
    Label Dbdane, Dblogin;

    ///Historia
    @FXML
    private TableView<Zarezerwowane> Htable;

    @FXML
    private TableColumn<?, ?> HID, Hnr_rezerwacji, Hnr_lotu, Hstart, Hz, Hladowanie, Hw, Hnr_miejsca, Hsamolot, Hstatus;

    ////Zarezerwowane
    @FXML
    private TableView<Zarezerwowane> Ztable;

    @FXML
    private TableColumn<?, ?> ZID, Znr_rezerwacji, Znr_lotu, Zstart, Zz, Zladowanie, Zw, Znr_miejsca, Zsamolot, Zstatus;

    int ZarezerwowaneID;
    String numerR, miejscR, numerlotu;

    @FXML
    Label Zblad;

    ////ZAREZERWUJ
    @FXML
    TextField Rmiejscowosc, Rnr_lotu, Rnr_miejsca;

    @FXML
    Label Rblad;

    @FXML
    private TableView<LOTY> tLoty;

    @FXML
    private TableColumn<?, ?> tNumerlotu, tStart, tMiejscestartu, tLadowanie, tDo, tMiejsc, tSamolot, tStatus, tID;

    @FXML
    Rectangle m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21, m22, m23, m24;
    boolean z1, z2, z3, z4, z5, z6, z7, z8, z9, z10, z11, z12, z13, z14, z15, z16, z17, z18, z19, z20, z21, z22, z23, z24;

    int RLOT, MI;
    boolean Rklik = false;

    //////////////////////////////ZAREZERWUJ/////////////////////////////////////

    @FXML
    private void Rwyszukaj() {
        System.out.println("Wyszukuje");
        polaczenie_lot_wyszukaj();
    }

    @FXML
    private void Rodswiez() {
        System.out.println("Odśwież");
        polaczenie_loty();
    }

    @FXML
    private void Rzarezerwuj() {
        System.out.println("Rezerwuje");
        if (Rklik) {
            Rblad.setVisible(false);
            if (MI > 0) {
                Rblad.setVisible(false);
                if (!miejsce_sprawdz(Integer.valueOf(Rnr_miejsca.getText()))) {
                    Rblad.setVisible(true);
                    if(Integer.valueOf(Rnr_miejsca.getText())<1 || Integer.valueOf(Rnr_miejsca.getText())>24)
                    {
                        Rblad.setText("Taki numer nie istnieje!");
                        Rblad.setVisible(true); 
                    }
                    else
                    {
                    Rblad.setVisible(false);
                    zarezerwuj();
                    polaczenie_loty();
                    Rnr_miejsca.setText("");
                    Rklik = false;
                    }
                } else {
                    Rblad.setText("Miejsce zajęte!");
                    Rblad.setVisible(true);
                }
            } else {
                Rblad.setText("Brak wolnych miejsc!");
                Rblad.setVisible(true);
            }
        } else {
            Rblad.setText("Wybierz lot z listy który chcesz zarezerwować!");
            Rblad.setVisible(true);
        }
    }

    /////////////////////////////////////////////Zarezerwowane//////////////////////////////
    @FXML
    private void Zanuluj() {
        System.out.println("Anulowano rezerwacje");

        Anuluj_polaczenie2();
    }

    @FXML
    private void Zodswiez() {
        System.out.println("Odśwież");
        Anuluj_polaczenie();
    }

    ///////////////////////////////////////////Historia//////////////////////////////
    @FXML
    private void Hodswiez() {
        System.out.println("Historia");
        Historia_polaczenie();
    }

    //////////////////////////////////////////DANE//////////////////////////////////
    @FXML
    private void Dzmien_dane() {
        if (uzytkownikdane()) {
            if (sprawdzenie_pesel()) {
                if (Dpesel.getText().matches("[0-9]+") && Dtelefon.getText().matches("[0-9]+")) {
                    Dbdane.setVisible(false);
                    System.out.println("Zmienam dane");
                    zmiendane();
                } else {
                    System.out.println("Niepoprawny format danych.");
                    Dbdane.setText("Niepoprawne dane!");
                    Dbdane.setVisible(true);
                }
            } else {
                System.out.println("Pesel telefon");
                Dbdane.setText("Niepoprawne dane!");
                Dbdane.setVisible(true);
            }
        } else {
            System.out.println("Uzupełnij pola.");
            Dbdane.setText("Uzupełnij wszystkie pola!");
            Dbdane.setVisible(true);
        }
    }

    @FXML
    private void Dzmien_dane_logowania() {
        if (uzytkowniklogowanie()) {
            if (sprawdzenie_haslo()) {
                Dblogin.setVisible(false);
                System.out.println("Zmieniam daneL");
                zmiendanelogowanie();
            } else {
                System.out.println("Hasła się nie zgadzają.");
                Dblogin.setText("Podane hasła nie są takie same!");
                Dblogin.setVisible(true);
            }
        } else {
            System.out.println("Uzupełnij pola");
            Dblogin.setText("Uzupełnij wszystkie pola!");
            Dblogin.setVisible(true);
        }
    }

    ///////////////////////////////////////////////Wyloguj////////////////////////////
    @FXML
    private void wyloguj() {
        SceneMenager.renderScene("logowanie");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DANE(); 
        wstaw_tekst_do_pola_anuluj();
        //Dimie.setText("IMIE");
        polaczenie_loty();
        Historia_polaczenie();
        Anuluj_polaczenie();
        wstaw_tekst_do_pola_rezerw();
        
        System.out.println("Zalogowany: " + LOGIN);

        Dimie.setText(IMIE);
        Dnazwisko.setText(NAZWISKO);
        Dadres.setText(ADRES);
        Dpesel.setText(PESEL);
        Dtelefon.setText(TELEFON);
        Dlogin.setText(LOGIN);

        System.out.println("Data w formacie [yyyy/MM/dd]: " + sdf1.format(nowDate));

    }

    public void miejsca(String nrmi) {
        if ("1".equals(nrmi)) {
            m1.setFill(Color.web("#ff0000"));
            z1 = true;
        }
        if ("2".equals(nrmi)) {
            m2.setFill(Color.web("#ff0000"));
            z2 = true;
        }
        if ("3".equals(nrmi)) {
            m3.setFill(Color.web("#ff0000"));
            z3 = true;
        }
        if ("4".equals(nrmi)) {
            m4.setFill(Color.web("#ff0000"));
            z4 = true;
        }
        if ("5".equals(nrmi)) {
            m5.setFill(Color.web("#ff0000"));
            z5 = true;
        }
        if ("6".equals(nrmi)) {
            m6.setFill(Color.web("#ff0000"));
            z6 = true;
        }
        if ("7".equals(nrmi)) {
            m7.setFill(Color.web("#ff0000"));
            z7 = true;
        }
        if ("8".equals(nrmi)) {
            m8.setFill(Color.web("#ff0000"));
            z8 = true;
        }
        if ("9".equals(nrmi)) {
            m9.setFill(Color.web("#ff0000"));
            z9 = true;
        }
        if ("10".equals(nrmi)) {
            m10.setFill(Color.web("#ff0000"));
            z10 = true;
        }
        if ("11".equals(nrmi)) {
            m11.setFill(Color.web("#ff0000"));
            z11 = true;
        }
        if ("12".equals(nrmi)) {
            m12.setFill(Color.web("#ff0000"));
            z12 = true;
        }
        if ("13".equals(nrmi)) {
            m13.setFill(Color.web("#ff0000"));
            z13 = true;
        }
        if ("14".equals(nrmi)) {
            m14.setFill(Color.web("#ff0000"));
            z14 = true;
        }
        if ("15".equals(nrmi)) {
            m15.setFill(Color.web("#ff0000"));
            z15 = true;
        }
        if ("16".equals(nrmi)) {
            m16.setFill(Color.web("#ff0000"));
            z16 = true;
        }
        if ("17".equals(nrmi)) {
            m17.setFill(Color.web("#ff0000"));
            z17 = true;
        }
        if ("18".equals(nrmi)) {
            m18.setFill(Color.web("#ff0000"));
            z18 = true;
        }
        if ("19".equals(nrmi)) {
            m19.setFill(Color.web("#ff0000"));
            z19 = true;
        }
        if ("20".equals(nrmi)) {
            m20.setFill(Color.web("#ff0000"));
            z20 = true;
        }
        if ("21".equals(nrmi)) {
            m21.setFill(Color.web("#ff0000"));
            z21 = true;
        }
        if ("22".equals(nrmi)) {
            m22.setFill(Color.web("#ff0000"));
            z22 = true;
        }
        if ("23".equals(nrmi)) {
            m23.setFill(Color.web("#ff0000"));
            z23 = true;
        }
        if ("24".equals(nrmi)) {
            m24.setFill(Color.web("#ff0000"));
            z24 = true;
        }

    }

    public void miejsca_reset() {
        for (int i = 1; i <= 24; i++) {

            if (i == 1) {
                m1.setFill(Color.web("#21f82c"));
                z1 = false;
            }
            if (i == 2) {
                m2.setFill(Color.web("#21f82c"));
                z2 = false;
            }
            if (i == 3) {
                m3.setFill(Color.web("#21f82c"));
                z3 = false;
            }
            if (i == 4) {
                m4.setFill(Color.web("#21f82c"));
                z4 = false;
            }
            if (i == 5) {
                m5.setFill(Color.web("#21f82c"));
                z5 = false;
            }
            if (i == 6) {
                m6.setFill(Color.web("#21f82c"));
                z6 = false;
            }
            if (i == 7) {
                m7.setFill(Color.web("#21f82c"));
                z7 = false;
            }
            if (i == 8) {
                m8.setFill(Color.web("#21f82c"));
                z8 = false;
            }
            if (i == 9) {
                m9.setFill(Color.web("#21f82c"));
                z9 = false;
            }
            if (i == 10) {
                m10.setFill(Color.web("#21f82c"));
                z10 = false;
            }
            if (i == 11) {
                m11.setFill(Color.web("#21f82c"));
                z11 = false;
            }
            if (i == 12) {
                m12.setFill(Color.web("#21f82c"));
                z12 = false;
            }
            if (i == 13) {
                m13.setFill(Color.web("#21f82c"));
                z13 = false;
            }
            if (i == 14) {
                m14.setFill(Color.web("#21f82c"));
                z14 = false;
            }
            if (i == 15) {
                m15.setFill(Color.web("#21f82c"));
                z15 = false;
            }
            if (i == 16) {
                m16.setFill(Color.web("#21f82c"));
                z16 = false;
            }
            if (i == 17) {
                m17.setFill(Color.web("#21f82c"));
                z17 = false;
            }
            if (i == 18) {
                m18.setFill(Color.web("#21f82c"));
                z18 = false;
            }
            if (i == 19) {
                m19.setFill(Color.web("#21f82c"));
                z19 = false;
            }
            if (i == 20) {
                m20.setFill(Color.web("#21f82c"));
                z20 = false;
            }
            if (i == 21) {
                m21.setFill(Color.web("#21f82c"));
                z21 = false;
            }
            if (i == 22) {
                m22.setFill(Color.web("#21f82c"));
                z22 = false;
            }
            if (i == 23) {
                m23.setFill(Color.web("#21f82c"));
                z23 = false;
            }
            if (i == 24) {
                m24.setFill(Color.web("#21f82c"));
                z24 = false;
            }
        }

    }

    public boolean miejsce_sprawdz(int i) {
        if (i == 1) {
            return z1;
        }
        if (i == 2) {
            return z2;
        }
        if (i == 3) {
            return z3;
        }
        if (i == 4) {
            return z4;
        }
        if (i == 5) {
            return z5;
        }
        if (i == 6) {
            return z6;
        }
        if (i == 7) {
            return z7;
        }
        if (i == 8) {
            return z8;
        }
        if (i == 9) {
            return z9;
        }
        if (i == 10) {
            return z10;
        }
        if (i == 11) {
            return z11;
        }
        if (i == 12) {
            return z12;
        }
        if (i == 13) {
            return z13;
        }
        if (i == 14) {
            return z14;
        }
        if (i == 15) {
            return z15;
        }
        if (i == 16) {
            return z16;
        }
        if (i == 17) {
            return z17;
        }
        if (i == 18) {
            return z18;
        }
        if (i == 19) {
            return z19;
        }
        if (i == 20) {
            return z20;
        }
        if (i == 21) {
            return z21;
        }
        if (i == 22) {
            return z22;
        }
        if (i == 23) {
            return z23;
        }
        if (i == 24) {
            return z24;
        }
        return false;
    }

    private void Historia_polaczenie() {

        HID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Hnr_rezerwacji.setCellValueFactory(new PropertyValueFactory<>("nr_rezerwacji"));
        Hnr_lotu.setCellValueFactory(new PropertyValueFactory<>("nr_lotu"));
        Hstart.setCellValueFactory(new PropertyValueFactory<>("start"));
        Hz.setCellValueFactory(new PropertyValueFactory<>("z"));
        Hladowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        Hw.setCellValueFactory(new PropertyValueFactory<>("w"));
        Hnr_miejsca.setCellValueFactory(new PropertyValueFactory<>("nr_miejsca"));
        Hsamolot.setCellValueFactory(new PropertyValueFactory<>("samolot"));
        Hstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Zarezerwowane> rezerwacja = FXCollections.observableArrayList();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            //step4 execute query  
            ResultSet rs = stmt.executeQuery("SELECT R.ID_REZERWACJA, R.NUMER_LOTU, R.NUMER_MIEJSCA, R.ID_KLIENT, R.NUMER_REZERWACJI, S.ID_SAMOLOT, S.MODEL, L.NUMER_LOTU, L.ID_SAMOLOT, L.STARTOWANIE, L.Z, L.LADOWANIE, L.DO, L.STATUS, L.NUMER_LOTU FROM LOTY L, REZERWACJE R, SAMOLOTY S WHERE R.ID_KLIENT='" + PESEL + "' AND L.ID_SAMOLOT = S.ID_SAMOLOT AND R.NUMER_LOTU = L.NUMER_LOTU");

            while (rs.next()) {
                //System.out.println("       NUMER-LOTU , MODEL,               STARTOWANIE         LADOWANIE        STATUS                  Z                    DO                         NRMIEJSCA      IDKLIENT                   NRREZERWACJA      ");
                System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4) + "  " + rs.getString(5) + "  " + rs.getString(6) + "  " + rs.getString(7) + "  " + rs.getString(8) + "  " + rs.getString(9) + "  " + rs.getString(10));
                Zarezerwowane zar = new Zarezerwowane(rs.getInt(1), rs.getString(5), rs.getString(2), rs.getString(3), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(7), rs.getString(14));

                rezerwacja.add(zar);

            }
            con.close();
            System.out.println("Wczytano historie połączeń.");
        } catch (Exception e) {
            System.out.println(e);
        }

        Htable.setItems(rezerwacja);
    }

    private void Anuluj_polaczenie2() {
        ZID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Znr_rezerwacji.setCellValueFactory(new PropertyValueFactory<>("nr_rezerwacji"));
        Znr_lotu.setCellValueFactory(new PropertyValueFactory<>("nr_lotu"));
        Zstart.setCellValueFactory(new PropertyValueFactory<>("start"));
        Zz.setCellValueFactory(new PropertyValueFactory<>("z"));
        Zladowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        Zw.setCellValueFactory(new PropertyValueFactory<>("w"));
        Znr_miejsca.setCellValueFactory(new PropertyValueFactory<>("nr_miejsca"));
        Zsamolot.setCellValueFactory(new PropertyValueFactory<>("samolot"));
        Zstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Zarezerwowane> rezerwacja = FXCollections.observableArrayList();

        String numerRr;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();
           //UPDATE NR_REZERWACJI i MIEJSCA
            //step4 execute query  
            String status = "Dostępny";
            numerRr = "(A) " + numerR;
            miejscR = miejscR + "(W)";
            stmt.executeUpdate("UPDATE REZERWACJE SET NUMER_MIEJSCA='" + miejscR + "', NUMER_REZERWACJI='" + numerRr + "' WHERE NUMER_REZERWACJI='" + numerR + "'");
            System.out.println(numerR + " " + miejscR);
            stmt.executeUpdate("UPDATE LOTY SET DOSTEPNE_MIEJSCA=DOSTEPNE_MIEJSCA+1, STATUS='" + status + "' WHERE NUMER_LOTU='" + numerlotu + "'");

            con.close();
            System.out.println("Anulowano rezerwacje");
        } catch (Exception e) {
            System.out.println(e);
        }

        Anuluj_polaczenie();
    }

    private void Anuluj_polaczenie() {

        ZID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Znr_rezerwacji.setCellValueFactory(new PropertyValueFactory<>("nr_rezerwacji"));
        Znr_lotu.setCellValueFactory(new PropertyValueFactory<>("nr_lotu"));
        Zstart.setCellValueFactory(new PropertyValueFactory<>("start"));
        Zz.setCellValueFactory(new PropertyValueFactory<>("z"));
        Zladowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        Zw.setCellValueFactory(new PropertyValueFactory<>("w"));
        Znr_miejsca.setCellValueFactory(new PropertyValueFactory<>("nr_miejsca"));
        Zsamolot.setCellValueFactory(new PropertyValueFactory<>("samolot"));
        Zstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Zarezerwowane> rezerwacja = FXCollections.observableArrayList();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            //step4 execute query  
            ResultSet rs = stmt.executeQuery("SELECT R.ID_REZERWACJA, R.NUMER_LOTU, R.NUMER_MIEJSCA, R.ID_KLIENT, R.NUMER_REZERWACJI, S.ID_SAMOLOT, S.MODEL, L.NUMER_LOTU, L.ID_SAMOLOT, L.STARTOWANIE, L.Z, L.LADOWANIE, L.DO, L.STATUS, L.NUMER_LOTU FROM LOTY L, REZERWACJE R, SAMOLOTY S WHERE R.ID_KLIENT='" + PESEL + "' AND L.ID_SAMOLOT = S.ID_SAMOLOT AND R.NUMER_LOTU = L.NUMER_LOTU AND R.NUMER_REZERWACJI NOT LIKE '(A)%' AND NOT STATUS='Wykonany'");

            while (rs.next()) {
                //System.out.println("       NUMER-LOTU , MODEL,               STARTOWANIE         LADOWANIE        STATUS                  Z                    DO                         NRMIEJSCA      IDKLIENT                   NRREZERWACJA      ");
                System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4) + "  " + rs.getString(5) + "  " + rs.getString(6) + "  " + rs.getString(7) + "  " + rs.getString(8) + "  " + rs.getString(9) + "  " + rs.getString(10));
                Zarezerwowane zar = new Zarezerwowane(rs.getInt(1), rs.getString(5), rs.getString(2), rs.getString(3), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(7), rs.getString(14));

                rezerwacja.add(zar);

            }
            con.close();
            System.out.println("Wczytano historie połączeń.");
        } catch (Exception e) {
            System.out.println(e);
        }

        Ztable.setItems(rezerwacja);
    }

    private void wstaw_tekst_do_pola_anuluj() {
        Ztable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Zarezerwowane odd = Ztable.getItems().get(Ztable.getSelectionModel().getSelectedIndex());
                numerlotu = odd.getNr_lotu();
                numerR = odd.getNr_rezerwacji();
                miejscR = odd.getNr_miejsca();
                ZarezerwowaneID = odd.getID();
                System.out.println(ZarezerwowaneID);

            }
        });
    }

    private void zmiendane() {
        String imie, nazwisko, adres, pesel, telefon;

        imie = Dimie.getText();
        nazwisko = Dnazwisko.getText();
        adres = Dadres.getText();
        pesel = Dpesel.getText();
        telefon = Dtelefon.getText();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            stmt.executeUpdate("UPDATE KLIENT SET IMIE='" + imie + "', NAZWISKO='" + nazwisko + "', ADRES='" + adres + "', PESEL='" + pesel + "',TELEFON='" + telefon + "' WHERE LOGIN='" + LOGIN + "'");

            con.close();
            System.out.println("Dane zostały zmienione.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean uzytkownikdane() {
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call UZYTKOWNIKDANE('" + Dimie.getText() + "','" + Dnazwisko.getText() + "','" + Dadres.getText() + "','" + Dpesel.getText() + "','" + Dtelefon.getText() + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        if ("TRUE".equals(wynik)) {
            return true;
        } else {
            return false;
        }
    }

    private void zmiendanelogowanie() {

        String login, haslo, phaslo;

        login = Dlogin.getText();
        haslo = Dhaslo.getText();
        phaslo = Dphaslo.getText();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            stmt.executeUpdate("UPDATE KLIENT SET LOGIN='" + login + "' WHERE LOGIN='" + LOGIN + "'");
            stmt.executeUpdate("UPDATE KLIENCI_LOGOWANIE SET LOGIN='" + login + "', HASLO='" + haslo + "' WHERE LOGIN='" + LOGIN + "' ");
            con.close();
            System.out.println("Zmieniono dane logowania");
        } catch (Exception e) {
            System.out.println(e);
        }
        LOGIN = login;
    }

    private boolean uzytkowniklogowanie() {
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call UZYTKOWNIKLOGOWANIE('" + Dlogin.getText() + "','" + Dhaslo.getText() + "','" + Dphaslo.getText() + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        if ("TRUE".equals(wynik)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean sprawdzenie_haslo() {
        //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call HASLO('" + Dhaslo.getText() + "','" + Dphaslo.getText() + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        if ("TRUE".equals(wynik)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean sprawdzenie_pesel() {
        //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call PESEL('" + Dpesel.getText() + "','" + Dtelefon.getText() + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        if ("TRUE".equals(wynik)) {
            return true;
        } else {
            return false;
        }
    }

    private void polaczenie_loty() {

        tNumerlotu.setCellValueFactory(new PropertyValueFactory<>("NUMER_LOTU"));
        tStart.setCellValueFactory(new PropertyValueFactory<>("startowanie"));
        tMiejscestartu.setCellValueFactory(new PropertyValueFactory<>("z"));
        tLadowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        tDo.setCellValueFactory(new PropertyValueFactory<>("DO"));
        tMiejsc.setCellValueFactory(new PropertyValueFactory<>("DOSTEPNE_MIEJSCA"));
        tSamolot.setCellValueFactory(new PropertyValueFactory<>("model"));
        tStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tID.setCellValueFactory(new PropertyValueFactory<>("ID_LOT"));

        ObservableList<LOTY> loty = FXCollections.observableArrayList();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

        //step4 execute query  
            // insert the data 
            ResultSet rs = stmt.executeQuery("select L.ID_LOT, L.NUMER_LOTU, S.MODEL, L.DOSTEPNE_MIEJSCA, L.STARTOWANIE, L.LADOWANIE, L.POWROT, L.STATUS, L.Z, L.DO, S.ID_SAMOLOT, L.ID_SAMOLOT from LOTY L,SAMOLOTY S WHERE L.ID_SAMOLOT=S.ID_SAMOLOT AND NOT STATUS='Wykonany'");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getInt(2) + "  " + rs.getString(3) + "  " + rs.getInt(4) + "  " + rs.getString(5) + "  " + rs.getString(6) + "  " + rs.getString(7) + "  " + rs.getString(8) + "  " + rs.getString(9) + "  " + rs.getString(10));
                LOTY lot = new LOTY(rs.getInt(1), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));

                loty.add(lot);

            }

            tLoty.setItems(loty);
            System.out.println("Wczytano loty.");
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void polaczenie_lot_wyszukaj() {

        tNumerlotu.setCellValueFactory(new PropertyValueFactory<>("NUMER_LOTU"));
        tStart.setCellValueFactory(new PropertyValueFactory<>("startowanie"));
        tMiejscestartu.setCellValueFactory(new PropertyValueFactory<>("z"));
        tLadowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        tDo.setCellValueFactory(new PropertyValueFactory<>("DO"));
        tMiejsc.setCellValueFactory(new PropertyValueFactory<>("DOSTEPNE_MIEJSCA"));
        tSamolot.setCellValueFactory(new PropertyValueFactory<>("model"));
        tStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tID.setCellValueFactory(new PropertyValueFactory<>("ID_LOT"));

        ObservableList<LOTY> loty = FXCollections.observableArrayList();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            //step4 execute query  
            ResultSet rs = stmt.executeQuery("select L.ID_LOT, L.NUMER_LOTU, S.MODEL, L.DOSTEPNE_MIEJSCA, L.STARTOWANIE, L.LADOWANIE, L.POWROT, L.STATUS, L.Z, L.DO, L.ID_SAMOLOT,S.ID_SAMOLOT from LOTY L, SAMOLOTY S WHERE L.ID_SAMOLOT=S.ID_SAMOLOT AND NUMER_LOTU='" + Rnr_lotu.getText() + "' OR L.ID_SAMOLOT=S.ID_SAMOLOT AND DO='" + Rmiejscowosc.getText() + "'");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getInt(2) + "  " + rs.getString(3) + "  " + rs.getInt(4) + "  " + rs.getString(5) + "  " + rs.getString(6) + "  " + rs.getString(7) + "  " + rs.getString(8) + "  " + rs.getString(9) + "  " + rs.getString(10));
                LOTY lot = new LOTY(rs.getInt(1), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));

                loty.add(lot);

            }
            con.close();
            System.out.println("Wyszukiwanie lotów...");
        } catch (Exception e) {
            System.out.println(e);
        }

        tLoty.setItems(loty);
    }

    @FXML
    void DO(Event ev) {
        if (DANEOSOBOWE.isSelected()) {
            System.out.println("Dane osobowe");

        }
    }

    @FXML
    void HR(Event ev) {
        if (HISTORIAREZERWACJI.isSelected()) {
            System.out.println("Historia rezerwacji");

        }
    }

    @FXML
    void Zar(Event ev) {
        if (ZAREZERWOWANE.isSelected()) {
            System.out.println("Zarezerwowane");

        }
    }

    @FXML
    void Rez(Event ev) {
        if (REZERWACJE.isSelected()) {
            System.out.println("Rezerwacja");

        }
    }

    private void wstaw_tekst_do_pola_rezerw() {
        tLoty.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Rklik = true;
                miejsca_reset();
                LOTY odd = tLoty.getItems().get(tLoty.getSelectionModel().getSelectedIndex());
                System.out.println(odd.getNUMER_LOTU());
                RLOT = odd.getNUMER_LOTU();
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
                    Statement stmt = con.createStatement();

                //step4 execute query  
                    // insert the data 
                    ResultSet rs = stmt.executeQuery("SELECT NUMER_LOTU, NUMER_MIEJSCA FROM REZERWACJE WHERE NUMER_LOTU='" + odd.getNUMER_LOTU() + "'");
                    while (rs.next()) {
                        System.out.println(rs.getString(1) + "  " + rs.getString(2));
                        miejsca(rs.getString(2));

                    }

                    ResultSet rs2 = stmt.executeQuery("SELECT DOSTEPNE_MIEJSCA FROM LOTY WHERE NUMER_LOTU='" + odd.getNUMER_LOTU() + "'");
                    while (rs2.next()) {
                        MI = rs2.getInt(1);

                    }

                    System.out.println("Wczytano lot.");
                    con.close();

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });
    }

    public void zarezerwuj() {
        int numer_rez = 1;
        String status = "Dostępny";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NUMER_REZERWACJI FROM NUMERY_REZERWACJI");
            while (rs.next()) {
                System.out.println(rs.getInt(1));
                numer_rez = rs.getInt(1);

            }
            if (MI == 1) {
                status = "Brak miejsc";
            }
            stmt.executeUpdate("UPDATE NUMERY_REZERWACJI SET NUMER_REZERWACJI=NUMER_REZERWACJI+1");
            stmt.executeUpdate("UPDATE LOTY SET DOSTEPNE_MIEJSCA=DOSTEPNE_MIEJSCA-1, STATUS='" + status + "' WHERE NUMER_LOTU='" + RLOT + "'");

        //step4 execute query  
            // insert the data 
            stmt.executeUpdate("INSERT INTO REZERWACJE (NUMER_LOTU,NUMER_MIEJSCA,ID_KLIENT,NUMER_REZERWACJI)" + "VALUES ('" + RLOT + "', '" + Rnr_miejsca.getText() + "', '" + PESEL + "', '" + numer_rez + "')");

            System.out.println("Dodales");
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void DANE() {
        System.out.println("DANEEEEEEEEEEE");
        KONTOID = 2;

        IMIE = "blad";
        NAZWISKO = "blad";
        ADRES = "blad";
        PESEL = "blad";
        TELEFON = "blad";
        LOGIN = logowanie.getloginek();
        System.out.println(LOGIN);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            ResultSet rs2 = stmt.executeQuery("SELECT * FROM KLIENT WHERE LOGIN='" + LOGIN + "'");
            while (rs2.next()) {
                System.out.println(rs2.getInt(1) + " " + rs2.getString(2) + " " + rs2.getString(3) + " " + rs2.getString(4) + " " + rs2.getString(5) + " " + rs2.getString(6));
                IMIE = rs2.getString(2);
                NAZWISKO = rs2.getString(3);
                ADRES = rs2.getString(4);
                PESEL = rs2.getString(5);
                TELEFON = rs2.getString(6);
            }

            System.out.println("Wczytano dane");
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
