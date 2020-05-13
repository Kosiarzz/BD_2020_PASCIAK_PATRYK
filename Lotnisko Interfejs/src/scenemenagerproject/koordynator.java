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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import klasy.LOTY;
import klasy.Samolot;

public class koordynator  implements Initializable {
    
    ///Dodaj lot
    @FXML
    TextField startg, ladowanieg, powrotg, miejscowosc,samolot;
    
    @FXML
    DatePicker startd, ladowanied, powrotd;
    
    @FXML
    Label Dblad;
    
    @FXML
    private TableView<Samolot> Dtable;
    
    @FXML
    private TableColumn<?, ?> DID, Dmodel, Dmiejsc, Drodzaj;
    
    int IDSAMOLOT;
    
    /////Edytuj lot
    @FXML
    TextField Estartd,Eladowanied,Epowrotd,Emiejscowosc,Estatus,Enr_lotu,Esamolot,Emiejsc;

    @FXML
    Label Ebzmiany, Ebwyszukaj;
    
    @FXML
    private TableView<Samolot> Dtable1;
    
    @FXML
    private TableColumn<?, ?> DID1, Dmodel1, Dmiejsc1, Drodzaj1;
    
    int EIDSAMOLOT,EIDLOT;
    
    //usuń lot
    
    @FXML
    TextField Unumer;
    
    @FXML
    Label Ublad;
    
    int UsunID=-1;
    
    @FXML
    private TableView<LOTY> Utable;
    
    @FXML
    private TableColumn<?, ?> Unr_lotu,Ustart, Uladowanie, Upowrot, Udo, Umiejsc, Usamolot, Ustatus, UID;
    
    //Wyszukaj
    
    @FXML
    TextField Wlot,Wstat,Wmiej;
    
    @FXML
    Label Wblad;
    
    @FXML
    private TableView<LOTY> Wtable;
    
    @FXML
    private TableColumn<?, ?> Wnr_lotu,Wstart, Wladowanie, Wpowrot, Wdo, Wmiejsc, Wsamolot, Wstatus, WID;
    
    
    
    //////////////////////////DODAJ LOT/////////////////
    @FXML
    private void dodaj_lot(){
        if(sprawdzdodajlot()){
            Dblad.setVisible(false);
        System.out.println("Dodaje");
        dodajlot();
        }
        else
        {
           Dblad.setText("Uzupełnij wszystkie pola!");
           Dblad.setVisible(true);
        }
    }
    
    //////////////////////////EDYTUJ LOT/////////////////
    @FXML
    private void Ezmiany(){
        if(polczeniewyszukajdane())
        {
            Ebzmiany.setVisible(false);
            System.out.println("Szukam");
            Ezmien();
        }
        else
        {
            Ebzmiany.setText("Uzupełnij wszystkie pola!");
            Ebzmiany.setVisible(true);
        }
    }
    
    @FXML 
    private void Ewyszukaj(){
        
        if(edytujlotdane())
        {
            Ebwyszukaj.setVisible(false);
            System.out.println("Podano numer");
            polaczenie_Ewyszukaj();
        }
        else
        {
            Ebwyszukaj.setVisible(true);
        }
        
    }
    //////////////////USUN LOT//////////////////////////////
    @FXML
    private void Uwyszukaj(){
        if(usunwyszukajpolaczenie())
        {
            Ublad.setVisible(false);
        System.out.println("Szukam");
        Usunwyszukaj();
        }
        else
        {
            Ublad.setText("Podaj numer lotu!");
            Ublad.setVisible(true);
        }
    }
    
    @FXML
    private void Uusun(){
        if(UsunID>-1)
        {
        Ublad.setVisible(false);
        usun_lot();
        System.out.println("Usuwam");
        }
        else
        {
            Ublad.setText("Wyierz pozycję z listy!");
            Ublad.setVisible(true);
        }
    }
    
    @FXML
    private void Uodswiez(){
        Usunpolaczenie();
    }
    
    ///////////////WYSZUKAJ////////////////////////////
    @FXML
    private void Wwyszukajb(){
        if(wyszukajspr())
        {
            Wblad.setVisible(false);
            Wwyszukaj();
        }
        else
        {
            Wblad.setText("Podaj jakiś parametr!");
            Wblad.setVisible(true);
        }
    }
    
    @FXML
    private void Wodswiez(){
        Wyszukajpolaczenie();
    }
    
