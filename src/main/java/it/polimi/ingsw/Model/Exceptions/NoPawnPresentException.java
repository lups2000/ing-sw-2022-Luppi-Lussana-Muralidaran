package it.polimi.ingsw.Model.Exceptions;

public class NoPawnPresentException extends Exception{

    @Override
    public String getMessage() {
        return ("Pawn not present!");
    }
}
