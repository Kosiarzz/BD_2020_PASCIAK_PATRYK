/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenemenagerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.*;

public class logowanie {
    
    @FXML
    private TextField sam_marka, sam_model, sam_kolor,sam_moc,sam_miejsc,sam_drzwi,sam_oddzial;
    
    public void polaczenie(){  
        
    try{  
        //step1 load the driver class  
        Class.forName("oracle.jdbc.driver.OracleDriver");  

        //step2 create  the connection object  
        Connection con=DriverManager.getConnection(  
        "jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011");  

        //step3 create the statement object  
        Statement stmt=con.createStatement();  

        //step4 execute query  
        ResultSet rs=stmt.executeQuery("select * from KLIENT");  
        while(rs.next())  
        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  

        //step5 close the connection object  
        con.close();  

    }
    catch(Exception e)
    { 
        System.out.println(e);
    }  

    }
    
}  
