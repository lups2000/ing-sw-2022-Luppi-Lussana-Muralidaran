package it.polimi.ingsw.Model;

/**
 * @author Matteo Luppi, Pradeeban Muralidaran
 */
public enum PawnColor {
    GREEN(Colors.GREEN_PAWN),RED(Colors.RED_PAWN),YELLOW(Colors.YELLOW_PAWN),PINK(Colors.PINK_PAWN),BLUE(Colors.BLUE_PAWN);

    private final String visualColor;

    PawnColor(String visualColor) {
        this.visualColor=visualColor;
    }

    public String getVisualColor() {
        return visualColor;
    }
}
