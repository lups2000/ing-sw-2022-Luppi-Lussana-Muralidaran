package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.View.Gui.Scenes.ErrorAlert;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.observer.Observable4View;
import it.polimi.ingsw.observer.Observer4View;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.List;

/**
 * GUI main controller, mostly used to switch from one scene to another
 */
public class GuiMainController extends Observable4View {

    private static Scene currentScene;
    private static GuiGenericController currentController;

    public static GuiGenericController getCurrentController() {
        return currentController;
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static <T> T nextPane(List<Observer4View> observerList, Scene scene, String fxml) {
        T controller = null;

        try {
            FXMLLoader loader = new FXMLLoader(GuiMainController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();
            ((Observable4View) controller).addAllObservers(observerList);

            currentController = (GuiGenericController) controller;
            currentScene = scene;
            currentScene.setRoot(root);
        } catch (IOException e) {
            Client.LOGGER.severe(e.getMessage());
        }
        return controller;
    }

    public static <T> T nextPane(List<Observer4View> observerList, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        return nextPane(observerList, scene, fxml);
    }

    public static <T> T nextPane(List<Observer4View> observerList, String fxml) {
        return nextPane(observerList, currentScene, fxml);
    }

    public static void nextPane(GuiGenericController guiGenericController,Scene scene,String fxml){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GuiMainController.class.getResource("/fxml/" +fxml));
            fxmlLoader.setController(guiGenericController);
            currentController=guiGenericController;

            Parent root = fxmlLoader.load();
            currentScene=scene;
            currentScene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void nextPane(GuiGenericController guiGenericController,String fxml){
        nextPane(guiGenericController,currentScene,fxml);
    }

    /*
    public static void showAlert(String title,String message) {
        ErrorAlert.display(title,message);
    }*/

    public static void showAlert(String title, String message) {
        FXMLLoader loader = new FXMLLoader(GuiMainController.class.getResource("/fxml/Alert.fxml"));

        Parent parent;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        AlertController alertController = loader.getController();
        Scene alertScene = new Scene(parent);
        alertController.setScene(alertScene);
        alertController.setAlertTitle(title);
        alertController.setAlertMessage(message);
        alertController.displayAlert();
    }
}
