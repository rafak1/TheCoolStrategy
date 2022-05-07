package project.gameObjects.Enemies;

import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.Level;
import project.MainVariables;
import project.Player;

import java.util.Objects;

public abstract class BasicEnemy implements Enemy {
    boolean deployed;
    Level currLevel;
    DoubleProperty x;
    DoubleProperty y;
    int pathIndex;
    Image enemySprite;
    ImageView enemyImageView;
    PathTransition pathTransition;
    public int health;
    int moneyGiven = 10;
    public int damage = 1;
    Boolean isDead = true;
    String imageUrl = "/images/BasicEnemy.png";


    /**
     * Creates an enemy at a given level
     *
     * @param level in question
     */
    public BasicEnemy(Level level) {
        x = new SimpleDoubleProperty();
        y = new SimpleDoubleProperty();
        deployed = false;
        currLevel = level;
        pathIndex = 0;
        enemySprite = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY / 10, MainVariables.sizeY / 10, true, true);
    }

    /**
     * Damages an enemy by a given amount
     * @param value damage value
     */
    public void damageEnemy(int value) {
        health -= value;
        if (health <= 0) {
            this.kill();
        }
    }

    public PathTransition getPathTransition() {
        return pathTransition;
    }

    public void setPathTransition(PathTransition a) {
        pathTransition = a;
    }

    void deleteEnemy(int a) {
        isDead = true;
        Player.changePlayerMoney(a);
        if (enemyImageView != null) enemyImageView.setImage(null);
        deployed = false;
        if (pathTransition != null) pathTransition.stop();
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

    public double getX() {
        return x.get();
    }

    public double getY() {
        return y.get();
    }

    public Image getEnemySprite() {
        return enemySprite;
    }

    public ImageView getEnemyImageView() {
        return enemyImageView;
    }

    public void setEnemyImageView(ImageView a) {
        enemyImageView = a;
        x.bind(a.translateXProperty());
        y.bind(a.translateYProperty());
    }

    ;

    public int getEnemyDamage() {
        return damage;
    }

    public boolean isKilled() {
        return isDead;
    }
}
