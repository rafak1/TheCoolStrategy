package project;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
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
import project.gameObjects.Turrets.BigTurret;
import project.gameObjects.Turrets.SmallTurret;
import project.gameObjects.Turrets.Turret;

import java.util.ArrayList;
import java.util.Objects;

import static project.GameMaster.*;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;

public class DeployTurret
{
	Node clickedNode;
	Integer turretType;//0-not selected, 1-small turret, 2- big turret
	Double clickX;
	Double clickY;
	Integer colIndex;
	Integer rowIndex;
	public static ArrayList <Turret> allTowers=new ArrayList <>();//I don't know if wee need it, but it may be useful

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

	/**
	 * This menu provides a lot of different actions when grid element is clicked
	 */
	public void showContextMenu()
	{
		if (levelLoader.getLevelObjects()[rowIndex][colIndex] == 0) {
			if (turretType == 0) {
				showMessage("Select a Tower", 180, 40, 2);
			} else if (turretType == 1) {
				if (Player.money.get() < SmallTurret.price) {
					showMessage("You are broke", 180, 40, 2);
				} else {
					levelLoader.getLevelObjects()[rowIndex][colIndex]=2;
					Turret t=new SmallTurret(rowIndex, colIndex);
					allTowers.add(t);
					if(ed!=null)
					{ed.newTower(t);}
				}
			} else if (turretType == 2) {
				if (Player.money.get() < BigTurret.price) {
					showMessage("You are broke", 180, 40, 2);
				} else
				{
					levelLoader.getLevelObjects()[rowIndex][colIndex]=2;
					Turret t=new BigTurret(rowIndex, colIndex);
					allTowers.add(t);
					if(ed!=null)
					{ed.newTower(t);}
				}
			}
		} else {
			showMessage("You can't place a turret here", 180, 40, 2);
		}
	}

	/**
	 * TODO można wykorzystać gdzieś indziej więc może swoja klasa na to? albo nawet połączyć z imagebutton
	 *
	 * @param s      message to put in a box
	 * @param width  width of the message
	 * @param height height of the message
	 * @param delay  how long the message is shown
	 */
	void showMessage(String s, double width, double height, double delay)
	{
		Rectangle r=new Rectangle();
		r.setWidth(width);
		r.setHeight(height);
		r.setFill(Color.RED);
		r.setMouseTransparent(true);

		Text text=new Text(s);
		text.setFill(Color.BEIGE);
		text.setStyle("-fx-font: 20 arial;");
		text.setMouseTransparent(true);

		StackPane stack = new StackPane();
		stack.getChildren().addAll(r, text);
		stack.setLayoutX(clickX);
		stack.setLayoutY(clickY);
		stack.setMouseTransparent(true);

		masterRoot.getChildren().add(stack);

		//fancy fade away transition
		FadeTransition ft = new FadeTransition(Duration.seconds(delay), stack);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setCycleCount(4);
		ft.play();

		//remove after it fades away
		PauseTransition transition = new PauseTransition(Duration.seconds(delay));
		transition.play();
		transition.setOnFinished(e->masterRoot.getChildren().remove(stack));
	}

	void drawButtons()
	{
		Button smallButton=singleButton("/images/smallTurret.png", sizeX-290, sizeY-600, SmallTurret.price);
		smallButton.setOnAction(e->turretType=1);
		Button bigButton=singleButton("/images/bigTurret.png", sizeX-290, sizeY-750, BigTurret.price);
		bigButton.setOnAction(e->turretType=2);
	}

	Button singleButton(String path, double posX, double posY, Integer price)
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
