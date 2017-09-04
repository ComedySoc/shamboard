package shamboard;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {

    private MediaManager mediaManager = MediaManager.getInstance();

    public void playSound(ActionEvent actionEvent) {
        Button buttonPressed = (Button) actionEvent.getSource();
        buttonPressed.setStyle("-fx-background-color: green");
    }

    public void playRunOffMusic(ActionEvent actionEvent) {
        String songName = "midnight-gerlactic.mp3";
        mediaManager.play(songName);

        Button buttonPressed = (Button) actionEvent.getSource();
        buttonPressed.setStyle("-fx-background-color: green");
    }
}
