package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.observer.Observable4View;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * this controller is used when the user enters his nickname
 */
public class NicknameController extends Observable4View implements GuiGenericController {
    @FXML
    public TextField nicknameField;
    @FXML
    public Pane pane;
    @FXML
    public Button confirmButton;

    @FXML
    public void initialize() {
        confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConfirmButtonClick);
    }

    private void onConfirmButtonClick (Event event) {

        String nickName=nicknameField.getText();
        confirmButton.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.sendNickname(nickName))).start();

    }
}
