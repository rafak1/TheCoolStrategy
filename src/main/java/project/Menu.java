package project;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Menu {
    Group root;
    Scene scene;

    /**
    * Creates menu on a given stage
     */
    public Menu(Stage stage){
        root = new Group();
        scene = new Scene(root);
        stage.setScene(scene);
        Canvas canvas = new Canvas(MainVariables.sizeX, MainVariables.sizeY);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image title = new Image(getClass().getResource("/title.png").toString());
        Image button1 = new Image(getClass().getResource("/Button1.png").toString());
        Image button2 = new Image(getClass().getResource("/Button2.png").toString());
        gc.drawImage(title,(MainVariables.sizeX-300)/2,50) ;
        gc.drawImage(button1,50,300) ;
        gc.drawImage(button2,500,300) ;
    }
}
