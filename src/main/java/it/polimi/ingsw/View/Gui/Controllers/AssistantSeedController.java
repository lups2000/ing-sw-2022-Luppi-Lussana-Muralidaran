package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.observer.Observable4View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AssistantSeedController extends Observable4View implements GuiGenericController {

    private List<AssistantSeed> assistantSeedsAvailable;

    @FXML
    private Button king;

    @FXML
    private Button magician;

    @FXML
    private Button witch;

    @FXML
    private Button samurai;

    public AssistantSeedController(){
        this.assistantSeedsAvailable=new ArrayList<>();
    }

    /**
     * Initializes the event handler
     */
    @FXML
    public void initialize(){

        king.setDisable(!assistantSeedsAvailable.contains(AssistantSeed.KING));
        witch.setDisable(!assistantSeedsAvailable.contains(AssistantSeed.WITCH));
        samurai.setDisable(!assistantSeedsAvailable.contains(AssistantSeed.SAMURAI));
        magician.setDisable(!assistantSeedsAvailable.contains(AssistantSeed.MAGICIAN));


        witch.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantSeedClick(AssistantSeed.WITCH));
        king.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantSeedClick(AssistantSeed.KING));
        samurai.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantSeedClick(AssistantSeed.SAMURAI));
        magician.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onAssistantSeedClick(AssistantSeed.MAGICIAN));

    }

    /**
     * Disables the select seed for other players
     *
     * @param assistantSeed is the selected assistant seed
     */
    private void onAssistantSeedClick(AssistantSeed assistantSeed) {

        king.setDisable(true);
        witch.setDisable(true);
        magician.setDisable(true);
        samurai.setDisable(true);

        new Thread(() -> notifyObserver(obs -> obs.sendAssistantSeed(assistantSeed))).start();
    }

    /**
     * Refreshes the list of available seeds
     *
     * @param assistantSeedsAvailable list of available seeds
     */
    public void setAssistantSeedsAvailable(List<AssistantSeed> assistantSeedsAvailable) {
        this.assistantSeedsAvailable = assistantSeedsAvailable;
    }
}
