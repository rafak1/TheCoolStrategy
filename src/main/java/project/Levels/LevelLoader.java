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

import static java.lang.Math.abs;
import static project.MainVariables.*;









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


    public void load(File f) throws NoSuchLevelException, WrongFileFormatException, IncorrectPathException, IncorrectEnemyIdException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(f);
        } catch (Throwable a) {
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
    public void load(int k) throws NoSuchLevelException, WrongFileFormatException, IncorrectPathException, IncorrectEnemyIdException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\project\\Levels\\Level" + k + ".lvl");
        } catch (Throwable a) {
            a.printStackTrace();
            throw new NoSuchLevelException();
        }
        loader(fileReader);
    }

    void loader(FileReader fileReader) throws WrongFileFormatException, IncorrectPathException, IncorrectEnemyIdException {
        enemies.clear();
        path = new ArrayList<>();
        levelObjects = new int[gridSizeX][gridSizeY];
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                levelObjects[i][j] = 0;
            }
        }
        Scanner scanner=new Scanner(fileReader);
        //read from level file
        try
        {
            pathLength=scanner.nextInt();
        }catch(Exception e)
        {
            throw new WrongFileFormatException();
        }
        passiveIncome=scanner.nextInt();
        int x=scanner.nextInt();
        for(int i=0; i<x; i++)
        {
            Pair <Integer, Integer> a=new Pair <>(scanner.nextInt(), scanner.nextInt());
            path.add(a);
        }

        int scenery=scanner.nextInt();   //Read scenery fields
        for(int i=0; i<scenery; i++)
        {
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
                    throw new IncorrectEnemyIdException();
                }
                curr.add(toAdd);
            }
            enemies.add(curr);
        }
        createPath();
    }

    /**
     * Creates a Path with a path currently from LevelLoader
     */
    void createPath() throws IncorrectPathException {
        enemyPath = new Path();
        Pair<Integer, Integer> curr = path.get(0);
        levelObjects[curr.getKey()][curr.getValue()] = 1;
        if (curr.getValue() != 0) throw new IncorrectPathException();
        enemyPath.getElements().add(new MoveTo(curr.getKey() * gridSize + gridSize / 2, -(double) gridSize / 2));
        int path_i = 1;
        while (path_i < path.size()) {
            if (((abs((path.get(path_i).getKey() - curr.getKey())) == 1 || abs((path.get(path_i).getValue() - curr.getValue())) == 1) && !(abs((path.get(path_i).getKey() - curr.getKey())) == 1 && abs((path.get(path_i).getValue() - curr.getValue())) == 1)) && levelObjects[path.get(path_i).getKey()][path.get(path_i).getValue()] == 0) {
                curr = path.get(path_i);
                levelObjects[curr.getKey()][curr.getValue()] = 1;
                enemyPath.getElements().add(new LineTo(curr.getKey() * gridSize + gridSize / 2, curr.getValue() * gridSize + gridSize / 2));
                path_i++;
            } else throw new IncorrectPathException();
            if (path_i == path.size()) {
                if ((path.get(path_i - 1).getValue() != gridSizeY - 1)) throw new IncorrectPathException();
                enemyPath.getElements().add(new LineTo(path.get(path_i - 1).getKey() * gridSize + gridSize / 2, (path.get(path_i - 1).getValue() + 1) * gridSize + gridSize / 2));
            }
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
