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
    
    //zmienne ZAREZERWUJ
    @FXML
    Rectangle m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21, m22, m23, m24;
    
    @FXML
    TextField ZWmiejscowosc, ZWnrlotu; //wyszukiwanie w Zarezerwuj
    
    @FXML
    TextField Znrmiejsca, Zpesel; //Dane do rezerwacji
   
    
    @FXML
    private TableView<LOTY> tLoty;
    
    @FXML
    private TableColumn<?, ?> tNumerlotu, tStart, tMiejscestartu, tLadowanie, tDo, tMiejsc, tSamolot, tStatus, tID;
    
    String ZID;
    
    @FXML
    Label Zblad,nr_lotu;
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        wstaw_tekst_do_pola_edytuj();
        wstaw_tekst_do_pola_rezerwuj();
        
        //ZAREZERWUj
        polaczenie_loty();
        
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        System.out.println(date);
        
    }
    
     ///////////////////////////////ZAKŁADKA Edytuj/////////////////////////////////
    @FXML
    private void EWwyszukaj(){
        polaczenie_wyszukaj();
    }
    
    
    private void wstaw_tekst_do_pola_edytuj()
    {
        Etable.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
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
    
    private void wstaw_tekst_do_pola_rezerwuj()
    {
        tLoty.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                LOTY odd = tLoty.getItems().get(tLoty.getSelectionModel().getSelectedIndex());
                ZID = String.valueOf(odd.getNUMER_LOTU());
                System.out.println(ZID);
                nr_lotu.setText("Wybrany lot: "+ String.valueOf(odd.getNUMER_LOTU()));
            }
        });
    }

    @FXML
    private void Ezmien(){
        polaczenie_wyszukaj_zmien();
    }
    
    ///////////////////////////////ZAKŁADKA USUŃ/////////////////////////////////
    @FXML
    private void Hwyszukaj(){
        System.out.println("Historia");
    }
    
    ///////////////////////////////ZAKŁADKA USUŃ/////////////////////////////////
    @FXML
    private void Uusun(){
        System.out.println("USUŃ");
    }
    
    @FXML
    private void Uwyszukaj(){
        System.out.println("WYSZUKAJ");
    }
    
    ///////////////////////////////ZAKŁADKA DODAJ/////////////////////////////////
    @FXML
    private void Ddodaj(){
        System.out.println("DODAJ");
        polaczenie_dodajklienta();
    }
    
    
    ///////////////////////////////ZAKŁADKA WYLOGUJ/////////////////////////////////
    @FXML
    private void wyloguj(){
        SceneMenager.renderScene("logowanie");
    }
    
    ///////////////////////////////ZAKŁADKA ZAREZERWUJ//////////////////////////////
    @FXML
    private void zarezerwuj(){
          polaczenie_lot_rezerwuj();
    }
        
    @FXML
    private void wyszukaj(){
        
        m1.setFill(Color.GREY);
        m2.setFill(Color.CHARTREUSE);
        m3.setFill(Color.RED);
        System.out.println("Wyszukaj");
        polaczenie_lot_wyszukaj();
    }
    
    @FXML
    private void ZOdswiez(){
        polaczenie_loty();
        
    }
    
    
    public void miejsca(String mi, int nrmi ){
        if("Dostepne".equals(mi) && nrmi==1){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==2){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==3){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==4){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==5){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==6){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==7){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==8){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==9){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==10){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==11){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==12){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==13){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==14){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==15){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==16){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==17){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==18){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==19){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==20){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==21){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==22){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==23){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}
        if("Dostepne".equals(mi) && nrmi==24){m1.setFill(Color.GREEN);}else{m1.setFill(Color.GREY);}      
    }
    
    
    
    //////////////////////////////POŁĄCZENIA//////////////////////////////////////
    private void polaczenie_dodajklienta(){
        
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        // insert the data 
        stmt.executeUpdate("INSERT INTO KLIENT (IMIE,NAZWISKO,ADRES,PESEL,TELEFON,LOGIN)" + "VALUES ('"+Dimie.getText()+"', '"+Dnazwisko.getText()+"', '"+Dadres.getText()+"','"+Dpesel.getText()+"','"+Dtelefon.getText()+"','brak')");
     
        System.out.println("Dodales");
        con.close();  
        
        
        }
        catch(Exception e)
        { 
            System.out.println(e);
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
        System.out.println("Dodales");
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
        ResultSet rs=stmt.executeQuery("select L.ID_LOT, L.NUMER_LOTU, (SELECT MODEL FROM SAMOLOTY WHERE ID_SAMOLOT=L.ID_SAMOLOT), L.DOSTEPNE_MIEJSCA, L.STARTOWANIE, L.LADOWANIE, L.POWROT, L.STATUS, L.Z, L.DO from LOTY L,SAMOLOTY WHERE NUMER_LOTU='"+ZWnrlotu.getText()+"' OR DO='"+ZWmiejscowosc.getText()+"'"); 
        while(rs.next()){
            System.out.println(rs.getInt(1)+"  "+rs.getInt(2)+"  "+rs.getString(3)+"  "+rs.getInt(4)+"  "+rs.getString(5) +"  "+rs.getString(6) +"  "+rs.getString(7) +"  "+rs.getString(8) +"  "+rs.getString(9) +"  "+rs.getString(10));
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
        
         
        
         tLoty.setItems(loty);
     }
     
     public void polaczenie_lot_rezerwuj(){
         
          try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
              System.out.println("Dodawanie rezerwacji");
              System.out.println(ZID);
              System.out.println(Znrmiejsca.getText());
              System.out.println(Zpesel.getText());
        //step4 execute query  
        // insert the data 
        stmt.executeUpdate("INSERT INTO REZERWACJE (NUMER_LOTU,NUMER_MIEJSCA,ID_KLIENT,NUMER_REZERWACJI)" + "VALUES ('"+ZID+"', '"+Znrmiejsca.getText()+"', '"+Zpesel.getText()+"', '5')");
     
        System.out.println("Dodales");
        con.close();  
        
        
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
         
         
     }
    
     
    public void polaczenie_wyszukaj(){
        
    System.out.println("wyszukuje");
        EtID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Etimie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        Etnazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        Etadres.setCellValueFactory(new PropertyValueFactory<>("adres"));
        Etpesel.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        Ettelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        Etlogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        ObservableList<DaneKlienta> dane = FXCollections.observableArrayList();
        
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        ResultSet rs=stmt.executeQuery("select * from KLIENT WHERE IMIE='"+EWimie.getText()+"' OR NAZWISKO='"+EWnazwisko.getText()+"' OR PESEL='"+EWpesel.getText()+"'");  
        while(rs.next()){
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3) +"  "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6)+"  "+rs.getString(7));  
            
        DaneKlienta person2 = new DaneKlienta(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
        
        dane.add(person2);  
            
        }
        con.close();  
           System.out.println("KOniec połaczenia");
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
         
        
        Etable.setItems(dane);
        
    }
    
    public void polaczenie_wyszukaj_zmien(){
         try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  
             System.out.println(EID);
        //step4 execute query  
        // insert the data  Eimie, Enazwisko, Eadres, Epesel, Etelefon;
        stmt.executeUpdate("UPDATE KLIENT SET IMIE='"+Eimie.getText()+"', NAZWISKO='"+Enazwisko.getText()+"', ADRES='"+Eadres.getText()+"', PESEL='"+Epesel.getText()+"', TELEFON='"+Etelefon.getText()+"' WHERE ID_KLIENT="+EID);
        System.out.println("UPDATE");
        con.close();  
        
        
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
    }
    
}