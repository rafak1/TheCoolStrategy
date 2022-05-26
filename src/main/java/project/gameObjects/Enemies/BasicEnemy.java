package project.gameObjects.Enemies;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import project.MessagesAndEffects;
import project.Player;
import project.Settings;

import static project.GameMaster.masterRoot;

public abstract class BasicEnemy implements Enemy {
    static {    //add all enemies and their ids here
        //System.out.println("enemies - loaded");
        Enemy.enemyId.put(1, SmallEnemy.class);
        Enemy.enemyId.put(2, BigEnemy.class);
        Enemy.enemyId.put(3, FastEnemy.class);
        Enemy.enemyId.put(4, BigFastEnemy.class);
    }

    double enemySpeed = 1;
    boolean deployed;
    DoubleProperty x;
    DoubleProperty y;
    int pathIndex;
    Image enemySprite;
    ImageView enemyImageView;
    PathTransition pathTransition;
    Timeline sequence;
    public int health = (int) (10 * Settings.difficultyMultiplier);

    int moneyGiven;
    public int damage;
    Boolean isDead;
    String imageUrl;
    ScaleTransition animation;

    public BasicEnemy() {
        isDead = false;
        deployed = false;
        x = new SimpleDoubleProperty();
        y = new SimpleDoubleProperty();
    }

    /**
     * Damages an enemy by a given amount
     *
     * @param value damage value
     */
    public void damageEnemy(int value) {
        if (!isDead) {
            health -= value;
            if (health <= 0) {
                Player.changePlayerMoney(moneyGiven);
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
        MessagesAndEffects.showEffect("/images/gameObjects/tombstone.png", getX(), getY(), 0.5, masterRoot);

        if (sequence != null) sequence.stop();
        if (pathTransition != null) {
            pathTransition.setOnFinished(null);
            pathTransition.stop();
        }
        isDead=true;
        //deployed=false;
        if(enemyImageView!=null)
        {enemyImageView.setImage(null);}
    }

    /**
     * Starts walking animation
     * Should be called after setting imageView
     */
    public void startAnimation() {
        Platform.runLater(() -> {
            animation = new ScaleTransition();
            sequence = new Timeline();
            animation.setDuration(Duration.millis(5));
            animation.setToX(-1);
            animation.setAutoReverse(true);
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setNode(this.getEnemyImageView());
            //sequence.getKeyFrames().add(new KeyFrame( Duration.ZERO, event -> {System.out.println("started"); animation.play(); System.out.println("played"); new PauseTransition(Duration.millis(100));}));
            //sequence.setCycleCount(Animation.INDEFINITE);
            //sequence.play();  //TODO
            animation.play();
        });
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

    public double getEnemySpeed() {
        return enemySpeed;
    }
}
