package it.polimi.ingsw.Model.Exceptions;

public class NoNoEntryTilesAvailableException extends Exception{

    @Override
    public String getMessage() {
        return ("There are no available tiles at the moment!");
    }
}
