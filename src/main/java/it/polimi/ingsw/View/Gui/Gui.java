package it.polimi.ingsw.View.Gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gui extends Application {

    //Button button;
    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        Label label1 = new Label("Welcome to Eriantys board game!");
        Button button1 = new Button("Click here to start");
        button1.setAlignment(Pos.CENTER);
        button1.setOnAction(e -> window.setScene(scene2));

        //Layout 1
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        layout1.setAlignment(Pos.TOP_CENTER);
        scene1 = new Scene(layout1, 200, 200);

        //Button 2
        Button button2 = new Button("Go back to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        //Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 600, 300);

        window.setScene(scene1);
        window.setTitle("Eriantys");
        window.show();
    }



    //è una prova
    /*@Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Eriantys");
        button = new Button();
        button.setText("Click here to start");
        button.setOnAction(e -> System.out.println("Prova"));

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 750, 500);

        //Image icon = new Image("src/main/java/it.polimi.ingsw/View/Gui/Images/logocranio.png");
        //stage.getIcons().add(icon);

        //stage.setFullScreen(true);
        //stage.setFullScreenExitHint("WELCOME TO ERIANTYS!\nPress esc button if you want exit fullscreen");
        stage.setScene(scene);
        stage.show();
    }*/
}
