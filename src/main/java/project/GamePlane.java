package project;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GamePlane {
    Level currLevel;
    Group root;
    StackPane holder;
    Canvas canvas;
    Scene scene;
    /**
     * loads an i-th level from GameMaster
     * @param i level id
     */
    public void loadLevel(int i){
        currLevel = GameMaster.levels[i];
        root=new Group();
        canvas = new Canvas(MainVariables.sizeX, MainVariables.sizeY);
        holder = new StackPane();
        root.getChildren().add( holder );
        holder.getChildren().add(canvas);
        holder.setStyle("-fx-background-color: green");
    }

    public Group getRoot(){
        return root;
    }

}
