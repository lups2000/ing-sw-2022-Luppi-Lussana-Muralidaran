package it.polimi.ingsw.Model;
/**
 * @author Matteo Luppi, Pradeeban Muralidaran
 */
public enum ColorTower {
    WHITE(Colors.WHITE_TOWER,"White"),GRAY(Colors.GRAY_TOWER,"Gray"),BLACK(Colors.BLACK_TOWER,"Black");

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
