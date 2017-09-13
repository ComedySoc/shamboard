package shamboard;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;

public class Controller {

    public Button btnFade;
    private MediaManager mediaManager = MediaManager.getInstance();
    private HashMap<String, File> selectedFiles = new HashMap<String, File>();

    public void playSound(ActionEvent actionEvent) {
        Button buttonPressed = (Button) actionEvent.getSource();
        buttonPressed.setStyle("-fx-background-color: green");
    }

    public void playRunOffMusic(ActionEvent actionEvent) {
        Button buttonPressed = (Button) actionEvent.getSource();
        mediaManager.currentButton = buttonPressed;

        String songName = "midnight-gerlactic.mp3";
        mediaManager.play(songName);
    }

    public void toggleFade() {
        if (SystemVars.getInstance().fade) {
            btnFade.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
        } else {
            btnFade.setStyle("-fx-background-color: green");
        }
        SystemVars.getInstance().fade = !SystemVars.getInstance().fade;
    }

    public void stop() {
        mediaManager.stop();
    }

    private Button generateFileChooserButton(String fieldName) {
        Button btn = new Button("...");
        btn.setOnAction((ActionEvent event) -> {
            FileChooser fc = new FileChooser();
            selectedFiles.put(fieldName, fc.showOpenDialog(null));
        });

        return btn;
    }

    public void openOptionsDialog(MouseEvent mouseEvent) {
        // Create the custom dialog.
        Dialog<HashMap<String, File>> dialog = new Dialog<>();
        dialog.setTitle("Options");
        dialog.setHeaderText("Choose the locations of the files:");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Button btnHouseDir = generateFileChooserButton("House music");
        Button btnRunOnFile = generateFileChooserButton("Run ON Music");
        Button btnRunOffFile = generateFileChooserButton("Run off Music");

        grid.add(new Label("House Music"), 0, 0);
        grid.add(new Label(selectedFiles.containsKey("House Music") ? selectedFiles.get("House Music").getAbsolutePath() : ""), 1, 0);
        grid.add(btnHouseDir, 2, 0);

        grid.add(new Label("Run On Music"), 0, 1);
        grid.add(new Label(selectedFiles.containsKey("Run On Music") ? selectedFiles.get("Run On Music").getAbsolutePath() : ""), 1, 1);
        grid.add(btnRunOnFile, 2, 1);

        Label lblRunOffLocation = new Label("");
//        lblRunOffLocation.textProperty().bind((SimpleStringProperty) selectedFiles.get("Run Off Music").getAbsolutePath());

        grid.add(new Label("Run Off Music"), 0, 2);
//        grid.add(new Label(selectedFiles.containsKey("Run Off Music") ? selectedFiles.get("Run Off Music").getAbsolutePath() : ""), 1, 2);
        grid.add(lblRunOffLocation, 1, 2);
        grid.add(btnRunOffFile, 2, 2);

        dialog.getDialogPane().setContent(grid);

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == loginButtonType) {
                return selectedFiles;
            }
            return null;
        });

        Optional<HashMap<String, File>> result = dialog.showAndWait();

        result.ifPresent(hashMap -> {
            hashMap.forEach((fieldName, value) -> {
                System.out.println(fieldName + ": " + value);
                selectedFiles = hashMap;
            });
        });
    }
}
