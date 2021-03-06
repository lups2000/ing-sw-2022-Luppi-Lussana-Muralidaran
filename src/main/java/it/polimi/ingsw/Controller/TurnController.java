package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.*;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;
import it.polimi.ingsw.Utils.StoreGame;
import it.polimi.ingsw.View.Gui.Controllers.GuiMainController;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.*;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.ServerSide.Generic;
import it.polimi.ingsw.network.server.Server;


import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/** This class represents the Turn manager
 */
public class TurnController implements Serializable {

    @Serial
    private static final long serialVersionUID=-5987205913389392005L;
    private Game model;
    private final MainController mainController;
    private Player firstPlayerToPlayAssistant;
    private Player currentPlayerToPlayAssistant;
    private TurnPhase turnPhase;
    private transient Map<String, VirtualView> virtualViewMap;
    private AssistantCard currentAssistantCard;
    private CharacterCard currentCharacterCard;
    private int currentIdCharCard;
    private CloudTile currentCloudTile;
    private String currentMessageMoveStud;
    private PawnColor currentStudent;
    private int currentIslandIndex;
    private int currentStepsMotherNature;
    private boolean currentStop  = false;
    private boolean winner=false;
    private boolean hasAnswered=false;

    /**
     * Constructor
     * @param model it is the Model according to the MVC pattern
     */
    public TurnController(Game model,Map<String, VirtualView> virtualViewMap,MainController mainController){
        this.model=model;
        this.turnPhase = TurnPhase.PLANNING1; //motivo: vedi nota bene in roundManager
        this.firstPlayerToPlayAssistant=model.getFirstPlayer(); //initially he is the first one who joins the game
        this.virtualViewMap=virtualViewMap;
        this.mainController=mainController;
    }

    /**
     * sets the view of the map
     *
     * @param virtualViewMap becomes the current map view
     */
    public void setVirtualViewMap(Map<String, VirtualView> virtualViewMap) {this.virtualViewMap = virtualViewMap;}

    /**
     * sets the game model
     *
     * @param model becomes the model of the game
     */
    public void setModel(Game model) {this.model = model;}

    /**
     * manages all the messages from the main controller in the various situations
     *
     * @param message is analyzed in various cases on the type of message
     */
    public void messageFromMainController(Message message){

        switch (message.getMessageType()) {

            case REPLY_ASSISTANT_CARD -> {
                AssistantCardReply assistantCardReply = (AssistantCardReply) message;
                currentAssistantCard = assistantCardReply.getAssistantCard();
                hasAnswered = true;
            }

            case REPLY_CHARACTER_CARD -> {
                CharacterCardReply characterCardReply=(CharacterCardReply) message;
                currentIdCharCard = characterCardReply.getIdCharacterCard()-1;

                if(currentIdCharCard < 0){
                    currentCharacterCard=null;
                }
                else{
                    currentCharacterCard=model.getCharacterCards().get(currentIdCharCard);
                }
                hasAnswered=true;
            }

            case REPLY_CLOUD_TILE -> {
                CloudTileReply cloudTileReply=(CloudTileReply) message;
                currentCloudTile=model.getCloudTiles().get(cloudTileReply.getIdCloudTile());
                hasAnswered=true;
            }

            case GENERIC_MESSAGE -> {
                Generic generic=(Generic) message;
                currentMessageMoveStud=generic.getMessage();
                hasAnswered=true;
            }

            case REPLY_MOVE_STUD_DINING -> {
                StudentToDiningReply studentToDiningReply=(StudentToDiningReply) message;
                currentStudent=studentToDiningReply.getPawnColor();
                hasAnswered=true;
            }

            case REPLY_MOVE_STUD_ISLAND -> {
                StudentToIslandReply studentToIslandReply=(StudentToIslandReply) message;
                currentStudent=studentToIslandReply.getPawnColor();
                currentIslandIndex= studentToIslandReply.getIslandIndex();
                hasAnswered=true;
            }

            case REPLY_MOVE_MOTHER_NATURE -> {
                MotherNatureMoveReply motherNatureMoveReply=(MotherNatureMoveReply) message;
                currentStepsMotherNature=motherNatureMoveReply.getSteps();
                hasAnswered=true;
            }

            case REPLY_ISLAND -> {
                IslandReply islandReply = (IslandReply) message;
                currentIslandIndex = islandReply.getIndexIsland();
                hasAnswered=true;
            }

            case REPLY_COLOR -> {
                ColorReply colorReply = (ColorReply) message;
                currentStudent = colorReply.getChosenColor();
                hasAnswered=true;
            }

            case REPLY_STUDENT_OR_STOP -> {
                StudentOrStopReply studentOrStopReply = (StudentOrStopReply) message;
                if(studentOrStopReply.isStop()){
                    currentStop = true;
                }
                else{
                    currentStudent = studentOrStopReply.getPawnColor();
                }
                hasAnswered = true;
            }

        }
    }

