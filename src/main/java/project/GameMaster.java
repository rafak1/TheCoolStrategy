package project;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import project.Levels.LevelLoader;
import project.gameObjects.Enemies.Enemy;
import project.gameObjects.Turrets.EnemyDetection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static project.DeployTurret.allTowers;
import static project.LevelSelection.selectionRoot;
import static project.MainVariables.*;
import static project.Menu.scene;
import static project.gameObjects.Turrets.EnemyDetection.listener;

public class GameMaster {
    public static LevelLoader levelLoader = new LevelLoader();
    public static Group masterRoot;
    public LinkedList <LinkedList <Enemy>> enemies;
    public static ImageView[][] board;
    public static GridPane grid;
    public static Label moneyText;
    public static Label healthText;
    public static volatile Integer gameState; //this variable tells us what's the current state of the game - enemies are walking/level not started/time to place turrets etc
    Thread enemyThread;
    Label waveText;
    int currentWave = 1;
    Path enemyPath;
    public static LinkedList <Enemy> currWave;
    GraphicsContext gc;
    public static EnemyDetection ed;


    /**
     * loads an i-th level from GameMaster
     *
     * @param n level id
     */
    public void loadLevel(int n) {
        try {
            levelLoader.load(n);
        } catch (Throwable a) {
            a.printStackTrace();
        }
        gridSize = (int) (sizeY / 10);
        gameState = 0;
        masterRoot = new Group();
        grid = new GridPane();
        enemies = levelLoader.getEnemies();
        board = new ImageView[gridSizeX][gridSizeY];

        for (int i = 0; i < gridSizeY; i++) {
            ColumnConstraints column = new ColumnConstraints(gridSize);
            grid.getColumnConstraints().add(column);
        }
        for(int i=0; i<gridSizeX; i++)
        {
            RowConstraints row = new RowConstraints(gridSize);
            grid.getRowConstraints().add(row);
        }

        Canvas canvas = new Canvas(sizeX, sizeY);
        masterRoot.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.web("0xfd4d5d"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Image dirtImg = new Image(Objects.requireNonNull(getClass().getResource("/images/dirt.png")).toString(), gridSize, gridSize, true, true);
        Image grassImg = new Image(Objects.requireNonNull(getClass().getResource("/images/grass.png")).toString(), gridSize, gridSize, true, true);
        Random random = new Random();
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                switch (levelLoader.getLevelObjects()[i][j]) {
                    case 1:
                        board[i][j] = new ImageView(dirtImg);
                        grid.add(board[i][j], i, j, 1, 1);
                        break;
                    case 0:
                        board[i][j] = new ImageView(grassImg);
                        grid.add(board[i][j], i, j, 1, 1);
                        break;
                    case 2:
                        Image sceneryImg = new Image(Objects.requireNonNull(getClass().getResource("/images/scenery" + ThreadLocalRandom.current().nextInt(0, 3) + ".png")).toString(), gridSize, gridSize, true, true);
                        board[i][j] = new ImageView(sceneryImg);
                        if (random.nextBoolean()) board[i][j].setScaleX(-1);
                        grid.add(board[i][j], i, j, 1, 1);
                        break;
                }
            }
        }
        masterRoot.getChildren().add(grid);

        //path
        enemyPath = levelLoader.getEnemyPath();
        //masterRoot.getChildren().add(enemyPath);

        //money and player health
        moneyText = new Label();
        healthText = new Label();
        moneyText.setText(String.valueOf(Player.money));
        healthText.setText(String.valueOf(Player.health));
        moneyText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        moneyText.setLayoutX(sizeX - 180);
        moneyText.setLayoutY(sizeY - 110);
        healthText.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        healthText.setLayoutX(sizeX - 180);
        healthText.setLayoutY(sizeY - 210);
        masterRoot.getChildren().add(moneyText);
        masterRoot.getChildren().add(healthText);
        Image coin = new Image(Objects.requireNonNull(getClass().getResource("/images/heart.png")).toString(), 75, 75, true, true);
        Image heart = new Image(Objects.requireNonNull(getClass().getResource("/images/coin.png")).toString(), 75, 75, true, true);
        gc.drawImage(heart, sizeX - 275, sizeY - 100);
        gc.drawImage(coin, sizeX - 275, sizeY - 200);


        //wave number
        waveText = new Label();
        waveText.setText("wave: " + currentWave);
        waveText.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        waveText.setLayoutX(sizeX - 270);
        waveText.setLayoutY(30);
        masterRoot.getChildren().add(waveText);
        //buttons
        ImageButton backButton = new ImageButton("/images/backbutton.png", sizeX * 0.86, sizeY * 0.7, (int) (sizeX * 0.2), (int) (sizeY * 0.08));
        masterRoot.getChildren().add(backButton.get());
        backButton.get().setOnAction(e -> clearLevel());

        ImageButton startLevelButton = new ImageButton("/images/startbutton.png", sizeX * 0.86, sizeY * 0.58, (int) (sizeX * 0.2), (int) (sizeY * 0.08));
        masterRoot.getChildren().add(startLevelButton.get());
        startLevelButton.get().setOnAction(e ->{
            gameState=1;
            masterRoot.getChildren().remove(startLevelButton.get());
            moveEnemies();
            ed=new EnemyDetection();
            ed.dispatcher();
        });

