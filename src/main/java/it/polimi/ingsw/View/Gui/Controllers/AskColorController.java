package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.observer.Observable4View;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.Map;

public class AskColorController extends Observable4View implements  GuiGenericController {

    private final Stage stage;

    private double xOffset;
    private double yOffset;
    private Map<PawnColor, Integer> availableStudents;

    @FXML
    private BorderPane rootPane;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private Button confirmButton;

    /**
     * Default constructor.
     */
    public AskColorController() {
        stage = new Stage();
        stage.initOwner(GuiMainController.getCurrentScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        xOffset = 0;
        yOffset = 0;
        availableStudents=new HashMap<>();
    }

    /**
     * Sets the number of available students
     *
     * @param availableStudents is the new number of available students
     */
    public void setAvailableStudents(Map<PawnColor, Integer> availableStudents) {
        this.availableStudents = availableStudents;
    }

    /**
     * It initializes the display and starts adds the event handler for mouse click and drag
     */
    @FXML
    public void initialize() {

        initialDisplay();

        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onRootPaneMousePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onRootPaneMouseDragged);

        confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConfirmButtonClick);
    }

    /**
     * Shows the stage and waits
     */
    public void display() {
        stage.showAndWait();
    }

    /**
     * Gets coordinates when mouse clicked
     *
     * @param event where the coordinates are get
     */
    private void onRootPaneMousePressed(MouseEvent event) {
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    /**
     * Gets coordinates when mouse dragged
     *
     * @param event where the coordinates are get
     */
    private void onRootPaneMouseDragged(MouseEvent event) {
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    /**
     * Controls which pawn color has been selected
     *
     * @param event which triggers this method
     */
    private void onConfirmButtonClick(Event event){
        String choice = choiceBox.getValue().toString();
        PawnColor pawnColor = null;
        switch (choice) {
            case "red" -> pawnColor = PawnColor.RED;
            case "blue" -> pawnColor = PawnColor.BLUE;
            case "green" -> pawnColor = PawnColor.GREEN;
            case "yellow" -> pawnColor = PawnColor.YELLOW;
            case "pink" -> pawnColor = PawnColor.PINK;
        }

        PawnColor finalPawnColor = pawnColor;
        new Thread(()->notifyObserver(obs->obs.sendChosenColor(finalPawnColor))).start();

        stage.close();
    }

    /**
     * Sets the scene on screen
     *
     * @param scene contains the elements to display on screen
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Displays the scene
     */
    private void initialDisplay(){

        for(PawnColor pawnColor : availableStudents.keySet()){
            int temp=0;
            String color=null;
            ObservableList<String> list = choiceBox.getItems();
            while (temp<availableStudents.get(pawnColor)){
                switch (pawnColor){
                    case RED -> color="red";
                    case BLUE -> color="blue";
                    case GREEN -> color="green";
                    case YELLOW -> color="yellow";
                    case PINK -> color="pink";
                }
                list.add(color);
                temp++;
            }
        }
    }
}
