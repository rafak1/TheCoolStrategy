package project;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import project.gameObjects.BigTurret;
import project.gameObjects.SmallTurret;
import project.gameObjects.Tower;

import java.util.ArrayList;
import java.util.Objects;

import static project.GameMaster.*;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;
import static project.gameObjects.BigTurret.bigTurretPrice;
import static project.gameObjects.SmallTurret.smallTurretPrice;

public class DeployTurret
{
	Node clickedNode;
	Integer turretType;
	Double clickX;
	Double clickY;
	Integer colIndex;
	Integer rowIndex;
	ArrayList <Tower> allTowers=new ArrayList <>();

	/**
	 * Placing turrets on the map
	 */
	public DeployTurret()
	{
		turretType=0;
		drawButtons();


		EventHandler <MouseEvent> eventHandler=e->{
			clickX=e.getSceneX();
			clickY=e.getSceneY();
			clickedNode=e.getPickResult().getIntersectedNode();
			colIndex=GridPane.getRowIndex(clickedNode);
			rowIndex=GridPane.getColumnIndex(clickedNode);
			showContextMenu();
		};
		grid.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	public void showContextMenu()
	{
		if(currLevel.levelObjects[rowIndex][colIndex]==0)
		{
			if(gameState==0)
			{
				if (turretType == 0) {
					if (Player.money.get() < smallTurretPrice) {
						showMessage("You are broke", 180, 40, 2, clickX, clickY, masterRoot);
					} else {
						currLevel.levelObjects[rowIndex][colIndex] = 2;
						allTowers.add(new SmallTurret(rowIndex, colIndex));
					}
				} else {
					if (Player.money.get() < bigTurretPrice) {
						showMessage("You are broke", 180, 40, 2, clickX, clickY, masterRoot);
					} else {
						currLevel.levelObjects[rowIndex][colIndex] = 2;
						allTowers.add(new BigTurret(rowIndex, colIndex));
					}
				}
			} else {
				showMessage("You can't place turrets\n while enemies are walking!", 250, 50, 3, clickX, clickY, masterRoot);
			}
		}
	}

	public static void showMessage(String s, double width, double height, double delay, double clickX, double clickY, Group root)    //TODO można wykorzystać gdzieś indziej więc może swoja klasa na to? albo nawet połączyć z imagebutton
	{
		Rectangle r = new Rectangle();
		r.setWidth(width);
		r.setHeight(height);
		r.setFill(Color.RED);

		Text text = new Text(s);
		text.setFill(Color.BEIGE);
		text.setStyle("-fx-font: 20 arial;");

		StackPane stack = new StackPane();
		stack.getChildren().addAll(r, text);
		stack.setLayoutX(clickX);
		stack.setLayoutY(clickY);

		root.getChildren().add(stack);

		//fancy fade away transition
		FadeTransition ft = new FadeTransition(Duration.seconds(delay), stack);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setCycleCount(4);
		ft.play();

		//remove after it fades away
		PauseTransition transition = new PauseTransition(Duration.seconds(delay));
		transition.play();
		transition.setOnFinished(e -> root.getChildren().remove(stack));
	}

	void drawButtons()
	{
		Button smallButton=showButton("/images/smallTurret.png", sizeX-290, sizeY-600, smallTurretPrice);
		smallButton.setOnAction(e->{
			turretType=0;
		});
		Button bigButton=showButton("/images/bigTurret.png", sizeX-290, sizeY-750, bigTurretPrice);
		bigButton.setOnAction(e->{
			turretType=1;
		});
	}

	Button showButton(String path, double posX, double posY, Integer price)
	{
		Button button=new Button(price+" $");
		button.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResource(path)).toString(), 100, 100, true, true)));
		button.setLayoutX(posX);
		button.setLayoutY(posY);
		button.setStyle("-fx-font-size:40");
		masterRoot.getChildren().add(button);
		return button;
	}
}
