package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.PlayerStatus;
import it.polimi.ingsw.Model.SchoolBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveMoreMotherNatureTest {

    Game game =new Game();

    @Test
    void effect() throws NoPawnPresentException, TooManyPawnsPresent {
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));
        game.initGame(2,true);
        game.getPlayers().get(0).chooseDeck(AssistantSeed.KING);
        //pick the first one,value 1 maxstep=1
        game.getPlayers().get(0).pickAssistantCard(game.getPlayers().get(0).getDeckAssistantCard().getCards().get(0));
        assertEquals(game.getPlayers().get(0).getCurrentAssistant().getMaxStepsMotherNature(),1);
        game.getPlayers().get(0).setStatus(PlayerStatus.PLAYING_ACTION);

        MoveMoreMotherNature moveMoreMotherNature=new MoveMoreMotherNature(game);
        moveMoreMotherNature.effect();
        assertEquals(moveMoreMotherNature.getCost(),2);

        assertEquals(game.getPlayers().get(0).getCurrentAssistant().getMaxStepsMotherNature(),3);


    }

}