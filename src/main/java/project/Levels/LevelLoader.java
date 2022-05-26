package project.Levels;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Pair;
import project.gameObjects.Enemies.Enemy;
import project.gameObjects.Enemies.SmallEnemy;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static project.MainVariables.*;

class NoSuchLevelException extends Throwable {
}

public class LevelLoader {
    int[][] levelObjects;        //can be deleted and replaced only with path
    ArrayList <Pair <Integer, Integer>> path;
    LinkedList <LinkedList <Enemy>> enemies=new LinkedList <>();
    int waves;
    Path enemyPath;
    static SmallEnemy loadIds=null;

    public LevelLoader()
    {
        loadIds=new SmallEnemy();
    }


    public void load(File f) throws NoSuchLevelException
    {
        FileReader fileReader;
        try
        {
            fileReader=new FileReader(f);
        }catch(Throwable a)
        {
            a.printStackTrace();
            throw new NoSuchLevelException();
        }
        loader(fileReader);
    }

    /**
     * Loads a level into LevelLoader
     *
     * @param k id of  a level
     */
    public void load(int k) throws NoSuchLevelException
    {
        FileReader fileReader;
        try
        {
            fileReader=new FileReader(System.getProperty("user.dir")+"\\src\\main\\java\\project\\Levels\\Level"+k);
        }catch(Throwable a)
        {
            a.printStackTrace();
            throw new NoSuchLevelException();
        }
        loader(fileReader);
    }

    void loader(FileReader fileReader)
    {
        enemies.clear();
        path=new ArrayList <>();
        levelObjects=new int[gridSizeX][gridSizeY];
        for(int i=0; i<gridSizeX; i++)
        {
            for(int j=0; j<gridSizeY; j++)
            {
                levelObjects[i][j]=0;
            }
        }
        Scanner scanner = new Scanner(fileReader);
        //read from level file
        pathLength = scanner.nextInt();
        passiveIncome = scanner.nextInt();
        int x = scanner.nextInt();
        for (int i=0; i < x; i++) {
            Pair<Integer, Integer> a = new Pair<>(scanner.nextInt(), scanner.nextInt());
            path.add(a);
            levelObjects[a.getKey()][a.getValue()] = 1;
        }

        int scenery = scanner.nextInt();   //Read scenery fields
        for (int i = 0; i < scenery; i++) {
            int sceneryX = scanner.nextInt();
            int sceneryY = scanner.nextInt();
            levelObjects[sceneryX][sceneryY] = 2;
        }
        waves = scanner.nextInt();
        for (int i = 0; i < waves; i++) {
            LinkedList<Enemy> curr = new LinkedList<>();
            int y = scanner.nextInt();
            for (int j = 0; j < y; j++) {
                int z = scanner.nextInt();
                if (z == 0) {
                    curr.add(null);
                    continue;
                }
                Class<? extends Enemy> toAddClass = Enemy.enemyId.get(z);
                Enemy toAdd = null;
                try {
                    toAdd = toAddClass.getConstructor().newInstance();
                } catch (Throwable a) {
                    a.printStackTrace();
                }
                curr.add(toAdd);
            }
            enemies.add(curr);
        }
        createPath();
    }

    void createPath() {
        enemyPath = new Path();
        Pair<Integer, Integer> curr = path.get(0);
        Pair<Integer, Integer> next;
        enemyPath.getElements().add(new MoveTo(curr.getKey()*gridSize+gridSize/2, -(double)gridSize/2));
        int path_i = 1;
        boolean isXTheSame;
        curr = path.get(path_i);
        outer:
        while (path_i < path.size()) {
            next = path.get(path_i++);
            isXTheSame = curr.getKey().equals(next.getKey());
            while ((isXTheSame && curr.getKey().equals(next.getKey())) || (!isXTheSame && curr.getValue().equals(next.getValue()))) {
                if (path_i >= path.size()) {
                    enemyPath.getElements().add(new LineTo(next.getKey()*gridSize+gridSize/2, (next.getValue()+1)*gridSize+gridSize/2));
                    break outer;
                }
                next = path.get(path_i++);
            }
            enemyPath.getElements().add(new LineTo(path.get(path_i-2).getKey()*gridSize+gridSize/2, path.get(path_i-2).getValue()*gridSize+gridSize/2));
            if (path_i == path.size())
                enemyPath.getElements().add(new LineTo(path.get(path_i - 1).getKey() * gridSize + gridSize / 2, (path.get(path_i - 1).getValue() + 1) * gridSize + gridSize / 2));
            curr=next;
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

    public LinkedList<LinkedList<Enemy>> getEnemies() {
        return enemies;
    }

    public int[][] getLevelObjects() {
        return levelObjects;
    }
}
