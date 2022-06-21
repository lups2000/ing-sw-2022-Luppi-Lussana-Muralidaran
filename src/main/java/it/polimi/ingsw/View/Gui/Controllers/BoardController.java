package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Model.*;
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
    private List<Player> players;
    private Game game;

    public BoardController(){
        assistantCards=new ArrayList<>();
        characterCards=new ArrayList<>();
        players=new ArrayList<>();
        game=new Game();
    }

    //assistantCards
    @FXML
    private ImageView assistantPlayer0;
    @FXML
    private ImageView assistantPlayer1;
    @FXML
    private ImageView assistantPlayer2;
    @FXML
    private Label labelAssistantCardPlayer0;
    @FXML
    private Label labelAssistantCardPlayer1;
    @FXML
    private Label labelAssistantCardPlayer2;

    //characterCards
    @FXML
    private Button character1;
    @FXML
    private Button character2;
    @FXML
    private Button character3;
    @FXML
    private Label labelCharacterCards;

    //schoolBoards
    @FXML
    private AnchorPane schoolBoardPlayer0;
    @FXML
    private AnchorPane schoolBoardPlayer1;
    @FXML
    private AnchorPane schoolBoardPlayer2;
    @FXML
    private Label labelPlayer0;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label labelPlayer2;

    //Coins
    @FXML
    private ImageView coinImagePlayer0;
    @FXML
    private ImageView coinImagePlayer1;
    @FXML
    private ImageView coinImagePlayer2;
    @FXML
    private Label numCoinsPlayer0;
    @FXML
    private Label numCoinsPlayer1;
    @FXML
    private Label numCoinsPlayer2;

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
    @FXML
    private AnchorPane cloud2;

    //islands

    @FXML
    public void initialize(){

        initialDisplay(game.getMaxNumPlayers(),game.getExpertsVariant());

    }
    public void setCharacterCards(List<CharacterCard> characterCards) {this.characterCards = characterCards;}
    public void setAssistantCards(List<AssistantCard> assistantCards) {this.assistantCards = assistantCards;}
    public void setGame(Game game) {this.game = game;}
    public void setPlayers(List<Player> players) {this.players = players;}

    private void initialDisplay(int numPlayers,boolean expertVariant){

        if(numPlayers==2){

            //schoolBoards
            //label SchoolBoard
            labelPlayer0.setText(players.get(0).getNickname());
            labelPlayer1.setText(players.get(1).getNickname());
            labelPlayer1.setLayoutX(625);
            schoolBoardPlayer1.setLayoutX(447);
            schoolBoardPlayer2.setVisible(false);
            labelPlayer2.setVisible(false);

            displayEntireSchoolBoards();

            //label Assistant
            labelAssistantCardPlayer0.setText(players.get(0).getNickname());
            labelAssistantCardPlayer1.setText(players.get(1).getNickname());
            labelAssistantCardPlayer2.setVisible(false);

            //clouds
            cloud0.setVisible(true);
            cloud1.setVisible(true);
            cloud2.setVisible(false);
            displayCloudTiles();

            if(expertVariant){
                labelCharacterCards.setVisible(true);
                //coins
                numCoinsPlayer0.setStyle("-fx-font-weight: bold");
                numCoinsPlayer0.setText("1");
                numCoinsPlayer1.setStyle("-fx-font-weight: bold");
                coinImagePlayer1.setLayoutX(395);
                numCoinsPlayer1.setLayoutX(417);
                numCoinsPlayer1.setText("1");
                coinImagePlayer2.setVisible(false);
                numCoinsPlayer2.setVisible(false);
                //characterCards
                displayCharacterCards();
            }
            else{
                labelCharacterCards.setVisible(false);
                character1.setVisible(false);
                character2.setVisible(false);
                character3.setVisible(false);
                coinImagePlayer0.setVisible(false);
                coinImagePlayer1.setVisible(false);
                coinImagePlayer2.setVisible(false);
                numCoinsPlayer0.setVisible(false);
                numCoinsPlayer1.setVisible(false);
                numCoinsPlayer2.setVisible(false);
            }
        }
        else if(players.size()==3){

            //schoolBoards
            //label SchoolBoard
            labelPlayer0.setText(players.get(0).getNickname());
            labelPlayer1.setText(players.get(1).getNickname());
            labelPlayer2.setText(players.get(2).getNickname());

            displayEntireSchoolBoards();

            //label Assistant
            labelAssistantCardPlayer0.setText(players.get(0).getNickname());
            labelAssistantCardPlayer1.setText(players.get(1).getNickname());
            labelAssistantCardPlayer2.setText(players.get(2).getNickname());

            //clouds
            displayCloudTiles();

            if(game.getExpertsVariant()){
                labelCharacterCards.setVisible(true);
                //coins
                numCoinsPlayer0.setStyle("-fx-font-weight: bold");
                numCoinsPlayer0.setText("1");
                numCoinsPlayer1.setStyle("-fx-font-weight: bold");
                numCoinsPlayer1.setText("1");
                numCoinsPlayer2.setStyle("-fx-font-weight: bold");
                numCoinsPlayer2.setText("1");
                //characterCards
                displayCharacterCards();
            }
            else{
                labelCharacterCards.setVisible(false);
                character1.setVisible(false);
                character2.setVisible(false);
                character3.setVisible(false);
                coinImagePlayer0.setVisible(false);
                coinImagePlayer1.setVisible(false);
                coinImagePlayer2.setVisible(false);
                numCoinsPlayer0.setVisible(false);
                numCoinsPlayer1.setVisible(false);
                numCoinsPlayer2.setVisible(false);

            }
        }
        //labelAssistant
        labelAssistantCardPlayer0.setVisible(false);
        labelAssistantCardPlayer1.setVisible(false);
        labelAssistantCardPlayer2.setVisible(false);

    }

    private void displayEntireSchoolBoards(){
        if(players.size()==2){
            displayTowersPlayer(schoolBoardPlayer0,players.get(0));
            displayTowersPlayer(schoolBoardPlayer1,players.get(1));
            displayStudentWaitingPlayer(schoolBoardPlayer0,players.get(0));
            displayStudentWaitingPlayer(schoolBoardPlayer1,players.get(1));
        }
        else {
            displayTowersPlayer(schoolBoardPlayer0,players.get(0));
            displayTowersPlayer(schoolBoardPlayer1,players.get(1));
            displayStudentWaitingPlayer(schoolBoardPlayer0,players.get(0));
            displayStudentWaitingPlayer(schoolBoardPlayer1,players.get(1));
            displayTowersPlayer(schoolBoardPlayer2,players.get(2));
            displayStudentWaitingPlayer(schoolBoardPlayer2,players.get(2));

        }
    }

    private void displayCloudTiles(){
        if(players.size()==2){
            displayCloudStudents(cloud0,game.getCloudTiles().get(0));
            displayCloudStudents(cloud1,game.getCloudTiles().get(1));
        }
        else{
            displayCloudStudents(cloud0,game.getCloudTiles().get(0));
            displayCloudStudents(cloud1,game.getCloudTiles().get(1));
            displayCloudStudents(cloud2,game.getCloudTiles().get(2));
        }
    }

    private void displayCloudStudents(AnchorPane cloud,CloudTile cloudTile){
        List<Integer> layoutsX=Arrays.asList(21,55,45,40);
        List<Integer> layoutsY=Arrays.asList(45,41,16,63);
        List<Integer> layoutsXLabel=Arrays.asList(26,60,51,46);
        List<Integer> layoutsYLabel=Arrays.asList(46,43,17,63);
        Map<ImageView,Label> imageLabelMap =new HashMap<>();
        ImageView student;
        Label counter=null;
        int pos=0;
        for(PawnColor pawnColor : PawnColor.values()){
            int temp=0;
            student=null;
            while(temp<cloudTile.getStudents().get(pawnColor)){

                student = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + pawnColor + ".png"))));
                student.setFitWidth(20);
                student.setFitHeight(20);
                temp++;
            }
            if(student!=null && pos<cloudTile.getMaxNumStudents()){
                student.setLayoutX(layoutsX.get(pos));
                student.setLayoutY(layoutsY.get(pos));
                counter = new Label();
                counter.setText(Integer.toString(temp));
                counter.setStyle("-fx-font-weight: bold");
                counter.setLayoutX(layoutsXLabel.get(pos));
                counter.setLayoutY(layoutsYLabel.get(pos));
                pos++;
                imageLabelMap.put(student,counter);

            }
        }
        for(ImageView imageView : imageLabelMap.keySet()){
            cloud.getChildren().addAll(imageView,imageLabelMap.get(imageView));
        }
    }


    private void displayStudentWaitingPlayer(AnchorPane anchorPane,Player player){
        List<Integer> layoutsX=Arrays.asList(14,37);
        List<Integer> layoutsY=Arrays.asList(16,39,62,84,108);
        List<ImageView> imageViewList = new ArrayList<>();
        ImageView student;
        for(PawnColor pawnColor : PawnColor.values()){
            int temp=0;
            student=null;
            while (temp<player.getSchoolBoard().getStudentsWaiting().get(pawnColor)){
                student = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + pawnColor + ".png"))));
                student.setFitWidth(16);
                student.setFitHeight(16);
                imageViewList.add(student);
                temp++;
            }
        }
        ImageView imageView=null;
        int tmp=0;
        for(int j=0;j<5;j++){
            for(int i=0;i<2;i++){
                if(i!=0 || j!=0){
                    if(tmp<player.getSchoolBoard().getNumMaxStudentsWaiting()){
                        imageView=imageViewList.get(tmp);
                        imageView.setLayoutX(layoutsX.get(i));
                        imageView.setLayoutY(layoutsY.get(j));
                        anchorPane.getChildren().add(imageView);
                        tmp++;
                    }
                }

            }
        }
    }

    private void displayTowersPlayer(AnchorPane anchorPane,Player player){
        List<Integer> layoutsX=Arrays.asList(311,341);
        List<Integer> layoutsY=Arrays.asList(-4,19,43,66);
        List<ImageView> imageViewList = new ArrayList<>();
        String colorTower=player.getColorTower().getNameColor();
        for(int i=0;i<player.getSchoolBoard().getNumTowers();i++){
            ImageView tower = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + colorTower + "_tower.png"))));
            tower.setFitWidth(50);
            tower.setFitHeight(50);
            imageViewList.add(tower);
        }
        int temp=0;
        ImageView imageView=null;
        for(int j=0;j<4;j++){
            for(int i=0;i<2;i++){
                if(temp<player.getSchoolBoard().getNumTowers()){
                    imageView=imageViewList.get(temp);
                    imageView.setLayoutX(layoutsX.get(i));
                    imageView.setLayoutY(layoutsY.get(j));
                    anchorPane.getChildren().add(imageView);
                    temp++;
                }
            }
        }
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
        character1.setDisable(true);
        character2.setDisable(true);
        character3.setDisable(true);

    }

    /*
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
    }*/


}
