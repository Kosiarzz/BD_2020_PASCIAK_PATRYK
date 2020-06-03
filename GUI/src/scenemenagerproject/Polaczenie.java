package scenemenagerproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Polaczenie {
    
    public static Connection getConnection(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        Connection connection = null;
        
        try{
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","C##Patryk","Patryk011"); 
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return connection;
    }
}
