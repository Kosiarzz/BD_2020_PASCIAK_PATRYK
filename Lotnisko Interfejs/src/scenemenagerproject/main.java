package scenemenagerproject;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;


public class main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        SceneMenager.setStage(primaryStage);
        SceneMenager.addScene("uzytkownik", "/sceny/uzytkownik.fxml");
        SceneMenager.addScene("pracownik", "/sceny/pracownik.fxml");
        SceneMenager.addScene("koordynator", "/sceny/koordynator.fxml");
        SceneMenager.addScene("logowanie", "/sceny/logowanie.fxml");
        
        SceneMenager.renderScene("logowanie");
    }

 
    public static void main(String[] args) {
        launch(args);
    }
    
}
