package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;
import project.Settings;

import java.util.Objects;

public class BigEnemy extends BasicEnemy {

    public BigEnemy()
    {
        super();
        pathIndex = 0;
        damage = 5;
        health = (int) (20 * Settings.difficultyMultiplier);
        moneyGiven = 10;
	    imageUrl="/images/gameObjects/BigEnemy.png";
	    enemySprite=new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY/10, MainVariables.sizeY/10, true, true);
    }

}
