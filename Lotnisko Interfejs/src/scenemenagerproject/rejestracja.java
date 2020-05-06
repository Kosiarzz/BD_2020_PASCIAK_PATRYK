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
            System.out.println("Pola uzupełnione.");
            blad.setVisible(false);
            if(sprawdzenie_haslo())
            {
                System.out.println("Hasła poprawne.");
                System.out.println("Tworzenie konta...");
                blad.setVisible(false);
                polaczenie();
            }
            else
            {
                blad.setText("Hasła nie są takie same!");
                blad.setVisible(true);
            }
        }
        else
        {
            blad.setText("Musisz uzupełnić wszystkie pola!");
            blad.setVisible(true);
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
    
    private boolean sprawdzenie_danych(){
            //slogin, simie, snazwisko, sadres, spesel, stelefon, shaslo, sphaslo;
          String wynik="FALSE";
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
            CallableStatement cstmt = connection.prepareCall("{?=call REJESTRACJA('"+simie+"','"+snazwisko+"','"+sadres+"','"+spesel+"','"+stelefon+"','"+slogin+"','"+shaslo+"','"+sphaslo+"')}");
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
            CallableStatement cstmt = connection.prepareCall("{?=call HASLO('"+shaslo+"','"+sphaslo+"')}");
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