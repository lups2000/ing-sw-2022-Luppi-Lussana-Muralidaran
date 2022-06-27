package it.polimi.ingsw.View.Gui.Controllers;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.Gui.Scenes.ErrorAlert;
import it.polimi.ingsw.observer.Observable4View;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 * this controller is used for the scene when the user insert ip address and port number to connect to the server
 */
public class LoginController extends Observable4View implements GuiGenericController {

    @FXML
    private Parent pane;

    @FXML
    public TextField ipAddressField;

    @FXML
    private TextField portNumberField;

    @FXML
    private Button connectionButton;

    @FXML
    public void initialize() {
        connectionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConnectionButtonClick);
    }

    private void onConnectionButtonClick (Event event){
        String ipAddress = ipAddressField.getText();
        String portNumber = portNumberField.getText();

        boolean okIp = ClientController.okIpAddress(ipAddress);
        boolean okPort = ClientController.okPortNumber(portNumber);

        if(okIp && okPort){
            connectionButton.setDisable(true);
            new Thread(() -> notifyObserver(obs -> obs.connectClientToServer(ipAddress,portNumber))).start();
        }
        else{
            GuiMainController.showAlert("Error","Address or Port invalid!");
        }


    }

}
