package project;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import project.gameObjects.BigTurret;
import project.gameObjects.SmallTurret;
import project.gameObjects.Tower;

import java.util.ArrayList;

import static project.GameMaster.*;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;
import static project.TurretVariables.bigTurretPrice;
import static project.TurretVariables.smallTurretPrice;

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
		ImageButton bigButton=new ImageButton("/images/bigTurret.png", sizeX-250, sizeY-675, 100, 100);
		masterRoot.getChildren().add(bigButton.get());
		bigButton.get().setOnAction(e->{
			turretType=1;
		});
		Label bigPrice=new Label(bigTurretPrice.toString()+" $");
		bigPrice.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		bigPrice.setLayoutX(sizeX-150);
		bigPrice.setLayoutY(sizeY-665);
		masterRoot.getChildren().add(bigPrice);


		ImageButton smallButton=new ImageButton("/images/smallTurret.png", sizeX-250, sizeY-575, 100, 100);
		masterRoot.getChildren().add(smallButton.get());
		smallButton.get().setOnAction(e->{
			turretType=0;
		});
		Label smallPrice=new Label(smallTurretPrice.toString()+" $");
		smallPrice.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		smallPrice.setLayoutX(sizeX-150);
		smallPrice.setLayoutY(sizeY-565);
		masterRoot.getChildren().add(smallPrice);


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
				if(turretType==0)
				{
					if(Player.money<smallTurretPrice)
					{
						showMessage("You are broke", 180, 40, 2);
					}
					else
					{
						currLevel.levelObjects[rowIndex][colIndex]=2;
						allTowers.add(new SmallTurret(rowIndex, colIndex));
					}
				}
				else
				{
					if(Player.money<bigTurretPrice)
					{
						showMessage("You are broke", 180, 40, 2);
					}
					else
					{
						currLevel.levelObjects[rowIndex][colIndex]=2;
						allTowers.add(new BigTurret(rowIndex, colIndex));
					}
				}
			}
			else
			{
				showMessage("You can't place turrets\n while enemies are walking!", 250, 50, 3);
			}
		}
	}

	public void showMessage(String s, double width, double height, double delay)
	{
		Rectangle r=new Rectangle();
		r.setWidth(width);
		r.setHeight(height);
		r.setFill(Color.RED);

		Text text=new Text(s);
		text.setFill(Color.BEIGE);
		text.setStyle("-fx-font: 20 arial;");

		StackPane stack=new StackPane();
		stack.getChildren().addAll(r, text);
		stack.setLayoutX(clickX);
		stack.setLayoutY(clickY);

		masterRoot.getChildren().add(stack);

		//fancy fade away transition
		FadeTransition ft=new FadeTransition(Duration.seconds(delay), stack);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setCycleCount(4);
		ft.play();

		//remove after it fades away
		PauseTransition transition=new PauseTransition(Duration.seconds(delay));
		transition.play();
		transition.setOnFinished(e->masterRoot.getChildren().remove(stack));
	}
}
