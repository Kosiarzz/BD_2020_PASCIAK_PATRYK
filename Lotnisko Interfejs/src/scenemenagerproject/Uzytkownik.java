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
import klasy.LOTY;
import klasy.Zarezerwowane;

public class Uzytkownik  implements Initializable {
    
    int KONTOID=2;
    String LOG="blad";
    String IMIE="blad";
    String NAZWISKO="blad";
    String ADRES="blad";
    String PESEL="blad";
    String TELEFON="blad";
    String LOGIN="blad";
    
    @FXML
    Tab DANEOSOBOWE, HISTORIAREZERWACJI, ZAREZERWOWANE,REZERWACJE; 
    
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
    
    //////////////////////////////ZAREZERWUJ/////////////////////////////////////
    @FXML
    private void Rwyszukaj(){
        System.out.println("Wyszukuje");
        polaczenie_lot_wyszukaj();
    }
    
    @FXML
    private void Rodswiez(){
        System.out.println("Odśwież");
        polaczenie_loty();
    }
    
    @FXML
    private void Rzarezerwuj(){
        System.out.println("Rezerwuje");
    }
    
    /////////////////////////////////////////////Zarezerwowane//////////////////////////////
    @FXML
    private void Zanuluj(){
        System.out.println("Anulowano rezerwacje");
        
        Anuluj_polaczenie2();
    }
    
    @FXML
    private void Zodswiez(){
        System.out.println("Odśwież");
        Anuluj_polaczenie();
    }
    
    ///////////////////////////////////////////Historia//////////////////////////////
    @FXML
    private void Hodswiez(){
        System.out.println("Historia");
        Historia_polaczenie();
    }
    
    //////////////////////////////////////////DANE//////////////////////////////////
    @FXML
    private void Dzmien_dane(){
        if(uzytkownikdane())
        {
            if(sprawdzenie_pesel()){
                if(Dpesel.getText().matches("[0-9]+") && Dtelefon.getText().matches("[0-9]+"))
                {
                    Dbdane.setVisible(false);
                    System.out.println("Zmienam dane");
                    zmiendane();
                }
                else
                {
                    System.out.println("Niepoprawny format danych.");
                    Dbdane.setText("Niepoprawne dane!");
                    Dbdane.setVisible(true);
                }
            }
            else
            {
                System.out.println("Pesel telefon");
                Dbdane.setText("Niepoprawne dane!");
                Dbdane.setVisible(true);
            }
        }
        else
        {
            System.out.println("Uzupełnij pola.");
            Dbdane.setText("Uzupełnij wszystkie pola!");
            Dbdane.setVisible(true);
        }
    }
    
    @FXML
    private void Dzmien_dane_logowania(){
        if(uzytkowniklogowanie())
        {
            if(sprawdzenie_haslo())
            {
                Dblogin.setVisible(false);
                System.out.println("Zmieniam daneL");
                zmiendanelogowanie();
            }
            else
            {
                System.out.println("Hasła się nie zgadzają.");
                Dblogin.setText("Podane hasła nie są takie same!");
                Dblogin.setVisible(true); 
            }
        }
        else
        {
            System.out.println("Uzupełnij pola");
            Dblogin.setText("Uzupełnij wszystkie pola!");
            Dblogin.setVisible(true);
        }
    }
    
    ///////////////////////////////////////////////Wyloguj////////////////////////////
    @FXML
    private void wyloguj(){
        SceneMenager.renderScene("logowanie");
    }
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {   
         wstaw_tekst_do_pola_anuluj();
        //Dimie.setText("IMIE");
         polaczenie_loty();
         Historia_polaczenie();
         Anuluj_polaczenie2();
         System.out.println("Zalogowany: "+LOG);
         
         
         Dimie.setText(IMIE);
         Dnazwisko.setText(NAZWISKO);
         Dadres.setText(ADRES);
         Dpesel.setText(PESEL);
         Dtelefon.setText(TELEFON);
         Dlogin.setText(LOGIN);
    }
    
    
    private void Historia_polaczenie(){
        
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
        
         
         
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        ResultSet rs=stmt.executeQuery("SELECT R.NUMER_LOTU, (SELECT MODEL FROM SAMOLOTY WHERE ID_SAMOLOT=L.ID_SAMOLOT), L.STARTOWANIE, L.LADOWANIE, L.STATUS, L.Z, L.DO, R.NUMER_MIEJSCA, R.ID_KLIENT, R.NUMER_REZERWACJI FROM REZERWACJE R,LOTY L, SAMOLOTY S WHERE R.ID_KLIENT='patryk'"); 
        while(rs.next()){
            //System.out.println("       NUMER-LOTU , MODEL,               STARTOWANIE         LADOWANIE        STATUS                  Z                    DO                         NRMIEJSCA      IDKLIENT                   NRREZERWACJA      ");
            System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5) +"  "+rs.getString(6) +"  "+rs.getString(7) +"  "+rs.getString(8) +"  "+rs.getString(9) +"  "+rs.getString(10));
            Zarezerwowane zar = new Zarezerwowane(0,rs.getString(10),rs.getString(1),rs.getString(8),rs.getString(3),rs.getString(6),rs.getString(4),rs.getString(7),rs.getString(2),rs.getString(5));
        
