package shamboard;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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

    private void stopCurrentTrack() {
        mediaPlayer.stop();
        mediaPlayer.dispose();
    }

    private void playNewTrack(String songName) {
        songName = "src/media/" + songName;
        Media song = new Media(new File(songName).toURI().toString());
        mediaPlayer = new MediaPlayer(song);
        mediaPlayer.play();
    }

    public void play(String songName) {
        boolean playing = mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);

        if (playing) {
            if (SystemVars.getInstance().fade) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(mediaPlayer.volumeProperty(), 0)));

                timeline.setOnFinished(event -> {
                    stopCurrentTrack();
                    playNewTrack(songName);
                });
                timeline.play();
            } else {
                stopCurrentTrack();
                playNewTrack(songName);
            }

        } else {
            playNewTrack(songName);
        }

    }
}
