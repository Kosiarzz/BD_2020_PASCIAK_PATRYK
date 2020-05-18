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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import klasy.DaneKlienta;
import klasy.LOTY;
import klasy.Zarezerwowane;

public class pracownik implements Initializable {

    //zmienne edycja
    @FXML
    TextField EWimie, EWnazwisko, EWpesel, Eimie, Enazwisko, Eadres, Epesel, Etelefon;

    int EID;

    @FXML
    Label Eblad;

    @FXML
    private TableView<DaneKlienta> Etable;

    @FXML
    private TableColumn<?, ?> EtID, Etimie, Etnazwisko, Etadres, Etpesel, Ettelefon, Etlogin;

    //zmienne Historia
    @FXML
    TextField Hpesel;

    @FXML
    Label Hblad;

    //zmienne USUN
    @FXML
    TextField Unumer, Upesel;

    @FXML
    Label Ublad1, Ublad2;

    //zmienne DODAJ
    @FXML
    TextField Dimie, Dnazwisko, Dadres, Dpesel, Dtelefon;

    @FXML
    Label Dblad;

    boolean istnieje;

    //zmienne ZAREZERWUJ
    @FXML
    Rectangle m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21, m22, m23, m24;
    boolean z1, z2, z3, z4, z5, z6, z7, z8, z9, z10, z11, z12, z13, z14, z15, z16, z17, z18, z19, z20, z21, z22, z23, z24;

    int RLOT, MI;
    boolean Rklik = false, Uklik = false;

    @FXML
    TextField ZWmiejscowosc, ZWnrlotu; //wyszukiwanie w Zarezerwuj

    @FXML
    TextField Znrmiejsca, Zpesel; //Dane do rezerwacji

    @FXML
    private TableView<LOTY> tLoty;

    @FXML
    private TableColumn<?, ?> tNumerlotu, tStart, tMiejscestartu, tLadowanie, tDo, tMiejsc, tSamolot, tStatus, tID;

    String ZID;
    boolean klik;
    int ZarezerwowaneID;
    String numerR, miejscR, numerlotu;

    @FXML
    Label Zblad, nr_lotu, Wwblad;

    @FXML
    private TableView<Zarezerwowane> Uutable;

    @FXML
    private TableColumn<?, ?> Uunrr, Uunrl, Uustart, Uuz, Uuladowanie, Uuw, Uunrmiejsca, uuID, Uusamolot, Uustatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wstaw_tekst_do_pola_edytuj();
        wstaw_tekst_do_pola_rezerwuj();
        wstaw_tekst_do_pola_rezerw();
        wstaw_tekst_do_pola_anuluj();

