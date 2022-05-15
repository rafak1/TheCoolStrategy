package project;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static project.MainVariables.*;

/**
 * Main JavaFx app
 */
public class App extends Application {
    @Override
    public void start(Stage stage)
    {
        Rectangle2D screenBounds=Screen.getPrimary().getBounds();
        sizeX=screenBounds.getWidth();
        sizeY = screenBounds.getHeight();
        gridSize = (double) sizeY / 10;
        stage.setTitle("Tower Defence the Game!");
        Menu menu=new Menu(stage);
        stage.show();

        stage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch();
    }

}