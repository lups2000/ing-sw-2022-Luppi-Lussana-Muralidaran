package it.polimi.ingsw.View.Gui.Scenes;
import it.polimi.ingsw.network.server.Server;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Pradeeban Muralidaran
 */

public class ServerLogin extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader= new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/ServerLogin.fxml"));
        Parent root=null;
        try{
            root=fxmlLoader.load();
        }
        catch (IOException e){
            Server.LOGGER.severe(e.getMessage());
            System.exit(1);
        }

        primaryStage.setTitle("Eriantys - Server Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
        /*primaryStage.setTitle("Server Login");

        button = new Button();
        button.setText("Log in to server");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Controllo input e connessione server
            }
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }*/
}