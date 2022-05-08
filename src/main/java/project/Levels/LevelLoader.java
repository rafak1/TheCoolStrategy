package project.Levels;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Pair;
import project.gameObjects.Enemies.Enemy;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static project.MainVariables.*;

class NoSuchLevelException extends Throwable {
}

public class LevelLoader {
    int[][] levelObjects;        //can be deleted and replaced only with path
    ArrayList<Pair<Integer, Integer>> path = new ArrayList<>();
    int startY;
    int startX;
    Queue<Queue<Enemy>> enemies = new LinkedList<>();
    int waves;
    Path enemyPath;

    public LevelLoader load(int k) throws NoSuchLevelException, IOException {
        levelObjects = new int[gridSizeX][gridSizeY];
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                levelObjects[i][j] = 0;
            }
        }
        FileReader fileReader;
        try {
            fileReader = new FileReader(("\\Level" + k + ".txt"));
        } catch (Throwable a) {
            throw new NoSuchLevelException();
        }
        //read from level file
        pathLength = fileReader.read();
        passiveIncome = fileReader.read();
        int x = fileReader.read();
        for (int i = 0; i < x; i++) {
            path.add(new Pair<>(fileReader.read(), fileReader.read()));
            levelObjects[path.get(path.size() - 1).getKey()][path.get(path.size() - 1).getValue()] = 1;
        }
        waves = fileReader.read();
        for (int i = 0; i < waves; i++) {
            LinkedList<Enemy> curr = new LinkedList<>();
            int y = fileReader.read();
            for (int j = 0; j < y; j++) {
                int z = fileReader.read();
                if (z == 0) {
                    curr.add(null);
                    continue;
                }
                Class<? extends Enemy> toAddClass = Enemy.enemyId.get(z);
                Enemy toAdd = null;
                try {
                    toAdd = toAddClass.getConstructor().newInstance();
                } catch (Throwable ignore) {
                }
                curr.add(toAdd);
            }
            enemies.add(curr);
        }
        createPath();
        return this;
    }

    void createPath() {
        enemyPath = new Path();
        Pair<Integer, Integer> curr = path.get(0);
        Pair<Integer, Integer> next;
        enemyPath.getElements().add(new MoveTo(curr.getKey() * gridSize + (double) gridSize / 2, -(double) gridSize / 2));
        int path_i = 1;
        boolean isXTheSame;
        curr = path.get(path_i++);
        while (path_i < path.size()) {
            next = path.get(path_i++);
            isXTheSame = curr.getKey().equals(next.getKey());
            while ((isXTheSame && curr.getKey().equals(next.getKey())) || (!isXTheSame && curr.getValue().equals(next.getValue()))) {
                next = path.get(path_i++);
            }
            enemyPath.getElements().add(new LineTo(next.getKey() * gridSize + (double) gridSize / 2, next.getValue() * gridSize + (double) gridSize / 2));
            curr = next;
        }
    }

    /**
     * Should be called after loading a level
     *
     * @return a path for enemies
     */
    public Path getEnemyPath() {
        return enemyPath;
    }

    public int[][] getLevelObjects() {
        return levelObjects;
    }
}
