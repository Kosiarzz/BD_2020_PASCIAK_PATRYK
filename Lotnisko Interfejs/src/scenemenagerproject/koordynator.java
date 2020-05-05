/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenemenagerproject;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    
    int UsunID;
    
    @FXML
    private TableView<LOTY> Utable;
    
    @FXML
    private TableColumn<?, ?> Unr_lotu,Ustart, Uladowanie, Upowrot, Udo, Umiejsc, Usamolot, Ustatus, UID;
    
    
    //////////////////////////DODAJ LOT/////////////////
    @FXML
    private void dodaj_lot(){
        System.out.println("Dodaje");
        dodajlot();
    }
    
    //////////////////////////EDYTUJ LOT/////////////////
    @FXML
    private void Ezmiany(){
        System.out.println("zapisuje");
        Ezmien();
    }
    
    @FXML 
    private void Ewyszukaj(){
        System.out.println("Szukam");
        polaczenie_Ewyszukaj();
        
    }
    //////////////////USUN LOT//////////////////////////////
    @FXML
    private void Uwyszukaj(){
        System.out.println("Szukam");
        Usunwyszukaj();
    }
    
    @FXML
    private void Uusun(){
        System.out.println("Usuwam");
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
        
        Usunpolaczenie();
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
      
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        
        stmt.executeUpdate("INSERT INTO LOTY (NUMER_LOTU,ID_SAMOLOT,DOSTEPNE_MIEJSCA,STARTOWANIE,LADOWANIE,POWROT,STATUS,Z,DO)" + "VALUES ('0', '1', '24','"+start+"','"+ladowanie+"','"+powrot+"','Dostępny','KorsarzLOT','"+miejs+"')");
     
        
        con.close();  
           System.out.println("Koniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
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
    
    private void polaczenie_Ewyszukaj(){
        
        
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
            System.out.println(Enr_lotu.getText());
        ResultSet rs=stmt.executeQuery("SELECT * FROM LOTY WHERE NUMER_LOTU='"+Enr_lotu.getText()+"'"); 
         while(rs.next()){
            System.out.println(rs.getInt(1)+" 2 "+rs.getString(2)+" 3 "+rs.getInt(3)+"  4 "+rs.getInt(4)+" 5 "+rs.getString(5) +" 6 "+rs.getString(6) +" 7 "+rs.getString(7) +" 8 "+rs.getString(8) +" 9 "+rs.getString(9) +" 10 "+rs.getString(10));
            EIDLOT = rs.getInt(1);
            Esamolot.setText(String.valueOf(rs.getInt(3)));
            Emiejsc.setText(String.valueOf(rs.getInt(4)));
            Estartd.setText(rs.getString(5));
            Eladowanied.setText(rs.getString(6));
            Epowrotd.setText(rs.getString(7));
            Estatus.setText(rs.getString(8));
            Emiejscowosc.setText(rs.getString(10));
            
         } 
        
        con.close();  
           System.out.println("KOniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
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
        ResultSet rs=stmt.executeQuery("SELECT * FROM LOTY"); 
         while(rs.next()){
            System.out.println(rs.getInt(1)+" 2 "+rs.getString(2)+" 3 "+rs.getInt(3)+"  4 "+rs.getInt(4)+" 5 "+rs.getString(5) +" 6 "+rs.getString(6) +" 7 "+rs.getString(7) +" 8 "+rs.getString(8) +" 9 "+rs.getString(9) +" 10 "+rs.getString(10));
            
            LOTY lot = new LOTY(rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getString(3),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
        
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
        ResultSet rs=stmt.executeQuery("SELECT * FROM LOTY WHERE NUMER_LOTU='"+Unumer.getText()+"'"); 
         while(rs.next()){
            System.out.println(rs.getInt(1)+" 2 "+rs.getString(2)+" 3 "+rs.getInt(3)+"  4 "+rs.getInt(4)+" 5 "+rs.getString(5) +" 6 "+rs.getString(6) +" 7 "+rs.getString(7) +" 8 "+rs.getString(8) +" 9 "+rs.getString(9) +" 10 "+rs.getString(10));
            
            LOTY lot = new LOTY(rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getString(3),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10));
        
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
    
    private void Usun(){
        
    }
}
