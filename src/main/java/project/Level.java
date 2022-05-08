package project;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Pair;
import project.gameObjects.Enemies.BigEnemy;
import project.gameObjects.Enemies.Enemy;
import project.gameObjects.Enemies.SmallEnemy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static project.MainVariables.*;

public class Level
{
    public int[][] levelObjects;        //can be deleted and replaced only with path
    public ArrayList <Pair <Integer, Integer>> path=new ArrayList <>();
    public int startY;
    public int startX;
    public Queue<Enemy> enemies = new LinkedList<>();
    public Path enemyPath;

    public Level(int k)
    {
        //this is temporary, it fills grid with random values. In the future this data will be read from a file
        levelObjects = new int[gridSizeX][gridSizeY];
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                levelObjects[i][j] = 0;
            }
        }
        startX = 2;
        startY = 0;
        enemies.add(new SmallEnemy());
        enemies.add(new SmallEnemy());
        enemies.add(new BigEnemy());
        enemies.add(null);
        enemies.add(null);
        enemies.add(new SmallEnemy());
        enemies.add(new SmallEnemy());
        enemies.add(new SmallEnemy());
        enemies.add(null);
        enemies.add(null);
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(null);
        enemies.add(null);
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(null);
        enemies.add(null);
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());
        enemies.add(new BigEnemy());

        //path
        enemyPath = new Path();
        enemyPath.getElements().add(new MoveTo(startX * gridSize + (double) gridSize / 2, -(double) gridSize / 2));
        enemyPath.getElements().add(new LineTo(2 * gridSize + (double) gridSize / 2, 2 * gridSize + (double) gridSize / 2));
        enemyPath.getElements().add(new LineTo(5 * gridSize + (double) gridSize / 2, 2 * gridSize + (double) gridSize / 2));
        enemyPath.getElements().add(new LineTo(5 * gridSize + (double) gridSize / 2, 10 * gridSize + (double) gridSize / 2));
        levelObjects[2][0] = 1;
        levelObjects[2][1] = 2;
        levelObjects[2][2] = 3;
        levelObjects[3][2] = 4;
        levelObjects[4][2] = 5;
        levelObjects[5][2] = 6;
        levelObjects[5][3] = 7;
        levelObjects[5][4] = 8;
        levelObjects[5][5] = 9;
        levelObjects[5][6] = 10;
        levelObjects[5][7]=11;
        levelObjects[5][8]=12;
        levelObjects[5][9]=13;

    }
}
