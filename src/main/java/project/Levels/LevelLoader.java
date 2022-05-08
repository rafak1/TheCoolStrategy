package project.Levels;

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
        enemyPath.getElements().add(new MoveTo(curr.getKey(), curr.getValue()));
        int path_i = 1;
        while (path_i < path.size()) {
            curr = path.get(path_i++);
            //TODO reszta
        }
    }


    public Path getEnemyPath() {
        createPath();
        return enemyPath;
    }

    public int[][] getLevelObjects() {
        return levelObjects;
    }
}
