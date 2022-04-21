package it.polimi.ingsw.Model.Exceptions;

public class NoNoEntryTilesException extends Exception{

    @Override
    public String getMessage() {
        return ("No entry tiles are finished");
    }
}
