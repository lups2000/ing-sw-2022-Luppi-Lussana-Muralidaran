package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.observer.Observable4View;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * this controller is used for the very first screen to welcome the user
 */
public class InitialScreenController extends Observable4View implements GuiGenericController {

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Button startButton;

    @FXML
    public void initialize() {
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onStartButtonClick);
    }

    private void onStartButtonClick(Event event){
        GuiMainController.nextPane(observers, event, "ServerLogin.fxml");
    }
}
