package project;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

import static project.LevelSelection.selectionRoot;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;

public class Menu
{
    public static Group menuRoot;
    public static Scene scene;

    /**
     * Creates menu on a given stage
     */
    public Menu(Stage stage)
    {
        menuRoot=new Group();
        scene=new Scene(menuRoot);
        stage.setScene(scene);

        Canvas canvas=new Canvas(sizeX, sizeY);
        menuRoot.getChildren().add(canvas);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.setFill(Color.web("0xfd4d5d"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Image title=new Image(Objects.requireNonNull(getClass().getResource("/images/title.png")).toString());
        gc.drawImage(title, (sizeX-300)/2, (sizeY)/9);


        ImageButton startButton=new ImageButton("/images/Button1.png", (sizeX-100)/3, sizeY/2, 100, 50);
        menuRoot.getChildren().add(startButton.get());
        startButton.get().setOnAction(e->{
            LevelSelection game=new LevelSelection();
            scene.setRoot(selectionRoot);
        });

        ImageButton exitButton=new ImageButton("/images/Button2.png", (sizeX-100)*2/3, sizeY/2, 100, 50);
        menuRoot.getChildren().add(exitButton.get());
        exitButton.get().setOnAction(arg0->System.exit(0));
    }
}
