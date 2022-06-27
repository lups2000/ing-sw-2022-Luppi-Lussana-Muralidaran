package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.observer.Observable4View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterCardController extends Observable4View implements GuiGenericController {

    private List<CharacterCard> characterCards;

    @FXML
    private Button character1;

    @FXML
    private Button character2;

    @FXML
    private Button character3;

    @FXML
    private Button skipButton;

    public CharacterCardController(List<CharacterCard> characterCards){
        this.characterCards=characterCards;
    }

    @FXML
    public void initialize(){

        initialDisplay();

        character1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onCharacterCardClick(1));
        character2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onCharacterCardClick(2));
        character3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onCharacterCardClick(3));
        skipButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onCharacterCardClick(-1));

    }

    private void onCharacterCardClick(int id){
        character1.setDisable(true);
        character2.setDisable(true);
        character3.setDisable(true);
        skipButton.setDisable(true);

        new Thread(() -> notifyObserver(obs -> obs.sendCharacterCard(id))).start();
    }


    private void initialDisplay(){

        ImageView character=null;
        List<ImageView> tempCharacters=new ArrayList<>();
        for (CharacterCard characterCard : characterCards) {
            int temp = -1;
            switch (characterCard.getType()) {
                case ONE_STUDENT_TO_ISLAND -> temp = 1;
                case CHOOSE_ISLAND -> temp = 2;
                case MOVE_MORE_MOTHER_NATURE -> temp = 3;
                case PUT_NO_ENTRY_TILES -> temp = 4;
                case NO_COUNT_TOWER -> temp = 5;
                case SWITCH_STUDENTS -> temp = 6;
                case TWO_ADDITIONAL_POINTS -> temp = 7;
                case COLOR_NO_INFLUENCE -> temp = 8;
                case SWITCH_DINING_WAITING -> temp = 9;
                case STUDENT_TO_DINING -> temp = 10;
                case COLOR_TO_STUDENT_BAG -> temp = 11;
                case CONTROL_ON_PROFESSOR -> temp = 12;
            }
            character = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/CharacterCards/CarteTOT_front" + temp + ".jpg"))));
            character.setFitWidth(200);
            character.setFitHeight(300);
            tempCharacters.add(character);
        }
        character1.setGraphic(tempCharacters.get(0));
        character2.setGraphic(tempCharacters.get(1));
        character3.setGraphic(tempCharacters.get(2));
    }


}