        Player.health.set(playerHealth);
        Player.money.set(startingMoney);

        //temp. mouse listener
        new DeployTurret();
    }

    /**
     * Method responsible for removing/clearing everything in the level when leaving
     */
    void clearLevel()
    {
        gameState=0;
        if(enemyThread!=null && enemyThread.isAlive())
        {
            enemyThread.interrupt();
        }
        if(listener!=null && listener.isAlive())
        {
            listener.interrupt();
        }
        allTowers.clear();
        if(ed!=null)
        {ed.killThreads();}
        if(currWave!=null)
        {
            Iterator <Enemy> iter=currWave.iterator();
            while(iter.hasNext())
            {
                Enemy curr=iter.next();
                if(curr!=null)
                {
                    System.out.println("clearlevel");
                    curr.kill();
                }
                iter.remove();
            }
        }
        scene.setRoot(selectionRoot);
        this.setWave(1);
        Player.health.set(playerHealth);
        Player.money.set(startingMoney);
    }

    /**
     * Starts currently loaded level into GamePane
     */
    public void moveEnemies()
    {
        enemyThread=new Thread(()->{
            try {
                startEnemyFlow();
            } catch (InterruptedException ignored) {
            }
        });
        enemyThread.setDaemon(true);
        enemyThread.start();
    }

    /**
     * Sets waveText to a given value
     *
     * @param a value
     */
    void setWave(int a) {
        Platform.runLater(() -> {
            waveText.setText("wave: " + a);
        });
    }

    /**
     * Shows 'you lost' screen, then returns to level selection
     */
    void youLostScreen() {
        System.out.println("STOP");
        gameState = 0;
        if (currWave != null) {
            for (Enemy curr : currWave) {
                if (curr != null && curr.getPathTransition() != null) {
                    curr.getPathTransition().pause();
                }
            }
        }
        Platform.runLater(() -> {
            Rectangle darkScreen = new Rectangle(sizeX, sizeY, Color.BLACK);
            darkScreen.setOpacity(0.7);
            masterRoot.getChildren().add(darkScreen);
            ImageView youLost = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/youLost.png")).toString(), sizeX / 2, sizeY / 3, true, true)); // sizeX/5 sizeY/3
            youLost.toFront();
            youLost.setX(sizeX / 2 - sizeX / 4);
            youLost.setY(sizeY / 2 - sizeY / 6);
            masterRoot.getChildren().add(youLost);
        });
        try {
            Thread.sleep(looseScreenTime);
        } catch (InterruptedException ignored) {
        }
        this.clearLevel();
    }

    /**
     * starts to create and moves enemies currently loaded into GamePlane
     * Should be called in a thread
     */
    void startEnemyFlow() throws InterruptedException {
        boolean deployedThisCycle;
        if (levelLoader == null) return;
        Enemy enemy;
        int clock = 0;
        outer:
        while(currentWave<=enemies.size())
        {
            currWave=enemies.get(currentWave-1);
            this.setWave(currentWave);
            currentWave++;
            while(gameState==1)
            {
                Thread.sleep(timeIntervals);
                if(currWave.isEmpty())
                {
                    continue outer;
                }
                clock++;
                if(Player.health.get()<=0)
                {
                    this.youLostScreen();
                    break outer;
                }
                if(clock%10==0) {
                    /*System.out.println("----------------");
                    for(int i=0; i<currWave.size();i++) if(currWave.get(i)!=null && currWave.get(i).isDeployed()) System.out.println(currWave.get(i));
                    System.out.println("=================");*/
                    Iterator<Enemy> iter = currWave.iterator();
                    clock = 0;
                    Player.changePlayerMoney(passiveIncome);
                    deployedThisCycle = false;
                    while (iter.hasNext()) {
                        enemy = iter.next();
                        if (enemy == null) {
                            if (!deployedThisCycle) {
                                iter.remove();
                            }
                            deployedThisCycle=true;
                            continue;
                        }
                        if(!enemy.isDeployed() && !deployedThisCycle) {
                            //System.out.println(enemy + " deployed");
                            PathTransition next = new PathTransition();
                            enemy.SetDeployed(true);
                            enemy.setEnemyImageView(new ImageView(enemy.getEnemySprite()));
                            enemy.startAnimation();
                            next.setDuration(Duration.seconds(pathLength));
                            Enemy finalEnemy = enemy;
                            Platform.runLater(() -> masterRoot.getChildren().add(finalEnemy.getEnemyImageView()));
                            next.setNode(enemy.getEnemyImageView());
                            next.setPath(enemyPath);
                            next.setOnFinished(actionEvent -> {
                                if (gameState != 0) {
                                    Player.changePlayerHealth(-finalEnemy.getEnemyDamage());
                                    if (!finalEnemy.isKilled()) {
                                        System.out.println("lambda");
                                        finalEnemy.kill();
                                    }
                                }
                            });
                            enemy.setPathTransition(next);
                            next.play();
                            deployedThisCycle = true;
                        }
                        else if (enemy.isDeployed() && enemy.isKilled()) {
                            iter.remove();
                            enemy.SetDeployed(false);
                        }
                    }
                }
            }
        }
    }
}
