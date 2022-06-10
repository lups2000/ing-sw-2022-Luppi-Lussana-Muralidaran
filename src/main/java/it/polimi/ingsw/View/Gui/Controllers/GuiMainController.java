package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.observer.Observable4View;
import it.polimi.ingsw.observer.Observer4View;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
}
