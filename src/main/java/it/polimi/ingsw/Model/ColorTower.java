package it.polimi.ingsw.Model;
/**
 * @author Matteo Luppi, Pradeeban Muralidaran
 */
public enum ColorTower {
    WHITE(Colors.WHITE_TOWER),GRAY(Colors.GRAY_TOWER),BLACK(Colors.BLACK_TOWER);

    private final String visualColor;

    ColorTower(String visualColor) {
        this.visualColor=visualColor;
    }

    public String getVisualColor() {
        return visualColor;
    }
}
