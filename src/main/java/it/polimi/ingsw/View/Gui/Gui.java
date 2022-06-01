package it.polimi.ingsw.View.Gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * @author Pradeeban Muralidaran
 * */
public class Gui extends Application {

    //Button button;
    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("src/main/java/it/polimi/ingsw/View/Gui/MainScreen.fxml"));
        primaryStage.setTitle("Eriantys");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