    /**
     * This method calls planningPhase1(),planningPhase2(),choosePlayerToPlayAction(),action1(),action2(),action3() respectively!
     * After each player's turn we check if there is a winner;otherwise we call another time the method roundManager
     */
    public void roundManager(){

        while(turnPhase != TurnPhase.END){


            //nota bene: al primo giro le clouds sono gia piene!Quindi inizializzo turnPhase a PLANNING1
            planningPhase1();

            //notifyPlayers("The cloud tiles have been filled!"); //meglio cosi piuttosto che notificare la modifica nel model
            ReducedGame reducedGame = new ReducedGame(model);
            for(VirtualView virtualView: virtualViewMap.values()){ //show to the players the current situation
                virtualView.showGameBoard(reducedGame);
            }

            planningPhase2();
            reducedGame.setCloudTiles(model.getCloudTiles());
            reducedGame.setCharacterCards(model.getCharacterCards());
            reducedGame.setCurrentHand(model.getCurrentHand());
            reducedGame.setIslands(model.getIslands());
            reducedGame.setPlayers(model.getPlayers());
            reducedGame.setExpertVariant(model.getExpertsVariant());
            reducedGame.setNoEntryTilesCounter(model.getNoEntryTilesCounter());

            for(VirtualView virtualView: virtualViewMap.values()){ //show to the players the current situation
                virtualView.showGameBoard(reducedGame);
            }

            if(checkWinner()){ //if there is a player that has finished his assistantCards
                winner=true;
                turnPhase=TurnPhase.END;
                break; //exit from the while
            }
            //I put the players.size() because if a player is disconnected the round continues
            for(int i=0;i<model.getPlayers().size();i++){
                Player currentActionPlayer=choosePlayerToPlayAction();
                actionPhase1(currentActionPlayer);
                actionPhase2(currentActionPlayer);
                if(checkWinner()){
                    winner=true;
                    break;
                }
                else{
                    actionPhase3(currentActionPlayer);
                }
            }
            if(winner){
                turnPhase=TurnPhase.END;
            }
            else{
                turnPhase = TurnPhase.START;
                //qua salvo su disco la situazione del match
                StoreGame storeGame=new StoreGame(mainController);
                storeGame.saveMatch(mainController);
            }
        }
        this.endGame();
    }

    /**
     * This method represents the first part of the planning phase, where the cloud tiles are filled
     */
    private void planningPhase1(){
        if(turnPhase == TurnPhase.START){
            for(CloudTile cloudTile : model.getCloudTiles()){
                try {
                    model.fillCloudTile(cloudTile);
                } catch (TooManyPawnsPresent e) {
                    e.printStackTrace();
                }
            }
            turnPhase = TurnPhase.PLANNING1;
        }
    }

