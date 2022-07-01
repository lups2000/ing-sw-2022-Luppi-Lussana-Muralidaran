package it.polimi.ingsw.Model.Exceptions;

/**
 * This method sends a warning message when the NoEntryTiles are finished
  */


public class NoNoEntryTilesException extends Exception{

    @Override
    public String getMessage() {
        return ("No entry tiles are finished");
    }
}
