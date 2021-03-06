package project.gameObjects.Enemies;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import project.MessagesAndEffects;
import project.Player;
import project.Settings;

import java.util.Objects;

import static project.GameMaster.masterRoot;
import static project.MainVariables.effectsSound;

public abstract class BasicEnemy implements Enemy {
    static {    //add all enemies and their ids here
        //System.out.println("enemies - loaded");
        Enemy.enemyId.put(1, SmallEnemy.class);
        Enemy.enemyId.put(2, BigEnemy.class);
        Enemy.enemyId.put(3, FastEnemy.class);
        Enemy.enemyId.put(4, BigFastEnemy.class);
        Enemy.enemyId.put(5, BigBigEnemy.class);
        Enemy.enemyId.put(6, BigBigBigEnemy.class);
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
    double waveMultiplier;
    int animationSpeed;
    public int health=(int)(10*Settings.difficultyMultiplier);

    int moneyGiven;
    public int damage;
    Boolean isDead;
    String imageUrl;
    ScaleTransition animation;
    Media sound;
    MediaPlayer musicPlayer;

    public BasicEnemy(int wave)
    {
        animationSpeed=300;
        waveMultiplier=1+((double)wave)/2;
        health*=waveMultiplier;
        isDead=false;
        deployed=false;
        x=new SimpleDoubleProperty();
        y=new SimpleDoubleProperty();
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
        animationSpeed*= 1+2/pathTransition.getDuration().toMillis();
    }


    /**
     * Deletes enemy's imageView
     */
    public void kill()
    {
        sound=new Media(Objects.requireNonNull(getClass().getResource("/music/death.mp3")).toString());
        musicPlayer=new MediaPlayer(sound);
        musicPlayer.setVolume(effectsSound);
        if(musicPlayer!=null)
        {musicPlayer.play();}
        MessagesAndEffects.showEffect("/images/gameObjects/tombstone.png", getX(), getY(), 0.5, masterRoot);

        if(sequence!=null)
        {sequence.stop();}
        if(pathTransition!=null)
        {
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
        animation = new ScaleTransition();
        animation.setDuration(Duration.millis(50));
        animation.setToX(-enemyImageView.scaleXProperty().get());
        animation.setCycleCount(1);
        animation.setNode(enemyImageView);
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(animationSpeed),
                        event->{
                            animation.setToX(-enemyImageView.scaleXProperty().get());
                            animation.play();}
                )
        );
        Platform.runLater(()-> {
            timeline.setAutoReverse(true);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
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
