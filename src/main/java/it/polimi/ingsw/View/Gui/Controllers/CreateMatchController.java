package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.observer.Observable4View;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.awt.event.MouseEvent;

public class CreateMatchController extends Observable4View implements GuiGenericController{

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Button confirmButton;

    private int minNumPlayers;
    private int maxNumPlayers;
    private boolean expertVariant;

    //constructor
    public CreateMatchController(){
        minNumPlayers=0;
        maxNumPlayers=0;
        expertVariant=false;
    }

    public void setMinMaxPlayersNumber(int min,int max){
        this.minNumPlayers=min;
        this.maxNumPlayers=max;
    }

    @FXML
    public void initialize(){
        ObservableList<String> list = choiceBox.getItems();
        list.add("2");
        list.add("3");
        //choiceBox.getItems().addAll(2,3);


        //confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED,this::);

    }

    private void onConfirmButtonClick(Event event){
        confirmButton.setDisable(true);


    }
}
