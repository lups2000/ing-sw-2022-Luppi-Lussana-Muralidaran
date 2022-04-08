package it.polimi.ingsw.Model;

public class NoPawnPresentException extends Exception{

    @Override
    public String getMessage() {
        return ("Error: Pawn not present");
    }
}
