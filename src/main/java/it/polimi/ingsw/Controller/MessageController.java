package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.*;
import it.polimi.ingsw.network.Messages.Message;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * The task of this class is to verify that the format of the messages sent by the client is valid
 * @author Matteo Luppi
 */
public class MessageController implements Serializable {

    @Serial
    private static final long serialVersionUID=-2882272847827482742L;
    private final MainController mainController;
    private final transient Map<String, VirtualView> virtualViewsMap;
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
            view.showLoginInfo(nickName,false,true);
            //view.showGenericMessage("We are sorry but the nickName has already been taken! Try again: ");
            return false;
        }
        else if(nickName==null || nickName.isEmpty() || nickName.equals("SERVER")){ //if the input is invalid
            view.showLoginInfo(null,false,true);
            //view.showGenericMessage("We are sorry but the nickName is invalid!Try again: ");
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
        Player player = model.getPlayerByNickName(message.getNickName());
        int maxStepsAllowed;
        maxStepsAllowed = player.getCurrentAssistant().getMaxStepsMotherNature();

        if(motherNatureMoveReply.getSteps()>0 && motherNatureMoveReply.getSteps()<=maxStepsAllowed){
            return true;
        }

        VirtualView virtualView=virtualViewsMap.get(message.getNickName());
        virtualView.showGenericMessage("You didn't provide a valid number of steps! It must be between 1 and " + maxStepsAllowed);
        virtualView.askMoveMotherNature(model.getIslands(),model.getPlayerByNickName(message.getNickName()).getCurrentAssistant().getMaxStepsMotherNature());
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
     * Checks if the given character card is valid to use or not
     *
     * @param message is "yes" if the player decides to use a character card
     * @return true if you provide a valid character card, false if not
     */
    public boolean checkCharacterCard(Message message){ //da invocare solo se la risposta è yes
        CharacterCardReply characterCardReply=(CharacterCardReply) message;
        if(characterCardReply.getIdCharacterCard()==-1){ //no character card
            return true;
        }
        else{
            CharacterCard characterCard=model.getCharacterCards().get(characterCardReply.getIdCharacterCard()-1);

            if(characterCard.getCost()>=1 && characterCard.getCost()<=3){
                return true;
            }
            VirtualView virtualView=virtualViewsMap.get(message.getNickName());
            virtualView.showGenericMessage("You didn't provide a valid Character Card!");
            virtualView.askPlayCharacterCard(model.getCharacterCards());
            return false;
        }
    }

    /**
     * to check if the chosen island's index is legit
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkIsland(Message message){
        IslandReply islandReply = (IslandReply) message;
        int index = islandReply.getIndexIsland();
        return index >= 0 && index < model.getIslands().size();
    }


    /**
     * to check if the chosen color is legit
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkColor(Message message){
        ColorReply colorReply = (ColorReply) message;
        PawnColor chosenColor = colorReply.getChosenColor();
        return (chosenColor.equals(PawnColor.BLUE) || chosenColor.equals(PawnColor.GREEN) || chosenColor.equals(PawnColor.PINK) ||
                chosenColor.equals(PawnColor.RED) || chosenColor.equals(PawnColor.YELLOW));
    }


    /**
     * to check if the chosen color is legit, pawncolor = null means the user decided to stop switching the students
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkStudentOrStop(Message message){
        StudentOrStopReply studentOrStopReply = (StudentOrStopReply) message;
        PawnColor chosenColor = studentOrStopReply.getPawnColor();
        boolean stop = studentOrStopReply.isStop();
        if(stop) {
            return true;
        }
        else {
            return (chosenColor.equals(PawnColor.BLUE) || chosenColor.equals(PawnColor.GREEN) || chosenColor.equals(PawnColor.PINK) ||
                    chosenColor.equals(PawnColor.RED) || chosenColor.equals(PawnColor.YELLOW));
        }
    }


    /**
     * to check if the chosen is cloud tile exists in the current game and was not already picked by some other player before
     *
     * @param message the message received to check
     * @return the result of the check, true or false
     */
    public boolean checkCloudTile(Message message){
        CloudTileReply cloudTileReply=(CloudTileReply) message;
        CloudTile cloudTile;
        try{
            cloudTile=model.getCloudTiles().get(cloudTileReply.getIdCloudTile());
        }catch (Exception e){
            return false;
        }

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
        virtualView.askMoveStudToDining(player);
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
        Island islandChosen=model.getIslands().get(studentToIslandReply.getIslandIndex());
        PawnColor chosenColor = studentToIslandReply.getPawnColor();
        Player player = mainController.getGame().getPlayerByNickName(studentToIslandReply.getNickName());

        //first we check that the chosen island is legit
        if(islandChosen.getIndex()>=0 && islandChosen.getIndex()<model.getIslands().size()){
            //here we check that the chosen color is legit
            if(chosenColor==PawnColor.BLUE || chosenColor==PawnColor.RED ||
                    chosenColor==PawnColor.YELLOW || chosenColor==PawnColor.GREEN || chosenColor==PawnColor.PINK){
                //here we control that the player has in his waiting room at least a student of the chosen color
                if(player.getSchoolBoard().getStudentsWaiting().get(chosenColor) > 0) {
                    return true;
                }
            }
        }

        VirtualView virtualView=virtualViewsMap.get(message.getNickName());
        virtualView.showGenericMessage("You didn't provide a valid Island!");
        virtualView.askMoveStudToIsland(player,model.getIslands());
        return false;
    }
}
