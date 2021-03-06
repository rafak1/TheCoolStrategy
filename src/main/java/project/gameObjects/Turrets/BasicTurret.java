package project.gameObjects.Turrets;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import project.MessagesAndEffects;
import project.gameObjects.Enemies.Enemy;

import java.util.Objects;

import static project.GameMaster.*;
import static project.MainVariables.gridSizeY;
import static project.MainVariables.sizeY;

public class BasicTurret {
    Integer X;
    Integer Y;
    double radius;
    Integer damage;
    long rateOfFire;
    RotateTransition rt;
    Node turretImage;
    Circle turretRadius;
    Enemy target;
    Rotate rotate;
    boolean isIdle;
    Media sound;
    MediaPlayer musicPlayer;

    /**
     * Draws a turret on the board
     *
     * @param imgSrc path to the image
     */
    public Node drawTurret(String imgSrc)
    {
        Image turretImage=new Image(Objects.requireNonNull(getClass().getResource(imgSrc)).toString(), sizeY/10, sizeY/10, true, true);
        board[X][Y]=new ImageView(turretImage);
        grid.add(board[X][Y], X, Y, 1, 1);
        return board[X][Y];
    }

    public double getX() {
        return X * sizeY / 10;
    }

    public double getY() {
        return Y * sizeY / 10;
    }

    /**
     * Rotates turret
     */
    void idle() {
        Platform.runLater(() -> {
            rt.stop();
            rt.setDuration(Duration.millis(5000));
            rt.setNode(turretImage);
            rt.setCycleCount(Animation.INDEFINITE);
            rt.setToAngle(turretImage.getRotate() + 360);
            rt.play();
        });
    }

    void followAnEnemy() {
        Platform.runLater(() -> {
            rt.stop();
            rt.setDuration(Duration.millis(100));
            rt.setCycleCount(1);
            double angle = Math.toDegrees(Math.atan2(turretRadius.getLayoutY() - 50 - target.getY(), target.getX() + 50 - turretRadius.getLayoutX()));
            angle = 90 - angle;
            if (angle < 0) {
                angle += 360;
            }
            turretImage.setRotate(turretImage.getRotate() % 360);
            if (turretImage.getRotate() - angle > 180) {
                rt.setToAngle(360 + angle);
            } else if (turretImage.getRotate() - angle < -180) {
                rt.setToAngle(angle - 360);
            } else {
                rt.setToAngle(angle);
            }
            rt.setAutoReverse(false);
            rt.play();
        });
    }

    void createRadius() {
        Circle circle = new Circle();
        circle.setLayoutX(X * sizeY / gridSizeY + 50);
        circle.setLayoutY(Y * sizeY / gridSizeY + 50);
        circle.setRadius(radius);
        circle.setOpacity(0.05);
        circle.setMouseTransparent(true);
        turretRadius = circle;
        masterRoot.getChildren().add(turretRadius);
    }

    void fightLogic() {
        while (gameState == 1) {
            if (!findTarget()) {
                if (!isIdle) {
                    stopFireSound();
                    idle();
                }
                isIdle = true;
            } else {
                isIdle = false;
                followAnEnemy();
                playFireSound();
                shootAnimation();
                synchronized (target) {
                    if (target != null) {
                        target.damageEnemy(damage);
                    }
                }
            }
            try
            {
                Thread.sleep(rateOfFire);
            }catch(InterruptedException ignored)
            {
            }
        }
    }

    public void stopFireSound()
    {
        if(musicPlayer!=null)
        {musicPlayer.stop();}
    }

    private void playFireSound()
    {
        if(musicPlayer!=null)
        {musicPlayer.play();}
    }

    void shootAnimation()
    {
        MessagesAndEffects.showEffect("/images/gameObjects/explosion.png", turretRadius.getLayoutX()-50, turretRadius.getLayoutY()-50, 0.2, masterRoot);
    }

    boolean findTarget()
    {
        synchronized(currWave)
        {
            double minimum=0;
            Enemy curr=null;
            for(Enemy e: currWave)//TODO: SYNCHRONIZED ENEMIES
            {
                if (e != null && e.isDeployed()) {
                    if (inRange(e)) {
                        double a = e.getPathTransition().getCurrentTime().toMillis() / e.getPathTransition().getDuration().toMillis();
                        if (a > minimum) {
                            curr = e;
                            minimum = a;
                        }
                    }
                }
            }
            if (curr != null) {
                target = curr;
                return true;
            }
            target = null;
            return false;
        }
    }

    boolean inRange(Enemy e) {
        double x = turretRadius.getLayoutX() - e.getX() - 50;
        double y = turretRadius.getLayoutY() - e.getY() - 50;
        return x * x + y * y < radius * radius;
    }
}
