package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;
import project.Settings;

import java.util.Objects;

public class SmallEnemy extends BasicEnemy
{

    public SmallEnemy(int wave) {
        super(wave);
        pathIndex = 0;
        damage = 1;
        health = (int) (10 * Settings.difficultyMultiplier * waveMultiplier);
        moneyGiven = 3;
        imageUrl = "/images/gameObjects/BasicEnemy.png";
        enemySprite = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY / 10, MainVariables.sizeY / 10, true, true);
    }
}