    /**
     * This method represents the second part of the planning phase, where each player plays an Assistant Card
     */
    private void planningPhase2(){

        boolean assistantOk=false;

        if(turnPhase == TurnPhase.PLANNING1){

            model.getCurrentHand().clear();

            for(int i= firstPlayerToPlayAssistant.getId();i<model.getPlayers().size();i++){
                if(model.getPlayers().get(i).getStatus()==PlayerStatus.WAITING){

                    currentPlayerToPlayAssistant=model.getPlayers().get(i);
                    //notify to other players that it's the turn of the current player
                    this.notifyOtherPlayers("\nNow it's the Turn of "+currentPlayerToPlayAssistant.getNickname()+" who plays the Assistant Card! Please Wait...",currentPlayerToPlayAssistant);

                    VirtualView virtualViewCurrentPlayer=virtualViewMap.get(currentPlayerToPlayAssistant.getNickname()); //getting the virtual view of the current player
                    virtualViewCurrentPlayer.showGenericMessage("\nHey "+ currentPlayerToPlayAssistant.getNickname() +", now it's your turn!");
                    //ask to the current player which Assistant Card he wants to move

                    while(!assistantOk){

                        //if the assistant cards of the current player are finished, call model.checkWinner()
                        if(currentPlayerToPlayAssistant.getDeckAssistantCard().getCards().size()==0){
                            model.checkWinner();
                            assistantOk=true;
                            i=1000;
                        }
                        //otherwise
                        else{
                            virtualViewCurrentPlayer.askAssistantCard(currentPlayerToPlayAssistant.getDeckAssistantCard().getCards()); //this must be contained in a loop

                            waitAnswer(); //wait for the answer of the current Player

                            if(checkAssistantCard(currentAssistantCard)){ //assistantCard ok
                                //virtualViewCurrentPlayer.showGenericMessage("AssistantCard played: < Value: "+currentAssistantCard.getValue()+", MaxStepsMotherNature: "+currentAssistantCard.getMaxStepsMotherNature()+" > ");
                                notifyOtherPlayers(currentPlayerToPlayAssistant.getNickname()+" has played the following AssistantCard: <  Value: "+currentAssistantCard.getValue()+", MaxStepsMotherNature: "+currentAssistantCard.getMaxStepsMotherNature()+" > ",currentPlayerToPlayAssistant);

                                currentPlayerToPlayAssistant.pickAssistantCard(currentAssistantCard);
                                currentPlayerToPlayAssistant.setStatus(PlayerStatus.PLAYING_ASSISTANT);
                                model.getCurrentHand().put(currentPlayerToPlayAssistant, currentPlayerToPlayAssistant.getCurrentAssistant());
                                assistantOk=true;
                            }
                            else{
                                //if the assistantCard is invalid
                                virtualViewCurrentPlayer.showGenericMessage("Assistant Card invalid!Please Retry...");
                            }
                        }
                    }
                    assistantOk=false;

                    turnPhase = TurnPhase.PLANNING2;
                }
                else{
                    break; //exit from the loop
                }
                if(i>=model.getPlayers().size()-1 && i!=1000){
                    i=-1;
                }
            }
            //each player has played his assistant card,now everybody waits for his turn in the action phase
            for(Player player : model.getCurrentHand().keySet()){
                if(player.getStatus()!=PlayerStatus.WINNER){
                    player.setStatus(PlayerStatus.WAITING_ACTION);
                }
            }
        }
    }

    /**
     * to wait the player's answer before going on with the game
     */
    private void waitAnswer() {
        while (!hasAnswered) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        hasAnswered = false;
    }


