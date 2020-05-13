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
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        // insert the data
        stmt.executeUpdate("INSERT INTO KLIENCI_LOGOWANIE (LOGIN,HASLO)" + "VALUES ('"+slogin+"', '"+shaslo+"')");  
        stmt.executeUpdate("INSERT INTO KLIENT (IMIE,NAZWISKO,ADRES,PESEL,TELEFON,LOGIN)" + "VALUES ('"+simie+"', '"+snazwisko+"', '"+sadres+"','"+spesel+"','"+stelefon+"','"+slogin+"')");
     
        System.out.println("Konto stworzone.");
        con.close();  
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
    }
    
    private void polaczenie2(){
        
        try{   
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        // insert the data
        stmt.executeUpdate("INSERT INTO KLIENCI_LOGOWANIE (LOGIN,HASLO)" + "VALUES ('"+slogin+"', '"+shaslo+"')");  
        stmt.executeUpdate("UPDATE KLIENT SET LOGIN='"+slogin+"' WHERE PESEL='"+spesel+"'");
     
        System.out.println("Konto stworzone.");
        con.close();  
        }
        catch(Exception e)
        { 
            System.out.println(e);
        } 
        
    }
    
    private boolean sprawdzenie_danych(){
            //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("SPRAWDZAM DANE");
          String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
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
            else if(ex.getErrorCode()==20004)
            {
                System.out.println("LOGIN");
                blad.setText("Konto o takim loginie lub peselu juz istnieje!");
                blad.setVisible(true);
            }
            else
            {
                System.out.println("DZIALA");
                blad.setText("DZIALA");
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
    
    private void sprawdzenie_danych2(){
            //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
        System.out.println("SPRAWDZAM DANE");
          String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call REJESTRACJA_PESEL('"+simie+"','"+snazwisko+"','"+sadres+"','"+spesel+"','"+stelefon+"','"+slogin+"')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
          
            cstmt.execute();
            wynik=cstmt.getString(1);
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
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call REJESTRACJA_LOGIN('"+slogin+"','"+shaslo+"','"+sphaslo+"')}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.execute();
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
                System.out.println("Login dostępny");
                rlogin=true;
            }
            
        }
          catch(Exception e){ 
            System.out.println(e); 
        }

    }
    
}