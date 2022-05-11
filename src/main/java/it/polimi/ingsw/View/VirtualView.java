package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.server.ClientConnection;

import java.util.List;

/** This class represents the 'real' view to the Controller. It hides the implementation
 * of the network to the Controller
 * @author Matteo Luppi
 */
public class VirtualView implements View{

    private ClientConnection clientConnection;

    public VirtualView(ClientConnection clientConnection){
        this.clientConnection=clientConnection;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    @Override
    public void askNickName() {

    }

    @Override
    public void askNumPlayers() {

    }

    @Override
    public void askPlayAssistantSeed(List<AssistantSeed> assistantSeedAvailable) {

    }

    @Override
    public void askPlayAssistantCard(List<AssistantCard> assistantCards) {

    }


    @Override
    public void askMoveStudToDining(PawnColor pawnColor, SchoolBoard schoolBoard) {

    }

    @Override
    public void askMoveStudToIsland(PawnColor pawnColor, SchoolBoard schoolBoard) {

    }

    @Override
    public void askMoveMotherNature(Island island) {

    }

    @Override
    public void askChooseCloudTile(CloudTile cloudTile) {

    }

    @Override
    public void askPlayCharacterCard(List<CharacterCard> characterCards) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLobby(List<String> players, int numPlayers) {

    }

    @Override
    public void showVictoryMessage(String victoryMessage) {

    }

    @Override
    public void showLoseMessage(String loseMessage) {

    }
}
