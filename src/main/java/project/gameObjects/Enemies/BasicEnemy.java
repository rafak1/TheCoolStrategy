package project.gameObjects.Enemies;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import project.Player;

public abstract class BasicEnemy implements Enemy {
    static {    //add all enemies and their ids here
        //System.out.println("enemies - loaded");
        Enemy.enemyId.put(1, SmallEnemy.class);
        Enemy.enemyId.put(2, BigEnemy.class);
    }

    boolean deployed;
    DoubleProperty x;
    DoubleProperty y;
    int pathIndex;
    Image enemySprite;
    ImageView enemyImageView;
    PathTransition pathTransition;
    public int health = 10;

    int moneyGiven;
    public int damage;
    Boolean isDead;
    String imageUrl;
    ScaleTransition animation;

    /**
     * Damages an enemy by a given amount
     *
     * @param value damage value
     */
    public void damageEnemy(int value) {
        if (!isDead) {
            health -= value;
            if (health <= 0) {
                isDead = true;
                this.kill();
            }
        }
    }

    public PathTransition getPathTransition()
    {
        return pathTransition;
    }

    public void setPathTransition(PathTransition a)
    {
        pathTransition=a;
    }


    /**
     * Deletes enemy's imageView
     */
    public void kill()
    {

        if(pathTransition!=null)
        {
            pathTransition.setOnFinished(null);
            pathTransition.stop();
        }
        isDead=true;
        //deployed=false;
        Player.changePlayerMoney(moneyGiven);
        if(enemyImageView!=null)
        {enemyImageView.setImage(null);}
    }

    /**
     * Starts walking animation
     * Should be called after setting imageView
     */
    public void startAnimation() {
        animation = new ScaleTransition();
        animation.setDuration(Duration.millis(10));
        animation.setToX(-1);
        animation.setAutoReverse(true);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setNode(this.getEnemyImageView());
        animation.play();
    }

    public boolean isDeployed() {
        return deployed;
    }

    public void SetDeployed(boolean a) {
        deployed = a;
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

    public int getEnemyDamage() {
        return damage;
    }

    public boolean isKilled() {
        return isDead;
    }
}
