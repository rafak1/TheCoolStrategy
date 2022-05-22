package project;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import static project.GameMaster.masterRoot;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;
import static project.Menu.menuRoot;
import static project.Menu.scene;

/**
 * You can select a level from this class
 */
public class LevelSelection
{
	public static Group selectionRoot;
	GameMaster gameMaster;
	int currentLevel;

	public LevelSelection()
	{
		selectionRoot = new Group();
		gameMaster = new GameMaster();

		Button[] possibleLevels = new Button[6];

		for (int i = 0; i < 6; i++) {

			Canvas canvas = new Canvas(sizeX, sizeY);
			selectionRoot.getChildren().add(canvas);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.setFill(Color.web("0xfd4d5d"));
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

			possibleLevels[i] = new ImageButton("/images/levelbutton" + (i + 1) + ".png", 0, 0, 0, 0).get();
			possibleLevels[i].setPrefSize(sizeX * 0.2, sizeY * 0.1);
			currentLevel = i;
			int finalI = i;
			possibleLevels[i].setOnAction(value -> {
				gameMaster.loadLevel(finalI);
				scene.setRoot(masterRoot);
			});
		}
		FlowPane flow = new FlowPane((sizeX * 0.2), sizeY * 0.1, possibleLevels);
		flow.setStyle("-fx-background-color: #fd4d5d;");
		flow.setPrefWrapLength(sizeX);

		selectionRoot.getChildren().add(flow);


		ImageButton backButton = new ImageButton("/images/backbutton.png", sizeX * 0.8, sizeY * 0.8, (int) (sizeX * 0.1), (int) (sizeY * 0.1));
		ImageButton settingsButton = new ImageButton("/images/settingsbutton.png", sizeX * 0.15, sizeY * 0.8, (int) (sizeX * 0.15), (int) (sizeY * 0.1));
		selectionRoot.getChildren().add(backButton.get());
		selectionRoot.getChildren().add(settingsButton.get());
		backButton.get().setOnAction(e -> scene.setRoot(menuRoot));
		settingsButton.get().setOnAction(e -> scene.setRoot(menuRoot)); //TODO
	}
}
