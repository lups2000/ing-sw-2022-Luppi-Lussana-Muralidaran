package it.polimi.ingsw.Model;

public class NoTowersException extends Exception{

    @Override
    public String getMessage() {
        return ("No towers are present!");
    }
}
