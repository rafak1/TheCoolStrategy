package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;
import project.Settings;

import java.util.Objects;

public class BigBigEnemy extends BasicEnemy {
    public BigBigEnemy(int wave) {
        super(wave);
        pathIndex = 0;
        damage = 50;
        enemySpeed = 2.0;
        if (Settings.difficultyMultiplier == 2.0) health = (int) (150 * 1.7 * waveMultiplier);
        else health = (int) (150 * Settings.difficultyMultiplier * waveMultiplier);
        moneyGiven = 80;
        imageUrl = "/images/gameObjects/BigBigEnemy.png";
        enemySprite = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY / 8, MainVariables.sizeY / 8, true, true);
    }
}
