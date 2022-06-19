package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Model.AssistantCard;
import it.polimi.ingsw.observer.Observable4View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AssistantCardController extends Observable4View implements GuiGenericController {

    List<AssistantCard> assistantCardsAvailable;

    @FXML
    private Button assistant1;

    @FXML
    private Button assistant2;

    @FXML
    private Button assistant3;

    @FXML
    private Button assistant4;

    @FXML
    private Button assistant5;

    @FXML
    private Button assistant6;

    @FXML
    private Button assistant7;

    @FXML
    private Button assistant8;

    @FXML
    private Button assistant9;

    @FXML
    private Button assistant10;

    public AssistantCardController(){
        this.assistantCardsAvailable = new ArrayList<>();
    }

    @FXML
    public void initialize(){

        assistant1.setDisable(!checkCard(1,1));
        assistant2.setDisable(!checkCard(2,1));
        assistant3.setDisable(!checkCard(3,2));
        assistant4.setDisable(!checkCard(4,2));
        assistant5.setDisable(!checkCard(5,3));
        assistant6.setDisable(!checkCard(6,3));
        assistant7.setDisable(!checkCard(7,4));
        assistant8.setDisable(!checkCard(8,4));
        assistant9.setDisable(!checkCard(9,5));
        assistant10.setDisable(!checkCard(10,5));

        assistant1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(1,1)));
        assistant2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(2,1)));
        assistant3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(3,2)));
        assistant4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(4,2)));
        assistant5.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(5,3)));
        assistant6.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(6,3)));
        assistant7.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(7,4)));
        assistant8.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(8,4)));
        assistant9.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(9,5)));
        assistant10.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantCardClicked(new AssistantCard(10,5)));


    }

    private boolean checkCard(int value,int maxSteps){
        for (AssistantCard assistantCard : assistantCardsAvailable){
            if(assistantCard.getValue()==value && assistantCard.getMaxStepsMotherNature()==maxSteps){ //card present
                return true;
            }
        }
        return false;
    }

    private void onAssistantCardClicked(AssistantCard assistantCard){

        assistant1.setDisable(true);
        assistant2.setDisable(true);
        assistant3.setDisable(true);
        assistant4.setDisable(true);
        assistant5.setDisable(true);
        assistant6.setDisable(true);
        assistant7.setDisable(true);
        assistant8.setDisable(true);
        assistant9.setDisable(true);
        assistant10.setDisable(true);

        new Thread(()->notifyObserver(obs->obs.sendAssistantCard(assistantCard))).start();
    }

    public void setAssistantCardsAvailable(List<AssistantCard> assistantCardsAvailable) {
        this.assistantCardsAvailable = assistantCardsAvailable;
    }
}
