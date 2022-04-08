package it.polimi.ingsw.Model;

public class NoTowersException extends Exception{

    @Override
    public String getMessage() {
        return ("Error: No towers are present");
    }
}
