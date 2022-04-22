package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Game class is tested by GameTest
 * @author Matteo Luppi
 */

class GameTest {
    Game game2=new Game();
    Game game3=new Game();

    /* This test tests the correct creation of a Game instance and the private method fillIsland*/
    @Test
    @DisplayName("setup")
    void setup(){
        assertEquals(game2.getStatus(),GameState.CREATING);
        assertEquals(game2.getSeedsAvailable().size(),4);
        assertEquals(game2.getSeedsAvailable().get(0),AssistantSeed.KING);
        assertEquals(game2.getSeedsAvailable().get(1),AssistantSeed.SAMURAI);
        assertEquals(game2.getSeedsAvailable().get(2),AssistantSeed.WITCH);
        assertEquals(game2.getSeedsAvailable().get(3),AssistantSeed.WIZARD);
        assertEquals(game2.getIslands().size(),12);
        for(Island island : game2.getIslands()){
            if(island.getIndex()!=0 && island.getIndex()!=6){
                assertFalse(island.isMotherNature());
                assertNull(island.getTower());
                assertTrue(island.getStudents().get(PawnColor.RED)!=0 || island.getStudents().get(PawnColor.YELLOW)!=0 || island.getStudents().get(PawnColor.BLUE)!=0 || island.getStudents().get(PawnColor.GREEN)!=0 || island.getStudents().get(PawnColor.PINK)!=0);
            }
            else {
                assertNull(island.getTower());
                if(island.getIndex()==0){
                    assertTrue(island.isMotherNature());
                }
                else{
                    assertFalse(island.isMotherNature());
                }
                assertEquals(island.getStudents().get(PawnColor.RED),0);
                assertEquals(island.getStudents().get(PawnColor.BLUE),0);
                assertEquals(island.getStudents().get(PawnColor.GREEN),0);
                assertEquals(island.getStudents().get(PawnColor.PINK),0);
                assertEquals(island.getStudents().get(PawnColor.YELLOW),0);
            }
        }
    }

    @Test
    @DisplayName("initGame1PlayerOr5Players")
    void initGame1Player(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            game2.initGame(1,false);
        });
        assertEquals("The number of players must be between 2 and 4!", exception.getMessage());

        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> {
            game2.initGame(5,false);
        });
        assertEquals("The number of players must be between 2 and 4!", exception1.getMessage());
    }

    @Test
    @DisplayName("initGame2PlayersNoExpert")
    void initGame2PlayersNoExpert() throws NoPawnPresentException, TooManyPawnsPresent {
        game2.initGame(2,false);

        assertEquals(game2.getSchoolBoards().size(),2);
        for(SchoolBoard schoolBoard : game2.getSchoolBoards()){
            assertEquals(schoolBoard.getNumMaxPlayers(),2);
            assertFalse(schoolBoard.isExperts());
        }
        assertEquals(game2.getCloudTiles().size(),2);
        for(int i=0;i<2;i++){
            assertEquals(game2.getCloudTiles().get(i).getId(),i);
            assertEquals(game2.getCloudTiles().get(i).getNumStudents(),3);
        }
    }

    @Test
    @DisplayName("initGame2PlayersExpert")
    void initGame2PlayersExpert() throws NoPawnPresentException, TooManyPawnsPresent {
        game3.initGame(2,true);

        assertEquals(game3.getSchoolBoards().size(),2);
        for(SchoolBoard schoolBoard : game3.getSchoolBoards()){
            assertEquals(schoolBoard.getNumMaxPlayers(),2);
            assertTrue(schoolBoard.isExperts());
        }
        assertEquals(game3.getCloudTiles().size(),2);
        for(int i=0;i<2;i++){
            assertEquals(game3.getCloudTiles().get(i).getId(),i);
            assertEquals(game3.getCloudTiles().get(i).getNumStudents(),3);
        }
        assertEquals(game3.getCharacterCards().size(),3);

    }

    /* Just to reach more coverage*/
    @Test
    @DisplayName("create10Games")
    void create10Games() throws NoPawnPresentException, TooManyPawnsPresent {
        List<Game> games=new ArrayList<>();
        for(int i=0;i<50;i++){
            games.add(i,new Game());
            games.get(i).initGame(2,true);
            assertEquals(games.get(i).getCharacterCards().size(),3);
        }
    }

    @Test
    @DisplayName("addPlayer")
    void addPlayer() {
    }

    @Test
    @DisplayName("changeStatus")
    void changeStatus() {
    }

    @Test
    @DisplayName("fillBoard")
    void fillBoard() {
    }

    @Test
    @DisplayName("fillCloudTile")
    void fillCloudTile() {
    }

    @Test
    @DisplayName("fillIslands")
    void fillIslands() {
    }
}