    /**
     * This method check if the assistant card played by the current player is
     * valid or not. If an Assistant card has already been played by a player, the next players
     * cannot play it in the current turn. They could play the same card only if they have only this card
     *
     * @param assistantCard that has been just played by the player
     * @return true/false
     */
    private boolean checkAssistantCard(AssistantCard assistantCard){
        Player firstPlayerEverToPlayAssistant=model.getPlayers().get(0);
        //if the player '0' has more Assistant Cards than the number of players of the Game - 1,the Assistant card must be different by the other ones in the current hand
        if(firstPlayerEverToPlayAssistant.getDeckAssistantCard().getCards().size() > model.getPlayers().size()-1){
            if (model.getCurrentHand().size() != 0) {
                for (AssistantCard card : model.getCurrentHand().values()) {
                    if (card.getValue() == assistantCard.getValue() && card.getMaxStepsMotherNature() == assistantCard.getMaxStepsMotherNature()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if the player's coins number is enough to play a character card chosen by him
     *
     * @param characterCard is the character card the player wants to play
     * @param player who wants to play the character card
     * @return
     */
    private boolean checkCharacterCard(CharacterCard characterCard,Player player){
        return player.getSchoolBoard().getNumCoins() >= characterCard.getCost();
    }

    /**
     * This method chooses who is the player who plays the current action phase
     * @return the player
     */
    private Player choosePlayerToPlayAction(){
        //now we must choose who plays the action phase
        Player first = null;
        int lower = 11;
        for(Map.Entry<Player,AssistantCard> entry : model.getCurrentHand().entrySet()){
            if(entry.getValue().getValue() < lower){
                lower = entry.getValue().getValue();
                first = entry.getKey();
            }
        }
        if(model.getCurrentHand().size() == this.model.getPlayers().size()){
            this.firstPlayerToPlayAssistant = first;
        }
        model.getCurrentHand().remove(first);
        first.setStatus(PlayerStatus.PLAYING_ACTION);

        return first;
    }


    /**
     * this method is used to activate the effect of the chosen character card
     *
     * @param currentView the virtual view of the player who just played the character card
     */
    private void playCharacterCard(VirtualView currentView,Player player) throws NoPawnPresentException, TooManyPawnsPresent {
        switch (currentCharacterCard.getType()) {

            case TWO_ADDITIONAL_POINTS -> { //OK
                TwoAdditionalPoints twoAdditionalPoints=(TwoAdditionalPoints) currentCharacterCard;
                player.getSchoolBoard().decreaseNumCoins(twoAdditionalPoints.getCost());
                twoAdditionalPoints.effect();
            }

            case MOVE_MORE_MOTHER_NATURE -> { //OK
                MoveMoreMotherNature moveMoreMotherNature= (MoveMoreMotherNature) currentCharacterCard;
                player.getSchoolBoard().decreaseNumCoins(moveMoreMotherNature.getCost());
                moveMoreMotherNature.effect();
            }

            case CONTROL_ON_PROFESSOR -> { //OK
                ControlOnProfessor controlOnProfessor=(ControlOnProfessor) currentCharacterCard;
                player.getSchoolBoard().decreaseNumCoins(controlOnProfessor.getCost());
                controlOnProfessor.effect();
            }

            case NO_COUNT_TOWER -> { //OK
                NoCountTower noCountTower =(NoCountTower) currentCharacterCard;
                player.getSchoolBoard().decreaseNumCoins(noCountTower.getCost());
                noCountTower.effect();
            }

            //the user chooses the island
            case CHOOSE_ISLAND -> { //OK
                ReducedGame reducedGame = new ReducedGame(model);
                ChooseIsland chooseIsland = (ChooseIsland) currentCharacterCard;
                currentView.showGameBoard(reducedGame);
                currentView.showGenericMessage("You have chosen to compute the influence on an Island...choose it!");
                currentView.askIsland(model.getIslands());

                waitAnswer();

                Island chosenIsland = model.getIslands().get(currentIslandIndex);
                player.getSchoolBoard().decreaseNumCoins(chooseIsland.getCost());
                try {
                    chooseIsland.effect(chosenIsland);
                } catch (NoTowersException | TooManyTowersException e) {
                    e.printStackTrace();
                }
                notifyOtherPlayers(player.getNickname() + " chose to compute the influence on the island with index " + currentIslandIndex,player);

                for(VirtualView virtualView : virtualViewMap.values()){
                    virtualView.showIslands(model.getIslands());
                }
            }

            //the user chooses an island
            case PUT_NO_ENTRY_TILES -> { //OK
                ReducedGame reducedGame = new ReducedGame(model);
                PutNoEntryTiles putNoEntryTiles = (PutNoEntryTiles) currentCharacterCard;
                currentView.showGameBoard(reducedGame);
                currentView.showGenericMessage("You have chosen to put a NoEntryTile on an Island...choose it!");
                currentView.askIsland(model.getIslands());
                waitAnswer();

                Island chosenIsland = model.getIslands().get(currentIslandIndex);
                player.getSchoolBoard().decreaseNumCoins(putNoEntryTiles.getCost());
                try {
                    putNoEntryTiles.effect(chosenIsland);
                } catch (NoNoEntryTilesException e) {
                    e.printStackTrace();
                }
                notifyOtherPlayers(player.getNickname() + " chose to put a no entry tile on the island with index " + currentIslandIndex,player);

                for(VirtualView virtualView : virtualViewMap.values()){
                    virtualView.showIslands(model.getIslands());
                }
            }

            //the user chooses a color
            case COLOR_TO_STUDENT_BAG -> { //OK
                ReducedGame reducedGame = new ReducedGame(model);
                ColorToStudentBag colorToStudentBag = (ColorToStudentBag) currentCharacterCard;
                currentView.showGameBoard(reducedGame);
                Map<PawnColor,Integer> availableStudents = new HashMap<>();
                availableStudents.put(PawnColor.RED,1);
                availableStudents.put(PawnColor.BLUE,1);
                availableStudents.put(PawnColor.YELLOW,1);
                availableStudents.put(PawnColor.PINK,1);
                availableStudents.put(PawnColor.GREEN,1);

                currentView.askColor(availableStudents);
                waitAnswer();

                player.getSchoolBoard().decreaseNumCoins(colorToStudentBag.getCost());
                notifyOtherPlayers(player.getNickname() + " chose the color " + currentStudent,player);

                colorToStudentBag.effect(currentStudent);
                model.allocateProfessors();
            }

            //the user chooses a color
            case COLOR_NO_INFLUENCE -> { //OK
                ReducedGame reducedGame = new ReducedGame(model);
                ColorNoInfluence colorNoInfluence = (ColorNoInfluence) currentCharacterCard;
                currentView.showGameBoard(reducedGame);
                Map<PawnColor,Integer> availableStudents = new HashMap<>();
                availableStudents.put(PawnColor.RED,1);
                availableStudents.put(PawnColor.BLUE,1);
                availableStudents.put(PawnColor.YELLOW,1);
                availableStudents.put(PawnColor.PINK,1);
                availableStudents.put(PawnColor.GREEN,1);
                currentView.askColor(availableStudents);

                waitAnswer();

                player.getSchoolBoard().decreaseNumCoins(colorNoInfluence.getCost());

                notifyOtherPlayers(player.getNickname() + " chose the color " + currentStudent+" as color with no influence",player);

                colorNoInfluence.effect(currentStudent);

            }

            //the user chooses a color
            case STUDENT_TO_DINING -> { //OK
                ReducedGame reducedGame = new ReducedGame(model);
                StudentToDining studentToDining = (StudentToDining) currentCharacterCard;
                currentView.showGameBoard(reducedGame);

                currentView.askColor(studentToDining.getStudents());
                waitAnswer();

                player.getSchoolBoard().decreaseNumCoins(studentToDining.getCost());
                notifyOtherPlayers(player.getNickname() + " pick the " + currentStudent +" student from the CharacterCard",player);

                studentToDining.effect(currentStudent);
                model.allocateProfessors();
            }

            //the user chooses an island and a color
            case ONE_STUDENT_TO_ISLAND -> { //OK
                OneStudentToIsland oneStudentToIsland = (OneStudentToIsland) currentCharacterCard;
                //currentView.showStudents(oneStudentToIsland.getStudents());
                currentView.showSchoolBoardPlayers(model.getPlayers());
                currentView.askColor(oneStudentToIsland.getStudents());
                waitAnswer();

                currentView.askIsland(model.getIslands());
                waitAnswer();

                player.getSchoolBoard().decreaseNumCoins(oneStudentToIsland.getCost());
                Island island = model.getIslands().get(currentIslandIndex);
                oneStudentToIsland.effect(island,currentStudent);

                notifyOtherPlayers("\n<  "+player.getNickname() + " put a " + currentStudent + " student on the island with index " + currentIslandIndex+"  >\n",player);

                for(VirtualView virtualView : virtualViewMap.values()){
                    virtualView.showIslands(model.getIslands());
                }
            }

            case SWITCH_STUDENTS -> { //OK
                SwitchStudents switchStudents = (SwitchStudents) currentCharacterCard;
                Map<PawnColor,Integer> availableWaiting = player.getSchoolBoard().getStudentsWaiting();
                Map<PawnColor,Integer> availableCard = switchStudents.getStudents();
                Map<PawnColor,Integer> fromEntrance = new HashMap<>();
                fromEntrance.put(PawnColor.RED,0);
                fromEntrance.put(PawnColor.BLUE,0);
                fromEntrance.put(PawnColor.YELLOW,0);
                fromEntrance.put(PawnColor.PINK,0);
                fromEntrance.put(PawnColor.GREEN,0);
                Map<PawnColor,Integer> fromCharacterCard = new HashMap<>();
                fromCharacterCard.put(PawnColor.RED,0);
                fromCharacterCard.put(PawnColor.BLUE,0);
                fromCharacterCard.put(PawnColor.YELLOW,0);
                fromCharacterCard.put(PawnColor.PINK,0);
                fromCharacterCard.put(PawnColor.GREEN,0);
                int i;
                currentView.showSchoolBoardPlayers(model.getPlayers());


                for(i=0;i<3;i++){
                    int temp=i+1;
                    currentView.showGenericMessage("Select UP TO 3 students to pick from your entrance: "+temp+"/3 ");
                    currentView.askStudOrStop(availableWaiting);
                    waitAnswer();

                    if(currentStop){
                        break;
                    }
                    else{
                        availableWaiting.put(currentStudent,availableWaiting.get(currentStudent)-1);
                        player.getSchoolBoard().setNumStudentsWaiting(player.getSchoolBoard().getNumStudentsWaiting()-1);
                        fromEntrance.put(currentStudent,fromEntrance.get(currentStudent)+1);
                    }
                }

                currentStop = false;


                for(int j=0;j<i;j++){
                    int temp=i;
                    int tmp=j+1;
                    currentView.showGenericMessage("\nSelect " + temp + " students to pick from this character card: "+tmp+"/"+temp);
                    currentView.askColor(availableCard);
                    waitAnswer();
                    availableCard.put(currentStudent,availableCard.get(currentStudent)-1);
                    fromCharacterCard.put(currentStudent,fromCharacterCard.get(currentStudent)+1);
                }

                player.getSchoolBoard().decreaseNumCoins(switchStudents.getCost());
                switchStudents.effect(fromCharacterCard,fromEntrance);

                model.allocateProfessors();

            }

            case SWITCH_DINING_WAITING -> { //OK
                boolean flagNoStudDining=true;
                SwitchDiningWaiting switchDiningWaiting = (SwitchDiningWaiting) currentCharacterCard;
                int i;
                currentView.showSchoolBoardPlayers(model.getPlayers());

                Map<PawnColor,Integer> availableWaiting = player.getSchoolBoard().getStudentsWaiting();
                Map<PawnColor,Integer> availableDining = player.getSchoolBoard().getStudentsDining();

                Map<PawnColor,Integer> exWaiting = new HashMap<>();
                exWaiting.put(PawnColor.RED,0);
                exWaiting.put(PawnColor.BLUE,0);
                exWaiting.put(PawnColor.YELLOW,0);
                exWaiting.put(PawnColor.PINK,0);
                exWaiting.put(PawnColor.GREEN,0);
                Map<PawnColor,Integer> exDining = new HashMap<>();
                exDining.put(PawnColor.RED,0);
                exDining.put(PawnColor.BLUE,0);
                exDining.put(PawnColor.YELLOW,0);
                exDining.put(PawnColor.PINK,0);
                exDining.put(PawnColor.GREEN,0);

                for(i=0;i<2;i++){
                    int temp=i+1;
                    currentView.showGenericMessage("Select UP TO 2 students to pick from your entrance and place them on your dining room: "+temp+"/2");
                    currentView.showGenericMessage("\nENTRANCE -> DINING ROOM:");
                    currentView.askStudOrStop(availableWaiting);
                    waitAnswer();
                    if(currentStop){
                        break;
                    }
                    else{
                        availableWaiting.put(currentStudent,availableWaiting.get(currentStudent)-1);
                        player.getSchoolBoard().setNumStudentsWaiting(player.getSchoolBoard().getNumStudentsWaiting()-1);
                        exWaiting.put(currentStudent,exWaiting.get(currentStudent)+1);
                    }
                }

                currentStop = false;

                for(PawnColor pawnColor : availableDining.keySet()){
                    if(availableDining.get(pawnColor)>0){
                        flagNoStudDining=false;
                        break;
                    }
                }
                if(flagNoStudDining){
                    currentView.showGenericMessage("You cannot play this card because you do not have enough students in your dining");
                }
                else{

                    for(int j=0;j<i;j++){
                        int temp=i;
                        int tmp=j+1;
                        currentView.showGenericMessage("\nSelect " + temp + " students to pick from your dining room and place them on your entrance: "+tmp+"/"+temp);
                        currentView.showGenericMessage("\nDINING ROOM -> ENTRANCE");

                        currentView.askColor(availableDining);
                        waitAnswer();

                        availableDining.put(currentStudent,availableDining.get(currentStudent)-1);
                        exDining.put(currentStudent,exDining.get(currentStudent)+1);
                    }
                }
                player.getSchoolBoard().decreaseNumCoins(switchDiningWaiting.getCost());
                switchDiningWaiting.effect(exWaiting,exDining);
                model.allocateProfessors();
            }
        }
    }

    /**
     * This is the method which represents the first step of the action phase where the player must move X students
     *
     * @param player current player chosen by the method ChoosePlayerToAction()
     */
    private void actionPhase1(Player player){
        boolean characterCardOk=false;

        if(turnPhase == TurnPhase.PLANNING2 && player.getStatus()==PlayerStatus.PLAYING_ACTION){

            VirtualView virtualViewCurrentPlayer=virtualViewMap.get(player.getNickname());
            virtualViewCurrentPlayer.showGenericMessage("\nHey "+ player.getNickname() +", now it's your turn!");
            notifyOtherPlayers("\n"+player.getNickname()+" is playing the action phase!Please wait...", player);

            if(model.getExpertsVariant()){ //if the expert Mode has been chosen
                List<CharacterCard> characterCardsGame=model.getCharacterCards();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while(!characterCardOk){

                    virtualViewCurrentPlayer.askPlayCharacterCard(characterCardsGame);

                    waitAnswer(); //wait for the answer of the current Player

                    if(currentCharacterCard==null){
                        virtualViewCurrentPlayer.showGenericMessage("No characterCard played!");
                        characterCardOk=true;
                    }
                    else if(checkCharacterCard(currentCharacterCard,player)){
                        virtualViewCurrentPlayer.showGenericMessage("CharacterCard played: "+currentCharacterCard);
                        notifyOtherPlayers(player.getNickname()+" has played the following CharacterCard: "+currentCharacterCard,player);
                        try {
                            playCharacterCard(virtualViewCurrentPlayer,player);
                        } catch (NoPawnPresentException | TooManyPawnsPresent e) {
                            e.printStackTrace();
                        }
                        characterCardOk=true;
                    }
                    else{
                        virtualViewCurrentPlayer.showGenericMessage("Invalid characterCard!You do not have enough coins to activate it...");
                    }
                }
            }

            ReducedGame reducedGame = new ReducedGame(model);
            for(VirtualView virtualView : virtualViewMap.values()){
                virtualView.updateFX(reducedGame);
            }

            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i=0;i<model.getMaxNumPlayers()+1;i++){ // the player must move numPlayers+1 students
                int numStud=model.getMaxNumPlayers()+1-i;
                int numTot=model.getMaxNumPlayers()+1;
                virtualViewCurrentPlayer.showGenericMessage("\nIt's time to move your students: "+numStud+" / "+numTot+" available");

                virtualViewCurrentPlayer.askMoveStud();
                waitAnswer();

                if(currentMessageMoveStud.equalsIgnoreCase("s")){
                    //we ask the player which PawnColor he wants to move
                    virtualViewCurrentPlayer.askMoveStudToDining(player);

                    waitAnswer();

                    try {
                        player.getSchoolBoard().moveStudToDining(currentStudent);
                        model.allocateProfessors();
                    } catch (NoPawnPresentException | TooManyPawnsPresent e) {
                        e.printStackTrace();
                    }
                }
                else if(currentMessageMoveStud.equalsIgnoreCase("i")){
                    //we ask the player where he wants to move the student
                    virtualViewCurrentPlayer.askMoveStudToIsland(player,model.getIslands());

                    waitAnswer();

                    try {
                        player.getSchoolBoard().moveStudToIsland(currentStudent,model.getIslands().get(currentIslandIndex));
                    } catch (NoPawnPresentException e) {
                        e.printStackTrace();
                    }
                    notifyOtherPlayers("\n<  "+player.getNickname() + " put a " + currentStudent+ " student on the island with index " + currentIslandIndex+"  >\n",player);
                    ReducedGame reducedGame1 = new ReducedGame(model);
                    for(VirtualView virtualView : virtualViewMap.values()){
                        //virtualView.showIslands(model.getIslands());
                        virtualView.showGameBoard(reducedGame);
                    }
                }
                else{ //non ci vado mai teoricamente
                    i--; //cosi da rifare ancora la mossa
                }
            }
            //disactivate the effect ControlOnProfessor
            for (Player player1 : model.getPlayers()){
                if (player1.getControlOnProfessor()){
                    player1.setControlOnProfessor(false);
                }
            }
            turnPhase = TurnPhase.ACTION1;
        }
    }

    /**
     * This is the method which represents the second step of the action phase where the player must move mother nature to an Island
     * @param player current player chosen by the method ChoosePlayerToAction()
     */
    private void actionPhase2(Player player){
        if(turnPhase == TurnPhase.ACTION1 && player.getStatus()==PlayerStatus.PLAYING_ACTION){
            //we ask the player on which island he wants to move mother nature
            VirtualView virtualViewPlayer=virtualViewMap.get(player.getNickname());
            virtualViewPlayer.showGenericMessage("\nIt's time to move Mother Nature!");

            virtualViewPlayer.askMoveMotherNature(model.getIslands(),player.getCurrentAssistant().getMaxStepsMotherNature());
            waitAnswer();

            try {
                int newIndex=(model.getMotherNature()+currentStepsMotherNature)%Island.getNumIslands();
                model.moveMotherNature(model.getIslands().get(newIndex));
            } catch (TooManyTowersException | NoTowersException e) {
                e.printStackTrace();
            }
            turnPhase = TurnPhase.ACTION2;
        }
    }

    /**
     * This is the method which represents the third step of the action phase where the player must pick a CloudTile
     * @param player current player chosen by the method ChoosePlayerToAction()
     */
    private void actionPhase3(Player player){
        if(turnPhase == TurnPhase.ACTION2 && player.getStatus()==PlayerStatus.PLAYING_ACTION){
            //we ask the player which CloudTile he wants to pick
            VirtualView virtualViewPlayer=virtualViewMap.get(player.getNickname());
            virtualViewPlayer.showGenericMessage("\nIt's time to pick a cloudTile!");
            virtualViewPlayer.askChooseCloudTile(model.getCloudTiles());

            waitAnswer();

            try {
                player.pickCloudTile(currentCloudTile);
            } catch (TooManyPawnsPresent e) {
                e.printStackTrace();
            }
            ReducedGame reducedGame = new ReducedGame(model);
            for(VirtualView virtualView : virtualViewMap.values()){
                virtualView.updateFX(reducedGame);
            }
            player.setStatus(PlayerStatus.WAITING);
            turnPhase = TurnPhase.PLANNING2;//in order to come back to the next player's action
        }
    }

    /**
     * Checks if there is a winner at the end of the action phase 2
     *
     * @return true if there is a winner, false if not
     */
    private boolean checkWinner(){
        for(Player player : model.getPlayers()){
            if(player.getStatus()==PlayerStatus.WINNER){
                return true;
            }
        }
        return false;
    }

    /**
     * Shows to the players the result of the match
     */
    private void endGame(){
        Player winner=null;
        for(Player player : model.getPlayers()){
            if(player.getStatus()==PlayerStatus.WINNER){
                winner=player;
                virtualViewMap.get(player.getNickname()).showWinMessage(player);
                break;
            }
        }
        for(Player player : model.getPlayers()){
            if(player.getId()!= winner.getId()){
                virtualViewMap.get(player.getNickname()).showLoseMessage(winner);
            }
        }
        model.changeStatus(GameState.ENDED);
        mainController.endedGame();
        Server.LOGGER.info("Game Ended!Server ready for a new match...");
    }

    /**
     * Method to send a message to all the players except the current one
     *
     * @param message to send
     * @param currentPlayer who is playing
     */
    public void notifyOtherPlayers(String message,Player currentPlayer){
        for(String nickname : virtualViewMap.keySet()){
            if(!nickname.equals(currentPlayer.getNickname())){
                virtualViewMap.get(nickname).showGenericMessage(message);
            }
        }
    }

}