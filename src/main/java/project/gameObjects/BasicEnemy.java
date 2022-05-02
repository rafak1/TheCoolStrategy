package project.gameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import project.Level;
import project.MainVariables;
import project.Player;

import java.util.Objects;

public class BasicEnemy {
    public boolean isDeployed;
    Level currLevel;
    public Pair<Integer, Integer> cords;
    int pathIndex;
    public Image enemySprite;
    public ImageView enemyImageView;
    public int health;
    int moneyGiven = 10;
    public int damage = 1;


    /**
     * Creates an enemy at a given level
     *
     * @param level in question
     */
    public BasicEnemy(Level level) {
        isDeployed = false;
        currLevel = level;
        cords = new Pair<>(level.startX, level.startY);
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
            this.kill();
        }
    }
    /**
     * Move enemy to next field
     */
    public void moveEnemy(){
        if(pathIndex >=currLevel.path.size()) return;
        cords = currLevel.path.get(pathIndex++);
    }

    /**
     * Deletes enemy's imageView
     */
    public void kill(){
        Player.money += moneyGiven;
        enemyImageView.setImage(null);
        isDeployed = false;
    }
}
