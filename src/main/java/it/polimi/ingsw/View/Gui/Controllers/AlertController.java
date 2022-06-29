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

    @FXML
    public void initialize() {
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onRootPaneMousePressed);
        rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onRootPaneMouseDragged);
        okBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onOkBtnClick);
    }


    private void onRootPaneMousePressed(MouseEvent event) {
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }

    private void onRootPaneMouseDragged(MouseEvent event) {
        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }


    private void onOkBtnClick(MouseEvent event) {
        stage.close();
    }

    public void setAlertTitle(String str) {
        titleLbl.setText(str);
    }


    public void setAlertMessage(String str) {
        messageLbl.setText(str);
    }

    public void displayAlert() {
        stage.showAndWait();
    }


    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

}