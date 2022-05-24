package project.gameObjects.Enemies;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import project.MainVariables;

import java.util.Objects;

public class BigEnemy extends BasicEnemy {

    public BigEnemy()
    {
        x=new SimpleDoubleProperty();
        y=new SimpleDoubleProperty();
        deployed=false;
        pathIndex=0;
        damage=5;
        health=20;
        moneyGiven=10;
        isDead=false;
        imageUrl="/images/BigEnemy.png";
        enemySprite=new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY/10, MainVariables.sizeY/10, true, true);
    }

}
