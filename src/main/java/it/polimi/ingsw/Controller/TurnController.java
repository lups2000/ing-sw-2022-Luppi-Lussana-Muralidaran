package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.ChooseIsland;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.*;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.ServerSide.Generic;
import it.polimi.ingsw.network.server.Server;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** This class represents the Turn manager
 * @author Matteo Luppi
 */
public class TurnController implements Serializable {

    @Serial
    private static final long serialVersionUID=-5987205913389392005L;
    private Game model;
    private Player firstPlayerToPlayAssistant;
    private TurnPhase turnPhase;
    private transient Map<String, VirtualView> virtualViewMap;
    private AssistantCard currentAssistantCard;
    private CharacterCard currentCharacterCard;
    private int currentIdCharCard;
    private CloudTile currentCloudTile;
    private String currentMessageMoveStud;
    private PawnColor currentStudent;
    private int currentIslandIndex;
    private boolean winner=false;
    private boolean hasAnswered=false;

    /**
     * Constructor
     * @param model it is the Model according to the MVC pattern
     */
    public TurnController(Game model,Map<String, VirtualView> virtualViewMap){
        this.model=model;
        this.turnPhase = TurnPhase.PLANNING1; //motivo: vedi nota bene in roundManager
        this.firstPlayerToPlayAssistant=model.getFirstPlayer(); //initially he is the first one who joins the game
        this.virtualViewMap=virtualViewMap;
    }

    public Player getFirstPlayerToPlayAssistant() {
        return firstPlayerToPlayAssistant;
    }

