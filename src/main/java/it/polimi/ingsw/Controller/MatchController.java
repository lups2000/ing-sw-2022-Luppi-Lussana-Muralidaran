package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;


import java.util.Map;

/** This class represents the Controller according to the MVC pattern
 * @author Matteo Luppi
 */
public class MatchController {

    private Game model;
    private Player firstPlayerToPlayAssistant;
    private MatchPhase matchPhase;

    /**
     * Constructor
     * @param model it is the Model according to the MVC pattern
     */
    public MatchController(Game model){
        this.model=model;
        this.matchPhase=MatchPhase.START;
        this.firstPlayerToPlayAssistant=model.getFirstPlayer(); //initially he is the first one who joins the game
    }

    /**
     * This method calls planningPhase1(),planningPhase2(),choosePlayerToPlayAction(),action1(),action2(),action3() respectively!
     * After each player's turn we check if there is a winner;otherwise we call another time the method roundManager
     */
    public void roundManager(){

        while(matchPhase!=MatchPhase.END){
            planningPhase1();
            planningPhase2();

            //I put the players.size() because if a player is disconnected the round continues
            for(int i=0;i<model.getPlayers().size();i++){
                Player currentActionPlayer=choosePlayerToPlayAction();
                actionPhase1(currentActionPlayer);
                actionPhase2(currentActionPlayer);
                if(checkWinner()){
                    matchPhase=MatchPhase.END;
                    break;
                }
                else{
                    actionPhase3(currentActionPlayer);
                }
            }
        }

    }

    /**
     * This method represents the first part of the planning phase, where the cloud tiles are filled
     */
    private void planningPhase1(){ //attenzione perche ora in game riempiamo le cloud al primo giro
        if(matchPhase==MatchPhase.START){
            for(CloudTile cloudTile : model.getCloudTiles()){
                try {
                    model.fillCloudTile(cloudTile);
                } catch (TooManyPawnsPresent e) {
                    e.printStackTrace();
                }
            }
            matchPhase=MatchPhase.PLANNING1;
        }
        else{
            //non ha senso invocarlo-->messaggio errore mandato dalla view?
        }
    }

