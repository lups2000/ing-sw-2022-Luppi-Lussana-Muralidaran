package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.observer.Observable4View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;
import java.util.List;


public class BoardController extends Observable4View implements GuiGenericController {

    private Map<Player,AssistantCard> currentHand;
    private List<CharacterCard> characterCards;
    private List<Player> players;
    private List<Island> islands;
    private Game game;

    public BoardController(){
        currentHand=new HashMap<>();
        characterCards=new ArrayList<>();
        players=new ArrayList<>();
        islands=new ArrayList<>();
        game=new Game();
    }

    //externalAnchorPane
    @FXML
    private AnchorPane externalAnchorPane;

    //mainAnchorPane
    @FXML
    private AnchorPane mainAnchorPane;

    //assistantCards
    @FXML
    private VBox assistantCards;

    //characterCards
    @FXML
    private Button character1;
    @FXML
    private Button character2;
    @FXML
    private Button character3;
    @FXML
    private Label labelCharacterCards;

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

    //islands
    @FXML
    private AnchorPane island0;
    @FXML
    private AnchorPane island1;
    @FXML
    private AnchorPane island2;
    @FXML
    private AnchorPane island3;
    @FXML
    private AnchorPane island4;
    @FXML
    private AnchorPane island5;
    @FXML
    private AnchorPane island6;
    @FXML
    private AnchorPane island7;
    @FXML
    private AnchorPane island8;
    @FXML
    private AnchorPane island9;
    @FXML
    private AnchorPane island10;
    @FXML
    private AnchorPane island11;



    @FXML
    public void initialize(){

        initialDisplay(game.getMaxNumPlayers(),game.getExpertsVariant());

    }
    public void setCharacterCards(List<CharacterCard> characterCards) {this.characterCards = characterCards;}
    public void setCurrentHand(Map<Player, AssistantCard> currentHand) {this.currentHand = currentHand;}
    public void setGame(Game game) {this.game = game;}
    public void setPlayers(List<Player> players) {this.players = players;}
    public void setIslands(List<Island> islands) {this.islands = islands;}

    private void initialDisplay(int numPlayers, boolean expertVariant){

        if(numPlayers==2){

            displayEntireSchoolBoards();

            displayCloudTiles();

            if(expertVariant){
                //characterCards
                displayCharacterCards();
            }
            else{
                labelCharacterCards.setVisible(false);
                character1.setVisible(false);
                character2.setVisible(false);
                character3.setVisible(false);
            }
        }
        else if(players.size()==3){

            displayEntireSchoolBoards();

            //clouds
            displayCloudTiles();

            if(game.getExpertsVariant()){

                //characterCards
                displayCharacterCards();
            }
            else{
                labelCharacterCards.setVisible(false);
                character1.setVisible(false);
                character2.setVisible(false);
                character3.setVisible(false);
            }
        }

        displayIslands();
        displayAssistantCards();
    }

    private void displayEntireSchoolBoards(){
        List<Integer> layoutsX;
        List<Integer> layoutsY;
        List<Integer> labelLayoutX;
        List<Integer> labelLayoutY;
        List<Integer> labelCoinLayoutX;
        List<Integer> labelCoinLayoutY;
        List<Integer> imageCoinLayoutX;
        List<Integer> imageCoinLayoutY;
        if(game.getPlayers().size()==2){
            layoutsX=Arrays.asList(447,447);
            layoutsY=Arrays.asList(584,30);
            labelLayoutX=Arrays.asList(625,625);
            labelLayoutY=Arrays.asList(550,0);
            imageCoinLayoutX=Arrays.asList(395,395);
            imageCoinLayoutY=Arrays.asList(600,48);
            labelCoinLayoutX=Arrays.asList(421,421);
            labelCoinLayoutY=Arrays.asList(644,93);
        }
        else{
            layoutsX=Arrays.asList(447,210,665);
            layoutsY=Arrays.asList(584,30,30);
            labelLayoutX=Arrays.asList(625,388,843);
            labelLayoutY=Arrays.asList(550,0,0);
            imageCoinLayoutX=Arrays.asList(395,156,1067);
            imageCoinLayoutY=Arrays.asList(600,48,48);
            labelCoinLayoutX=Arrays.asList(421,182,1093);
            labelCoinLayoutY=Arrays.asList(644,93,93);
        }
        int pos=0;
        for(Player player : game.getPlayers()){
            AnchorPane schoolBoard = new AnchorPane();
            schoolBoard.setPrefWidth(400);
            schoolBoard.setPrefHeight(140);
            schoolBoard.setId(Integer.toString(player.getId()));
            schoolBoard.getStyleClass().add("schoolboardBG");
            schoolBoard.setLayoutX(layoutsX.get(pos));
            schoolBoard.setLayoutY(layoutsY.get(pos));

            displayTowersPlayer(schoolBoard,player);
            displayStudentWaitingPlayer(schoolBoard,player);

            Label nickName=new Label();
            nickName.setText(player.getNickname());
            nickName.setFont(new Font("Matura MT Script Capitals",16));
            nickName.setTextFill(Color.rgb(0,118,255));
            nickName.setLayoutX(labelLayoutX.get(pos));
            nickName.setLayoutY(labelLayoutY.get(pos));

            ImageView coin=new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Coin.png"))));
            coin.setFitWidth(60);
            coin.setFitHeight(60);
            coin.setLayoutX(imageCoinLayoutX.get(pos));
            coin.setLayoutY(imageCoinLayoutY.get(pos));

            Label numCoins = new Label();
            numCoins.setText(Integer.toString(player.getSchoolBoard().getNumCoins()));
            numCoins.setFont(new Font("Matura MT Script Capitals",14));
            numCoins.setStyle("-fx-font-weight: bold");
            numCoins.setTextFill(Color.rgb(100,5,13));
            numCoins.setLayoutX(labelCoinLayoutX.get(pos));
            numCoins.setLayoutY(labelCoinLayoutY.get(pos));
            pos++;
            if(game.getExpertsVariant()){
                externalAnchorPane.getChildren().addAll(nickName,schoolBoard,coin,numCoins);
            }
            else {
                externalAnchorPane.getChildren().addAll(nickName,schoolBoard);
            }
        }

    }

