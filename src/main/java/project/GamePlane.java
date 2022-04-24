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

import java.util.Objects;

import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;

public class GamePlane
{
    Scene masterScene;
    Level currLevel;
    Group root;
    Group masterRoot;

    public GamePlane(Scene scene, Group mroot)
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
        GridPane grid=new GridPane();

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
     */
    public void startLevel() {
        if(currLevel == null) return;
        Timeline enemyTimeLine = new Timeline();
        //TODO
    }
}
