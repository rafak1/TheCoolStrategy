package project.gameObjects.Turrets;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import project.Player;

import java.util.ArrayList;
import java.util.Objects;

import static project.GameMaster.*;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;
import static project.MessagesAndEffects.showMessage;

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
				showMessage("Select a Tower", 180, 40, clickX, clickY, 2);
			} else if (turretType == 1) {
				if (Player.money.get() < SmallTurret.price) {
					showMessage("You are broke", 180, 40, clickX, clickY, 2);
				} else {
					levelLoader.getLevelObjects()[rowIndex][colIndex]=2;
					Turret t=new SmallTurret(rowIndex, colIndex);
					allTowers.add(t);
					if(ed!=null)
					{ed.newTower(t);}
				}
			} else if(turretType==2)
			{
				if(Player.money.get()<MediumTurret.price)
				{
					showMessage("You are broke", 180, 40, clickX, clickY, 2);
				}
				else
				{
					levelLoader.getLevelObjects()[rowIndex][colIndex]=2;
					Turret t=new MediumTurret(rowIndex, colIndex);
					allTowers.add(t);
					if(ed!=null)
					{ed.newTower(t);}
				}
			}
			else if(turretType==3)
			{
				if(Player.money.get()<BigTurret.price)
				{
					showMessage("You are broke", 180, 40, clickX, clickY, 2);
				}
				else
				{
					levelLoader.getLevelObjects()[rowIndex][colIndex]=2;
					Turret t=new BigTurret(rowIndex, colIndex);
					allTowers.add(t);
					if(ed!=null)
					{ed.newTower(t);}
				}
			}
		} else {
			showMessage("You can't place a turret here", 250, 40, clickX, clickY, 2);
		}
	}

	void drawButtons()
	{
		Button smallButton=singleButton("/images/gameObjects/smallTurret.png", sizeX-290, sizeY-600, SmallTurret.price);
		smallButton.setOnAction(e->turretType=1);
		Button mediumButton=singleButton("/images/gameObjects/mediumTurret.png", sizeX-290, sizeY-750, MediumTurret.price);
		mediumButton.setOnAction(e->turretType=2);
		Button bigButton=singleButton("/images/gameObjects/bigTurret.png", sizeX-290, sizeY-900, BigTurret.price);
		bigButton.setOnAction(e->turretType=3);
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
