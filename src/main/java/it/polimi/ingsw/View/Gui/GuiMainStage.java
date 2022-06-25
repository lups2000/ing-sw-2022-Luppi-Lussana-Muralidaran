package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.Gui.Controllers.InitialScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * main GUI class that implements the main stage, launched by the GUIApp
 */
public class GuiMainStage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Gui view = new Gui();
        ClientController clientController = new ClientController(view);
        view.addObserver(clientController);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/InitialScreen.fxml"));
        Parent baseLayout = fxmlLoader.load();

        InitialScreenController initialScreenController = fxmlLoader.getController();
        initialScreenController.addObserver(clientController);

        Scene scene = new Scene(baseLayout);
        stage.setScene(scene);
        stage.setHeight(1280d);
        stage.setWidth(700d);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.getIcons().add(new Image("/Images/logocranio.png"));// All Stages have this icon
        stage.setFullScreenExitHint("");
        stage.setTitle("Eriantys");
        stage.show();

    }
}
