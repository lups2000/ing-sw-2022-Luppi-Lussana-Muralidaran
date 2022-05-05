package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Matteo Luppi
 */
public class MatchController {

    private Game model;
    private Player currentPlayerTurn;
    private MatchPhase matchPhase;
    private List<Player> orderActionPhase;

    /**
     * Constructor
     * @param model it is the Model according to the MVC pattern
     */
    public MatchController(Game model){
        this.model=model;
        this.matchPhase=MatchPhase.START;
        this.currentPlayerTurn=model.getFirstPlayer(); //initially he is the first one who joins the game
        this.orderActionPhase=new ArrayList<>();
    }

    /**
     * This method represents the first part of the planning phase, where the cloud tiles are filled
     */
    public void planningPhase1(){ //attenzione perche ora in game riempiamo le cloud al primo giro
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
            //non ha senso invocarlo
        }
    }

    /**
     * This method represents the second part of the planning phase, where each player plays an Assistant Card
     */
    public void planningPhase2(){
        if(matchPhase==MatchPhase.PLANNING1){
            model.getCurrentHand().clear();
            for(int i= currentPlayerTurn.getId()%(model.getPlayers().size());i<model.getPlayers().size();i++){
                if(model.getPlayers().get(i).getStatus()==PlayerStatus.WAITING){
                    //every player must choose an assistant card-->NB: different from the others
                    //model.getPlayers().get(i).pickAssistantCard(); surrounded by tray catch block
                    model.getPlayers().get(i).setStatus(PlayerStatus.PLAYING_ASSISTANT);
                    model.getCurrentHand().put(model.getPlayers().get(i),model.getPlayers().get(i).getCurrentAssistant());
                    matchPhase=MatchPhase.PLANNING2;
                }
                else{
                    break; //exit from the loop
                }
            }
        }
        else{
            //non ha senso invocarlo
        }
    }

    /**
     * This method chooses who is the player who plays the action phase
     * @return the player
     */
    public Player choosePlayerToPlayAction(){
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
            this.currentPlayerTurn = first;
        }
        model.getCurrentHand().remove(first);
        first.setStatus(PlayerStatus.PLAYING_ACTION);
        return first;
    }

    /**
     * This is the method which represents the first step of the action phase where the player must move X students
     * @param player current player chosen by the method ChoosePlayerToAction()
     */
    public void actionPhase1(Player player){
        if(matchPhase==MatchPhase.PLANNING2){
            if(model.getExpertsVariant()){
                //we could ask if the player wants to play a character card
            }
            if(model.getMaxNumPlayers()==2){
                //the player must choose if he wants to move the students(3) to an island or to the dining
            }
            else if(model.getMaxNumPlayers()==3){
                //the player must choose if he wants to move the students(4) to an island or to the dining
            }
            matchPhase=MatchPhase.ACTION1;
        }
        else{
            //non ha senso invocarlo
        }
    }


}
