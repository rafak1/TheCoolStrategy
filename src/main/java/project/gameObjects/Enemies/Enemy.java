package project.gameObjects.Enemies;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.gameObjects.GameObject;

import java.util.HashMap;

public interface Enemy extends GameObject {
    static HashMap<Integer, Class<? extends Enemy>> enemyId = new HashMap<>();

    void damageEnemy(int value);

    //void moveEnemy();

    void kill();

    boolean isDeployed();

    void SetDeployed(boolean a);

    public void startAnimation();

    double getX();

    double getY();

    Image getEnemySprite();

    ImageView getEnemyImageView();

    void setEnemyImageView(ImageView a);

    int getEnemyDamage();

    PathTransition getPathTransition();

    void setPathTransition(PathTransition a);

    boolean isKilled();

    double getEnemySpeed();
}
