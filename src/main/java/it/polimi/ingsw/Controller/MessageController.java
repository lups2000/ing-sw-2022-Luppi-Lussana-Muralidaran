package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.*;
import it.polimi.ingsw.network.Messages.Message;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * The task of this class is to verify that the format of the messages sent by the client is valid
 * @author Matteo Luppi
 */
public class MessageController implements Serializable {

    //id serialization

    private final MainController mainController;
    private transient Map<String, VirtualView> virtualViewsMap;
    private final Game model;

    public MessageController(MainController mainController, Map<String,VirtualView> virtualViewMap){
        this.mainController = mainController;
        this.virtualViewsMap = virtualViewMap;
        this.model = mainController.getGame();
    }


    /**
     * to check if the chosen nickname is not already in use by some other player
     *
     * @param nickName the chosen nickname
     * @param view the view associated with the client
     * @return the result of the check, true or false
     */
    public boolean checkNickName(String nickName, View view){
        if(!model.isNickNameAvailable(nickName)){ //if the nickName is not available anymore
            view.showGenericMessage("We are sorry but the nickName has already been taken! Try again: ");
            return false;
        }
        else if(nickName==null || nickName.isEmpty() || nickName.equals("SERVER")){ //if the input is invalid
            view.showGenericMessage("We are sorry but the nickName is invalid!Try again: ");
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * to check if the number of players is 2 or 3
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkNumPlayers(Message message){
        NumPlayersReply numPlayersReply=(NumPlayersReply) message;

        if(numPlayersReply.getNumPlayers()>=2 && numPlayersReply.getNumPlayers()<4){
            return true;
        }
        else {
            VirtualView virtualView=virtualViewsMap.get(message.getNickName());
            virtualView.askNumPlayers();
            return false;
        }
    }

    /**
     * to check if the chosen mode is legit or not
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkExpertVariant(Message message){
        ExpertVariantReply expertVariantReply=(ExpertVariantReply) message;

        if(expertVariantReply.isExpertVariant() || !expertVariantReply.isExpertVariant()){
            return true;
        }
        else{
            VirtualView virtualView=virtualViewsMap.get(message.getNickName());
            virtualView.askExpertVariant();
            return false;
        }
    }

    /**
     * to check if the chosen assistant seed is available
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkAssistantSeed(Message message){
        AssistantSeedReply assistantSeedReply=(AssistantSeedReply) message;

        for(AssistantSeed assistantSeed :model.getSeedsAvailable()){
            if(assistantSeed.equals(assistantSeedReply.getAssistantSeed())){
                return true;
            }
        }
        VirtualView virtualView=virtualViewsMap.get(message.getNickName());
        virtualView.showGenericMessage("You didn't provide a valid AssistantSeed!");
        virtualView.askAssistantSeed(model.getSeedsAvailable());
        return false;
    }

    /**
     * to check if the wanted move for mother nature is allowed or not
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkMotherNature(Message message){
        MotherNatureMoveReply motherNatureMoveReply=(MotherNatureMoveReply) message;

        if(motherNatureMoveReply.getIsland().getIndex()>=0 && motherNatureMoveReply.getIsland().getIndex()<model.getIslands().size()
            /*aggiungere anche controlllo che il numero di passi richiesto sia concesso dal currentAssistant del currentPlayer TODO*/){
            return true;
        }

        VirtualView virtualView=virtualViewsMap.get(message.getNickName());
        virtualView.showGenericMessage("You didn't provide a valid island!The island index must be between 0 and "+(model.getIslands().size()-1));
        virtualView.askMoveMotherNature(model.getIslands());
        return false;
    }

    /**
     * to check if the chosen assistant card was already used before
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkAssistantCard(Message message){
        AssistantCardReply assistantCardReply=(AssistantCardReply) message;
        AssistantCard assistantCard = assistantCardReply.getAssistantCard();

        //si potrebbe aggiungere il controllo che non sia giocata da un altro giocatore nello stesso turno TODO
        if(assistantCard.getValue()>=1 && assistantCard.getValue()<=10 && assistantCard.getMaxStepsMotherNature()>=1 && assistantCard.getMaxStepsMotherNature()<=5
                && model.getPlayerByNickName(message.getNickName()).getDeckAssistantCard().getCards().contains(assistantCard)){
            return true;
        }

        VirtualView virtualView=virtualViewsMap.get(message.getNickName());
        virtualView.showGenericMessage("You didn't provide a valid AssistantCard!");
        virtualView.askAssistantCard(model.getPlayerByNickName(message.getNickName()).getDeckAssistantCard().getCards());
        return false;
    }

    /**
     * to check if the chosen is cloud tile exists in the current game and was not already picked by some other player before
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkCloudTile(Message message){
        CloudTileReply cloudTileReply=(CloudTileReply) message;
        CloudTile cloudTile=cloudTileReply.getCloudTile();

        if(cloudTile.getId()>=0 && cloudTile.getId()<model.getCloudTiles().size() && cloudTile.getNumStudents() > 0){
            return true;
        }
        else{
            VirtualView virtualView=virtualViewsMap.get(message.getNickName());
            virtualView.showGenericMessage("You didn't provide a valid CloudTile!");
            virtualView.askChooseCloudTile(model.getCloudTiles());
            return false;
        }
    }

    /**
     * to check if there is at least one student of the chosen color in the player's waiting room
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkStudentToDining(Message message){
        StudentToDiningReply studentToDiningReply=(StudentToDiningReply) message;
        PawnColor chosenColor = studentToDiningReply.getPawnColor();
        Player player = mainController.getGame().getPlayerByNickName(studentToDiningReply.getNickName());

        //first we check that the chosen color is legit
        if(chosenColor==PawnColor.BLUE || chosenColor==PawnColor.RED ||
                chosenColor==PawnColor.YELLOW || chosenColor==PawnColor.GREEN || chosenColor==PawnColor.PINK){
            //here we control that the player has in his waiting room at least a student of the chosen color
            if(player.getSchoolBoard().getStudentsWaiting().get(chosenColor) != 0) {
                return true;
            }
        }

        VirtualView virtualView=virtualViewsMap.get(message.getNickName());
        virtualView.showGenericMessage("You didn't provide a valid Student!");
        virtualView.askMoveStudToDining(Arrays.asList(PawnColor.values()));
        return false;
    }

    /**
     * to check if there is at least one student of the chosen color in the player's waiting room
     * and if the selected island exists in the current game
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkStudentToIsland(Message message){
        StudentToIslandReply studentToIslandReply=(StudentToIslandReply)message;
        Island islandChosen=studentToIslandReply.getIsland();
        PawnColor chosenColor = studentToIslandReply.getPawnColor();
        Player player = mainController.getGame().getPlayerByNickName(studentToIslandReply.getNickName());

        //first we check that the chosen island is legit
        if(islandChosen.getIndex()>=0 && islandChosen.getIndex()<model.getIslands().size()){
            //here we check that the chosen color is legit
            if(chosenColor==PawnColor.BLUE || chosenColor==PawnColor.RED ||
                    chosenColor==PawnColor.YELLOW || chosenColor==PawnColor.GREEN || chosenColor==PawnColor.PINK){
                //here we control that the player has in his waiting room at least a student of the chosen color
                if(player.getSchoolBoard().getStudentsWaiting().get(chosenColor) != 0) {
                    return true;
                }
            }
        }

        VirtualView virtualView=virtualViewsMap.get(message.getNickName());
        virtualView.showGenericMessage("You didn't provide a valid Island!");
        virtualView.askMoveStudToIsland(model.getIslands());
        return false;
    }
}
