package project;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import project.gameObjects.BasicEnemy;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static project.LevelSelection.selectionRoot;
import static project.MainVariables.*;
import static project.Menu.scene;

public class GameMaster
{
    Level currLevel;
    public static Group masterRoot;
    Queue <? extends BasicEnemy> enemies=new LinkedList <>();
    GridPane grid;

    /**
     * loads an i-th level from GameMaster
     *
     * @param n level id
     */
    public void loadLevel(int n)
    {
        currLevel=new Level(n);
        masterRoot=new Group();
        grid=new GridPane();

        for(int i=0; i<gridSizeY; i++)
        {
            ColumnConstraints column=new ColumnConstraints(50);
            grid.getColumnConstraints().add(column);
        }
        for(int i=0; i<gridSizeX; i++)
        {
            RowConstraints row=new RowConstraints(50);
            grid.getRowConstraints().add(row);
        }


        Image dirtImg=new Image(Objects.requireNonNull(getClass().getResource("/images/dirt.png")).toString(), 50, 50, true, true);
        Image grassImg=new Image(Objects.requireNonNull(getClass().getResource("/images/grass.png")).toString(), 50, 50, true, true);
        for(int i=0; i<gridSizeX; i++)
        {
            for(int j=0; j<gridSizeY; j++)
            {
                if(currLevel.levelObjects[i][j]!=0)
                {grid.add(new ImageView(dirtImg), i, j, 1, 1);}
                else
                {grid.add(new ImageView(grassImg), i, j, 1, 1);}
            }
        }
        masterRoot.getChildren().add(grid);
        ImageButton backButton=new ImageButton("/images/back.png", sizeX-125, sizeY-125, 100, 100);
        masterRoot.getChildren().add(backButton.get());
        backButton.get().setOnAction(e->scene.setRoot(selectionRoot));

    }


    /**
     * starts level currently loaded into GamePlane
     * Should be called in a thread
     */
    public void  startLevel() throws InterruptedException {
        if(currLevel == null) return;

        while(!enemies.isEmpty()){
            Thread.sleep(timeIntervals);

            synchronized (enemies) {
                for (BasicEnemy enemy : enemies) {
                    if(enemy.isDeployed)
                    {
                        enemy.moveEnemy();
                        if(enemy.coords.getKey()==-1)
                        {
                            enemies.remove(enemy);
                            continue;
                        }
                        //TODO logic for deploying new enemies in intervals
                    }
                    else grid.add(new ImageView(enemy.enemySprite), enemy.coords.getKey(), enemy.coords.getValue(), 1, 1);
                    //TODO removing enemies
                }
            }
        }
    }
}
