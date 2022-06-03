package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;
import project.Settings;

import java.util.Objects;

public class BigEnemy extends BasicEnemy {

    public BigEnemy(int wave) {
        super(wave);
        pathIndex = 0;
        damage = 5;
        if (Settings.difficultyMultiplier == 2.0) health = (int) (20 * 1.7 * waveMultiplier);
        else health = (int) (20 * Settings.difficultyMultiplier * waveMultiplier);
        moneyGiven = 7;
        imageUrl = "/images/gameObjects/BigEnemy.png";
        enemySprite = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY / 9, MainVariables.sizeY / 10, true, true);
    }

}
