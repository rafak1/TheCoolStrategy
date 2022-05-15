package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;

import java.util.Objects;

public class BigEnemy extends BasicEnemy {

    int damage = 5;
    int health = 10;
    int moneyGiven = 30;
    String imageUrl = "/images/BigEnemy.png";

    public BigEnemy() {
        super();
        enemySprite = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY / 10, MainVariables.sizeY / 10, true, true);
    }

    @Override
    public int getEnemyDamage() {
        return damage;
    }

    @Override
    public void kill() {
        this.deleteEnemy(moneyGiven);
    }
}
