package it.polimi.ingsw.Model;

public class TooManyTowersException extends Exception{


    @Override
    public String getMessage() {
        return ("Too many towers are present!");
    }
}