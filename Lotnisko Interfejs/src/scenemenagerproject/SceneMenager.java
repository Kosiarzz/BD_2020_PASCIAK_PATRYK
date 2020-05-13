/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenemenagerproject;

import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

/**
 *
 * @author student
 */
public class SceneMenager {
    private static Stage stage;
    private static Hashtable<String,String> view = new Hashtable<>();
    
    public static void addScene(String name,String path)throws IOException{
        view.put(name,path);
    }
    public static void removeScene(String name){
        view.remove(name);
    }
    public static void renderScene(String name){
        String path="";
        try{
            path=view.get(name);
            Parent root = FXMLLoader.load(SceneMenager.class.getResource(path));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("KorsarzLOT");
            stage.show();
        } catch (IOException ex) {
            System.err.println("Nie znaleziono pliku XML");
        }catch(RuntimeException ex){
            System.err.println("Nazwa widoku nieprawidłowa");
        }
    }
    
    public static void setStage(Stage _stage){
        stage = _stage;
    }
    
}
