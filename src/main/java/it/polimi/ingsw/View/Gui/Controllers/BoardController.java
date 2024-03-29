package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.observer.Observable4View;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private List<CloudTile> cloudTiles;
    private boolean expertVariant;
    private Map<String,PawnColor> studentsWaiting = new HashMap<>();
    private Map<String,Island> islandsMap = new HashMap<>();
    private String idNodeStart;


    //externalAnchorPane
    @FXML
    private AnchorPane externalAnchorPane;

    //assistantCards
    @FXML
    private VBox assistantCards;

    //islands
    @FXML
    private HBox Hbox1Islands;
    @FXML
    private HBox Hbox2Islands;

    //professors
    @FXML
    private HBox professors;

    //clouds
    @FXML
    private HBox clouds;

    //characterCards
    @FXML
    private ImageView character1;
    @FXML
    private ImageView character2;
    @FXML
    private ImageView character3;
    @FXML
    private Label labelCharacterCards;
    @FXML
    private ImageView additionalCoinCharacter1;
    @FXML
    private ImageView additionalCoinCharacter2;
    @FXML
    private ImageView additionalCoinCharacter3;

    //noEntryTiles
    @FXML
    private Label numNoEntryTiles;

    //coin Labels
    @FXML
    private Label numCoinsLabel1;
    @FXML
    private Label numCoinsLabel2;
    @FXML
    private Label numCoinsLabel3;

    /**
     * Initialize the event handler
     */
    @FXML
    public void initialize(){

        numNoEntryTiles.setVisible(false);
        labelCharacterCards.setVisible(false);
        character1.setVisible(false);
        character2.setVisible(false);
        character3.setVisible(false);
        numCoinsLabel1.setText("");
        numCoinsLabel2.setText("");
        numCoinsLabel3.setText("");


    }
    public void setCharacterCards(List<CharacterCard> characterCards) {this.characterCards = characterCards;}
    public void setPlayers(List<Player> players) {this.players = players;}
    public void setIslands(List<Island> islands) {this.islands = islands;}

    /**
     * Method to display the element in the scene initially.
     * @param reducedGame
     */
    public void initialDisplay(ReducedGame reducedGame){

        expertVariant=reducedGame.isExpertVariant();
        characterCards=reducedGame.getCharacterCards();
        islands=reducedGame.getIslands();
        currentHand=reducedGame.getCurrentHand();
        cloudTiles=reducedGame.getCloudTiles();
        players=reducedGame.getPlayers();

        if(reducedGame.getPlayers().size()==2){

            displayEntireSchoolBoards(reducedGame.getPlayers());

            displayCloudTiles(reducedGame.getCloudTiles());

            if(reducedGame.isExpertVariant()){
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
        else if(reducedGame.getPlayers().size()==3){

            displayEntireSchoolBoards(reducedGame.getPlayers());

            //clouds
            displayCloudTiles(reducedGame.getCloudTiles());

            if(reducedGame.isExpertVariant()){

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

        displayProfessorsMainScreen();
        displayIslands(reducedGame.getIslands());
        displayAssistantCards();

        //numNoEntryTiles
        numNoEntryTiles.setText(Integer.toString(reducedGame.getNoEntryTilesCounter()));
        numNoEntryTiles.setVisible(true);
    }

    /**
     * Method to display the professors in the main screen.
     */
    private void displayProfessorsMainScreen(){
        boolean flag;
        Map<PawnColor,Image> pawnColorImageMap = new HashMap<>();
        pawnColorImageMap.put(PawnColor.RED,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.RED + ".png"))));
        pawnColorImageMap.put(PawnColor.BLUE,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.BLUE + ".png"))));
        pawnColorImageMap.put(PawnColor.YELLOW,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.YELLOW + ".png"))));
        pawnColorImageMap.put(PawnColor.GREEN,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.GREEN + ".png"))));
        pawnColorImageMap.put(PawnColor.PINK,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.PINK + ".png"))));

        professors.getChildren().clear();

        for(PawnColor pawnColor : PawnColor.values()){
            flag=false;
            for(Player player : players){
                if(player.getSchoolBoard().getProfessors().get(pawnColor)) {
                    flag=true;
                    break;
                }
            }
            if(!flag) {
                ImageView profMainScreen = new ImageView(pawnColorImageMap.get(pawnColor));
                profMainScreen.setFitWidth(36);
                profMainScreen.setFitHeight(40);
                professors.getChildren().add(profMainScreen);
            }
        }
    }

    /**
     * Method to display the professors on the player's schoolboards
     * @param anchorPane
     * @param player
     */
    private void displayProfessorsOnSchoolBoard(AnchorPane anchorPane,Player player){
        ImageView profSchoolBoard=null;
        Map<PawnColor,Image> pawnColorImageMap = new HashMap<>();
        pawnColorImageMap.put(PawnColor.RED,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.RED + ".png"))));
        pawnColorImageMap.put(PawnColor.BLUE,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.BLUE + ".png"))));
        pawnColorImageMap.put(PawnColor.YELLOW,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.YELLOW + ".png"))));
        pawnColorImageMap.put(PawnColor.GREEN,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.GREEN + ".png"))));
        pawnColorImageMap.put(PawnColor.PINK,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/teacher_" + PawnColor.PINK + ".png"))));

        for(PawnColor pawnColor : PawnColor.values()){
            if(player.getSchoolBoard().getProfessors().get(pawnColor)){
                profSchoolBoard = new ImageView(pawnColorImageMap.get(pawnColor));
                profSchoolBoard.setFitWidth(23);
                profSchoolBoard.setFitHeight(23);
                profSchoolBoard.setLayoutX(282);
                switch (pawnColor){
                    case GREEN -> profSchoolBoard.setLayoutY(13);
                    case RED -> profSchoolBoard.setLayoutY(37);
                    case YELLOW -> profSchoolBoard.setLayoutY(59);
                    case PINK -> profSchoolBoard.setLayoutY(82);
                    case BLUE -> profSchoolBoard.setLayoutY(105);
                }
                anchorPane.getChildren().add(profSchoolBoard);
            }
        }
    }

    /**
     * Method to display the schoolboards of the players.
     * @param players
     */
    public void displayEntireSchoolBoards(List<Player> players){

        studentsWaiting.clear();
        numCoinsLabel1.setText("");
        numCoinsLabel2.setText("");
        numCoinsLabel3.setText("");

        List<Integer> layoutsX;
        List<Integer> layoutsY;
        List<Integer> labelLayoutX;
        List<Integer> labelLayoutY;
        List<Integer> imageCoinLayoutX;
        List<Integer> imageCoinLayoutY;
        if(players.size()==2){
            layoutsX=Arrays.asList(447,447);
            layoutsY=Arrays.asList(584,30);
            labelLayoutX=Arrays.asList(625,625);
            labelLayoutY=Arrays.asList(550,0);
            imageCoinLayoutX=Arrays.asList(395,395);
            imageCoinLayoutY=Arrays.asList(600,48);

            numCoinsLabel1.setText(Integer.toString(players.get(0).getSchoolBoard().getNumCoins()));
            numCoinsLabel2.setText(Integer.toString(players.get(1).getSchoolBoard().getNumCoins()));
            numCoinsLabel3.setText("");
        }
        else{
            layoutsX=Arrays.asList(447,210,665);
            layoutsY=Arrays.asList(584,30,30);
            labelLayoutX=Arrays.asList(625,388,843);
            labelLayoutY=Arrays.asList(550,0,0);
            imageCoinLayoutX=Arrays.asList(395,156,1067);
            imageCoinLayoutY=Arrays.asList(600,48,48);

            numCoinsLabel2.setLayoutX(182);
            numCoinsLabel1.setText(Integer.toString(players.get(0).getSchoolBoard().getNumCoins()));
            numCoinsLabel2.setText(Integer.toString(players.get(1).getSchoolBoard().getNumCoins()));
            numCoinsLabel3.setText(Integer.toString(players.get(2).getSchoolBoard().getNumCoins()));
        }
        int pos=0;
        for(Player player : players){
            AnchorPane schoolBoard = new AnchorPane();
            schoolBoard.setPrefWidth(400);
            schoolBoard.setPrefHeight(140);
            schoolBoard.setId(Integer.toString(player.getId()));
            schoolBoard.getStyleClass().add("schoolboardBG");
            schoolBoard.setLayoutX(layoutsX.get(pos));
            schoolBoard.setLayoutY(layoutsY.get(pos));

            displayStudentWaitingPlayer(schoolBoard,player,false,false);
            displayStudentDiningPlayer(schoolBoard,player);
            displayProfessorsOnSchoolBoard(schoolBoard,player);
            displayTowersPlayer(schoolBoard,player);

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

            pos++;

            if(expertVariant){
                externalAnchorPane.getChildren().addAll(nickName,schoolBoard,coin);
            }
            else {
                externalAnchorPane.getChildren().addAll(nickName,schoolBoard);
            }
        }

    }

    /**
     * Method to display the cloudTiles og the game.
     * @param cloudTiles
     */
    public void displayCloudTiles(List<CloudTile> cloudTiles){
        clouds.getChildren().clear();
        for(CloudTile cloudTile : cloudTiles){
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(100);
            anchorPane.setPrefHeight(90);
            anchorPane.setId(Integer.toString(cloudTile.getId()));
            anchorPane.getStyleClass().add("cloudBG");
            displayCloudStudents(anchorPane,cloudTile);
            clouds.getChildren().add(anchorPane);
        }
    }

    /**
     * Method to display the islands.
     * @param islands
     */
    public void displayIslands(List<Island> islands){

        Hbox1Islands.getChildren().clear();
        Hbox2Islands.getChildren().clear();
        int i=0;
        for(Island island : islands){
            AnchorPane islandAnchor = new AnchorPane();
            islandAnchor.setPrefWidth(120);
            islandAnchor.setPrefHeight(100);
            islandAnchor.setId(Integer.toString(island.getIndex()));
            islandAnchor.getStyleClass().add("islandBG");
            displayIsland(islandAnchor,island);
            if(i<islands.size()/2){
                Hbox1Islands.getChildren().add(islandAnchor);
            }
            else{
                Hbox2Islands.getChildren().add(islandAnchor);
            }
            i++;
        }
    }

    /**
     * Method to display the single island with all its characteristics.
     * @param anchorPane
     * @param island
     */
    private void displayIsland(AnchorPane anchorPane,Island island){
        List<Integer> layoutsX=Arrays.asList(27,19,45,63,72); //order: B,G,R,P,Y
        List<Integer> layoutsY=Arrays.asList(26,46,41,56,35); //order: B,G,R,P,Y
        List<Integer> layoutsXLabel=Arrays.asList(33,25,51,69,78); //order: B,G,R,P,Y
        List<Integer> layoutsYLabel=Arrays.asList(26,47,41,55,34); //order: B,G,R,P,Y
        Map<ImageView,Label> imageLabelMap =new HashMap<>();
        ImageView student;
        Label counter=null;
        int pos=0;
        Map<PawnColor,Image> pawnColorImageMap = new HashMap<>();
        pawnColorImageMap.put(PawnColor.RED,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.RED + ".png"))));
        pawnColorImageMap.put(PawnColor.BLUE,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.BLUE + ".png"))));
        pawnColorImageMap.put(PawnColor.YELLOW,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.YELLOW + ".png"))));
        pawnColorImageMap.put(PawnColor.GREEN,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.GREEN + ".png"))));
        pawnColorImageMap.put(PawnColor.PINK,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.PINK + ".png"))));

        for(PawnColor pawnColor : PawnColor.values()){
            int temp=0;
            student=null;
            while(temp<island.getStudents().get(pawnColor)){
                student = new ImageView(pawnColorImageMap.get(pawnColor));
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
        if(island.getNoEntryTiles()>0){
            ImageView noEntryImage = new ImageView(new Image("/Images/WoodenPieces/deny_island_icon.png"));
            noEntryImage.setFitWidth(30);
            noEntryImage.setFitHeight(30);
            noEntryImage.setLayoutX(31);
            noEntryImage.setLayoutY(64);
            Label noEntryCounter = new Label();
            noEntryCounter.setStyle("-fx-font-weight: bold");
            noEntryCounter.setStyle("-fx-font-size: 14");
            noEntryCounter.setLayoutX(60);
            noEntryCounter.setLayoutY(78);
            noEntryCounter.setTextFill(Color.rgb(255,0,0));
            noEntryCounter.setText(Integer.toString(island.getNoEntryTiles()));

            anchorPane.getChildren().addAll(noEntryImage,noEntryCounter);
        }

    }

    /**
     * Method to display the students on the single island.
     * @param cloud
     * @param cloudTile
     */
    private void displayCloudStudents(AnchorPane cloud,CloudTile cloudTile){
        List<Integer> layoutsX=Arrays.asList(21,55,45,40);
        List<Integer> layoutsY=Arrays.asList(45,41,16,63);
        List<Integer> layoutsXLabel=Arrays.asList(26,60,51,46);
        List<Integer> layoutsYLabel=Arrays.asList(46,43,17,63);
        Map<ImageView,Label> imageLabelMap =new HashMap<>();
        ImageView student;
        Label counter=null;
        Map<PawnColor,Image> pawnColorImageMap = new HashMap<>();
        pawnColorImageMap.put(PawnColor.RED,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.RED + ".png"))));
        pawnColorImageMap.put(PawnColor.BLUE,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.BLUE + ".png"))));
        pawnColorImageMap.put(PawnColor.YELLOW,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.YELLOW + ".png"))));
        pawnColorImageMap.put(PawnColor.GREEN,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.GREEN + ".png"))));
        pawnColorImageMap.put(PawnColor.PINK,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.PINK + ".png"))));

        int pos=0;
        for(PawnColor pawnColor : PawnColor.values()){
            int temp=0;
            student=null;
            while(temp<cloudTile.getStudents().get(pawnColor)){

                student = new ImageView(pawnColorImageMap.get(pawnColor));
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

    /**
     * Method to display the students in the dining of the player's schoolboard
     * @param anchorPane
     * @param player
     */
    private void displayStudentDiningPlayer(AnchorPane anchorPane,Player player){
        List<Integer> layoutsX=Arrays.asList(75,95,114,133,152,171,190,209,228,247);
        int layoutY=0;
        List<ImageView> imageViewList = new ArrayList<>();
        ImageView student;
        Map<PawnColor,Image> pawnColorImageMap = new HashMap<>();
        pawnColorImageMap.put(PawnColor.RED,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.RED + ".png"))));
        pawnColorImageMap.put(PawnColor.BLUE,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.BLUE + ".png"))));
        pawnColorImageMap.put(PawnColor.YELLOW,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.YELLOW + ".png"))));
        pawnColorImageMap.put(PawnColor.GREEN,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.GREEN + ".png"))));
        pawnColorImageMap.put(PawnColor.PINK,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.PINK + ".png"))));

        for(PawnColor pawnColor : PawnColor.values()){
            int temp=0;
            student=null;
            imageViewList.clear();
            while (temp<player.getSchoolBoard().getStudentsDining().get(pawnColor)){
                student = new ImageView(pawnColorImageMap.get(pawnColor));
                student.setFitWidth(15);
                student.setFitHeight(15);
                imageViewList.add(student);
                temp++;
            }
            if(student!=null){
                //qua ho le x immagini degli studenti di un certo colore
                switch (pawnColor){
                    case GREEN -> layoutY=17;
                    case RED -> layoutY=40;
                    case YELLOW -> layoutY=63;
                    case PINK -> layoutY=85;
                    case BLUE -> layoutY=108;
                }
                ImageView studentImage =null;
                for(int j=0;j<temp;j++){
                    studentImage = imageViewList.get(j);
                    studentImage.setLayoutX(layoutsX.get(j));
                    studentImage.setLayoutY(layoutY);
                    anchorPane.getChildren().add(studentImage);
                }
            }
        }
    }

    /**
     * Method to display the students in the entrance/waiting of the player's schoolboard.
     * @param anchorPane
     * @param player
     * @param draggable boolean to indicate that the students could be dragged.
     * @param toIsland boolean to indicate that the students could be dragged to an island.
     */
    private void displayStudentWaitingPlayer(AnchorPane anchorPane,Player player,boolean draggable,boolean toIsland){
        List<Integer> layoutsX=Arrays.asList(14,37);
        List<Integer> layoutsY=Arrays.asList(16,39,62,84,108);
        List<ImageView> imageViewList = new ArrayList<>();
        List<PawnColor> pawnColors = new ArrayList<>();
        Map<PawnColor,Image> pawnColorImageMap = new HashMap<>();
        pawnColorImageMap.put(PawnColor.RED,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.RED + ".png"))));
        pawnColorImageMap.put(PawnColor.BLUE,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.BLUE + ".png"))));
        pawnColorImageMap.put(PawnColor.YELLOW,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.YELLOW + ".png"))));
        pawnColorImageMap.put(PawnColor.GREEN,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.GREEN + ".png"))));
        pawnColorImageMap.put(PawnColor.PINK,new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + PawnColor.PINK + ".png"))));

        ImageView student;
        for(PawnColor pawnColor : PawnColor.values()){
            int temp=0;
            student=null;
            while (temp<player.getSchoolBoard().getStudentsWaiting().get(pawnColor)){
                student = new ImageView(pawnColorImageMap.get(pawnColor));
                student.setFitWidth(16);
                student.setFitHeight(16);
                imageViewList.add(student);
                pawnColors.add(pawnColor);
                temp++;
            }
        }
        ImageView imageView=null;
        int tmp=0;
        for(int j=0;j<5;j++){
            for(int i=0;i<2;i++){
                if(i!=0 || j!=0){
                    if(tmp<player.getSchoolBoard().getNumStudentsWaiting()){
                        imageView=imageViewList.get(tmp);
                        imageView.setFitWidth(16);
                        imageView.setFitHeight(16);
                        imageView.setLayoutX(layoutsX.get(i));
                        imageView.setLayoutY(layoutsY.get(j));
                        imageView.setId(player.getId()+"-"+tmp);
                        studentsWaiting.put(imageView.getId(),pawnColors.get(tmp));
                        if(draggable){
                            makeDraggableDining(imageView,toIsland);
                        }
                        anchorPane.getChildren().add(imageView);
                        tmp++;
                    }
                }
            }
        }
    }

    /**
     * Method to display the towers on the player's schoolboard.
     * @param anchorPane
     * @param player
     */
    private void displayTowersPlayer(AnchorPane anchorPane,Player player){
        List<Integer> layoutsX=Arrays.asList(311,341);
        List<Integer> layoutsY=Arrays.asList(-4,19,43,66);
        List<ImageView> imageViewList = new ArrayList<>();
        String colorTower=player.getColorTower().getNameColor();
        Image towerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/WoodenPieces/" + colorTower + "_tower.png")));
        for(int i=0;i<player.getSchoolBoard().getNumTowers();i++){
            ImageView tower = new ImageView(towerImage);
            tower.setFitWidth(50);
            tower.setFitHeight(50);
            imageViewList.add(tower);
        }
        int temp=0;
        ImageView imageView1=null;
        for(int j=0;j<4;j++){
            for(int i=0;i<2;i++){
                if(temp<player.getSchoolBoard().getNumTowers()){
                    imageView1=imageViewList.get(temp);
                    imageView1.setLayoutX(layoutsX.get(i));
                    imageView1.setLayoutY(layoutsY.get(j));
                    anchorPane.getChildren().add(imageView1);
                    temp++;
                }
            }
        }

    }

    /**
     * Method to display the Character Cards on the main Screen.
     */
    private void displayCharacterCards(){
        Image character=null;
        List<Image> tempCharacters=new ArrayList<>();
        List<Boolean> tempUsed = new ArrayList<>();
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
            character = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/CharacterCards/CarteTOT_front" + temp + ".jpg")));
            tempCharacters.add(character);
            tempUsed.add(characterCard.isUsed());
        }
        character1.setImage(tempCharacters.get(0));
        character2.setImage(tempCharacters.get(1));
        character3.setImage(tempCharacters.get(2));
        labelCharacterCards.setVisible(true);
        character1.setVisible(true);
        character2.setVisible(true);
        character3.setVisible(true);
        character1.setDisable(true);
        character2.setDisable(true);
        character3.setDisable(true);
        if(tempUsed.get(0)){
            additionalCoinCharacter1.setVisible(true);
        }
        if(tempUsed.get(1)){
            additionalCoinCharacter2.setVisible(true);
        }
        if(tempUsed.get(2)){
            additionalCoinCharacter3.setVisible(true);
        }

    }

    /**
     * Method to display the Assistant Cards on the main Screen.
     */
    private void displayAssistantCards(){
        assistantCards.getChildren().clear();

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

    /**
     * Method invoked when a student must be moved from the entrance to the dining.
     * @param currentPlayer
     */
    public void moveStudToDining(Player currentPlayer){

        numCoinsLabel1.setText("");
        numCoinsLabel2.setText("");
        numCoinsLabel3.setText("");

        studentsWaiting.clear();

        List<Integer> layoutsX;
        List<Integer> layoutsY;
        List<Integer> labelLayoutX;
        List<Integer> labelLayoutY;
        List<Integer> imageCoinLayoutX;
        List<Integer> imageCoinLayoutY;
        if(players.size()==2){
            layoutsX=Arrays.asList(447,447);
            layoutsY=Arrays.asList(584,30);
            labelLayoutX=Arrays.asList(625,625);
            labelLayoutY=Arrays.asList(550,0);
            imageCoinLayoutX=Arrays.asList(395,395);
            imageCoinLayoutY=Arrays.asList(600,48);

            numCoinsLabel1.setText(Integer.toString(players.get(0).getSchoolBoard().getNumCoins()));
            numCoinsLabel2.setText(Integer.toString(players.get(1).getSchoolBoard().getNumCoins()));
            numCoinsLabel3.setText("");
        }
        else{
            layoutsX=Arrays.asList(447,210,665);
            layoutsY=Arrays.asList(584,30,30);
            labelLayoutX=Arrays.asList(625,388,843);
            labelLayoutY=Arrays.asList(550,0,0);
            imageCoinLayoutX=Arrays.asList(395,156,1067);
            imageCoinLayoutY=Arrays.asList(600,48,48);

            numCoinsLabel2.setLayoutX(182);
            numCoinsLabel1.setText(Integer.toString(players.get(0).getSchoolBoard().getNumCoins()));
            numCoinsLabel2.setText(Integer.toString(players.get(1).getSchoolBoard().getNumCoins()));
            numCoinsLabel3.setText(Integer.toString(players.get(2).getSchoolBoard().getNumCoins()));
        }
        int pos=0;
        for(Player player : players){
            AnchorPane schoolBoard = new AnchorPane();
            schoolBoard.setPrefWidth(400);
            schoolBoard.setPrefHeight(140);
            schoolBoard.setId(Integer.toString(player.getId()));
            schoolBoard.getStyleClass().add("schoolboardBG");
            schoolBoard.setLayoutX(layoutsX.get(pos));
            schoolBoard.setLayoutY(layoutsY.get(pos));

            displayStudentWaitingPlayer(schoolBoard,player, player.getId() == currentPlayer.getId(),false);
            displayStudentDiningPlayer(schoolBoard,player);
            displayProfessorsOnSchoolBoard(schoolBoard,player);
            displayTowersPlayer(schoolBoard,player);

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

            pos++;

            if(expertVariant){
                externalAnchorPane.getChildren().addAll(nickName,schoolBoard,coin);
            }
            else {
                externalAnchorPane.getChildren().addAll(nickName,schoolBoard);
            }
        }
    }

    /**
     * Method invoked when Mother Nature must be moved.
     * @param islands
     * @param steps
     */
    public void moveMotherNature(List<Island> islands,int steps){

        Hbox1Islands.getChildren().clear();
        Hbox2Islands.getChildren().clear();
        List<AnchorPane> islandAnchorPanes = new ArrayList<>();
        int i=0;
        int indexMother=0;


        for(Island island : islands){
            if(island.isMotherNature()){
                indexMother=island.getIndex();
                break;
            }
        }
        int tempIndex=0;
        for(int j=1;j<=steps;j++){
            tempIndex = indexMother + j;
            if(tempIndex >= islands.size()){
                tempIndex = tempIndex % islands.size();
            }
            Island island = islands.get(tempIndex);
            AnchorPane islandAnchor = new AnchorPane();
            islandAnchor.setPrefWidth(120);
            islandAnchor.setPrefHeight(100);
            islandAnchor.setId(Integer.toString(island.getIndex()));
            islandAnchor.getStyleClass().add("islandBG");
            islandAnchor.setId(Integer.toString(island.getIndex()));
            islandsMap.put(islandAnchor.getId(),island);
            displayIsland(islandAnchor,island);
            makeDraggableIsland(islandAnchor,false);
            islandAnchorPanes.add(islandAnchor);
        }
        int tmp=0;
        for(int j = 1; j<=islands.size()-steps;j++){
            tmp = tempIndex + j;
            if(tmp >= islands.size()){
                tmp = tmp%islands.size();
            }
            Island island = islands.get(tmp);
            AnchorPane islandAnchor = new AnchorPane();
            islandAnchor.setPrefWidth(120);
            islandAnchor.setPrefHeight(100);
            islandAnchor.setId(Integer.toString(island.getIndex()));
            islandAnchor.getStyleClass().add("islandBG");
            islandAnchor.setId(Integer.toString(island.getIndex()));
            islandsMap.put(islandAnchor.getId(),island);
            displayIsland(islandAnchor,island);

            islandAnchor.setOpacity(0.5);
            islandAnchor.setDisable(true);
            islandAnchorPanes.add(islandAnchor);
        }


        islandAnchorPanes.sort((o1, o2) -> {
            if(Integer.parseInt(o1.getId())>Integer.parseInt(o2.getId())){
                return 1;
            }
            else{
                return -1;
            }
        });
        for(AnchorPane anchorPane : islandAnchorPanes){
            if(i<islandAnchorPanes.size()/2){
                Hbox1Islands.getChildren().add(anchorPane);
            }
            else{
                Hbox2Islands.getChildren().add(anchorPane);
            }
            i++;
        }
    }

    /**
     * Method invoked when a cloud tile must be chosen by a player.
     * @param cloudTiles
     */
    public void chooseCloudTile(List<CloudTile> cloudTiles){
        clouds.getChildren().clear();


        for(CloudTile cloudTile : cloudTiles){
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefWidth(100);
            anchorPane.setPrefHeight(90);
            anchorPane.setId(Integer.toString(cloudTile.getId()));
            anchorPane.getStyleClass().add("cloudBG");
            if(cloudTile.getNumStudents()>0){
                displayCloudStudents(anchorPane,cloudTile);
                makeDraggableCloud(anchorPane);
            }
            else{
                anchorPane.setOpacity(0.5);
                anchorPane.setDisable(true);
                anchorPane.setCursor(Cursor.DEFAULT);
            }
            clouds.getChildren().add(anchorPane);
        }
    }

    /**
     * Method invoked when a student must be moved from the entrance to an Island.
     * @param currentPlayer
     * @param islands
     */
    public void moveStudToIsland(Player currentPlayer,List<Island> islands){

        numCoinsLabel1.setText("");
        numCoinsLabel2.setText("");
        numCoinsLabel3.setText("");

        List<Integer> layoutsX;
        List<Integer> layoutsY;
        List<Integer> labelLayoutX;
        List<Integer> labelLayoutY;
        List<Integer> imageCoinLayoutX;
        List<Integer> imageCoinLayoutY;
        if(players.size()==2){
            layoutsX=Arrays.asList(447,447);
            layoutsY=Arrays.asList(584,30);
            labelLayoutX=Arrays.asList(625,625);
            labelLayoutY=Arrays.asList(550,0);
            imageCoinLayoutX=Arrays.asList(395,395);
            imageCoinLayoutY=Arrays.asList(600,48);

            numCoinsLabel1.setText(Integer.toString(players.get(0).getSchoolBoard().getNumCoins()));
            numCoinsLabel2.setText(Integer.toString(players.get(1).getSchoolBoard().getNumCoins()));
            numCoinsLabel3.setText("");
        }
        else{
            layoutsX=Arrays.asList(447,210,665);
            layoutsY=Arrays.asList(584,30,30);
            labelLayoutX=Arrays.asList(625,388,843);
            labelLayoutY=Arrays.asList(550,0,0);
            imageCoinLayoutX=Arrays.asList(395,156,1067);
            imageCoinLayoutY=Arrays.asList(600,48,48);

            numCoinsLabel2.setLayoutX(182);
            numCoinsLabel1.setText(Integer.toString(players.get(0).getSchoolBoard().getNumCoins()));
            numCoinsLabel2.setText(Integer.toString(players.get(1).getSchoolBoard().getNumCoins()));
            numCoinsLabel3.setText(Integer.toString(players.get(2).getSchoolBoard().getNumCoins()));
        }
        int pos=0;
        for(Player player : players){
            AnchorPane schoolBoard = new AnchorPane();
            schoolBoard.setPrefWidth(400);
            schoolBoard.setPrefHeight(140);
            schoolBoard.setId(Integer.toString(player.getId()));
            schoolBoard.getStyleClass().add("schoolboardBG");
            schoolBoard.setLayoutX(layoutsX.get(pos));
            schoolBoard.setLayoutY(layoutsY.get(pos));

            displayStudentWaitingPlayer(schoolBoard,player, player.getId() == currentPlayer.getId(),true);
            displayStudentDiningPlayer(schoolBoard,player);
            displayProfessorsOnSchoolBoard(schoolBoard,player);
            displayTowersPlayer(schoolBoard,player);

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

            pos++;

            if(expertVariant){
                externalAnchorPane.getChildren().addAll(nickName,schoolBoard,coin);
            }
            else {
                externalAnchorPane.getChildren().addAll(nickName,schoolBoard);
            }
        }

        Hbox1Islands.getChildren().clear();
        Hbox2Islands.getChildren().clear();

        int i=0;
        for(Island island : islands){
            AnchorPane islandAnchor = new AnchorPane();
            islandAnchor.setPrefWidth(120);
            islandAnchor.setPrefHeight(100);
            islandAnchor.setId(Integer.toString(island.getIndex()));
            islandAnchor.getStyleClass().add("islandBG");
            displayIsland(islandAnchor,island);

            makeDraggableIsland(islandAnchor,true);

            if(i<islands.size()/2){
                Hbox1Islands.getChildren().add(islandAnchor);
            }
            else{
                Hbox2Islands.getChildren().add(islandAnchor);
            }
            i++;
        }
    }

    /**
     * Method invoked by certain character cards to simply choose an Island.
     * @param islands
     */
    public void askIsland(List<Island> islands){

        Hbox1Islands.getChildren().clear();
        Hbox2Islands.getChildren().clear();
        int i=0;
        for(Island island : islands){
            AnchorPane islandAnchor = new AnchorPane();
            islandAnchor.setPrefWidth(120);
            islandAnchor.setPrefHeight(100);
            islandAnchor.setId(Integer.toString(island.getIndex()));
            islandAnchor.getStyleClass().add("islandBG");
            displayIsland(islandAnchor,island);

            makeDraggableIslandForCharacter(islandAnchor);

            if(i<islands.size()/2){
                Hbox1Islands.getChildren().add(islandAnchor);
            }
            else{
                Hbox2Islands.getChildren().add(islandAnchor);
            }
            i++;
        }
    }


    //Click / Drag and Drop

    private void makeDraggableDining(Node node,boolean toIsland){
        node.setCursor(Cursor.HAND);
        if(!toIsland){ //means that the student must be moved to the dining
            node.setOnMouseClicked( e->{
                idNodeStart=e.getPickResult().getIntersectedNode().getId();
                PawnColor pawnColorChosen = studentsWaiting.get(idNodeStart);
                new Thread(() -> notifyObserver(obs -> obs.sendStudentToDining(pawnColorChosen))).start();
            });
        }
        else{ //means that the player wants to move a student to an Island
            node.setOnDragDetected(e -> {
                Dragboard db = node.startDragAndDrop(TransferMode.ANY);
                idNodeStart=e.getPickResult().getIntersectedNode().getId();
                ClipboardContent content = new ClipboardContent();
                content.putString(idNodeStart);
                db.setContent(content);
            });
            node.setOnMouseDragged(e->{
                e.setDragDetect(true);
            });
        }

    }

    private void makeDraggableIsland(Node node,boolean withDrop){
        node.setCursor(Cursor.HAND);

        if(!withDrop){
            node.setOnMouseClicked(e->{
                idNodeStart=e.getPickResult().getIntersectedNode().getId();
                int idIslandClicked = Integer.parseInt(idNodeStart);

                int indexMotherNat=0;
                for(Island island : islandsMap.values()){
                    if(island.isMotherNature()){
                        indexMotherNat=island.getIndex();
                    }
                }
                int finalIndexMotherNat = indexMotherNat;
                int numIslands =islandsMap.values().size();
                int steps = ((idIslandClicked - finalIndexMotherNat)+numIslands) % numIslands;
                new Thread(()->notifyObserver(obs->obs.sendMoveMotherNature(steps))).start();
            });
        }
        else{
            node.setOnDragOver(e->{
                if(e.getGestureSource() != node && e.getDragboard().hasString()){
                    e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    node.setOpacity(0.5);
                }
                e.consume();
            });
            node.setOnDragExited(e->{
                node.setOpacity(1);
                e.consume();
            });
            node.setOnDragDropped(e->{
                PawnColor pawnColorChosen;
                Dragboard db = e.getDragboard();
                boolean success =false;
                pawnColorChosen = studentsWaiting.get(idNodeStart);
                if(db.hasString()){
                    if(pawnColorChosen != null){
                        success=true;
                    }
                }
                e.setDropCompleted(success);
                e.consume();

                new Thread(()->notifyObserver(obs->obs.sendStudentToIsland(pawnColorChosen,Integer.parseInt(node.getId())))).start();
            });
        }
    }

    private void makeDraggableCloud(Node node){
        node.setCursor(Cursor.HAND);

        node.setOnMouseClicked(e->{
            int idCloudClicked;
            idNodeStart=e.getPickResult().getIntersectedNode().getId();
            if(idNodeStart==null){
                idCloudClicked = -1;
            }
            else{
                idCloudClicked = Integer.parseInt(idNodeStart);
            }
            int finalIdCloudClicked = idCloudClicked;
            new Thread(()->notifyObserver(obs->obs.sendCloudTile(finalIdCloudClicked))).start();
        });
    }

    private void makeDraggableIslandForCharacter(Node node){
        node.setCursor(Cursor.HAND);

        node.setOnMouseClicked(e->{
            int idIslandClicked;
            idNodeStart=e.getPickResult().getIntersectedNode().getId();
            if(idNodeStart==null){
                idIslandClicked = -1;
            }
            else{
                idIslandClicked = Integer.parseInt(idNodeStart);
            }
            int finalIdCloudClicked = idIslandClicked;
            new Thread(()->notifyObserver(obs->obs.sendChosenIsland(finalIdCloudClicked))).start();
        });
    }

}