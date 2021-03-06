package it.polimi.ingsw.Model;
/**
 * This enum class represents the color of the towers.
 */
public enum ColorTower {
    WHITE(Colors.WHITE_TOWER,"white"),GRAY(Colors.GRAY_TOWER,"gray"),BLACK(Colors.BLACK_TOWER,"black");

    private final String visualColor;
    private final String nameColor;

    ColorTower(String visualColor,String nameColor) {
        this.visualColor=visualColor;
        this.nameColor=nameColor;
    }

    public String getVisualColor() {
        return visualColor;
    }
    public String getNameColor() {return nameColor;}
}
