package it.polimi.ingsw.View.Gui;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gui extends Application {

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        //Image icon = new Image("src/main/java/it.polimi.ingsw/View/Gui/Images/logocranio.png");

        //stage.getIcons().add(icon);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("WELCOME TO ERIANTYS!\nPress esc button if you want exit fullscreen");
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.show();
    }
}
