package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Island class is tested by IslandTest
 * @author Matteo Luppi
 */

class PlayerTest {
    SchoolBoard schoolBoard=new SchoolBoard(3,false);
    Player player=new Player(2,"Teo",AssistantSeed.KING,schoolBoard);

    @Test
    @DisplayName("getNickname")
    void getNickname() {
        assertEquals(player.getNickname(),"Teo");
    }

    @Test
    @DisplayName("getId")
    void getId() {
        assertEquals(player.getId(),2);

    }

    @Test
    @DisplayName("getPlayerStatus")
    void getPlayerStatus() {
        assertEquals(player.getStatus(),PlayerStatus.WAITING);
    }

    @Test
    @DisplayName("getColorTower")
    void getColorTower() {
        assertEquals(player.getColorTower(),ColorTower.GRAY);
    }

    @Test
    @DisplayName("getSchoolBoard")
    void getSchoolBoard() {
        assertEquals(player.getSchoolBoard(),schoolBoard);
    }

    @Test
    @DisplayName("setPlayerStatus")
    void setPlayerStatus() {
        player.setStatus(PlayerStatus.PLAYING_ACTION);
        assertEquals(player.getStatus(),PlayerStatus.PLAYING_ACTION);
    }

    @Test
    @DisplayName("getTwoAdditionalPoints")
    void getTwoAdditionalPoints(){
        assertFalse(player.isTwoAdditionalPoints());
    }

    @Test
    @DisplayName("setTwoAdditionalPoints")
    void setTwoAdditionalPoints(){
        assertFalse(player.isTwoAdditionalPoints());
        player.setTwoAdditionalPoints(true);
        assertTrue(player.isTwoAdditionalPoints());
    }

    @Test
    @DisplayName("getAssistantDeck")
    void getAssistantDeck(){
        assertEquals(player.getDeckAssistantCard().getSeed(),AssistantSeed.KING);
    }

    @Test
    @DisplayName("getControlOnProfessor")
    void getControlOnProfessor(){
        assertFalse(player.getControlOnProfessor());
    }

    @Test
    @DisplayName("setControlOnProfessor")
    void setControlOnProfessor(){
        assertFalse(player.getControlOnProfessor());
        player.setControlOnProfessor(true);
        assertTrue(player.getControlOnProfessor());
    }

    @Test
    @DisplayName("pickCloudTile")
    void pickCloudTile() throws TooManyPawnsPresent, NoPawnPresentException {
        Game game = new Game();
        game.initGame(2,true);
        game.addPlayer("Paolo",AssistantSeed.MAGICIAN);

        for(PawnColor pawnColor: PawnColor.values()){
            while(game.getPlayers().get(0).getSchoolBoard().getStudentsWaiting().get(pawnColor)>0){
                game.getPlayers().get(0).getSchoolBoard().moveStudToDining(pawnColor);
            }
        }
        //now the player's waiting room is empty
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumStudentsWaiting(),0);
        for(PawnColor pawnColor: PawnColor.values()){
            assertEquals(game.getPlayers().get(0).getSchoolBoard().getStudentsWaiting().get(pawnColor),0);
        }
        //the cloud with index 0 contains 3 students
        game.getPlayers().get(0).pickCloudTile(game.getCloudTiles().get(0));

        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumStudentsWaiting(),3);
        assertEquals(game.getCloudTiles().get(0).getNumStudents(),0);
    }

}