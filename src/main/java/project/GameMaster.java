package project;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import project.gameObjects.BasicEnemy;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static project.MainVariables.*;

public class GameMaster
{
    Scene masterScene;
    Level currLevel;
    Group root;
    Group masterRoot;
    Queue<? extends BasicEnemy> enemies = new LinkedList<>();
    GridPane grid;

    public GameMaster(Scene scene, Group mroot)
    {
        masterScene=scene;
        masterRoot=mroot;
    }

    /**
     * loads an i-th level from GameMaster
     *
     * @param n level id
     */
    public void loadLevel(int n)
    {
        currLevel=new Level(n);
        root=new Group();
        grid = new GridPane();

        for(int i=0; i<10; i++)
        {
            ColumnConstraints column=new ColumnConstraints(50);
            RowConstraints row=new RowConstraints(50);
            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
        }


        Image dirtImg=new Image(Objects.requireNonNull(getClass().getResource("/images/dirt.png")).toString(), 50, 50, true, true);
        Image grassImg=new Image(Objects.requireNonNull(getClass().getResource("/images/grass.png")).toString(), 50, 50, true, true);
        for(int i=0; i<10; i++)
        {
            for(int j=0; j<10; j++)
            {
                if(currLevel.levelObjects[i][j]!=0)
                {grid.add(new ImageView(dirtImg), i, j, 1, 1);}
                else
                {grid.add(new ImageView(grassImg), i, j, 1, 1);}
            }
        }
        root.getChildren().add(grid);
        ImageButton backButton=new ImageButton("/images/back.png", (int)(sizeX-125), (int)(sizeY-125), 100, 100);
        root.getChildren().add(backButton.get());
        backButton.get().setOnAction(e->masterScene.setRoot(masterRoot));

    }

    public Group getRoot(){
        return root;
    }


    /**
     * starts level currently loaded into GamePlane
     * Should be called in a thread
     */
    @SuppressWarnings("All")
    public void  startLevel() throws InterruptedException {
        if(currLevel == null) return;

        while(!enemies.isEmpty()){
            Thread.sleep(timeIntervals);

            synchronized (enemies) {
                for (BasicEnemy enemy : enemies) {
                    if(enemy.isDeployed){
                        enemy.moveEnemy();
                        if(enemy.coords.getKey() == -1){
                            enemies.remove(enemy);
                            continue;
                        }
                    }   //TODO logic for deplying new enemies in intervals
                    else grid.add(new ImageView(enemy.enemySprite), enemy.coords.getKey(), enemy.coords.getValue(), 1, 1);
                    //TODO removing enemies
                }
            }
        }
    }
}
