/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenemenagerproject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class pracownik implements Initializable {
    
    //zmienne ZAREZERWUJ
    @FXML
    Rectangle m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21, m22, m23, m24;
    
    @FXML
    TextField ZWmiejscowosc, ZWnrlotu; //wyszukiwanie w Zarezerwuj
    TextField Znrmiejsca, Zpesel; //Dane do rezerwacji
    
    String wartosc = "siemka";
    
    
    @FXML
    private TableView tLoty;
    
    @FXML
    private TableColumn<?, ?> tStart, tLadowanie, tDo, tMiejsc, tStatus;
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        ustawTabeleZarezerwuj();
        wstawDoTabeliZarezerwuj();
    }
    
    
    
    
    ///////////////////////////////ZAKŁADKA WYLOGUJ/////////////////////////////////
    @FXML
    private void wyloguj(){
        SceneMenager.renderScene("logowanie");
    }
    
    ///////////////////////////////ZAKŁADKA ZAREZERWUJ//////////////////////////////
    @FXML
    private void zarezerwuj(){
        
    }
        
    @FXML
    private void wyszukaj(){
        
        m1.setFill(Color.GREY);
        m2.setFill(Color.CHARTREUSE);
        m3.setFill(Color.RED);
        System.out.println("klik");
    }
    
    public void ustawTabeleZarezerwuj()
    {
       //ustawianie kolumn
       tStart.setCellValueFactory(new PropertyValueFactory<>(""));
    
    }
    public void wstawDoTabeliZarezerwuj()
    {
       tLoty.getItems().add(wartosc);
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
    
}