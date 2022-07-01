package it.polimi.ingsw.View.Gui.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertController implements GuiGenericController{


    private final Stage stage;

    private double xOffset;
    private double yOffset;

    @FXML
    private BorderPane rootPane;
    @FXML
    private Label titleLbl;
    @FXML
    private Label messageLbl;
    @FXML
    private Button okBtn;

    /**
     * Default constructor.
     */
    public AlertController() {
        stage = new Stage();
        stage.initOwner(GuiMainController.getCurrentScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        xOffset = 0;
        yOffset = 0;
    }

    /**
     * Initialize the event handler
     */
    @FXML
    public void initialize() {
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onRootPaneMousePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onRootPaneMouseDragged);
        okBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onOkBtnClick);
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
     * Closes the stage
     *
     * @param event which trigger the event
     */
    private void onOkBtnClick(MouseEvent event) {
        stage.close();
    }

    /**
     * Sets alert title
     *
     * @param str string which is printed as alert title
     */
    public void setAlertTitle(String str) {
        titleLbl.setText(str);
    }

    /**
     * Sets alert message
     *
     * @param str string which is printed as alert message
     */
    public void setAlertMessage(String str) {
        messageLbl.setText(str);
    }

    /**
     * Displays the alert
     */
    public void displayAlert() {
        stage.showAndWait();
    }

    /**
     * Sets the scene on screen
     *
     * @param scene contains the elements to display on screen
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

}