        //ZAREZERWUj
        polaczenie_loty();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        System.out.println(date);

    }

    ///////////////////////////////ZAKŁADKA Edytuj/////////////////////////////////
    @FXML
    private void EWwyszukaj() {
        polaczenie_wyszukaj();
    }

    private void wstaw_tekst_do_pola_edytuj() {
        Etable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                klik = true;
                DaneKlienta odd = Etable.getItems().get(Etable.getSelectionModel().getSelectedIndex());
                EID = odd.getID();
                Eimie.setText(String.valueOf(odd.getImie()));
                Enazwisko.setText(String.valueOf(odd.getNazwisko()));
                Eadres.setText(String.valueOf(odd.getAdres()));
                Epesel.setText(String.valueOf(odd.getPesel()));
                Etelefon.setText(String.valueOf(odd.getTelefon()));
            }
        });
    }

    private void wstaw_tekst_do_pola_rezerwuj() {
        tLoty.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LOTY odd = tLoty.getItems().get(tLoty.getSelectionModel().getSelectedIndex());
                ZID = String.valueOf(odd.getNUMER_LOTU());
                System.out.println(ZID);
                nr_lotu.setText("Wybrany lot: " + String.valueOf(odd.getNUMER_LOTU()));
            }
        });
    }

    @FXML
    private void Ezmien() {

        if (klik) {
            if (Eimie.getText().length() > 0 && Enazwisko.getText().length() > 0 && Eadres.getText().length() > 0 && Epesel.getText().length() > 0 && Etelefon.getText().length() > 0) {
                Eblad.setVisible(false);
                if (Epesel.getText().length() == 11 && Epesel.getText().matches("[0-9]+")) {
                    Eblad.setVisible(false);
                    if (Etelefon.getText().length() == 9 && Etelefon.getText().matches("[0-9]+")) {
                        Eblad.setVisible(false);
                        polaczenie_wyszukaj_zmien();
                        polaczenie_wyszukaj();
                        klik = false;
                    } else {
                        Eblad.setText("Niepoprawny numer telefonu!");
                        Eblad.setVisible(true);
                    }
                } else {
                    Eblad.setText("Niepoprawny numer pesel!");
                    Eblad.setVisible(true);
                }
            } else {
                Eblad.setText("Uzupełnij wszystkie dane!");
                Eblad.setVisible(true);
            }
        } else {
            Eblad.setText("Wybierz dane z tabeli!");
            Eblad.setVisible(true);
        }

    }

    ///////////////////////////////ZAKŁADKA USUŃ/////////////////////////////////
    @FXML
    private void Hwyszukaj() {
        System.out.println("Historia");
    }

    ///////////////////////////////ZAKŁADKA USUŃ/////////////////////////////////
    @FXML
    private void Uusun() {
        System.out.println("USUŃ");
        if (Uklik) {
            Ublad2.setVisible(false);
            Anuluj_polaczenie2();
            Uklik = false;
            Historia_polaczenie();
        } else {
            Ublad2.setText("Wybierz rezerwacje z listy!");
            Ublad2.setVisible(true);
        }
    }

    @FXML
    private void Uwyszukaj() {

        System.out.println("WYSZUKAJ");
        if (Upesel.getText().length() > 0) {
            Ublad1.setVisible(false);
            if (Upesel.getText().length() == 11 && Upesel.getText().matches("[0-9]+")) {
                Ublad1.setVisible(false);
                Historia_polaczenie();
            } else {
                Ublad1.setText("Nieprawidłowy pesel!");
                Ublad1.setVisible(true);
            }
        } else if (Unumer.getText().length() > 0) {
            Ublad1.setVisible(false);
            if (Unumer.getText().matches("[0-9]+")) {
                Ublad1.setVisible(false);
                Historia_polaczenie();
            } else {
                Ublad1.setText("Nieprawidłowy numer!");
                Ublad1.setVisible(true);
            }
        } else {
            Ublad1.setText("Uzupełnij pole!");
            Ublad1.setVisible(true);
        }

    }

    ///////////////////////////////ZAKŁADKA DODAJ/////////////////////////////////
    @FXML
    private void Ddodaj() {
        sprawdzenie_danych2();
        System.out.println("DODAJ");

        if (Dimie.getText().length() > 0 && Dnazwisko.getText().length() > 0 && Dadres.getText().length() > 0 && Dpesel.getText().length() > 0 && Dtelefon.getText().length() > 0) {
            Dblad.setVisible(false);
            if (Dpesel.getText().length() == 11 && Dpesel.getText().matches("[0-9]+")) {
                Dblad.setVisible(false);
                if (Dtelefon.getText().length() == 9 && Dtelefon.getText().matches("[0-9]+")) {
                    Dblad.setVisible(false);
                    if (istnieje) {
                        System.out.println("isstnieje");
                        Zpesel.setText(Dpesel.getText());
                        Dimie.setText("");
                        Dnazwisko.setText("");
                        Dadres.setText("");
                        Dtelefon.setText("");
                        Dpesel.setText("");

                    } else {
                        System.out.println("nowy");
                        polaczenie_dodajklienta();
                        Zpesel.setText(Dpesel.getText());
                        Dimie.setText("");
                        Dnazwisko.setText("");
                        Dadres.setText("");
                        Dtelefon.setText("");
                        Dpesel.setText("");
                    }
                } else {
                    Dblad.setText("Niepoprawny numer telefonu!");
                    Dblad.setVisible(true);
                }
            } else {
                Dblad.setText("Niepoprawny pesel!");
                Dblad.setVisible(true);
            }
        } else {
            Dblad.setText("Uzupełnij wszystkie pola!");
            Dblad.setVisible(true);
        }
    }

    ///////////////////////////////ZAKŁADKA WYLOGUJ/////////////////////////////////
    @FXML
    private void wyloguj() {
        SceneMenager.renderScene("logowanie");
    }

    ///////////////////////////////ZAKŁADKA ZAREZERWUJ//////////////////////////////
    @FXML
    private void zarezerwuj() {

        if (Rklik) {
            Zblad.setVisible(false);
            if (sprawdzenie_rezerwacja()) {
                Zblad.setVisible(false);
                if (sprawdzenie_rezerwacja_pesel()) {
                    Zblad.setVisible(false);
                    if (Znrmiejsca.getText().matches("[0-9]+") && Integer.valueOf(Znrmiejsca.getText()) > 0 && Integer.valueOf(Znrmiejsca.getText()) < 25) {
                        Zblad.setVisible(false);
                        if (!miejsce_sprawdz(Integer.valueOf(Znrmiejsca.getText()))) {
                            Zblad.setVisible(false);
                            zarezerwujj();
                            polaczenie_loty();
                            Znrmiejsca.setText("");
                            Zpesel.setText("");
                            Rklik = false;
                            nr_lotu.setText("Wybrany lot: 0");
                        } else {
                            Zblad.setText("Miejsce jest już zarezerwowane!");
                            Zblad.setVisible(true);
                        }
                    } else {
                        Zblad.setText("Niepoprawny numer miejsca!");
                        Zblad.setVisible(true);
                    }
                }
            }
        } else {
            Zblad.setText("Wyberz lot z listy!");
            Zblad.setVisible(true);
        }
    }

    @FXML
    private void wyszukaj() {

        System.out.println("Wyszukaj");
        if (sprawdzenie_wyszukaj_loty()) {
            polaczenie_lot_wyszukaj();
        }
    }

    @FXML
    private void ZOdswiez() {
        polaczenie_loty();

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

    //////////////////////////////POŁĄCZENIA//////////////////////////////////////
    private void polaczenie_dodajklienta() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            //step4 execute query  
            // insert the data 
            stmt.executeUpdate("INSERT INTO KLIENT (IMIE,NAZWISKO,ADRES,PESEL,TELEFON,LOGIN)" + "VALUES ('" + Dimie.getText() + "', '" + Dnazwisko.getText() + "', '" + Dadres.getText() + "','" + Dpesel.getText() + "','" + Dtelefon.getText() + "','brak')");

            System.out.println("Dodales");
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void sprawdzenie_danych2() {
        //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("SPRAWDZAM DANE");
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String slogin = "Brak";
            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call REJESTRACJA_PESEL('" + Dimie + "','" + Dnazwisko + "','" + Dadres + "','" + Dpesel + "','" + Dtelefon + "','" + slogin + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
        } catch (SQLException ex) {
            System.out.println("EXCEPTION");
            if (ex.getErrorCode() == 00001) {
                System.out.println("Pesel już istnieje");
                istnieje = true;
            } else {
                System.out.println("pesel dostepny");
                istnieje = false;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean sprawdzenie_danych3() {
        //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("SPRAWDZAM DANE");
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String slogin = "Brak";
            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call PRACOWNIK_DANE_DODAJ('" + Dimie.getText() + "','" + Dnazwisko.getText() + "','" + Dadres.getText() + "','" + Dpesel.getText() + "','" + Dtelefon.getText() + "','" + slogin + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            System.out.println("Wynik = " + wynik);
            connection.close();
            Dblad.setVisible(false);
        } catch (SQLException ex) {
            System.out.println("EXCEPTIONN");
            if (ex.getErrorCode() == 20000) {
                System.out.println("Uzupełnij wszystkie pola");
                Dblad.setText("Uzupełnij wszytkie pola!");
                Dblad.setVisible(true);
            } else if (ex.getErrorCode() == 20002) {
                System.out.println("Zły telefon!");
                Dblad.setText("Uzupełnij wszytkie pola!");
                Dblad.setVisible(true);

            } else if (ex.getErrorCode() == 20003) {
                System.out.println("Zły pesel");
                Dblad.setText("Nieprawidłowy pesel!");
                Dblad.setVisible(true);

            } else if (ex.getErrorCode() == 20004) {
                System.out.println("Zły pesel");
                Dblad.setText("Nieoczekiwany błąd!");
                Dblad.setVisible(true);

            }

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
            System.out.println("Dodales");
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
            ResultSet rs = stmt.executeQuery("select L.ID_LOT, L.NUMER_LOTU, S.MODEL, L.DOSTEPNE_MIEJSCA, L.STARTOWANIE, L.LADOWANIE, L.POWROT, L.STATUS, L.Z, L.DO, L.ID_SAMOLOT,S.ID_SAMOLOT from LOTY L, SAMOLOTY S WHERE L.ID_SAMOLOT=S.ID_SAMOLOT AND NUMER_LOTU='" + ZWnrlotu.getText() + "' OR L.ID_SAMOLOT=S.ID_SAMOLOT AND DO='" + ZWmiejscowosc.getText() + "'");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getInt(2) + "  " + rs.getString(3) + "  " + rs.getInt(4) + "  " + rs.getString(5) + "  " + rs.getString(6) + "  " + rs.getString(7) + "  " + rs.getString(8) + "  " + rs.getString(9) + "  " + rs.getString(10));
                LOTY lot = new LOTY(rs.getInt(1), rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));

                loty.add(lot);

            }
            con.close();
            System.out.println("KOniec połaczenia");
        } catch (Exception e) {
            System.out.println(e);
        }

        tLoty.setItems(loty);
    }

    public void polaczenie_wyszukaj() {

        System.out.println("wyszukuje");
        EtID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Etimie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        Etnazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        Etadres.setCellValueFactory(new PropertyValueFactory<>("adres"));
        Etpesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        Ettelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        Etlogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        ObservableList<DaneKlienta> dane = FXCollections.observableArrayList();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            //step4 execute query  
            ResultSet rs = stmt.executeQuery("select * from KLIENT WHERE IMIE='" + EWimie.getText() + "' OR NAZWISKO='" + EWnazwisko.getText() + "' OR PESEL='" + EWpesel.getText() + "'");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4) + "  " + rs.getString(5) + "  " + rs.getString(6) + "  " + rs.getString(7));

                DaneKlienta person2 = new DaneKlienta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));

                dane.add(person2);

            }
            con.close();
            System.out.println("KOniec połaczenia");
        } catch (Exception e) {
            System.out.println(e);
        }

        Etable.setItems(dane);

    }

    public void polaczenie_wyszukaj_zmien() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();
            System.out.println(EID);
            //step4 execute query  
            // insert the data  Eimie, Enazwisko, Eadres, Epesel, Etelefon;
            stmt.executeUpdate("UPDATE KLIENT SET IMIE='" + Eimie.getText() + "', NAZWISKO='" + Enazwisko.getText() + "', ADRES='" + Eadres.getText() + "', PESEL='" + Epesel.getText() + "', TELEFON='" + Etelefon.getText() + "' WHERE ID_KLIENT=" + EID);
            System.out.println("UPDATE");
            con.close();

        } catch (Exception e) {
            System.out.println(e);
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
                nr_lotu.setText("Wybrany lot: " + String.valueOf(odd.getNUMER_LOTU()));
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

    private boolean sprawdzenie_wyszukaj_loty() {
        //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("WYZUKUJE");
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call WYSZUKAJ_LOT('" + ZWmiejscowosc.getText() + "','" + ZWnrlotu.getText() + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            Wwblad.setVisible(false);
        } catch (SQLException ex) {
            System.out.println("EXCEPTION");
            if (ex.getErrorCode() == 20000) {
                System.out.println("POLA");
                Wwblad.setText("Uzupełnij przynajmniej jedno pole!");
                Wwblad.setVisible(true);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        if ("TRUE".equals(wynik)) {
            System.out.println("REJESTRUJE");
            Wwblad.setVisible(false);
            return true;
        } else {
            System.out.println("BLAD");
            return false;
        }
    }

    public void zarezerwujj() {
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
            stmt.executeUpdate("INSERT INTO REZERWACJE (NUMER_LOTU,NUMER_MIEJSCA,ID_KLIENT,NUMER_REZERWACJI)" + "VALUES ('" + RLOT + "', '" + Znrmiejsca.getText() + "', '" + Zpesel.getText() + "', '" + numer_rez + "')");

            System.out.println("Dodales");
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private boolean sprawdzenie_rezerwacja() {
        //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("WYZUKUJE");
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call ZAREZERWUJ_PRACOWNIK('" + Znrmiejsca.getText() + "','" + Zpesel.getText() + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            Zblad.setVisible(false);
        } catch (SQLException ex) {
            System.out.println("EXCEPTION");
            if (ex.getErrorCode() == 20000) {
                System.out.println("POLA");
                Zblad.setText("Uzupełnij wszystkie pola!");
                Zblad.setVisible(true);
            }
            if (ex.getErrorCode() == 20001) {
                System.out.println("POLA");
                Zblad.setText("Wprowadź dane klienta!");
                Zblad.setVisible(true);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        if ("TRUE".equals(wynik)) {
            System.out.println("DODAJE");
            Zblad.setVisible(false);
            return true;
        } else {
            System.out.println("BLAD");
            return false;
        }
    }

    private boolean sprawdzenie_rezerwacja_pesel() {
        //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("WYZUKUJE");
        String wynik = "FALSE";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            CallableStatement cstmt = connection.prepareCall("{?=call SPRAWDZENIE_PESEL_BAZA('" + Zpesel.getText() + "')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);

            cstmt.execute();
            wynik = cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            Zblad.setVisible(false);
        } catch (SQLException ex) {
            System.out.println("EXCEPTION");
            if (ex.getErrorCode() == 20001) {
                System.out.println("POLA");
                Zblad.setText("Najpierw dodaj klienta!");
                Zblad.setVisible(true);
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    private void Historia_polaczenie() {
        //Uunrr, Uunrl, Uustart, Uuz, Uuladowanie, Uuw, Uunrmiejsca, uuID;

        uuID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Uunrr.setCellValueFactory(new PropertyValueFactory<>("nr_rezerwacji"));
        Uunrl.setCellValueFactory(new PropertyValueFactory<>("nr_lotu"));
        Uustart.setCellValueFactory(new PropertyValueFactory<>("start"));
        Uuz.setCellValueFactory(new PropertyValueFactory<>("z"));
        Uuladowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        Uuw.setCellValueFactory(new PropertyValueFactory<>("w"));
        Uunrmiejsca.setCellValueFactory(new PropertyValueFactory<>("nr_miejsca"));
        Uusamolot.setCellValueFactory(new PropertyValueFactory<>("samolot"));
        Uustatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        ObservableList<Zarezerwowane> rezerwacja = FXCollections.observableArrayList();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "C##Patryk", "Patryk011");
            Statement stmt = con.createStatement();

            //step4 execute query  
            ResultSet rs = stmt.executeQuery("SELECT R.ID_REZERWACJA, R.NUMER_LOTU, R.NUMER_MIEJSCA, R.ID_KLIENT, R.NUMER_REZERWACJI, S.ID_SAMOLOT, S.MODEL, L.NUMER_LOTU, L.ID_SAMOLOT, L.STARTOWANIE, L.Z, L.LADOWANIE, L.DO, L.STATUS, L.NUMER_LOTU FROM LOTY L, REZERWACJE R, SAMOLOTY S WHERE R.NUMER_REZERWACJI='" + Unumer.getText() + "' AND L.ID_SAMOLOT = S.ID_SAMOLOT AND r.numer_lotu=l.numer_lotu OR R.ID_KLIENT='" + Upesel.getText() + "' AND L.ID_SAMOLOT = S.ID_SAMOLOT AND r.numer_lotu=l.numer_lotu");

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

        Uutable.setItems(rezerwacja);
    }

    private void Anuluj_polaczenie2() {

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

    }

    private void wstaw_tekst_do_pola_anuluj() {
        Uutable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Uklik = true;
                Zarezerwowane odd = Uutable.getItems().get(Uutable.getSelectionModel().getSelectedIndex());
                numerlotu = odd.getNr_lotu();
                numerR = odd.getNr_rezerwacji();
                miejscR = odd.getNr_miejsca();
                ZarezerwowaneID = odd.getID();
                System.out.println(ZarezerwowaneID);

            }
        });
    }
}
