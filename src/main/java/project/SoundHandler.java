package project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundHandler {
    public static MediaPlayer musicPlayer;

    /**
     * Starts playing music
     *
     * @param volume volume at start
     */
    public void startMusic(double volume) {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource("/music/inTheHouse.mp3")).toString());
        musicPlayer = new MediaPlayer(sound);
        musicPlayer.setVolume(volume);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
    }
}