    /**
     * This method represents the second part of the planning phase, where each player plays an Assistant Card
     */
    private void planningPhase2(){
        if(matchPhase==MatchPhase.PLANNING1){
            model.getCurrentHand().clear();
            for(int i= firstPlayerToPlayAssistant.getId()%(model.getPlayers().size());i<model.getPlayers().size();i++){
                if(model.getPlayers().get(i).getStatus()==PlayerStatus.WAITING){
                    //every player must choose an assistant card-->NB: different from the others
                    //we must control that the Assistant chosen is not present in the currentHand!!!
                    //model.getPlayers().get(i).pickAssistantCard(); surrounded by try catch block
                    //if the assistant cards of the current player are finished I could call model.checkWinner() (the one written by Paolo)
                    model.getPlayers().get(i).setStatus(PlayerStatus.PLAYING_ASSISTANT);
                    model.getCurrentHand().put(model.getPlayers().get(i),model.getPlayers().get(i).getCurrentAssistant());
                    matchPhase=MatchPhase.PLANNING2;

                }
                else{
                    break; //exit from the loop
                }
            }
            //each player has played his assistant card,now everybody waits for his turn in the action phase
            for(Player player : model.getCurrentHand().keySet()){
                player.setStatus(PlayerStatus.WAITING_ACTION);
            }
        }
        else{
            //non ha senso invocarlo-->messaggio errore mandato dalla view?
        }
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
     * This is the method which represents the first step of the action phase where the player must move X students
     * @param player current player chosen by the method ChoosePlayerToAction()
     */
    private void actionPhase1(Player player){
        if(matchPhase==MatchPhase.PLANNING2 && player.getStatus()==PlayerStatus.PLAYING_ACTION){
            if(model.getExpertsVariant()){
                //we could ask if the player wants to play a character card
                //the player must choose between the three random character cards of the Game
            }
            if(model.getMaxNumPlayers()==2){
                //the player must choose if he wants to move the students(3) to an island or to the dining
                for(int i=0;i<3;i++){
                    //we ask the player if he wants to move to his dining room or to an island

                    /*
                    if("ToSchoolboard"){
                        //we ask the player which PawnColor he wants to move
                        try {
                            player.getSchoolBoard().moveStudToDining(PawnColorChosen);
                        } catch (NoPawnPresentException e) {
                            e.printStackTrace();
                        } catch (TooManyPawnsPresent e) {
                            e.printStackTrace();
                        }

                        try {
                            model.allocateProfessors(); //method we call each time there is a movement in the player's schoolboard
                        } catch (NoPawnPresentException e) {
                            e.printStackTrace();
                        } catch (TooManyPawnsPresent e) {
                            e.printStackTrace();
                        }
                    }
                    else if("ToIsland"){
                        //we ask the player where he wants to move the student
                        try {
                            player.getSchoolBoard().moveStudToIsland(PawnColorChosen,IslandChosen);
                        } catch (NoPawnPresentException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        //messaggio di errore tramite la view
                        i--; //cosi da rifare ancora la mossa
                    }
                     */
                }
                //action phase 1 of the player finished,now move to the 2 action phase
            }
            else if(model.getMaxNumPlayers()==3){
                //the player must choose if he wants to move the students(4) to an island or to the dining
                for(int i=0;i<4;i++){
                    //we ask the player if he wants to move to his dining room or to an island

                    /*
                    if("ToSchoolboard"){
                        //we ask the player which PawnColor he wants to move
                        try {
                            player.getSchoolBoard().moveStudToDining(PawnColorChosen);
                        } catch (NoPawnPresentException e) {
                            e.printStackTrace();
                        } catch (TooManyPawnsPresent e) {
                            e.printStackTrace();
                        }

                        try {
                            model.allocateProfessors(); //method we call each time there is a movement in the player's schoolboard
                        } catch (NoPawnPresentException e) {
                            e.printStackTrace();
                        } catch (TooManyPawnsPresent e) {
                            e.printStackTrace();
                        }
                    }
                    else if("ToIsland"){
                        //we ask the player where he wants to move the student
                        try {
                            player.getSchoolBoard().moveStudToIsland(PawnColorChosen,IslandChosen);
                        } catch (NoPawnPresentException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        //messaggio di errore tramite la view
                        i--; //cosi da rifare ancora la mossa
                    }
                     */
                }
            }
            matchPhase=MatchPhase.ACTION1;
        }
        else{
            //non ha senso invocarlo-->messaggio errore mandato dalla view?
        }
    }

    /**
     * This is the method which represents the second step of the action phase where the player must move mother nature to an Island
     * @param player current player chosen by the method ChoosePlayerToAction()
     */
    private void actionPhase2(Player player){
        if(matchPhase==MatchPhase.ACTION1 && player.getStatus()==PlayerStatus.PLAYING_ACTION){
            //we ask the player on which island he wants to move mother nature
            //we must control that the player can move mother nature there according to the assistant card played!!!
            //model.moveMotherNature(IslandChosen);
            matchPhase=MatchPhase.ACTION2;
        }
        else{
            //non ha senso invocarlo-->messaggio errore mandato dalla view?
        }
    }

    /**
     * This is the method which represents the third step of the action phase where the player must pick a CloudTile
     * @param player current player chosen by the method ChoosePlayerToAction()
     */
    private void actionPhase3(Player player){
        if(matchPhase==MatchPhase.ACTION2 && player.getStatus()==PlayerStatus.PLAYING_ACTION){
            //we ask the player which CloudTile he wants to pick
            //player.pickCloudTile(CloudTileChosen);
            player.setStatus(PlayerStatus.WAITING);
            matchPhase=MatchPhase.START;//in order to come back to the Planning phase 1
        }
        else{
            //non ha senso invocarlo-->messaggio di errore mandato dalla view?
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
}
