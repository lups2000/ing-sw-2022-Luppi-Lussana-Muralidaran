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
    public Button confermButton;

    @FXML
    public void initialize() {
        confermButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConfermButtonClick);
    }

    private void onConfermButtonClick (Event event) {
        String nickName=nicknameField.getText();
        confermButton.setDisable(true);
        //solo buttato giu ma devo guardare bene
        //new Thread(() -> notifyObserver(obs -> obs.sendNickname(nickName))).start();
        //o matchCreation (se è il primo player) o chiedere direttamente l'assistant seed TODO
    }
}
