package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.AssistantSeedReply;
import it.polimi.ingsw.network.Messages.ClientSide.ExpertVariantReply;
import it.polimi.ingsw.network.Messages.ClientSide.NumPlayersReply;
import it.polimi.ingsw.network.Messages.Message;

import java.io.Serializable;
import java.util.Map;

/**
 * The task of this class is to verify that the format of the messages sent by the client is valid
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






}