    //da vedere questa cosa
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
                currentCloudTile=model.getCloudTiles().get(cloudTileReply.getIdCloudTile()-1);
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

        }

    }


    /**
     * This method calls planningPhase1(),planningPhase2(),choosePlayerToPlayAction(),action1(),action2(),action3() respectively!
     * After each player's turn we check if there is a winner;otherwise we call another time the method roundManager
     */
    public void roundManager(){

        /*
        while(turnPhase != TurnPhase.END){

            //nota bene: al primo giro le clouds sono gia piene!Quindi inizializzo turnPhase a PLANNING1
            planningPhase1();

            notifyPlayers("The cloud tiles have been filled!");
            for(VirtualView virtualView: virtualViewMap.values()){ //show to the players the current situation
                virtualView.showGameBoard(model.getIslands(),model.getPlayers());
            }

            planningPhase2();

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
            }
        }*/
        planningPhase1();
        notifyPlayers("The cloud tiles have been filled!");
        for(VirtualView virtualView: virtualViewMap.values()){
            virtualView.showGameBoard(model.getIslands(),model.getCloudTiles(),model.getPlayers());
        }
        planningPhase2();
        Player currentActionPlayer=choosePlayerToPlayAction(); //funziona,sembra tutto ok
        actionPhase1(currentActionPlayer);


    }

    /**
     * This method represents the first part of the planning phase, where the cloud tiles are filled
     */
    private void planningPhase1(){ //attenzione perche ora in game riempiamo le cloud al primo giro
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
    private synchronized void planningPhase2(){
        //è giusto che sia sync??
        boolean assistantOk=false;

        if(turnPhase == TurnPhase.PLANNING1){

            model.getCurrentHand().clear();

            for(int i= firstPlayerToPlayAssistant.getId();i<model.getPlayers().size();i++){
                if(model.getPlayers().get(i).getStatus()==PlayerStatus.WAITING){

                    //List<AssistantCard> assistantCardsAvailable=new ArrayList<>(model.getPlayers().get(i).getDeckAssistantCard().getCards());
                    Player currentPlayer=model.getPlayers().get(i);
                    //notify to other players that it's the turn of the current player
                    this.notifyOtherPlayers("Now it's the Turn of "+currentPlayer.getNickname()+" who plays the Assistant Card! Please Wait...",currentPlayer);

                    VirtualView virtualViewCurrentPlayer=virtualViewMap.get(currentPlayer.getNickname()); //getting the virtual view of the current player
                    virtualViewCurrentPlayer.showGenericMessage("Hey "+ currentPlayer.getNickname() +", now it's your turn!");
                    //ask to the current player which Assistant Card he wants to move

                    while(!assistantOk){

                        virtualViewCurrentPlayer.askAssistantCard(currentPlayer.getDeckAssistantCard().getCards()); //this must be contained in a loop
                        //metodo waitAnswer senza wait e notify (?)
                        waitAnswer(); //wait for the answer of the current Player

                        if(checkAssistantCard(currentAssistantCard)){ //assistantCard ok
                            virtualViewCurrentPlayer.showGenericMessage(Colors.RESET+"AssistantCard played:  Value: "+currentAssistantCard.getValue()+", MaxStepsMotherNature: "+currentAssistantCard.getMaxStepsMotherNature());
                            notifyOtherPlayers(currentPlayer.getNickname()+" has played the following AssistantCard:  Value: "+currentAssistantCard.getValue()+", MaxStepsMotherNature: "+currentAssistantCard.getMaxStepsMotherNature(),currentPlayer);

                            currentPlayer.pickAssistantCard(currentAssistantCard);
                            currentPlayer.setStatus(PlayerStatus.PLAYING_ASSISTANT);
                            model.getCurrentHand().put(currentPlayer, currentPlayer.getCurrentAssistant());
                            assistantOk=true;
                        }
                        else{
                            //if the assistantCard is invalid
                            virtualViewCurrentPlayer.showGenericMessage("Assistant Card invalid!Please Retry...");
                        }
                    }
                    assistantOk=false;

                    //if the assistant cards of the current player are finished I could call model.checkWinner() (the one written by Paolo)
                    //TODO

                    turnPhase = TurnPhase.PLANNING2;
                }
                else{
                    break; //exit from the loop
                }
                if(i>=model.getPlayers().size()-1){
                    i=-1;
                }
            }
            //each player has played his assistant card,now everybody waits for his turn in the action phase
            for(Player player : model.getCurrentHand().keySet()){
                player.setStatus(PlayerStatus.WAITING_ACTION);
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
     */
    private void playCharacterCard() throws NoPawnPresentException, TooManyPawnsPresent {
        switch (currentCharacterCard.getType()) {
            //alcune posso raggrupparle qui
            //TODO
            case NO_COUNT_TOWER, CONTROL_ON_PROFESSOR, TWO_ADDITIONAL_POINTS, MOVE_MORE_MOTHER_NATURE -> model.getCharacterCards().get(currentIdCharCard).effect();

            case CHOOSE_ISLAND -> {
                //chiedo l'isola (dovrebbe già esserci)
                model.getCharacterCards().get(currentIdCharCard).effect();
                //mostro il risultato dell'influenza sull'isola (se non c'è già potrebbe tornare utile come metodo da invocare a fine fase 2)
            }
            case SWITCH_STUDENTS -> {
                //mostra i 6 studenti su questa carta e fai selezionare 3 da pickare e 3 da depositare
                model.getCharacterCards().get(currentIdCharCard).effect();
            }
            case STUDENT_TO_DINING -> {
                //mostrare i 4 studenti di questa carta e farne scegliere uno
                model.getCharacterCards().get(currentIdCharCard).effect();
            }
            case COLOR_TO_STUDENT_BAG, COLOR_NO_INFLUENCE -> {
                //chiedi un pawncolor al client
                model.getCharacterCards().get(currentIdCharCard).effect();
            }
            case PUT_NO_ENTRY_TILES -> {
                //chiedi un'isola su cui piazzare la tessera divieto
                model.getCharacterCards().get(currentIdCharCard).effect();
            }
            case ONE_STUDENT_TO_ISLAND -> {
                //mostra i 4 studenti presenti su questa carta, chiedi un'isola
                model.getCharacterCards().get(currentIdCharCard).effect();
            }
            case SWITCH_DINING_WAITING -> {
                //mostra la propria schoolboard e chiedi max due switch
                model.getCharacterCards().get(currentIdCharCard).effect();
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
            virtualViewCurrentPlayer.showGenericMessage("Hey "+ player.getNickname() +", now it's your turn!");
            notifyOtherPlayers(player.getNickname()+" is playing the action phase!Please wait...", player);

            if(model.getExpertsVariant()){ //if the expert Mode has been chosen
                List<CharacterCard> characterCardsGame=model.getCharacterCards();

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
                        characterCardOk=true;
                    }
                    else{
                        virtualViewCurrentPlayer.showGenericMessage("Invalid characterCard!You do not have enough coins to activate it");
                    }
                }

                if(currentCharacterCard != null) {
                    try {
                        playCharacterCard();
                    } catch (NoPawnPresentException | TooManyPawnsPresent e) {
                        e.printStackTrace();
                    }
                }
                characterCardOk=false;
            }

            for(int i=0;i<model.getMaxNumPlayers()+1;i++){ // the player must move numPlayers+1 students
                int numStud=model.getMaxNumPlayers()+1-i;
                int numTot=model.getMaxNumPlayers()+1;

                virtualViewCurrentPlayer.showGenericMessage("It's time to move your students: "+numStud+" / "+numTot+" available");
                virtualViewCurrentPlayer.askMoveStud();
                waitAnswer();

                if(currentMessageMoveStud.equalsIgnoreCase("s")){
                    //we ask the player which PawnColor he wants to move
                    virtualViewCurrentPlayer.askMoveStudToDining(player.getSchoolBoard().getStudentsWaiting());
                    waitAnswer();

                    try {
                        player.getSchoolBoard().moveStudToDining(currentStudent);
                    } catch (NoPawnPresentException | TooManyPawnsPresent e) {
                        e.printStackTrace();
                    }
                    try {
                        model.allocateProfessors(); //method we call each time there is a movement in the player's schoolboard
                    } catch (NoPawnPresentException | TooManyPawnsPresent e) {
                        e.printStackTrace();
                    }

                    virtualViewCurrentPlayer.showSchoolBoard(player.getSchoolBoard());

                }
                else if(currentMessageMoveStud.equalsIgnoreCase("i")){
                    //we ask the player where he wants to move the student
                    virtualViewCurrentPlayer.askMoveStudToIsland(player.getSchoolBoard().getStudentsWaiting(),model.getIslands());
                    waitAnswer();

                    try {
                        player.getSchoolBoard().moveStudToIsland(currentStudent,model.getIslands().get(currentIslandIndex));
                    } catch (NoPawnPresentException e) {
                        e.printStackTrace();
                    }
                    virtualViewCurrentPlayer.showGameBoard(model.getIslands(),model.getCloudTiles(),model.getPlayers());
                }
                else{ //non ci vado mai teoricamente
                    //messaggio di errore tramite la view
                    i--; //cosi da rifare ancora la mossa
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
            //we must control that the player can move mother nature there according to the assistant card played!!!
            //model.moveMotherNature(IslandChosen);
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
            virtualViewPlayer.askChooseCloudTile(model.getCloudTiles());

            waitAnswer();

            try {
                player.pickCloudTile(currentCloudTile);

            } catch (TooManyPawnsPresent e) {
                e.printStackTrace();
            }

            player.setStatus(PlayerStatus.WAITING);
            turnPhase = TurnPhase.PLANNING2;//in order to come back to the next player's action
        }
    }

    private boolean checkWinner(){
        //we check if there is a winner at the end of the action phase 2
        for(Player player : model.getPlayers()){
            if(player.getStatus()==PlayerStatus.WINNER){
                return true;
            }
        }
        return false;
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

    public void notifyPlayers(String message){
        for(String nickname : virtualViewMap.keySet()){
            virtualViewMap.get(nickname).showGenericMessage(message);
        }
    }
}
