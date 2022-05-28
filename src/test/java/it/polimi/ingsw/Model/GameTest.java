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
        assertEquals(game2.getStatus(),GameState.LOGGING);
        assertEquals(game2.getSeedsAvailable().size(),4);
        assertEquals(game2.getSeedsAvailable().get(0),AssistantSeed.KING);
        assertEquals(game2.getSeedsAvailable().get(1),AssistantSeed.SAMURAI);
        assertEquals(game2.getSeedsAvailable().get(2),AssistantSeed.WITCH);
        assertEquals(game2.getSeedsAvailable().get(3),AssistantSeed.MAGICIAN);
        assertEquals(game2.getIslands().size(),12);
        for(Island island : game2.getIslands()){
            if(island.getIndex()!=0 && island.getIndex()!=6){
                assertFalse(island.isMotherNature());
                assertNull(island.getTower());
                assertTrue(island.getStudents().get(PawnColor.RED)!=0 || island.getStudents().get(PawnColor.YELLOW)!=0 || island.getStudents().get(PawnColor.BLUE)!=0 || island.getStudents().get(PawnColor.GREEN)!=0 || island.getStudents().get(PawnColor.PINK)!=0);
                // System.out.println(island.getStudents()); just to verify
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
        game2.addPlayer("Paolo");
        game2.addPlayer("Teo");
        game2.initGame(2,false);

        assertEquals(game2.getCloudTiles().size(),2);
        for(int i=0;i<2;i++){
            assertEquals(game2.getCloudTiles().get(i).getId(),i);
            assertEquals(game2.getCloudTiles().get(i).getNumStudents(),3);
        }
    }

    @Test
    @DisplayName("initGame2PlayersExpert")
    void initGame2PlayersExpert() throws NoPawnPresentException, TooManyPawnsPresent {
        game3.addPlayer("Paolo");
        game3.addPlayer("Teo");
        game3.initGame(2,true);

        assertEquals(game3.getCloudTiles().size(),2);
        for(int i=0;i<2;i++){
            assertEquals(game3.getCloudTiles().get(i).getId(),i);
            assertEquals(game3.getCloudTiles().get(i).getNumStudents(),3);
        }
        assertEquals(game3.getCharacterCards().size(),3);

    }


    @Test
    @DisplayName("addPlayer")
    void addPlayer() throws NoPawnPresentException, TooManyPawnsPresent {
        game2.addPlayer("Paolo");
        game2.addPlayer("Teo");
        game2.initGame(2,false);

        //assertEquals("The seed has already been chosen!", exception.getMessage());

        assertEquals(game2.getPlayers().size(),2);
        assertEquals(game2.getPlayers().get(0).getId(),0);
        assertEquals(game2.getPlayers().get(1).getId(),1);
        assertEquals(game2.getPlayers().get(0).getNickname(),"Paolo");
        assertEquals(game2.getPlayers().get(1).getNickname(),"Teo");
        assertEquals(game2.getPlayers().get(0).getColorTower(),ColorTower.WHITE);
        assertEquals(game2.getPlayers().get(1).getColorTower(),ColorTower.BLACK);
        //assertEquals(game2.getSeedsAvailable().size(),2);
        assertEquals(game2.getFirstPlayer(),game2.getPlayers().get(0));
        //assertFalse(game2.getSeedsAvailable().contains(AssistantSeed.KING) && game2.getSeedsAvailable().contains(AssistantSeed.WITCH));
        //assertTrue(game2.getSeedsAvailable().contains(AssistantSeed.MAGICIAN) && game2.getSeedsAvailable().contains(AssistantSeed.SAMURAI));

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

        /*Throwable exception1 = assertThrows(IllegalStateException.class, () -> {
            game2.addPlayer("Pradee");
        });
        assertEquals("Too many players!", exception1.getMessage());
        */
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
        game2.addPlayer("Teo");
        game2.addPlayer("Paolo");
        game2.initGame(2,false);
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
    @DisplayName("influenceWithNoEntryTiles")
    void influenceWithNoEntryTiles() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);

        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has control on the RED
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has control on the BLUE
        //2 RED and 1 BLUE students on the island 0
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.BLUE);
        //island 1 has 1 NoEntryTile
        game.getIslands().get(0).setNoEntryTiles(1);
        assertEquals(game.getIslands().get(0).getNoEntryTiles(),1);
        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertEquals(game.getNoEntryTilesCounter(),5);
        assertEquals(game.getIslands().get(0).getNoEntryTiles(),0);
        assertNull(game.getIslands().get(0).getTower());
        //System.out.println(game.getIslands().get(0).getStudents()); just to verify
    }

    @Test
    @DisplayName("influenceGeneral")
    void influenceGeneral() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);
        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has control on the RED
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has control on the BLUE
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.PINK); //Paolo has control on the PINK
        //2 RED and 1 BLUE students on the island 0
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.BLUE);

        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(0).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),7);

        //now Paolo add 2 more students on the Island 0
        game.getIslands().get(0).addStudent(PawnColor.BLUE);
        game.getIslands().get(0).addStudent(PawnColor.PINK);

        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        //no change because the Paolo's influence is equal to Teo's one
        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(0).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),7);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),8);

        //now Paolo add 1 more student to Island 0
        game.getIslands().get(0).addStudent(PawnColor.PINK);

        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        //Paolo now becomes the owner of Island 0
        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(1).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),8);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),7);
    }

    @Test
    @DisplayName("influenceDrawNoTowersOnIsland")
    void influenceDrawNoTowersOnIsland() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);
        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has control on the RED
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has control on the BLUE
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.PINK); //Paolo has control on the PINK
        //2 RED and 1 BLUE students on the island 0
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.BLUE);
        game.getIslands().get(0).addStudent(PawnColor.PINK);

        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNull(game.getIslands().get(0).getTower());
    }

    @Test
    @DisplayName("influence2MorePoints")
    void influence2MorePoints() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);
        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has control on the RED
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has control on the BLUE
        //2 RED and 1 BLUE students on the island 0
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.BLUE);
        game.getIslands().get(0).addStudent(PawnColor.PINK);

        assertNull(game.getIslands().get(0).getTower());
        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(0).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),7);

        game.getIslands().get(0).addStudent(PawnColor.BLUE);
        //effect
        game.getPlayers().get(1).setTwoAdditionalPoints(true);

        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(1).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),8);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),7);

    }

    @Test
    @DisplayName("influenceNoCountTower")
    void influenceNoCountTower() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);
        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has control on the RED
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has control on the BLUE
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.PINK); //Paolo has control on the PINK
        //2 RED and 1 BLUE students on the island 0
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.BLUE);

        assertNull(game.getIslands().get(0).getTower());
        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(0).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),7);

        game.getIslands().get(0).addStudent(PawnColor.PINK);
        game.getIslands().get(0).addStudent(PawnColor.BLUE);
        //effect
        game.setNoCountTower();

        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(1).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),8);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),7);

    }

    @Test
    @DisplayName("influenceNoColor")
    void influenceNoColor() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);
        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has control on the RED
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has control on the BLUE
        //2 RED and 1 BLUE students on the island 0
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.BLUE);

        assertNull(game.getIslands().get(0).getTower());
        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(0).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),7);

        game.getIslands().get(0).addStudent(PawnColor.BLUE);
        //effect
        game.setNoColorInfluence(PawnColor.RED);

        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(1).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),8);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),7);

    }

    @Test
    @DisplayName("influenceNoColorNoTower")
    void influenceNoColorNoTower() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);
        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has control on the RED
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has control on the BLUE
        //2 RED and 1 BLUE students on the island 0
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.BLUE);

        assertNull(game.getIslands().get(0).getTower());
        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(0).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),7);

        //effect
        game.setNoColorInfluence(PawnColor.RED);
        game.setNoCountTower();
        game.moveMotherNature(game.getIslands().get(0)); //this method calls influence indirectly

        assertNotNull(game.getIslands().get(0).getTower());
        assertEquals(game.getIslands().get(0).getTower(),game.getPlayers().get(1).getColorTower());
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),8);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),7);

    }

    @Test
    @DisplayName("checkArchipelagoWithUpdateIndexes")
    void checkArchipelagoWithUpdateIndexes() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);
        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has control on the RED

        /* just to verify
        for(Player player:game.getPlayers()){
            System.out.println(player.getSchoolBoard().getProfessors());
        }*/

        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(1).addStudent(PawnColor.RED);
        game.getIslands().get(11).addStudent(PawnColor.RED);
        /* just to verify
        for(Island island: game.getIslands()){
            System.out.println(island.getStudents());
        }*/

        game.moveMotherNature(game.getIslands().get(1));
        assertEquals(game.getIslands().get(1).getTower(),ColorTower.WHITE);
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),7);

        game.moveMotherNature(game.getIslands().get(11));
        assertEquals(game.getIslands().get(11).getTower(),ColorTower.WHITE);
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),6);

        game.moveMotherNature(game.getIslands().get(0));
        assertEquals(game.getIslands().get(9).getTower(),ColorTower.WHITE);
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),5);
        //now there is an archipelago-->merge among island 0,island 1 and island 11
        assertEquals(Island.getNumIslands(),10);
        assertEquals(game.getIslands().get(9).getNumTowers(),3);

        //now Paolo has the control on the RED professor
        game.getPlayers().get(0).getSchoolBoard().removeProfessor(PawnColor.RED);
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.RED);
        game.getIslands().get(9).addStudent(PawnColor.RED);
        game.moveMotherNature(game.getIslands().get(9));
        assertEquals(game.getIslands().get(9).getTower(),ColorTower.BLACK);
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),8);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),5);
        assertEquals(game.getIslands().get(9).getNumTowers(),3);
    }

    @Test
    @DisplayName("allocateProfessorGeneral")
    void allocateProfessorGeneral() throws NoPawnPresentException, TooManyPawnsPresent {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);

        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Teo has the RED professor

        assertTrue(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertFalse(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));

        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Teo still has the RED professor

        assertTrue(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertFalse(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));

        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Poalo now has the RED professor

        assertFalse(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));

        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Paolo still has the RED professor

        assertFalse(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));

        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //no change,Paolo has the RED professor
        assertFalse(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));

        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.PINK);
        game.allocateProfessors(); //Paolo has the RED and PINK professors
        assertFalse(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.PINK));

        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.BLUE);
        game.allocateProfessors(); //Paolo has RED,BLUE and PINK professor
        assertFalse(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.PINK));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.BLUE));

        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.BLUE);
        game.allocateProfessors(); //no change
        game.allocateProfessors();
        assertFalse(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.PINK));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.BLUE));
        /* just to verify
        for(Player player: game.getPlayers()){
            System.out.println(player.getSchoolBoard().getProfessors());
        }*/
    }

    @Test
    @DisplayName("allocateProfessorWithEffect")
    void allocateProfessorWithEffect() throws NoPawnPresentException, TooManyPawnsPresent {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);

        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Teo has the RED professor

        assertTrue(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertFalse(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));

        game.getPlayers().get(1).setControlOnProfessor(true); //Paolo plays this character card
        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Paolo thanks to this card has the RED professor
        assertFalse(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));
        /* just to verify
        for(Player player: game.getPlayers()){
            System.out.println(player.getSchoolBoard().getStudentsDining());
        }
        for(Player player: game.getPlayers()){
            System.out.println(player.getSchoolBoard().getProfessors());
        }*/
        game.getPlayers().get(0).setControlOnProfessor(true); //Teo plays this character card
        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.PINK);
        game.allocateProfessors();//Teo has PINK and RED professors now
        assertTrue(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED));
        assertTrue(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.PINK));
        assertFalse(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED));

        /* just to verify
        for(Player player: game.getPlayers()){
            System.out.println(player.getSchoolBoard().getStudentsDining());
        }
        for(Player player: game.getPlayers()){
            System.out.println(player.getSchoolBoard().getProfessors());
        }*/
    }

    @Test
    @DisplayName("checkWinnerNoDraw")
    void checkWinner() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);

        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has the RED professor
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has the BLUE professor

        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.moveMotherNature(game.getIslands().get(0));
        game.getIslands().get(2).addStudent(PawnColor.RED);
        game.getIslands().get(2).addStudent(PawnColor.RED);
        game.moveMotherNature(game.getIslands().get(2));
        game.getIslands().get(4).addStudent(PawnColor.RED);
        game.getIslands().get(4).addStudent(PawnColor.RED);
        game.moveMotherNature(game.getIslands().get(4));
        game.getIslands().get(1).addStudent(PawnColor.BLUE);
        game.getIslands().get(1).addStudent(PawnColor.BLUE);
        game.moveMotherNature(game.getIslands().get(1));
        game.getIslands().get(3).addStudent(PawnColor.BLUE);
        game.getIslands().get(3).addStudent(PawnColor.BLUE);
        game.moveMotherNature(game.getIslands().get(3));

        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),5); //Teo wins
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),6);

        game.checkWinner();

        assertEquals(game.getPlayers().get(0).getStatus(),PlayerStatus.WINNER);
        assertEquals(game.getPlayers().get(1).getStatus(),PlayerStatus.WAITING);
    }

    @Test
    @DisplayName("checkWinnerDraw")
    void checkWinnerDraw() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game game=new Game();
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));

        game.initGame(2,true);

        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.RED); //Teo has the RED professor
        game.getPlayers().get(0).getSchoolBoard().addProfessor(PawnColor.PINK); //Teo has the PINK professor
        game.getPlayers().get(1).getSchoolBoard().addProfessor(PawnColor.BLUE); //Paolo has the BLUE professor

        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.getIslands().get(0).addStudent(PawnColor.RED);
        game.moveMotherNature(game.getIslands().get(0));
        game.getIslands().get(1).addStudent(PawnColor.BLUE);
        game.getIslands().get(1).addStudent(PawnColor.BLUE);
        game.moveMotherNature(game.getIslands().get(1));
        //same number of towers built-->draw
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getNumTowers(),7);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getNumTowers(),7);

        game.checkWinner();

        assertEquals(game.getPlayers().get(0).getStatus(),PlayerStatus.WINNER); //Teo wins because has more professors
        assertEquals(game.getPlayers().get(1).getStatus(),PlayerStatus.WAITING);

    }

}