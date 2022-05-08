package project.gameObjects.Enemies;

public class SmallEnemy extends BasicEnemy {
    static {
        Enemy.enemyId.put(1, BigEnemy.class);
    }

    public SmallEnemy() {
        super();
    }
}
