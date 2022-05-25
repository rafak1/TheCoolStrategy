package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;

import java.util.Objects;

public class SmallEnemy extends BasicEnemy
{

    public SmallEnemy()
    {
        super();
        pathIndex = 0;
        damage=1;
        health=10;
        moneyGiven=5;
        imageUrl="/images/gameObjects/BasicEnemy.png";
        enemySprite=new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY/10, MainVariables.sizeY/10, true, true);
    }
}
