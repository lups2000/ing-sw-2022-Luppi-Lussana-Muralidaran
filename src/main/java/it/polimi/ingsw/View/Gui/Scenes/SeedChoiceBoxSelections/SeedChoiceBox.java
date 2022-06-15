package it.polimi.ingsw.View.Gui.Scenes.SeedChoiceBoxSelections;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SeedChoiceBox implements Initializable {
    @FXML
    private ChoiceBox<String> seedChoice;

    private String[] deck = {"KING","SAMURAI","WITCH","MAGICIAN"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seedChoice.getItems().addAll(deck);
    }
}
