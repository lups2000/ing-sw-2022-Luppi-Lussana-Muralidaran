package it.polimi.ingsw.Model;

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
        player.setStatus(PlayerStatus.PLAYING);
        assertEquals(player.getStatus(),PlayerStatus.PLAYING);
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

}