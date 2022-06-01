package it.polimi.ingsw.View.Gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;


/**
 * @author Pradeeban Muralidaran
 * */
public class Gui extends Application {

    //Button button;
    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Eriantys");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
