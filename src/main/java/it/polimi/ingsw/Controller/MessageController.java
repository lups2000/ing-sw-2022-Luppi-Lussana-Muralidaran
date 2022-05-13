package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.*;
import it.polimi.ingsw.network.Messages.Message;

import java.io.Serializable;
import java.util.Map;

/**
 * The task of this class is to verify that the format of the messages sent by the client is valid
 * @author Matteo Luppi
 */
public class MessageController implements Serializable {

    //id serialization

    private Game model;
    private transient Map<Player, VirtualView> virtualViewMap;

    public MessageController(Game model, Map<Player,VirtualView> virtualViewMap){
        this.model=model;
        this.virtualViewMap=virtualViewMap;
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
            VirtualView virtualView=virtualViewMap.get(model.getPlayerByNickName(message.getNickName()));
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
            VirtualView virtualView=virtualViewMap.get(model.getPlayerByNickName(message.getNickName()));
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
        VirtualView virtualView=virtualViewMap.get(model.getPlayerByNickName(message.getNickName()));
        virtualView.showGenericMessage("You didn't provide a valid AssistantSeed!");
        virtualView.askAssistantSeed(model.getSeedsAvailable());
        return false;
    }

    public boolean checkMotherNature(Message message){
        MotherNatureMoveReply motherNatureMoveReply=(MotherNatureMoveReply) message;

        if(motherNatureMoveReply.getIsland().getIndex()>=0 && motherNatureMoveReply.getIsland().getIndex()<model.getIslands().size()){
            return true;
        }

        VirtualView virtualView=virtualViewMap.get(model.getPlayerByNickName(message.getNickName()));
        virtualView.showGenericMessage("You didn't provide a valid island!The island index must be between 0 and "+(model.getIslands().size()-1));
        virtualView.askMoveMotherNature(model.getIslands());
        return false;
    }

    public boolean checkAssistantCard(Message message){
        AssistantCardReply assistantCardReply=(AssistantCardReply) message;

        for(AssistantCard assistantCard : assistantCardReply.getAssistantCards()){
            if(assistantCard.getValue()>=1 && assistantCard.getValue()<=10 && assistantCard.getMaxStepsMotherNature()>=1 && assistantCard.getMaxStepsMotherNature()<=5
                && model.getPlayerByNickName(message.getNickName()).getDeckAssistantCard().getCards().contains(assistantCard)){
                return true;
            }
        }
        VirtualView virtualView=virtualViewMap.get(model.getPlayerByNickName(message.getNickName()));
        virtualView.showGenericMessage("You didn't provide a valid AssistantCard!");
        virtualView.askAssistantCard(model.getPlayerByNickName(message.getNickName()).getDeckAssistantCard().getCards());
        return false;
    }






}
