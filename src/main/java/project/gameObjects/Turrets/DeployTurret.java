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
import java.util.Random;

import static project.GameMaster.*;
import static project.MainVariables.*;
import static project.MessagesAndEffects.showMessage;

public class DeployTurret {
    Node clickedNode;
    Integer turretType;//0-not selected, 1-small turret, 2- big turret
    Double clickX;
    Double clickY;
    Integer colIndex;
    Integer rowIndex;
    public static ArrayList<BasicTurret> allTowers = new ArrayList<>();//I don't know if wee need it, but it may be useful

    /**
     * Placing turrets on the map
     */
    public DeployTurret() {
        turretType = 0;
        drawButtons();

        EventHandler<MouseEvent> eventHandler = e -> {
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
			if(turretType==0)
			{
				showMessage("Select a Tower", 180, 40, clickX, clickY, 2, masterRoot);
			}
			else if(turretType==2)
			{
				if(Player.money.get()<SmallTurret.price)
				{
					showMessage("You are broke", 180, 40, clickX, clickY, 2, masterRoot);
				}
				else
				{
					levelLoader.getLevelObjects()[rowIndex][colIndex]=3;
					BasicTurret t=new SmallTurret(rowIndex, colIndex);
					allTowers.add(t);
					if(ed!=null)
					{
						ed.newTower(t);
					}
				}
			}
			else if(turretType==3)
			{
				if(Player.money.get()<MediumTurret.price)
				{
					showMessage("You are broke", 180, 40, clickX, clickY, 2, masterRoot);
				}
				else
				{
					levelLoader.getLevelObjects()[rowIndex][colIndex]=3;
					BasicTurret t=new MediumTurret(rowIndex, colIndex);
					allTowers.add(t);
					if(ed!=null)
					{
						ed.newTower(t);
					}
				}
			}
			else if(turretType==4)
			{
				if(Player.money.get()<BigTurret.price)
				{
					showMessage("You are broke", 180, 40, clickX, clickY, 2, masterRoot);
				}
				else
				{
					levelLoader.getLevelObjects()[rowIndex][colIndex]=3;
					BasicTurret t=new BigTurret(rowIndex, colIndex);
					allTowers.add(t);
					if(ed!=null)
					{
						ed.newTower(t);
					}
				}
			}
			else if(turretType==1)
			{
				showMessage("You can't destroy this", 250, 40, clickX, clickY, 2, masterRoot);
			}

		}
		else if(levelLoader.getLevelObjects()[rowIndex][colIndex]==2)
		{
			if(turretType==1)
			{
				if(Player.money.get()<50)
				{
					showMessage("You are broke", 180, 40, clickX, clickY, 2, masterRoot);
				}
				else
				{
					Player.changePlayerMoney(-50);
					levelLoader.getLevelObjects()[rowIndex][colIndex]=0;
					grid.getChildren().remove(board[rowIndex][colIndex]);
					board[rowIndex][colIndex]=new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/terrain/desert.png")).toString(), gridSize, gridSize, true, true));
					if(new Random().nextBoolean())
					{board[rowIndex][colIndex].setScaleX(-1);}
					if(new Random().nextBoolean())
					{board[rowIndex][colIndex].setScaleY(-1);}
					grid.add(board[rowIndex][colIndex], rowIndex, colIndex, 1, 1);
				}
			}
			else
			{showMessage("You can't place a turret here", 250, 40, clickX, clickY, 2, masterRoot);}
		}
		else if(levelLoader.getLevelObjects()[rowIndex][colIndex]==3)
		{
			showMessage("You can't destroy this", 250, 40, clickX, clickY, 2, masterRoot);
			/*
			BasicTurret temp=null;
			for(BasicTurret t: allTowers)
			{
				if(t.X.equals(rowIndex)&&t.Y.equals(colIndex))
					temp=t;
			}
			levelLoader.getLevelObjects()[rowIndex][colIndex] = 0;
			if(temp!=null)
				allTowers.remove(temp);*/
		}
		else
		{
			if(turretType==1)
			{
				showMessage("You can't destroy this", 250, 40, clickX, clickY, 2, masterRoot);
			}
			else
			{
				showMessage("You can't place a turret here", 250, 40, clickX, clickY, 2, masterRoot);
			}
		}
	}

	void drawButtons()
	{
		Button hammerButton=singleButton("/images/UI/hammer.png", sizeX*0.853, sizeY*0.1, 50);
		hammerButton.setScaleX(0.75);
		hammerButton.setScaleY(0.75);
		hammerButton.setOnAction(e->turretType=1);
		Button smallButton=singleButton("/images/gameObjects/smallTurret.png", sizeX*0.853, sizeY*0.4, SmallTurret.price);
		smallButton.setScaleX(0.75);
		smallButton.setScaleY(0.75);
		smallButton.setOnAction(e->turretType=2);
		Button mediumButton=singleButton("/images/gameObjects/mediumTurret.png", sizeX*0.853, sizeY*0.3, MediumTurret.price);
		mediumButton.setScaleX(0.75);
		mediumButton.setScaleY(0.75);
		mediumButton.setOnAction(e->turretType=3);
		Button bigButton=singleButton("/images/gameObjects/bigTurret.png", sizeX*0.853, sizeY*0.2, BigTurret.price);
		bigButton.setScaleX(0.75);
		bigButton.setScaleY(0.75);
		bigButton.setOnAction(e->turretType=4);
	}

	Button singleButton(String path, double posX, double posY, Integer price) {
		Button button = new Button(price + " $");
		button.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResource(path)).toString(), sizeX * 0.0505, sizeX * 0.0505, true, true)));
		button.setLayoutX(posX);
		button.setLayoutY(posY);
		button.setStyle("-fx-font-size:40");
		masterRoot.getChildren().add(button);
		return button;
	}
}
