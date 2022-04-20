package it.polimi.ingsw.Model.Exceptions;

public class NoTowersException extends Exception{

    @Override
    public String getMessage() {
        return ("No towers are present!");
    }
}
