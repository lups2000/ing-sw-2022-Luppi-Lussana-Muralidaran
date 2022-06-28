package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.observer.Observable4View;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

public class CreateMatchController extends Observable4View implements GuiGenericController{

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Button confirmButton;

    @FXML
    public void initialize(){
        confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConfirmButtonClick);
    }

    private void onConfirmButtonClick(Event event){
        int numMaxPlayers=Integer.parseInt(choiceBox.getValue());
        boolean expertVariant=checkBox.isSelected();
        confirmButton.setDisable(true);

        new Thread(
                () -> notifyObserver(obs -> {
                    obs.sendNumPlayers(numMaxPlayers);
                    obs.sendExpertVariant(expertVariant);
                })
        ).start();


    }
}