    private void displayCloudTiles(){
        List<Integer> layoutsX=Arrays.asList(526,383,240);
        List<Integer> layoutsY=Arrays.asList(107,107,107);
        int pos=0;
        for(CloudTile cloudTile : game.getCloudTiles()){
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(100);
            anchorPane.setPrefHeight(90);
            anchorPane.setId(Integer.toString(cloudTile.getId()));
            anchorPane.getStyleClass().add("cloudBG");
            anchorPane.setLayoutX(layoutsX.get(pos));
            anchorPane.setLayoutY(layoutsY.get(pos));
            pos++;
            displayCloudStudents(anchorPane,cloudTile);
            mainAnchorPane.getChildren().add(anchorPane);
        }
    }

    private void displayIslands(){

        List<Integer> layoutsX=Arrays.asList(810,810,658,481,301,135,10,10,135,301,481,658);
        List<Integer> layoutsY=Arrays.asList(80,208,275,275,275,275,208,80,4,4,4,4);
        int pos=0;
        for(Island island : game.getIslands()){
            AnchorPane islandAnchor = new AnchorPane();
            islandAnchor.setPrefWidth(120);
            islandAnchor.setPrefHeight(100);
            islandAnchor.setId(Integer.toString(island.getIndex()));
            islandAnchor.getStyleClass().add("islandBG");
            islandAnchor.setLayoutX(layoutsX.get(pos));
            islandAnchor.setLayoutY(layoutsY.get(pos));
            displayIsland(islandAnchor,island);
            pos++;
            mainAnchorPane.getChildren().add(islandAnchor);
        }
    }

    private void displayIsland(AnchorPane anchorPane,Island island){
        List<Integer> layoutsX=Arrays.asList(27,19,45,63,72); //order: B,G,R,P,Y
        List<Integer> layoutsY=Arrays.asList(26,46,41,56,35); //order: B,G,R,P,Y
        List<Integer> layoutsXLabel=Arrays.asList(33,25,51,69,78); //order: B,G,R,P,Y
        List<Integer> layoutsYLabel=Arrays.asList(26,47,41,55,34); //order: B,G,R,P,Y
        Map<ImageView,Label> imageLabelMap =new HashMap<>();
        ImageView student;
        Label counter=null;
        int pos=0;

        for(PawnColor pawnColor : PawnColor.values()){
            int temp=0;
            student=null;
            while(temp<island.getStudents().get(pawnColor)){
                student = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + pawnColor + ".png"))));
                student.setFitWidth(20);
                student.setFitHeight(20);
                temp++;
            }
            if(student!=null){
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
            anchorPane.getChildren().addAll(imageView,imageLabelMap.get(imageView));
        }
        if(island.isMotherNature()){
            ImageView mama = new ImageView(new Image("/Images/WoodenPieces/mother_nature.png"));
            mama.setFitHeight(70);
            mama.setFitWidth(60);
            mama.setLayoutX(57);
            mama.setLayoutY(-24);
            anchorPane.getChildren().add(mama);
        }
        if(island.getTower()!=null){
            ImageView tower = new ImageView(new Image("/Images/WoodenPieces/"+island.getTower().getNameColor()+"_tower.png"));
            tower.setFitHeight(50);
            tower.setFitWidth(50);
            tower.setLayoutX(22);
            tower.setLayoutY(-21);
            anchorPane.getChildren().add(tower);

            Label cnt=new Label();
            cnt.setText(Integer.toString(island.getNumTowers()));
            cnt.setStyle("-fx-font-weight: bold");
            cnt.setStyle("-fx-font-size: 14");
            if(island.getTower()==ColorTower.BLACK){
                cnt.setTextFill(Color.rgb(255,255,255));
            }
            cnt.setLayoutX(43);
            cnt.setLayoutY(3);
            anchorPane.getChildren().add(cnt);
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

    private void displayAssistantCards(){
        int temp=-1;
        Image assistant=null;

        for (Player player : currentHand.keySet()) {
            temp = currentHand.get(player).getValue();
            assistant = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/AssistantCards/x2Size/Assistente(" + temp + ").png")));
            ImageView assistantCardImage = new ImageView();
            assistantCardImage.setImage(assistant);
            assistantCardImage.setFitWidth(120);
            assistantCardImage.setFitHeight(180);
            Label assistantCardPlayer = new Label();
            assistantCardPlayer.setText(player.getNickname());
            assistantCardPlayer.setFont(new Font("Matura MT Script Capitals",16));
            assistantCardPlayer.setTextFill(Color.rgb(0,0,255));
            assistantCards.getChildren().addAll(assistantCardPlayer,assistantCardImage);
        }
    }


}
