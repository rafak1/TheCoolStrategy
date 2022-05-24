package project.gameObjects.Enemies;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import project.MainVariables;

import java.util.Objects;

public class SmallEnemy extends BasicEnemy
{

    public SmallEnemy()
    {
        x=new SimpleDoubleProperty();
        y=new SimpleDoubleProperty();
        deployed=false;
        pathIndex=0;
        damage=1;
        health=10;
        moneyGiven=5;
        isDead=false;
        imageUrl="/images/BasicEnemy.png";
        enemySprite=new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY/10, MainVariables.sizeY/10, true, true);
    }
}
