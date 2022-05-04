package project.gameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public interface Enemy {
    void damageEnemy(int value);

    void moveEnemy();

    void kill();

    boolean isDeployed();

    void SetDeployed();

    Pair<Integer, Integer> getCords();

    Image getEnemySprite();

    ImageView getEnemyImageView();

    void setEnemyImageView(ImageView a);

    int getEnemyDamage();
}
