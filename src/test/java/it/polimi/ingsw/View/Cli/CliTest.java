package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.SchoolBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CliTest {

    @Test
    void showSchoolBoard() {
        Cli cli =new Cli();
        Game game=new Game();
        game.addPlayer("Lups");
        game.addPlayer("Paolo");
        game.initGame(2,true);
        cli.showSchoolBoard(game.getPlayers().get(0).getSchoolBoard());
    }

    @Test
    void showIslands(){
        Cli cli =new Cli();
        Game game=new Game();
        game.addPlayer("Lups");
        game.addPlayer("Paolo");
        game.initGame(2,true);
        cli.showIslands(game.getIslands());
    }

    @Test
    void showGameBoard(){
        Cli cli =new Cli();
        Game game=new Game();
        game.addPlayer("Lups");
        game.addPlayer("Paolo");
        game.initGame(2,true);
        cli.showGameBoard(game.getIslands(),game.getPlayers());
    }
}