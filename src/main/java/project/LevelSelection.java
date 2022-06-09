package project;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.Levels.IncorrectEnemyIdException;
import project.Levels.IncorrectPathException;
import project.Levels.NoSuchLevelException;
import project.Levels.WrongFileFormatException;

import java.util.Objects;

import static project.GameMaster.levelLoader;
import static project.GameMaster.masterRoot;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;
import static project.Menu.menuRoot;
import static project.Menu.scene;
import static project.MessagesAndEffects.showMessage;

/**
 * You can select a level from this class
 */
public class LevelSelection {
	public static Group selectionRoot = new Group();
	static Settings settingsRoot = new Settings(selectionRoot);
	GameMaster gameMaster;
	int currentLevel;

	public LevelSelection(Stage stage)
	{
		gameMaster=new GameMaster();

		FileChooser fileChooser=new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Level", "*.lvl"));
		Button[] possibleLevels = new Button[6];
		Canvas canvas = new Canvas(sizeX, sizeY);
		selectionRoot.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		//drawing background
		Image background = new Image(Objects.requireNonNull(getClass().getResource("/images/UI/background.png")).toString());
		gc.drawImage(background, 0, 0, sizeX, sizeY);

		for (int i = 0; i < 6; i++) {
			possibleLevels[i] = new ImageButton("/images/UI/levelbutton" + (i + 1) + ".png", 0, 0, 0, 0).get();
			possibleLevels[i].setPrefSize(sizeX * 0.2, sizeY * 0.1);
			int finalI = i;
			possibleLevels[i].setOnAction(value -> {
				currentLevel = finalI;
				try {
					levelLoader.load(finalI);
				} catch (Throwable a)
				{
					a.printStackTrace();
				}
				gameMaster.levelCreator();
				scene.setRoot(masterRoot);
			});
		}

		FlowPane flow=new FlowPane((sizeX*0.2), sizeY*0.1, possibleLevels);
		flow.setPrefWrapLength(sizeX);

		selectionRoot.getChildren().add(flow);


		ImageButton backButton = new ImageButton("/images/UI/backbutton.png", sizeX * 0.8, sizeY * 0.8, (int) (sizeX * 0.1), (int) (sizeY * 0.1));
		ImageButton settingsButton = new ImageButton("/images/UI/settingsbutton.png", sizeX * 0.15, sizeY * 0.8, (int) (sizeX * 0.15), (int) (sizeY * 0.1));
		selectionRoot.getChildren().add(backButton.get());
		selectionRoot.getChildren().add(settingsButton.get());
		backButton.get().setOnAction(e -> scene.setRoot(menuRoot));
		settingsButton.get().setOnAction(e -> scene.setRoot(settingsRoot.getSettingsRoot()));

		ImageButton loadLevelButton = new ImageButton("/images/UI/loadlevelbutton.png", sizeX * 0.4, sizeY * 0.8, (int) (sizeX * 0.23), (int) (sizeY * 0.1));
		selectionRoot.getChildren().add(loadLevelButton.get());
		loadLevelButton.get().setOnAction(value -> {
			try {
				//can't load the file
				levelLoader.load(fileChooser.showOpenDialog(stage));
				gameMaster.levelCreator();
				scene.setRoot(masterRoot);
			} catch (WrongFileFormatException a) {
				showMessage("Wrong file format!", 200.0, 40.0, sizeX / 2 - 100, sizeY / 2 - 20, 2, selectionRoot);
			} catch (IncorrectPathException a) {
				showMessage("Incorrect enemy path!", 200.0, 40.0, sizeX / 2 - 100, sizeY / 2 - 20, 2, selectionRoot);
			} catch (IncorrectEnemyIdException a) {
				showMessage("Incorrect enemy ID!", 200.0, 40.0, sizeX / 2 - 100, sizeY / 2 - 20, 2, selectionRoot);
			} catch (NoSuchLevelException a) {
				showMessage("No such level!", 200.0, 40.0, sizeX / 2 - 100, sizeY / 2 - 20, 2, selectionRoot);
			}
		});
	}

}
