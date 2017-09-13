package shamboard;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public final class MediaManager {
    private static final MediaManager INSTANCE = new MediaManager();
    private MediaPlayer mediaPlayer;

    public Button currentButton;

    public static MediaManager getInstance() {
        return INSTANCE;
    }

    private Timeline generateFadeTimeline() {
        return new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(mediaPlayer.volumeProperty(), 0)));
    }

    private void stopAndDispose() {
        mediaPlayer.stop();
        mediaPlayer.dispose();
    }

    private void playNewTrack(String songName) {
        songName = "src/media/" + songName;
        Media song = new Media(new File(songName).toURI().toString());
        mediaPlayer = new MediaPlayer(song);
        mediaPlayer.setOnStopped(() -> currentButton.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"));
        mediaPlayer.setOnPlaying(() -> currentButton.setStyle("-fx-background-color: green"));
        mediaPlayer.play();
    }

    public void stop() {
        if (SystemVars.getInstance().fade) {
            Timeline timeline = generateFadeTimeline();
            timeline.setOnFinished(event -> {
                stopAndDispose();
            });
            timeline.play();
        } else {
            stopAndDispose();
        }
    }

    public void play(String songName) {
        boolean playing = mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);

        if (playing) {
            if (SystemVars.getInstance().fade) {
                Timeline timeline = generateFadeTimeline();
                timeline.setOnFinished(event -> {
                    stopAndDispose();
                    playNewTrack(songName);
                });
                timeline.play();
            } else {
                stopAndDispose();
                playNewTrack(songName);
            }

        } else {
            playNewTrack(songName);
        }

    }
}
