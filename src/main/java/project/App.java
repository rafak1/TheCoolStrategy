package project;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main JavaFx app
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Tower Defence the Game!");
        Menu menu = new Menu(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}