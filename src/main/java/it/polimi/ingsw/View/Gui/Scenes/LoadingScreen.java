package it.polimi.ingsw.View.Gui.Scenes;
import it.polimi.ingsw.network.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author Pradeeban Muralidaran
 */
public class LoadingScreen extends Application {

    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/InitialScreen.fxml"));
        Parent root=null;
        try{
            root=fxmlLoader.load();
        }
        catch (IOException e){
            Server.LOGGER.severe(e.getMessage());
            System.exit(1);
        }

        primaryStage.setTitle("Eriantys");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
