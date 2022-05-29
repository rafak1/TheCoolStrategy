package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;
import project.Settings;

import java.util.Objects;

public class FastEnemy extends BasicEnemy {
    public FastEnemy(int wave) {
        super(wave);
        enemySpeed = 0.5;
        damage = 5;
        health = (int) (7 * Settings.difficultyMultiplier * waveMultiplier);
        moneyGiven = 12;
        imageUrl = "/images/gameObjects/FastEnemy.png";
        enemySprite = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY / 10, MainVariables.sizeY / 10, true, true);
    }
}
