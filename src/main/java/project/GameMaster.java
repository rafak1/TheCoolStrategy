package project;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import project.gameObjects.BasicEnemy;

import java.util.Iterator;
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
    public Queue <BasicEnemy> enemies=new LinkedList <>();
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
        enemies = currLevel.enemies;
        for(int i=0; i<gridSizeY; i++)
        {
            ColumnConstraints column=new ColumnConstraints(sizeY/10);
            grid.getColumnConstraints().add(column);
        }
        for(int i=0; i<gridSizeX; i++)
        {
            RowConstraints row=new RowConstraints(sizeY/10);
            grid.getRowConstraints().add(row);
        }


        Image dirtImg=new Image(Objects.requireNonNull(getClass().getResource("/images/dirt.png")).toString(), sizeY/10, sizeY/10, true, true);
        Image grassImg=new Image(Objects.requireNonNull(getClass().getResource("/images/grass.png")).toString(), sizeY/10, sizeY/10, true, true);
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
        ImageButton startLevelButton=new ImageButton("/images/start.png", sizeX-125, sizeY-275, 100, 100);
        masterRoot.getChildren().add(startLevelButton.get());
        startLevelButton.get().setOnAction(e->{
            masterRoot.getChildren().remove(startLevelButton.get());
            startLevel();
        });

    }

    /**
     * Starts currently loaded level into GamePane
     */
    public void startLevel(){
        Thread enemyThread = new Thread(()->
        {
            Platform.setImplicitExit(false);
            try {
                startEnemyFlow();
            } catch (InterruptedException ignored) {
            }
        });
        enemyThread.start();
    }

    /**
     * starts to create and moves enemies currently loaded into GamePlane
     * Should be called in a thread
     */
    void  startEnemyFlow() throws InterruptedException {
        boolean deployedThisCycle = false;
        if(currLevel == null) return;
        BasicEnemy enemy;
        while(!enemies.isEmpty()){
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            Thread.sleep(timeIntervals);
            deployedThisCycle = false;
            synchronized (grid) {
                Iterator<BasicEnemy> iter = enemies.iterator();
                    while(iter.hasNext()) {
                        enemy = iter.next();
                        BasicEnemy finalEnemy = enemy;
                        if (enemy.isDeployed) {
                            enemy.moveEnemy();
                            if (enemy.cords.getValue() == -1) {
                                iter.remove();
                                Platform.runLater(() ->grid.getChildren().remove(finalEnemy.enemyImageView));
                                enemy.kill();
                            } else {
                                Platform.runLater(() -> {
                                    grid.getChildren().remove(finalEnemy.enemyImageView);
                                    finalEnemy.enemyImageView = new ImageView(finalEnemy.enemySprite);
                                    grid.add(finalEnemy.enemyImageView, finalEnemy.cords.getKey(), finalEnemy.cords.getValue(), 1, 1);
                                });
                            }
                        } else {
                            if (!deployedThisCycle) {
                                enemy.isDeployed = true;
                                enemy.enemyImageView = new ImageView(enemy.enemySprite);
                                System.out.println(finalEnemy.cords.getKey() +" "+ finalEnemy.cords.getValue());
                                Platform.runLater(() -> grid.add(finalEnemy.enemyImageView, finalEnemy.cords.getKey(), finalEnemy.cords.getValue(), 1, 1));
                                deployedThisCycle = true;
                            }
                        }
                    }
            }
        }
    }
}
