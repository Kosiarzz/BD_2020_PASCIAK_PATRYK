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

public class Uzytkownik  implements Initializable {
    
    
    @FXML
    private void wyloguj(){
        SceneMenager.renderScene("logowanie");
    }
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
