package project;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Menu {
    Group root;
    Scene scene;

    /**
    * Creates menu on a given stage
     */
    public Menu(Stage stage){
        root=new Group();
        scene=new Scene(root);
        stage.setScene(scene);

        Canvas canvas=new Canvas(MainVariables.sizeX, MainVariables.sizeY);
        root.getChildren().add(canvas);
        GraphicsContext gc=canvas.getGraphicsContext2D();

        Image title=new Image(Objects.requireNonNull(getClass().getResource("/images/title.png")).toString());
        gc.drawImage(title, (MainVariables.sizeX-300)/2, 50);


        ImageButton startButton=new ImageButton("/images/Button1.png", 50, 300, 100, 50);
        root.getChildren().add(startButton.get());
        startButton.get().setOnAction(e->{
            LevelSelection game=new LevelSelection(root, scene);
            scene.setRoot(game.getRoot());
        });

        ImageButton exitButton=new ImageButton("/images/Button2.png", 350, 300, 100, 50);
        root.getChildren().add(exitButton.get());
        exitButton.get().setOnAction(arg0->System.exit(0));
    }
}
