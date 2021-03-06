package project;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

import static project.LevelSelection.selectionRoot;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;

public class Menu
{
    public static Group menuRoot;
    public static Scene scene;
    public static SoundHandler soundHandler = new SoundHandler();
    /**
     * Creates menu on a given stage
     */
    public Menu(Stage stage) {
        double volume = SystemInfo.pref.getDouble("volume", 1.0);
        soundHandler.startMusic(volume);


        menuRoot = new Group();
        scene = new Scene(menuRoot);
        stage.setScene(scene);

        Canvas canvas = new Canvas(sizeX, sizeY);
        menuRoot.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = new Image(Objects.requireNonNull(getClass().getResource("/images/UI/background.png")).toString());
        gc.drawImage(background, 0, 0, sizeX, sizeY);

        Image title=new Image(Objects.requireNonNull(getClass().getResource("/images/UI/title.png")).toString());
        gc.drawImage(title, (sizeX-300)/2, (sizeY)/9);


        ImageButton startButton=new ImageButton("/images/UI/startbutton.png", (sizeX-100)/3, sizeY/2, (int)(sizeX*0.1), (int)(sizeY*0.05));
        menuRoot.getChildren().add(startButton.get());
        startButton.get().setOnAction(e->{
            LevelSelection game=new LevelSelection(stage);
            scene.setRoot(selectionRoot);
        });

        ImageButton exitButton=new ImageButton("/images/UI/backbutton.png", (sizeX-100)*2/3, sizeY/2, (int)(sizeX*0.1), (int)(sizeY*0.05));
        menuRoot.getChildren().add(exitButton.get());
        exitButton.get().setOnAction(arg0->System.exit(0));
    }
}
