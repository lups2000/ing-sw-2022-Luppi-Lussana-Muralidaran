package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;
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
    void addPlayer() throws NoPawnPresentException, TooManyPawnsPresent {
        game2.initGame(2,false);
        game2.addPlayer("Teo",AssistantSeed.KING);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            game2.addPlayer("Paolo",AssistantSeed.KING);
        });
        assertEquals("The seed has already been chosen!", exception.getMessage());

        game2.addPlayer("Paolo",AssistantSeed.WITCH);

        assertEquals(game2.getPlayers().size(),2);
        assertEquals(game2.getPlayers().get(0).getId(),0);
        assertEquals(game2.getPlayers().get(1).getId(),1);
        assertEquals(game2.getPlayers().get(0).getNickname(),"Teo");
        assertEquals(game2.getPlayers().get(1).getNickname(),"Paolo");
        assertEquals(game2.getPlayers().get(0).getColorTower(),ColorTower.WHITE);
        assertEquals(game2.getPlayers().get(1).getColorTower(),ColorTower.BLACK);
        assertEquals(game2.getSeedsAvailable().size(),2);
        assertEquals(game2.getFirstPlayer(),game2.getPlayers().get(0));
        assertFalse(game2.getSeedsAvailable().contains(AssistantSeed.KING) && game2.getSeedsAvailable().contains(AssistantSeed.WITCH));
        assertTrue(game2.getSeedsAvailable().contains(AssistantSeed.WIZARD) && game2.getSeedsAvailable().contains(AssistantSeed.SAMURAI));

        //now some tests to test fillBoard() method
        assertFalse(game2.getPlayers().get(0).getSchoolBoard().isExperts());
        assertFalse(game2.getPlayers().get(1).getSchoolBoard().isExperts());
        assertEquals(game2.getPlayers().get(0).getSchoolBoard().getNumStudentsWaiting(),7);
        assertEquals(game2.getPlayers().get(1).getSchoolBoard().getNumStudentsWaiting(),7);

        /* Just to verify
        System.out.println(game2.getPlayers().get(0).getSchoolBoard().getStudentsWaiting());
        System.out.println("------------");
        System.out.println(game2.getPlayers().get(1).getSchoolBoard().getStudentsWaiting());
         */

        Throwable exception1 = assertThrows(IllegalStateException.class, () -> {
            game2.addPlayer("Pradee",AssistantSeed.WIZARD);
        });
        assertEquals("Too many players!", exception1.getMessage());

    }

    @Test
    @DisplayName("changeStatus")
    void changeStatus() {
        game2.changeStatus(GameState.ENDED);
        assertEquals(game2.getStatus(),GameState.ENDED);
        game2.changeStatus(GameState.CREATING);
        assertEquals(game2.getStatus(),GameState.CREATING);
    }

    @Test
    @DisplayName("moveMotherNature")
    void moveMotherNature() throws TooManyTowersException, NoTowersException, NoPawnPresentException, TooManyPawnsPresent {
        game2.initGame(2,false);
        game2.addPlayer("Teo",AssistantSeed.KING);
        game2.addPlayer("Paolo",AssistantSeed.WIZARD);
        game2.moveMotherNature(game2.getIslands().get(5));
        assertTrue(game2.getIslands().get(5).isMotherNature());
        assertEquals(game2.getMotherNature(),game2.getIslands().get(5).getIndex());
    }

    @Test
    @DisplayName("moveMotherNatureNull")
    void moveMotherNatureNull() {

        Throwable exception1 = assertThrows(NullPointerException.class, () -> {
            game2.moveMotherNature(null);
        });
        assertEquals("Parameter cannot be null!", exception1.getMessage());
    }

    @Test
    @DisplayName("influence")
    void influence(){

    }







    @Test
    @DisplayName("fillCloudTileWithExc")
    void fillCloudTileWithExc() {

    }


}