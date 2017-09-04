package shamboard;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public final class MediaManager {
    private static final MediaManager INSTANCE = new MediaManager();
    private MediaPlayer mediaPlayer;

    public static MediaManager getInstance() {
        return INSTANCE;
    }

    public void play(String songName) {
        boolean playing = mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);

        if (playing) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(mediaPlayer.volumeProperty(), 0)));
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }
            });
            timeline.play();
        }

        songName = "src/media/" + songName;
        Media song = new Media(new File(songName).toURI().toString());
        mediaPlayer = new MediaPlayer(song);
        mediaPlayer.play();
    }
}
