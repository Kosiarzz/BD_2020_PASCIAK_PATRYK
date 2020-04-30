/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenemenagerproject;

import java.net.URL;
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
    
    
    @FXML
    private void Zarejestruj(){
        
    }
    
    
    @FXML
    private void Powrot(){
        SceneMenager.renderScene("logowanie");
    }
 
      
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
}