    ////////////////WYLOGUJ/////////////////////////////////
    @FXML
    private void wyloguj(){
        SceneMenager.renderScene("logowanie");
    }
        
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        Dodaj_samoloty();
        Dodaj_samoloty2();
        wstaw_tekst_do_pola_samolot();
        wstaw_tekst_do_pola_samolot2();
        wstaw_tekst_do_usun();
        Usunpolaczenie();
        Wyszukajpolaczenie();
    }
    
    
    private void Dodaj_samoloty(){
        
        DID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Dmodel.setCellValueFactory(new PropertyValueFactory<>("model"));
        Dmiejsc.setCellValueFactory(new PropertyValueFactory<>("miejsc"));
        Drodzaj.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));

         
         
        ObservableList<Samolot> sam = FXCollections.observableArrayList();
        
         
         
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        ResultSet rs=stmt.executeQuery("SELECT * FROM SAMOLOTY"); 
        while(rs.next()){
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)+"  "+rs.getString(4));
            Samolot zar = new Samolot(rs.getInt(1),rs.getInt(3),rs.getString(2),rs.getString(4));
        
            sam.add(zar);  

         }
        con.close();  
           System.out.println("Koniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
         
        
         Dtable.setItems(sam);
     }
    
    private void wstaw_tekst_do_pola_samolot()
    {
        Dtable.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                Samolot odd = Dtable.getItems().get(Dtable.getSelectionModel().getSelectedIndex());
                IDSAMOLOT = odd.getID();
                samolot.setText(String.valueOf(odd.getModel()));
                System.out.println(IDSAMOLOT);
                
            }
        });
    }
    
     private void pobierz_tekst_usun()
    {
        Utable.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                Samolot odd = Dtable.getItems().get(Dtable.getSelectionModel().getSelectedIndex());
                IDSAMOLOT = odd.getID();
                samolot.setText(String.valueOf(odd.getModel()));
                System.out.println(IDSAMOLOT);
                
            }
        });
    }
    
    private void dodajlot(){
        
        String start,ladowanie,powrot,miejs,samolo;
        
        System.out.println("Dodaje");
        String data;
        data = startd.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        start = data +" "+ startg.getText();
        System.out.println("Start: " + start);
        data = ladowanied.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        ladowanie = data + " " + ladowanieg.getText();
        System.out.println("Lądowanie: " + ladowanie);
        data = powrotd.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        powrot = data + " "+powrotg.getText();
        System.out.println("Powrót: " + powrot);
        miejs = miejscowosc.getText();
        System.out.println("Miejscowosc: " + miejs);
        samolo = samolot.getText();
        System.out.println("Samolot: " + samolo);
        int wynik=0,wynikd=0;
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
        
        
        //step4 execute query  
        ResultSet rs=stmt.executeQuery("SELECT NUMER_LOTU FROM NUMERY_LOTOW"); 
         while(rs.next()){
             System.out.println(rs.getString(1));
             wynik = Integer.parseInt( rs.getString(1));
             wynikd = wynik+1;
         }
        stmt.executeUpdate("UPDATE NUMERY_LOTOW SET NUMER_LOTU='"+wynikd+"'");
        stmt.executeUpdate("INSERT INTO LOTY (NUMER_LOTU,ID_SAMOLOT,DOSTEPNE_MIEJSCA,STARTOWANIE,LADOWANIE,POWROT,STATUS,Z,DO)" + "VALUES ('"+wynik+"', '1', '24','"+start+"','"+ladowanie+"','"+powrot+"','Dostępny','KorsarzLOT','"+miejs+"')");
     
        
        con.close();  
           System.out.println("Koniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 

     }
    
    private boolean sprawdzdodajlot(){
        
        String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call DODAJLOTSPR('"+startg.getText()+"','"+startd.getValue()+"','"+ladowanieg.getText()+"','"+ladowanied.getValue()+"','"+powrotg.getText()+"','"+powrotd.getValue()+"','"+miejscowosc.getText()+"','"+samolot.getText()+"')}");
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
    
    
    
    private void Dodaj_samoloty2(){
        
        DID1.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Dmodel1.setCellValueFactory(new PropertyValueFactory<>("model"));
        Dmiejsc1.setCellValueFactory(new PropertyValueFactory<>("miejsc"));
        Drodzaj1.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));

         
         
        ObservableList<Samolot> sam = FXCollections.observableArrayList();
        
         
         
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        ResultSet rs=stmt.executeQuery("SELECT * FROM SAMOLOTY"); 
        while(rs.next()){
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)+"  "+rs.getString(4));
            Samolot zar = new Samolot(rs.getInt(1),rs.getInt(3),rs.getString(2),rs.getString(4));
        
            sam.add(zar);  

         }
        con.close();  
           System.out.println("Koniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
         
        
         Dtable1.setItems(sam);
     }
    
    private void wstaw_tekst_do_pola_samolot2()
    {
        Dtable1.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                Samolot odd = Dtable1.getItems().get(Dtable1.getSelectionModel().getSelectedIndex());
                EIDSAMOLOT = odd.getID();
                Esamolot.setText(String.valueOf(odd.getModel()));
                System.out.println(EIDSAMOLOT);
                
            }
        });
    }
    
     private void wstaw_tekst_do_usun()
    {
        Utable.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                LOTY odd = Utable.getItems().get(Utable.getSelectionModel().getSelectedIndex());
                UsunID = odd.getID_LOT();
                System.out.println(UsunID);
                
            }
        });
    }
    
    private void polaczenie_Ewyszukaj(){
        int ID_SAMOL=0;
        
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
            System.out.println(Enr_lotu.getText());
        ResultSet rs=stmt.executeQuery("SELECT L.ID_LOT,L.NUMER_LOTU,L.ID_SAMOLOT,L.DOSTEPNE_MIEJSCA,L.STARTOWANIE,L.LADOWANIE,L.POWROT,L.STATUS,L.Z,L.DO,S.ID_SAMOLOT,S.MODEL FROM LOTY L, SAMOLOTY S WHERE L.NUMER_LOTU='"+Enr_lotu.getText()+"' AND L.ID_SAMOLOT=S.ID_SAMOLOT"); 
         while(rs.next()){
            System.out.println(rs.getInt(1)+" 2 "+rs.getString(2)+" 3 "+rs.getInt(3)+"  4 "+rs.getInt(4)+" 5 "+rs.getString(5) +" 6 "+rs.getString(6) +" 7 "+rs.getString(7) +" 8 "+rs.getString(8) +" 9 "+rs.getString(9) +" 10 "+rs.getString(10) +" "+rs.getInt(11) + " "+rs.getString(12));
            EIDLOT = rs.getInt(1);
            
            Emiejsc.setText(String.valueOf(rs.getInt(4)));
            Estartd.setText(rs.getString(5));
            Eladowanied.setText(rs.getString(6));
            Epowrotd.setText(rs.getString(7));
            Estatus.setText(rs.getString(8));
            Emiejscowosc.setText(rs.getString(10));
            ID_SAMOL = rs.getInt(3);
         } 
         
        ResultSet rs2=stmt.executeQuery("SELECT ID_SAMOLOT,MODEL FROM SAMOLOTY WHERE ID_SAMOLOT='"+ID_SAMOL+"'");
        while(rs2.next()){
            Esamolot.setText(rs2.getString(2));
        }
        con.close();  
           System.out.println("KOniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
        
    }
    
    private boolean polczeniewyszukajdane(){
        
        String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call EDYTUJLOTDANE('"+Estartd.getText()+"','"+Eladowanied.getText()+"','"+Epowrotd.getText()+"','"+Emiejscowosc.getText()+"','"+Estatus.getText()+"','"+Emiejsc.getText()+"','"+Esamolot.getText()+"')}");
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
    
    private void Ezmien(){
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
            System.out.println(EIDSAMOLOT);
            System.out.println(EIDLOT);
        stmt.executeUpdate("UPDATE LOTY SET ID_SAMOLOT='"+EIDSAMOLOT+"',DOSTEPNE_MIEJSCA='"+Emiejsc.getText()+"',STARTOWANIE='"+Estartd.getText()+"',LADOWANIE='"+Eladowanied.getText()+"',POWROT='"+Epowrotd.getText()+"',STATUS='"+Estatus.getText()+"',DO='"+Emiejscowosc.getText()+"' WHERE ID_LOT='"+EIDLOT+"'");
        
        con.close();  
           System.out.println("KOniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
    }
    
    private void Usunpolaczenie(){
        
        Unr_lotu.setCellValueFactory(new PropertyValueFactory<>("NUMER_LOTU"));
        Ustart.setCellValueFactory(new PropertyValueFactory<>("startowanie"));
        Uladowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        Upowrot.setCellValueFactory(new PropertyValueFactory<>("powrot"));
        Umiejsc.setCellValueFactory(new PropertyValueFactory<>("DOSTEPNE_MIEJSCA"));
        Usamolot.setCellValueFactory(new PropertyValueFactory<>("model"));
        Ustatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        UID.setCellValueFactory(new PropertyValueFactory<>("ID_LOT")); 
        Udo.setCellValueFactory(new PropertyValueFactory<>("DO")); 
         
         
        ObservableList<LOTY> loty = FXCollections.observableArrayList();
        
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
            System.out.println(Enr_lotu.getText());
        ResultSet rs=stmt.executeQuery("SELECT L.ID_LOT,L.NUMER_LOTU,L.ID_SAMOLOT,L.DOSTEPNE_MIEJSCA,L.STARTOWANIE,L.LADOWANIE,L.POWROT,L.STATUS,L.Z,L.DO,S.ID_SAMOLOT,S.MODEL FROM LOTY L, SAMOLOTY S WHERE L.ID_SAMOLOT=S.ID_SAMOLOT"); 
         while(rs.next()){
            //System.out.println(rs.getInt(1)+" 2 "+rs.getString(2)+" 3 "+rs.getInt(3)+"  4 "+rs.getInt(4)+" 5 "+rs.getString(5) +" 6 "+rs.getString(6) +" 7 "+rs.getString(7) +" 8 "+rs.getString(8) +" 9 "+rs.getString(9) +" 10 "+rs.getString(10));
            
            LOTY lot = new LOTY(rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getString(12),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
        
            loty.add(lot);  
            
         } 
        
        con.close();    
           System.out.println("KOniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
         Utable.setItems(loty);
    }
    
    
    private void Usunwyszukaj(){
        
        Unr_lotu.setCellValueFactory(new PropertyValueFactory<>("NUMER_LOTU"));
        Ustart.setCellValueFactory(new PropertyValueFactory<>("startowanie"));
        Uladowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        Upowrot.setCellValueFactory(new PropertyValueFactory<>("powrot"));
        Umiejsc.setCellValueFactory(new PropertyValueFactory<>("DOSTEPNE_MIEJSCA"));
        Usamolot.setCellValueFactory(new PropertyValueFactory<>("model"));
        Ustatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        UID.setCellValueFactory(new PropertyValueFactory<>("ID_LOT")); 
        Udo.setCellValueFactory(new PropertyValueFactory<>("DO")); 
         
         
        ObservableList<LOTY> loty = FXCollections.observableArrayList();
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
            System.out.println(Enr_lotu.getText());
         ResultSet rs=stmt.executeQuery("SELECT L.ID_LOT,L.NUMER_LOTU,L.ID_SAMOLOT,L.DOSTEPNE_MIEJSCA,L.STARTOWANIE,L.LADOWANIE,L.POWROT,L.STATUS,L.Z,L.DO,S.ID_SAMOLOT,S.MODEL FROM LOTY L, SAMOLOTY S WHERE L.ID_SAMOLOT=S.ID_SAMOLOT AND L.NUMER_LOTU='"+Unumer.getText()+"'"); 
         while(rs.next()){
            //System.out.println(rs.getInt(1)+" 2 "+rs.getString(2)+" 3 "+rs.getInt(3)+"  4 "+rs.getInt(4)+" 5 "+rs.getString(5) +" 6 "+rs.getString(6) +" 7 "+rs.getString(7) +" 8 "+rs.getString(8) +" 9 "+rs.getString(9) +" 10 "+rs.getString(10));
            
            LOTY lot = new LOTY(rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getString(12),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
        
            loty.add(lot);  
            
         } 
        
        con.close();  
           System.out.println("KOniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
         Utable.setItems(loty);
    }
    
    private boolean edytujlotdane(){
        
         String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call NUMERLOTU('"+Enr_lotu.getText()+"')}");
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
    
    private boolean usunwyszukajpolaczenie(){
        
         String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call NUMERLOTU('"+Unumer.getText()+"')}");
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
    
    public void usun_lot(){
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
        stmt.executeUpdate("DELETE FROM LOTY WHERE ID_LOT='"+UsunID+"'");
        
        con.close();  
           System.out.println("Usunięto");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
    }
    
    private void Wyszukajpolaczenie(){
        
        Wnr_lotu.setCellValueFactory(new PropertyValueFactory<>("NUMER_LOTU"));
        Wstart.setCellValueFactory(new PropertyValueFactory<>("startowanie"));
        Wladowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        Wpowrot.setCellValueFactory(new PropertyValueFactory<>("powrot"));
        Wmiejsc.setCellValueFactory(new PropertyValueFactory<>("DOSTEPNE_MIEJSCA"));
        Wsamolot.setCellValueFactory(new PropertyValueFactory<>("model"));
        Wstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        WID.setCellValueFactory(new PropertyValueFactory<>("ID_LOT")); 
        Wdo.setCellValueFactory(new PropertyValueFactory<>("DO")); 
         
         
        ObservableList<LOTY> lotyu = FXCollections.observableArrayList();
        
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
        System.out.println(Enr_lotu.getText());
        ResultSet rs=stmt.executeQuery("SELECT L.ID_LOT,L.NUMER_LOTU,L.ID_SAMOLOT,L.DOSTEPNE_MIEJSCA,L.STARTOWANIE,L.LADOWANIE,L.POWROT,L.STATUS,L.Z,L.DO,S.ID_SAMOLOT,S.MODEL FROM LOTY L, SAMOLOTY S WHERE L.ID_SAMOLOT=S.ID_SAMOLOT"); 
         while(rs.next()){
            //System.out.println(rs.getInt(1)+" 2 "+rs.getString(2)+" 3 "+rs.getInt(3)+"  4 "+rs.getInt(4)+" 5 "+rs.getString(5) +" 6 "+rs.getString(6) +" 7 "+rs.getString(7) +" 8 "+rs.getString(8) +" 9 "+rs.getString(9) +" 10 "+rs.getString(10));
            
            LOTY lots = new LOTY(rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getString(12),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
        
            lotyu.add(lots);  
            
         } 
        
        con.close();  
           System.out.println("KOniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
         Wtable.setItems(lotyu);
    }
    
    private void Wwyszukaj(){
        
        Wnr_lotu.setCellValueFactory(new PropertyValueFactory<>("NUMER_LOTU"));
        Wstart.setCellValueFactory(new PropertyValueFactory<>("startowanie"));
        Wladowanie.setCellValueFactory(new PropertyValueFactory<>("ladowanie"));
        Wpowrot.setCellValueFactory(new PropertyValueFactory<>("powrot"));
        Wmiejsc.setCellValueFactory(new PropertyValueFactory<>("DOSTEPNE_MIEJSCA"));
        Wsamolot.setCellValueFactory(new PropertyValueFactory<>("model"));
        Wstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        WID.setCellValueFactory(new PropertyValueFactory<>("ID_LOT")); 
        Wdo.setCellValueFactory(new PropertyValueFactory<>("DO")); 
         
         
        ObservableList<LOTY> loty = FXCollections.observableArrayList();
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
            System.out.println(Enr_lotu.getText());
         ResultSet rs=stmt.executeQuery("SELECT L.ID_LOT,L.NUMER_LOTU,L.ID_SAMOLOT,L.DOSTEPNE_MIEJSCA,L.STARTOWANIE,L.LADOWANIE,L.POWROT,L.STATUS,L.Z,L.DO,S.ID_SAMOLOT,S.MODEL FROM LOTY L, SAMOLOTY S WHERE L.ID_SAMOLOT=S.ID_SAMOLOT AND L.NUMER_LOTU='"+Wlot.getText()+"' OR L.ID_SAMOLOT=S.ID_SAMOLOT AND L.DO='"+Wmiej.getText()+"' OR L.ID_SAMOLOT=S.ID_SAMOLOT AND L.STATUS='"+Wstat.getText()+"'"); 
         while(rs.next()){
            //System.out.println(rs.getInt(1)+" 2 "+rs.getString(2)+" 3 "+rs.getInt(3)+"  4 "+rs.getInt(4)+" 5 "+rs.getString(5) +" 6 "+rs.getString(6) +" 7 "+rs.getString(7) +" 8 "+rs.getString(8) +" 9 "+rs.getString(9) +" 10 "+rs.getString(10));
            
            LOTY lot = new LOTY(rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getString(12),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
        
            loty.add(lot);  
            
         } 
        
        con.close();  
           System.out.println("KOniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
         Wtable.setItems(loty);
    }
    
    
    private boolean wyszukajspr(){
        
         String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call WYSZUKAJ('"+Wlot.getText()+"','"+Wmiej.getText()+"','"+Wstat.getText()+"')}");
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
}
