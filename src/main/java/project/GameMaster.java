package project;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import project.gameObjects.BasicEnemy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import static project.LevelSelection.selectionRoot;
import static project.MainVariables.*;
import static project.Menu.scene;

public class GameMaster {
    public static Level currLevel;
    public static Group masterRoot;
    public Queue<BasicEnemy> enemies = new LinkedList<>();
    public static Node[][] board;
    public static GridPane grid;
    public static Label moneyText;//TODO: make a new thread to refresh it every second(or more often) //TODO dlaczego nie czaje mordo
    public static Label healthText;
    public static Integer gameState; //this variable tells us what's the current state of the game - enemies are walking/level not started/time to place turrets etc
    Thread enemyThread;

    /**
     * loads an i-th level from GameMaster
     *
     * @param n level id
     */
    public void loadLevel(int n) {
        gameState=0;
        currLevel=new Level(n);
        masterRoot=new Group();
        grid=new GridPane();
        enemies=currLevel.enemies;
        board=new Node[gridSizeX][gridSizeY];

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

        Canvas canvas = new Canvas(sizeX, sizeY);
        masterRoot.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image dirtImg = new Image(Objects.requireNonNull(getClass().getResource("/images/dirt.png")).toString(), sizeY / 10, sizeY / 10, true, true);
        Image grassImg = new Image(Objects.requireNonNull(getClass().getResource("/images/grass.png")).toString(), sizeY / 10, sizeY / 10, true, true);
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                if (currLevel.levelObjects[i][j] != 0) {
                    board[i][j]=new ImageView(dirtImg);
                    grid.add(board[i][j], i, j, 1, 1);
                }
                else
                {
                    board[i][j]=new ImageView(grassImg);
                    grid.add(board[i][j], i, j, 1, 1);
                }
            }
        }
        masterRoot.getChildren().add(grid);

        //money and player health
        moneyText = new Label();
        healthText = new Label();
        moneyText.setText(String.valueOf(Player.money));
        healthText.setText(String.valueOf(Player.health));
        moneyText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        moneyText.setLayoutX(sizeX - 180);
        moneyText.setLayoutY(sizeY-110);
        healthText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        healthText.setLayoutX(sizeX-180);
        healthText.setLayoutY(sizeY-210);
        masterRoot.getChildren().add(moneyText);
        masterRoot.getChildren().add(healthText);
        Image coin=new Image(Objects.requireNonNull(getClass().getResource("/images/heart.png")).toString(), 75, 75, true, true);
        Image heart=new Image(Objects.requireNonNull(getClass().getResource("/images/coin.png")).toString(), 75, 75, true, true);
        gc.drawImage(heart, sizeX-275, sizeY-100);
        gc.drawImage(coin, sizeX-275, sizeY-200);

        //buttons
        ImageButton backButton=new ImageButton("/images/back.png", sizeX-225, sizeY-325, 100, 100);
        masterRoot.getChildren().add(backButton.get());
        backButton.get().setOnAction(e->clearLevel());

        ImageButton startLevelButton=new ImageButton("/images/start.png", sizeX-225, sizeY-475, 100, 100);
        masterRoot.getChildren().add(startLevelButton.get());
        startLevelButton.get().setOnAction(e->{
            gameState=1;
            masterRoot.getChildren().remove(startLevelButton.get());
            moveEnemies();
        });

        //temp. mouse listener
        new DeployTurret();
    }

    /**
     * Method responsible for removing/clearing everything in the level when leaving
     */
    void clearLevel() {
        Player.changePlayerHealth(playerHealth);
        Player.changePlayerMoney(startingMoney);
        scene.setRoot(selectionRoot);
        if (enemyThread.isAlive()) enemyThread.interrupt();
    }


    /**
     * Starts currently loaded level into GamePane
     */
    public void moveEnemies()
    {
        enemyThread = new Thread(() -> {
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
        outer:
        while (true) {
            Thread.sleep(timeIntervals);
            Player.changePlayerMoney(passiveIncome);

            deployedThisCycle = false;
            synchronized (grid) {
                Iterator<BasicEnemy> iter = enemies.iterator();
                while (iter.hasNext()) {
                    enemy = iter.next();
                    BasicEnemy finalEnemy = enemy;
                    if (enemy.isDeployed) {
                        enemy.moveEnemy();
                        if (enemy.cords.getValue() == -1) {
                            iter.remove();
                            enemy.kill();
                            Player.changePlayerMoney(-1);
                            Platform.runLater(() -> {
                                grid.getChildren().remove(finalEnemy.enemyImageView);
                            });
                            if (Player.health.get() <= 0) {
                                clearLevel();
                                break outer;
                            }
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
                                Platform.runLater(() -> grid.add(finalEnemy.enemyImageView, finalEnemy.cords.getKey(), finalEnemy.cords.getValue(), 1, 1));
                                deployedThisCycle = true;
                            }
                        }
                    }
            }
        }
    }
}
