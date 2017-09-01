package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {

    public void playSound(ActionEvent actionEvent) {
        Button buttonPressed = (Button) actionEvent.getSource();
        buttonPressed.setStyle("-fx-background-color: green");
    }
}
