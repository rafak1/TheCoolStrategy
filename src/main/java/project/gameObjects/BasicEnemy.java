package project.gameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import project.Level;
import project.MainVariables;
import project.Player;

import java.util.Objects;

public abstract class BasicEnemy implements Enemy {
    boolean deployed;
    Level currLevel;
    Pair<Integer, Integer> cords;
    int pathIndex;
    Image enemySprite;
    ImageView enemyImageView;
    public int health;
    int moneyGiven = 10;
    public int damage = 1;
    String imageUrl = "/images/BasicEnemy.png";


    /**
     * Creates an enemy at a given level
     *
     * @param level in question
     */
    public BasicEnemy(Level level) {
        deployed = false;
        currLevel = level;
        cords = new Pair<>(level.startX, level.startY);
        pathIndex = 0;
        enemySprite = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY / 10, MainVariables.sizeY / 10, true, true);
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
    public void moveEnemy() {
        if (pathIndex >= currLevel.path.size()) return;
        cords = currLevel.path.get(pathIndex++);
    }

    void deleteEnemy(int a) {
        Player.changePlayerMoney(a);
        enemyImageView.setImage(null);
        deployed = false;
    }

    /**
     * Deletes enemy's imageView
     */
    public void kill() {
        this.deleteEnemy(moneyGiven);
    }

    public boolean isDeployed() {
        return deployed;
    }

    public void SetDeployed() {
        deployed = true;
    }

    public Pair<Integer, Integer> getCords() {
        return cords;
    }

    public Image getEnemySprite() {
        return enemySprite;
    }

    ;

    public ImageView getEnemyImageView() {
        return enemyImageView;
    }

    ;

    public void setEnemyImageView(ImageView a) {
        enemyImageView = a;
    }

    ;

    public int getEnemyDamage() {
        return damage;
    }
}
