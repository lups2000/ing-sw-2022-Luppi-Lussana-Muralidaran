package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Model.Game;
import org.junit.jupiter.api.Test;

class CliTest {


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
        cli.showGameBoard(game.getIslands(),game.getCloudTiles(),game.getPlayers());
    }
}