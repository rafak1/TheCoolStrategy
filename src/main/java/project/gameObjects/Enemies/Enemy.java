package project.gameObjects.Enemies;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface Enemy {
    void damageEnemy(int value);

    //void moveEnemy();

    void kill();

    boolean isDeployed();

    void SetDeployed();

    double getX();

    double getY();

    Image getEnemySprite();

    ImageView getEnemyImageView();

    void setEnemyImageView(ImageView a);

    int getEnemyDamage();

    PathTransition getPathTransition();

    void setPathTransition(PathTransition a);

    boolean isKilled();
}
