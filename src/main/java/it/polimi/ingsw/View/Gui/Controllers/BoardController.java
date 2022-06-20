package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Model.AssistantCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.observer.Observable4View;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.*;


public class BoardController extends Observable4View implements GuiGenericController {

    private List<AssistantCard> assistantCards;
    private List<CharacterCard> characterCards;


    public BoardController(){
        assistantCards=new ArrayList<>();
        characterCards=new ArrayList<>();
    }

    //assistantCards
    @FXML
    private ImageView assistantCurrentPlayer;
    @FXML
    private ImageView assistantPlayer1;
    @FXML
    private ImageView assistantPlayer2;

    //characterCards
    @FXML
    private Button character1;
    @FXML
    private Button character2;
    @FXML
    private Button character3;

    //schoolBoards
    @FXML
    private AnchorPane schoolBoardCurrentPlayer;
    @FXML
    private AnchorPane schoolBoardPlayer1;
    @FXML
    private AnchorPane schoolBoardPlayer2;

    //labelPlayers
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label labelPlayer2;

    //numCoins
    @FXML
    private Label numCoinsCurrentPlayer;

    //noEntryTiles
    @FXML
    private Label numNoEntryTiles;

    //professors
    @FXML
    private ImageView redProf;
    @FXML
    private ImageView blueProf;
    @FXML
    private ImageView greenProf;
    @FXML
    private ImageView yellowProf;
    @FXML
    private ImageView pinkProf;

    //clouds
    @FXML
    private AnchorPane cloud0;
    @FXML
    private AnchorPane cloud1;

    //islands

    @FXML
    public void initialize(){

        //characterCards
        character1.setDisable(true);
        character2.setDisable(true);
        character3.setDisable(true);
        displayCharacterCards();

        assistantPlayer2.setVisible(assistantCards.size()==3);
        displayAssistantCards();



    }

    //method to display the character cards
    private void displayCharacterCards(){
        ImageView character=null;
        List<ImageView> tempCharacters=new ArrayList<>();
        for(int i=0;i<characterCards.size();i++){
            int temp=-1;
            switch (characterCards.get(i).getType()){
                case ONE_STUDENT_TO_ISLAND->temp=1;
                case CONTROL_ON_PROFESSOR -> temp=2;
                case CHOOSE_ISLAND->temp=3;
                case MOVE_MORE_MOTHER_NATURE -> temp=4;
                case PUT_NO_ENTRY_TILES -> temp=5;
                case NO_COUNT_TOWER -> temp=6;
                case SWITCH_STUDENTS -> temp=7;
                case TWO_ADDITIONAL_POINTS -> temp=8;
                case COLOR_NO_INFLUENCE -> temp=9;
                case SWITCH_DINING_WAITING ->temp=10;
                case STUDENT_TO_DINING -> temp=11;
                case COLOR_TO_STUDENT_BAG -> temp=12;
            }
            character=new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/CharacterCards/CarteTOT_front" + temp + ".jpg"))));
            character.setFitWidth(120);
            character.setFitHeight(190);
            tempCharacters.add(character);
        }
        character1.setGraphic(tempCharacters.get(0));
        character2.setGraphic(tempCharacters.get(1));
        character3.setGraphic(tempCharacters.get(2));
    }

    private void displayAssistantCards(){
        int temp=-1;
        Image assistant=null;
        List<Image> tempAssistants=new ArrayList<>();
        for (AssistantCard assistantCard : assistantCards) {
            temp = assistantCard.getValue();
            assistant = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/AssistantCards/x2Size/Assistente(" + temp + ").png")));
            tempAssistants.add(assistant);
        }
        if(assistantCards.size()>=2){
            assistantCurrentPlayer.setImage(tempAssistants.get(0));
            assistantCurrentPlayer.setFitWidth(120);
            assistantCurrentPlayer.setFitWidth(180);
            assistantCurrentPlayer.setVisible(true);
            assistantPlayer1.setImage(tempAssistants.get(1));
            assistantPlayer1.setFitWidth(120);
            assistantPlayer1.setFitWidth(180);
            assistantPlayer1.setVisible(true);
            if(assistantCards.size()==3){
                assistantPlayer2.setImage(tempAssistants.get(2));
                assistantPlayer2.setFitWidth(120);
                assistantPlayer2.setFitWidth(180);
                assistantPlayer2.setVisible(true);
            }
        }
    }

    public void setCharacterCards(List<CharacterCard> characterCards) {this.characterCards = characterCards;}
    public void setAssistantCards(List<AssistantCard> assistantCards) {this.assistantCards = assistantCards;}
}
