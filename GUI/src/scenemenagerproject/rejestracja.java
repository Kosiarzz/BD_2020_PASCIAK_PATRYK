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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class rejestracja implements Initializable {
    
    @FXML
    private TextField login, imie, nazwisko, adres, pesel, telefon;
    
    @FXML
    private PasswordField haslo, phaslo;
    
    @FXML
    private Label blad;
    
    String slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
    boolean rpesel=false,rlogin=false;
    
    @FXML
    private void Zarejestruj(){
        slogin = login.getText();
        shaslo = haslo.getText();
        sphaslo = phaslo.getText();
        simie = imie.getText();
        snazwisko = nazwisko.getText();
        sadres = adres.getText();
        spesel = pesel.getText();
        stelefon = telefon.getText();
         
        if(sprawdzenie_danych())
        {
            if(pesel.getText().matches("[0-9]+"))
            {
                if(telefon.getText().matches("[0-9]+"))
                {
                    sprawdzenie_danych2();
                }
                else
                {
                    blad.setText("Niepoprawny telefon!");
                    blad.setVisible(true);  
                }
            }
            else
            {
                blad.setText("Niepoprawny pesel!");
                blad.setVisible(true);
            }
        }  
        
        if(sprawdzenie_login())
        {
            
        
        if(rpesel&& rlogin)
        {
            System.out.println("tworzenie konta");
            blad.setVisible(false);
            polaczenie();
        }
        if(!rpesel && rlogin)
        {
            blad.setVisible(false);
            System.out.println("pesel istnieje");
            polaczenie2();
        }
        }
        else
        {
            blad.setVisible(true);
            System.out.println("Taki login już istnieje!");
        }
    }

    @FXML
    private void Powrot(){
        SceneMenager.renderScene("logowanie");
    }
 
      
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    private void polaczenie(){
        
        try{   
       Connection con = Polaczenie.getConnection();
        Statement stmt=con.createStatement();  

        //step4 execute query  
        // insert the data
       // stmt.executeUpdate("INSERT INTO KLIENCI_LOGOWANIE (LOGIN,HASLO)" + "VALUES ('"+slogin+"', '"+shaslo+"')");  
        //stmt.executeUpdate("INSERT INTO KLIENT (IMIE,NAZWISKO,ADRES,PESEL,TELEFON,LOGIN)" + "VALUES ('"+simie+"', '"+snazwisko+"', '"+sadres+"','"+spesel+"','"+stelefon+"','"+slogin+"')");
     
        CallableStatement cstmt2 = con.prepareCall("call INSERT_REJESTRACJA_DANE(?,?,?,?,?,?)");
        cstmt2.setString(1, simie);
        cstmt2.setString(2, snazwisko);
        cstmt2.setString(3, sadres);
        cstmt2.setString(4, spesel);
        cstmt2.setString(5, stelefon);
        cstmt2.setString(6, slogin);
        cstmt2.executeQuery();
        
        CallableStatement cstmt = con.prepareCall("call INSERT_REJESTRACJA_LOGIN(?,?)");
        cstmt.setString(1, slogin);
        cstmt.setString(2, shaslo);
        cstmt.executeQuery();
        
        System.out.println("Konto stworzone.");
        con.close();  
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
       // SceneMenager.renderScene("logowanie");
    }
    
    private void polaczenie2(){
        
        try{   
       Connection con = Polaczenie.getConnection(); 
        Statement stmt=con.createStatement();  

        //step4 execute query  
        // insert the data
        
        CallableStatement cstmt = con.prepareCall("call INSERT_REJESTRACJA_LOGIN(?,?)");
        cstmt.setString(1, slogin);
        cstmt.setString(2, shaslo);
        cstmt.executeQuery();
        
        stmt.executeUpdate("UPDATE KLIENT SET LOGIN='"+slogin+"' WHERE PESEL='"+spesel+"'");
     
        System.out.println("Konto stworzone.");
        con.close();  
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        //SceneMenager.renderScene("logowanie");
    }
    
    private boolean sprawdzenie_danych(){
            //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("SPRAWDZAM DANE");
          String wynik="FALSE";
          try{
          Connection connection = Polaczenie.getConnection();
            CallableStatement cstmt = connection.prepareCall("{?=call REJESTRACJA_WALIDACJA_DANYCH('"+simie+"','"+snazwisko+"','"+sadres+"','"+spesel+"','"+stelefon+"','"+slogin+"','"+shaslo+"','"+sphaslo+"')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            
            cstmt.execute();
            wynik=cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            blad.setVisible(false);
        } 
          catch(SQLException ex){ 
              System.out.println("EXCEPTION");
            if(ex.getErrorCode()==20000)
            {
                System.out.println("POLA");
                blad.setText("Musisz uzupełnić wszystkie pola!");
                blad.setVisible(true);
            }
            else if(ex.getErrorCode()==20001)
            {
                System.out.println("HASLA");
                blad.setText("Hasła nie są takie same!");
                blad.setVisible(true);
            }
            else if(ex.getErrorCode()==20002)
            {
                System.out.println("TEL");
                blad.setText("Niepoprawny telefon!");
                blad.setVisible(true);
            }
            else if(ex.getErrorCode()==20003)
            {
                System.out.println("PESEL");
                blad.setText("Niepoprawny pesel!");
                blad.setVisible(true);
            }
            
        }
          catch(Exception e){ 
            System.out.println(e); 
        }
         
        if("TRUE".equals(wynik))
            {
                System.out.println("REJESTRUJE");
                return true;
            }
            else
            {
                System.out.println("BLAD");
                return false;
            }
    }
    public boolean sprawdzenie_login(){
        try {
           Connection con = Polaczenie.getConnection();
            Statement stmt = con.createStatement();


            CallableStatement cstmt = con.prepareCall("call LOGIN_ISTNIEJE(?)");
            cstmt.setString(1, slogin);
            cstmt.executeQuery();
            
            
            con.close();
        } 
        catch(SQLException ex){ 
              System.out.println("EXCEPTION");
            if(ex.getErrorCode()==20000)
            {
                
                blad.setText("Taki login już istnieje!");
                blad.setVisible(true);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
    
    private void sprawdzenie_danych2(){
            //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("SPRAWDZAM DANE");
          String wynik="FALSE";
          try{
           Connection connection = Polaczenie.getConnection();
            CallableStatement cstmt = connection.prepareCall("call PESEL_ISTNIEJE(?)");
            cstmt.setString(1, slogin);
            cstmt.executeQuery();
           
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            blad.setVisible(false);
        } 
          catch(SQLException ex){ 
              System.out.println("EXCEPTION");
            if(ex.getErrorCode()==20001)
            {
                System.out.println("Pesel już istnieje");
                blad.setText("Taki pesel już istnieje!");
                blad.setVisible(true);
                rpesel=false;
            }
            else
            {
                System.out.println("pesel dostepny");
                rpesel=true;
            }
            
        }
          catch(Exception e){ 
            System.out.println(e); 
        }
          
          
          
          
           try{
            Connection connection = Polaczenie.getConnection(); 
            CallableStatement cstmt = connection.prepareCall("call LOGIN_ISTNIEJE(?)");
            cstmt.setString(1, slogin);
            cstmt.executeQuery();
            wynik=cstmt.getString(1);
            //System.out.println("Wynik = "+wynik);          
            connection.close();
            blad.setVisible(false);
        } 
          catch(SQLException ex){ 
              System.out.println("EXCEPTION");
             if(ex.getErrorCode()==20001)
            {
                System.out.println("LOGIN");
                blad.setText("Taki login już istnieje!");
                blad.setVisible(true);
                rlogin=false;
            }
            else
            {
                blad.setText("Taki login już istnieje!");
                blad.setVisible(true);
                System.out.println("Login dostępny");
                rlogin=true;
            }
            
        }
          catch(Exception e){ 
              System.out.println("exxxx");
            System.out.println(e); 
        }

    }
    
}