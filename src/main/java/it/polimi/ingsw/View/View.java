package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;

import java.util.List;

public interface View {

    /**
     * Method to ask the nickName to the user
     */
    void askNickName();

    /**
     * Method to ask the number of player to the first player who joins the match
     */
    void askNumPlayers();

    /**
     * Method to ask the assistant seed to each player
     */
    void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable);

    void askAssistantCard(List<AssistantCard> assistantCards);

    void askMoveStudToDining(PawnColor pawnColor,SchoolBoard schoolBoard);

    void askMoveStudToIsland(PawnColor pawnColor,SchoolBoard schoolBoard);

    void askMoveMotherNature(Island island);

    void askChooseCloudTile(CloudTile cloudTile);

    void askExpertVariant();

    void askPlayCharacterCard(List<CharacterCard> characterCards);

    void showGenericMessage(String genericMessage);

    void showError(String error);

    void showLobby(List<String> players,int numPlayers);

    void showVictoryMessage(String victoryMessage);

    void showLoseMessage(String loseMessage);

    void showLoginPlayers(String nickName,boolean nickNameOk,boolean connectionOk);

}
