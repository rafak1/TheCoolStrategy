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
import project.gameObjects.Turrets.BasicTurret;
import project.gameObjects.Turrets.DeployTurret;
import project.gameObjects.Turrets.EnemyDetection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static project.LevelSelection.selectionRoot;
import static project.MainVariables.*;
import static project.Menu.scene;
import static project.gameObjects.Turrets.DeployTurret.allTowers;
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
    int currentWave=0;
    Path enemyPath;
    public static LinkedList <Enemy> currWave;
    GraphicsContext gc;
    public static EnemyDetection ed;


    /**
     * loads a level from GameMaster
     * TODO move this method to level loader class
     */
    public void levelCreator()
    {
        gridSize=(int)(sizeY/10);
        gameState=0;
        masterRoot=new Group();
        grid=new GridPane();
        enemies=levelLoader.getEnemies();
        board=new ImageView[gridSizeX][gridSizeY];

        for(int i=0; i<gridSizeY; i++)
        {
            ColumnConstraints column=new ColumnConstraints(gridSize);
            grid.getColumnConstraints().add(column);
        }
        for (int i = 0; i < gridSizeX; i++) {
            RowConstraints row = new RowConstraints(gridSize);
            grid.getRowConstraints().add(row);
        }

        Canvas canvas = new Canvas(sizeX, sizeY);
        masterRoot.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        ImageView background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/backgradient.png")).toString(), sizeX, sizeY, true, true));
        masterRoot.getChildren().add(background);

        Image dirtImg = new Image(Objects.requireNonNull(getClass().getResource("/images/terrain/dirt.png")).toString(), gridSize, gridSize, true, true);
        Image grassImg = new Image(Objects.requireNonNull(getClass().getResource("/images/terrain/desert.png")).toString(), gridSize, gridSize, true, true);
        Random random = new Random();
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                switch (levelLoader.getLevelObjects()[i][j]) {
                    case 1:
                        board[i][j] = new ImageView(dirtImg);
                        if (random.nextBoolean()) board[i][j].setScaleX(-1);
                        if (random.nextBoolean()) board[i][j].setScaleY(-1);
                        grid.add(board[i][j], i, j, 1, 1);
                        break;
                    case 0:
                        board[i][j] = new ImageView(grassImg);
                        if (random.nextBoolean()) board[i][j].setScaleX(-1);
                        if (random.nextBoolean()) board[i][j].setScaleY(-1);
                        grid.add(board[i][j], i, j, 1, 1);
                        break;
                    case 2:
                        Image sceneryImg = new Image(Objects.requireNonNull(getClass().getResource("/images/terrain/scenery" + ThreadLocalRandom.current().nextInt(0, 3) + ".png")).toString(), gridSize, gridSize, true, true);
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
        moneyText.setFont(Font.font("Verdana", FontWeight.BOLD, sizeX * 0.037));
        moneyText.setLayoutX(sizeX * 0.91);
        moneyText.setLayoutY(sizeY * 0.89);
        healthText.setFont(Font.font("Verdana", FontWeight.BOLD, sizeX * 0.037));
        healthText.setLayoutX(sizeX * 0.91);
        healthText.setLayoutY(sizeY * 0.8);
        masterRoot.getChildren().add(moneyText);
        masterRoot.getChildren().add(healthText);

        ImageView coin = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/coin.png")).toString(), sizeX * 0.0378, sizeX * 0.0378, true, true));
        masterRoot.getChildren().add(coin);
        coin.setX(sizeX * 0.86);
        coin.setY(sizeY * 0.9);
        ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/heart.png")).toString(), sizeX * 0.0378, sizeX * 0.0378, true, true));
        masterRoot.getChildren().add(heart);
        heart.setX(sizeX * 0.86);
        heart.setY(sizeY * 0.815);


        //wave number
        waveText = new Label();
        waveText.setText("wave: " + 1);
        waveText.setFont(Font.font("Verdana", FontWeight.BOLD, sizeX * 0.025));
        waveText.setLayoutX(sizeX * 0.863);
        waveText.setLayoutY(sizeY * 0.028);
        masterRoot.getChildren().add(waveText);
        //buttons
        ImageButton backButton = new ImageButton("/images/UI/backbutton.png", sizeX * 0.86, sizeY * 0.7, (int) (sizeX * 0.2), (int) (sizeY * 0.08));
        masterRoot.getChildren().add(backButton.get());
        backButton.get().setOnAction(e -> clearLevel());

        ImageButton startLevelButton = new ImageButton("/images/UI/startbutton.png", sizeX * 0.86, sizeY * 0.58, (int) (sizeX * 0.2), (int) (sizeY * 0.08));
        masterRoot.getChildren().add(startLevelButton.get());
        startLevelButton.get().setOnAction(e -> {
            gameState = 1;
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
        for(BasicTurret x: allTowers)
            x.stopFireSound();
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
                    curr.kill();
                }
                iter.remove();
            }
        }
        currentWave=0;
        scene.setRoot(selectionRoot);
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
     * Shows 'you lost' screen, then returns to level selection
     */
    void youLostWon(boolean lw)
    {
        if(lw)
        {System.out.println("YAY");}
        else
        {System.out.println("NAY");}
        gameState=0;
        if(currWave!=null)
        {
            for(Enemy curr: currWave)
            {
                if(curr!=null && curr.getPathTransition()!=null)
                {
                    curr.getPathTransition().pause();
                }
            }
        }
        String src;
        if(lw)
        {src="/images/UI/youWon.png";}
        else
        {src="/images/UI/youLost.png";}
        Platform.runLater(()->{
            Rectangle darkScreen=new Rectangle(sizeX, sizeY, Color.BLACK);
            darkScreen.setOpacity(0.7);
            masterRoot.getChildren().add(darkScreen);
            ImageView youLost=new ImageView(new Image(Objects.requireNonNull(getClass().getResource(src)).toString(), sizeX/2, sizeY/3, true, true)); // sizeX/5 sizeY/3
            youLost.toFront();
            youLost.setX(sizeX/2-sizeX/4);
            youLost.setY(sizeY/2-sizeY/6);
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
        while(currentWave<enemies.size())
        {
            currWave=enemies.get(currentWave);
            Platform.runLater(()->waveText.setText("wave: "+currentWave));
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
                    this.youLostWon(false);
                    break outer;
                }
                if(clock%10==0) {
                    Iterator<Enemy> iter = currWave.iterator();
                    clock = 0;
                    Player.changePlayerMoney(passiveIncome);
                    deployedThisCycle = false;
                    while (iter.hasNext()) {
                        enemy = iter.next();
                        if (enemy == null) {
                            if (!deployedThisCycle) {
                                synchronized(currWave)
                                {
                                    iter.remove();
                                }
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
                            if (Settings.difficultyMultiplier == 2.0)
                                next.setDuration(Duration.seconds(pathLength * 0.8 * enemy.getEnemySpeed()));
                            else
                                next.setDuration(Duration.seconds(pathLength * (1 / Settings.difficultyMultiplier) * enemy.getEnemySpeed()));
                            Enemy finalEnemy = enemy;
                            Platform.runLater(() -> masterRoot.getChildren().add(finalEnemy.getEnemyImageView()));
                            next.setNode(enemy.getEnemyImageView());
                            next.setPath(enemyPath);
                            next.setOnFinished(actionEvent -> {
                                if (gameState != 0) {
                                    Player.changePlayerHealth(-finalEnemy.getEnemyDamage());
                                    if (!finalEnemy.isKilled()) {
                                        finalEnemy.kill();
                                    }
                                }
                            });
                            enemy.setPathTransition(next);
                            next.play();
                            deployedThisCycle=true;
                        }
                        else if(enemy.isDeployed() && enemy.isKilled())
                        {
                            synchronized(currWave)
                            {
                                iter.remove();
                            }
                            enemy.SetDeployed(false);
                        }
                    }
                }
            }
        }
        if(Player.health.get()>0)
        {youLostWon(true);}
    }
}