            rezerwacja.add(zar);  

         }
        con.close();  
           System.out.println("Wczytano historie połączeń.");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
         
        
         Htable.setItems(rezerwacja);
     }
    private void Anuluj_polaczenie2(){
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
        
         
         
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        ResultSet rs=stmt.executeQuery("SELECT R.NUMER_LOTU, (SELECT MODEL FROM SAMOLOTY WHERE ID_SAMOLOT=L.ID_SAMOLOT), L.STARTOWANIE, L.LADOWANIE, L.STATUS, L.Z, L.DO, R.NUMER_MIEJSCA, R.ID_KLIENT, R.NUMER_REZERWACJI FROM REZERWACJE R,LOTY L, SAMOLOTY S WHERE R.ID_KLIENT='patryk'"); 
        while(rs.next()){
            //System.out.println("       NUMER-LOTU , MODEL,               STARTOWANIE         LADOWANIE        STATUS                  Z                    DO                         NRMIEJSCA      IDKLIENT                   NRREZERWACJA      ");
            System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5) +"  "+rs.getString(6) +"  "+rs.getString(7) +"  "+rs.getString(8) +"  "+rs.getString(9) +"  "+rs.getString(10));
            Zarezerwowane zar = new Zarezerwowane(0,rs.getString(10),rs.getString(1),rs.getString(8),rs.getString(3),rs.getString(6),rs.getString(4),rs.getString(7),rs.getString(2),rs.getString(5));
        
            rezerwacja.add(zar);  

         }
        con.close();  
           System.out.println("Wczytano historie połączeń.");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
         
        
         Ztable.setItems(rezerwacja);
    } 
    
    private void Anuluj_polaczenie(){
        
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
        
         
         
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        ResultSet rs=stmt.executeQuery("SELECT R.NUMER_LOTU, (SELECT MODEL FROM SAMOLOTY WHERE ID_SAMOLOT=L.ID_SAMOLOT), L.STARTOWANIE, L.LADOWANIE, L.STATUS, L.Z, L.DO, R.NUMER_MIEJSCA, R.ID_KLIENT, R.NUMER_REZERWACJI FROM REZERWACJE R,LOTY L, SAMOLOTY S WHERE R.ID_KLIENT='patryk'"); 
        while(rs.next()){
            //System.out.println("       NUMER-LOTU , MODEL,               STARTOWANIE         LADOWANIE        STATUS                  Z                    DO                         NRMIEJSCA      IDKLIENT                   NRREZERWACJA      ");
            System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5) +"  "+rs.getString(6) +"  "+rs.getString(7) +"  "+rs.getString(8) +"  "+rs.getString(9) +"  "+rs.getString(10));
            Zarezerwowane zar = new Zarezerwowane(0,rs.getString(10),rs.getString(1),rs.getString(8),rs.getString(3),rs.getString(6),rs.getString(4),rs.getString(7),rs.getString(2),rs.getString(5));
        
            rezerwacja.add(zar);  

         }
        con.close();  
           System.out.println("Wczytano historie połączeń.");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
         
        
         Ztable.setItems(rezerwacja);
     }
     
     private void wstaw_tekst_do_pola_anuluj()
    {
        Ztable.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                Zarezerwowane odd = Ztable.getItems().get(Ztable.getSelectionModel().getSelectedIndex());
                ZarezerwowaneID = odd.getID();
                System.out.println(ZarezerwowaneID);
                
            }
        });
    }
     
     private void zmiendane(){
        String imie,nazwisko,adres,pesel,telefon;
        
        imie = Dimie.getText();
        nazwisko = Dnazwisko.getText();
        adres = Dadres.getText();
        pesel = Dpesel.getText();
        telefon = Dtelefon.getText();
        
        
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        stmt.executeUpdate("SELECT KLIENT SET IMIE='"+imie+"', NAZWISKO='"+nazwisko+"', ADRES='"+adres+ "', PESEL='"+pesel+"',TELEFON='"+pesel+"' WHERE LOGIN='"+LOG+"'");
         
        
        con.close();  
           System.out.println("Dane zostały zmienione.");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
     }
     
     private boolean uzytkownikdane(){
         String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call UZYTKOWNIKDANE('"+Dimie.getText()+"','"+Dnazwisko.getText()+"','"+Dadres.getText()+"','"+Dpesel.getText()+"','"+Dtelefon.getText()+"')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            
            cstmt.execute();
            wynik=cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            
        } catch(Exception e){ 
            System.out.println(e); 
        }
         
        if("TRUE".equals(wynik))
            {
                return true;
            }
            else
            {
                return false;
            }
     }
     
     private void zmiendanelogowanie(){
         
        String login,haslo,phaslo;
         
        login = Dlogin.getText();
        haslo = Dhaslo.getText();
        phaslo = Dphaslo.getText();
        
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        stmt.executeUpdate("SELECT KLIENT SET LOGIN='"+login+"' WHERE LOGIN='"+LOG+"'");
        stmt.executeUpdate("SELECT KLIENCI_LOGOWANIE SET LOGIN='"+login+"', HASLO='"+haslo+"' WHERE LOGIN='"+LOG+"' ");
        con.close();  
           System.out.println("Zmieniono dane logowania");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
        LOG = login;
     }
     
     private boolean uzytkowniklogowanie(){
         String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call UZYTKOWNIKLOGOWANIE('"+Dlogin.getText()+"','"+Dhaslo.getText()+"','"+Dphaslo.getText()+"')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            
            cstmt.execute();
            wynik=cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            
        } catch(Exception e){ 
            System.out.println(e); 
        }
         
        if("TRUE".equals(wynik))
            {
                return true;
            }
            else
            {
                return false;
            }
     }
     
      private boolean sprawdzenie_haslo(){
            //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
          String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call HASLO('"+Dhaslo.getText()+"','"+Dphaslo.getText()+"')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            
            cstmt.execute();
            wynik=cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            
        } catch(Exception e){ 
            System.out.println(e); 
        }
         
        if("TRUE".equals(wynik))
            {
                return true;
            }
            else
            {
                return false;
            }
    }
      
      private boolean sprawdzenie_pesel(){
            //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
          String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call PESEL('"+Dpesel.getText()+"','"+Dtelefon.getText()+"')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            
            cstmt.execute();
            wynik=cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            
        } catch(Exception e){ 
            System.out.println(e); 
        }
         
        if("TRUE".equals(wynik))
            {
                return true;
            }
            else
            {
                return false;
            }
    }
     
     private void polaczenie_loty(){
        
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
        
         
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        // insert the data 
        ResultSet rs=stmt.executeQuery("select L.ID_LOT, L.NUMER_LOTU, (SELECT MODEL FROM SAMOLOTY WHERE ID_SAMOLOT=L.ID_SAMOLOT), L.DOSTEPNE_MIEJSCA, L.STARTOWANIE, L.LADOWANIE, L.POWROT, L.STATUS, L.Z, L.DO from LOTY L,SAMOLOTY "); 
         while(rs.next()){
            System.out.println(rs.getInt(1)+"  "+rs.getInt(2)+"  "+rs.getString(3)+"  "+rs.getInt(4)+"  "+rs.getString(5) +"  "+rs.getString(6) +"  "+rs.getString(7) +"  "+rs.getString(8) +"  "+rs.getString(9) +"  "+rs.getString(10));
            LOTY lot = new LOTY(rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getString(3),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
        
            loty.add(lot);  

         }
        
         tLoty.setItems(loty);
        System.out.println("Wczytano loty.");
        con.close();  
        
        
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
     }
     
     public void polaczenie_lot_wyszukaj(){
         
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
        
         
         
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        ResultSet rs=stmt.executeQuery("select L.ID_LOT, L.NUMER_LOTU, (SELECT MODEL FROM SAMOLOTY WHERE ID_SAMOLOT=L.ID_SAMOLOT), L.DOSTEPNE_MIEJSCA, L.STARTOWANIE, L.LADOWANIE, L.POWROT, L.STATUS, L.Z, L.DO from LOTY L,SAMOLOTY WHERE NUMER_LOTU='"+Rnr_lotu.getText()+"' OR DO='"+Rmiejscowosc.getText()+"'"); 
        while(rs.next()){
            System.out.println(rs.getInt(1)+"  "+rs.getInt(2)+"  "+rs.getString(3)+"  "+rs.getInt(4)+"  "+rs.getString(5) +"  "+rs.getString(6) +"  "+rs.getString(7) +"  "+rs.getString(8) +"  "+rs.getString(9) +"  "+rs.getString(10));
            LOTY lot = new LOTY(rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getString(3),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
        
            loty.add(lot);  

         }
        con.close();  
           System.out.println("Wyszukiwanie lotów...");
        }
        catch(Exception e)
        { 
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
}
