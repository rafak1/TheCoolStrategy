package project.gameObjects;

import javafx.scene.image.Image;
import javafx.util.Pair;
import project.Level;

import java.util.Objects;

public class BasicEnemy {
    public boolean isDeployed;
    Level currLevel;
    public Pair<Integer,Integer> coords;
    int pathIndex;
    public Image enemySprite;


    /**
     *  Creates an enemy at a given level
     * @param level in question
     */
    BasicEnemy(Level level){
        isDeployed = false;
        currLevel =  level;
        coords = new Pair<>(level.startX, level.startY);
        pathIndex=0;
        enemySprite=new Image(Objects.requireNonNull(getClass().getResource("/images/BasicEnemy.png")).toString(), 50, 50, true, true);
    }

    /**
     * Move enemy to next field
     */
    public void moveEnemy(){
        coords = currLevel.path.get(pathIndex++);
    }

}
