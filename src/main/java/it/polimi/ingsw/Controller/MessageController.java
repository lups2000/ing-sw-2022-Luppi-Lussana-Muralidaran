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

    public boolean checkNickName(String nickName, View view){
        if(!model.isNickNameAvailable(nickName)){ //if the nickName is not available anymore
            view.showGenericMessage("We are sorry but the nickName has been already take!");
            view.showLoginPlayers(null,false,true);
            return false;
        }
        else if(nickName==null || nickName.isEmpty() || nickName.equals("SERVER")){ //if the input is invalid
            view.showGenericMessage("We are sorry but the nickName is invalid!");
            view.showLoginPlayers(null,false,true);
            return false;
        }
        else{
            return true;
        }
    }

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

    public boolean checkMotherNature(Message message){
        MotherNatureMoveReply motherNatureMoveReply=(MotherNatureMoveReply) message;

        if(motherNatureMoveReply.getIsland().getIndex()>=0 && motherNatureMoveReply.getIsland().getIndex()<model.getIslands().size()){
            return true;
        }

        VirtualView virtualView=virtualViewsMap.get(message.getNickName());
        virtualView.showGenericMessage("You didn't provide a valid island!The island index must be between 0 and "+(model.getIslands().size()-1));
        virtualView.askMoveMotherNature(model.getIslands());
        return false;
    }

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

    public boolean checkCloudTile(Message message){
        CloudTileReply cloudTileReply=(CloudTileReply) message;

        CloudTile cloudTile=cloudTileReply.getCloudTile();
        if(cloudTile.getId()>=0 && cloudTile.getId()<model.getCloudTiles().size()){
            return true;
        }
        else{
            VirtualView virtualView=virtualViewsMap.get(message.getNickName());
            virtualView.showGenericMessage("You didn't provide a valid CloudTile!");
            virtualView.askChooseCloudTile(model.getCloudTiles());
            return false;
        }
    }

    public boolean checkStudentToDining(Message message){
        StudentToDiningReply studentToDiningReply=(StudentToDiningReply) message;

        if(studentToDiningReply.getPawnColor()==PawnColor.BLUE || studentToDiningReply.getPawnColor()==PawnColor.RED ||
             studentToDiningReply.getPawnColor()==PawnColor.YELLOW || studentToDiningReply.getPawnColor()==PawnColor.GREEN || studentToDiningReply.getPawnColor()==PawnColor.PINK){
            return true;
        }
        else{
            VirtualView virtualView=virtualViewsMap.get(message.getNickName());
            virtualView.showGenericMessage("You didn't provide a valid Student!");
            virtualView.askMoveStudToDining(Arrays.asList(PawnColor.values()));
            return false;
        }
    }

    public boolean checkStudentToIsland(Message message){
        StudentToIslandReply studentToIslandReply=(StudentToIslandReply)message;
        Island islandChosen=studentToIslandReply.getIsland();

        if(islandChosen.getIndex()>=0 && islandChosen.getIndex()<model.getIslands().size()){
            return true;
        }
        else{
            VirtualView virtualView=virtualViewsMap.get(message.getNickName());
            virtualView.showGenericMessage("You didn't provide a valid Island!");
            virtualView.askMoveStudToIsland(model.getIslands());
            return false;
        }
    }

}
