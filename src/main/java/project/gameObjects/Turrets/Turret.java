package project.gameObjects.Turrets;

import javafx.scene.Node;
import project.gameObjects.GameObject;

public interface Turret extends GameObject {
    Node drawTurret(String imgSrc);

    void idle();

    void followAnEnemy();

    void createRadius();

    void fightLogic();

    void shootAnimation();

    boolean findTarget();
}

