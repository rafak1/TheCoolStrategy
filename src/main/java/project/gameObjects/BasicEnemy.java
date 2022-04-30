package project.gameObjects;

import javafx.scene.image.Image;
import javafx.util.Pair;
import project.Level;
import project.MainVariables;

import java.util.Objects;

public class BasicEnemy {
    public boolean isDeployed;
    Level currLevel;
    public Pair<Integer,Integer> coords;
    int pathIndex;
    public Image enemySprite;
    public int health;


    /**
     *  Creates an enemy at a given level
     * @param level in question
     */
    BasicEnemy(Level level){
        isDeployed = false;
        currLevel =  level;
        coords = new Pair<>(level.startX, level.startY);
        pathIndex=0;
        enemySprite=new Image(Objects.requireNonNull(getClass().getResource("/images/BasicEnemy.png")).toString(), MainVariables.sizeY/10, MainVariables.sizeY/10, true, true);
    }

    /**
     * Damages an enemy by a given amount
     * @param value damage value
     */
    public void damageEnemy(int value){
        health-=value;
        if(health<=0){
            //TODO DIE
        }
    }
    /**
     * Move enemy to next field
     */
    public void moveEnemy(){
        coords = currLevel.path.get(pathIndex++);
    }

}
