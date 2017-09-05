package shamboard;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {

    public Button btnFade;
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

    public void toggleFade(ActionEvent actionEvent) {
        if (SystemVars.getInstance().fade) {
            btnFade.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
        } else {
            btnFade.setStyle("-fx-background-color: green");
        }
        SystemVars.getInstance().fade = !SystemVars.getInstance().fade;
    }
}
