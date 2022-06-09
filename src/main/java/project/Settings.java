package project;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

import static project.MainVariables.*;
import static project.Menu.scene;

public class Settings {

    public static double difficultyMultiplier = SystemInfo.pref.getDouble("difficulty", 1.0);
    Group settingsRoot;
    GameMaster gameMaster;

    public Settings(Group menuRoot) {
        settingsRoot = new Group();
        gameMaster = new GameMaster();


        //background
        Canvas canvas = new Canvas(sizeX, sizeY);
        settingsRoot.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = new Image(Objects.requireNonNull(getClass().getResource("/images/UI/background.png")).toString());
        gc.drawImage(background, 0, 0, sizeX, sizeY);

        //background gradient
        ImageView gradient = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/backgradient.png")).toString(), sizeX * 0.8, sizeY * 0.8, true, true));
        gradient.setX(sizeX * 0.1);
        gradient.setY(sizeY * 0.1);
        settingsRoot.getChildren().add(gradient);

        //difficulty slider
        Label difficultyText = new Label();
        difficultyText.setText("difficulty");
        difficultyText.setFont(Font.font("Verdana", FontWeight.BOLD, (sizeY / 22)));
        difficultyText.setLayoutX(sizeX * 0.43);
        difficultyText.setLayoutY(sizeY * 0.15);
        settingsRoot.getChildren().add(difficultyText);
        Slider difficulty = new Slider(0.5, 2, difficultyMultiplier);
        difficulty.setSnapToTicks(true);
        difficulty.setMajorTickUnit(0.5);
        difficulty.setMinorTickCount(0);
        difficulty.setTranslateX(sizeX * 0.25);
        difficulty.setTranslateY(sizeY * 0.2);
        difficulty.setPrefSize(sizeX * 0.5, sizeY * 0.1);
        settingsRoot.getChildren().add(difficulty);

        //difficulty images
        ImageView easy = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/difficultyeasy.png")).toString(), sizeX / 10, sizeY / 10, true, true)); // sizeX/5 sizeY/3
        easy.setX(sizeX * 0.22);
        easy.setY(sizeY * 0.14);
        settingsRoot.getChildren().add(easy);

        ImageView hard = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/difficultyhard.png")).toString(), sizeX / 10, sizeY / 10, true, true)); // sizeX/5 sizeY/3
        hard.setX(sizeX * 0.70);
        hard.setY(sizeY * 0.17);
        settingsRoot.getChildren().add(hard);


        //volume slider
        Label volumeText = new Label();
        volumeText.setText("music volume");
        volumeText.setFont(Font.font("Verdana", FontWeight.BOLD, (sizeY/22)));
        volumeText.setLayoutX(sizeX*0.43);
        volumeText.setLayoutY(sizeY*0.35);
        settingsRoot.getChildren().add(volumeText);
        Slider volume=new Slider(0, 1.0, SoundHandler.musicPlayer.getVolume());
        volume.setMajorTickUnit(0.5);
        volume.setTranslateX(sizeX*0.25);
        volume.setTranslateY(sizeY*0.4);
        volume.setPrefSize(sizeX*0.5, sizeY*0.1);
        settingsRoot.getChildren().add(volume);


        volume.valueProperty().addListener((observable, oldValue, newValue)->{
            SystemInfo.pref.putDouble("volume", (Double)newValue);
            SoundHandler.musicPlayer.setVolume((Double)newValue);
        });


        Label volumeEffectsText=new Label();
        volumeEffectsText.setText("effects volume");
        volumeEffectsText.setFont(Font.font("Verdana", FontWeight.BOLD, (sizeY/22)));
        volumeEffectsText.setLayoutX(sizeX*0.43);
        volumeEffectsText.setLayoutY(sizeY*0.55);
        settingsRoot.getChildren().add(volumeEffectsText);
        Slider volumeEffects=new Slider(0, 1.0, SoundHandler.musicPlayer.getVolume());
        volumeEffects.setMajorTickUnit(0.5);
        volumeEffects.setTranslateX(sizeX*0.25);
        volumeEffects.setTranslateY(sizeY*0.6);
        volumeEffects.setPrefSize(sizeX*0.5, sizeY*0.1);
        settingsRoot.getChildren().add(volumeEffects);


        volumeEffects.valueProperty().addListener((observable, oldValue, newValue)->{
            SystemInfo.pref.putDouble("volumeEffects", (Double)newValue);
            effectsSound=(Double)newValue;
        });

        ImageButton backButton=new ImageButton("/images/UI/backbutton.png", sizeX*0.75, sizeY*0.8, (int)(sizeX*0.1), (int)(sizeY*0.1));
        settingsRoot.getChildren().add(backButton.get());
        backButton.get().setOnAction(e->{
            scene.setRoot(menuRoot);
            SystemInfo.pref.putDouble("difficulty", difficulty.valueProperty().get());
            difficultyMultiplier=difficulty.valueProperty().get();
        });
    }

    public Group getSettingsRoot() {
        return settingsRoot;
    }
}
