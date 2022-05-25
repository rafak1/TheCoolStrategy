package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;
import project.Settings;

import java.util.Objects;

public class BigFastEnemy extends BasicEnemy {
    public BigFastEnemy() {
        super();
        enemySpeed = 0.4;
        damage = 20;
        health = (int) (17 * Settings.difficultyMultiplier);
        moneyGiven = 25;
        imageUrl = "/images/gameObjects/BigFastEnemy.png";
        enemySprite = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY / 10, MainVariables.sizeY / 10, true, true);
    }
}
