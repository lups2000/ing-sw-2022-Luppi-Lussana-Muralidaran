package it.polimi.ingsw.Model;

public class TooManyPawnsPresent extends Exception{

    @Override
    public String getMessage() {
        return ("Too many pawns are present!");
    }
}
