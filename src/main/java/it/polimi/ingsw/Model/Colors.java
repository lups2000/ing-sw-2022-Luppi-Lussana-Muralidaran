package it.polimi.ingsw.Model;

/**
 * @author Pradeeban Muralidaran
 */

public class Colors {

    //Reset
    public static final String RESET = "\u001b[0m";

    //Colors for students and professors
    public static final String PINK_PAWN = "\033[38;2;255;255;255;48;2;255;0;255m";
    public static final String RED_PAWN = "\033[38;2;255;255;255;48;2;255;0;0m";
    public static final String BLUE_PAWN = "\033[38;2;255;255;255;48;2;0;0;255m";
    public static final String YELLOW_PAWN = "\033[38;2;255;255;255;48;2;0;204;0m";
    public static final String GREEN_PAWN = "\033[38;2;0;0;0;48;2;255;255;0m";

    //Colors for towers
    public static final String GRAY_TOWER = "\033[38;2;255;255;255;48;2;128;128;128m";
    public static final String WHITE_TOWER = "\033[38;2;0;0;0;48;2;255;255;255m";
    public static final String BLACK_TOWER = "\033[38;2;255;255;255;48;2;0;0;0m";

}
