package it.polimi.ingsw.Model;

/**
 * This enum class represents the colors for students and professors
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
