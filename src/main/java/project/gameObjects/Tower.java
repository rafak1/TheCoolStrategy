package project.gameObjects;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

import static project.GameMaster.board;
import static project.GameMaster.grid;
import static project.MainVariables.sizeY;

public class Tower
{
	/**
	 * Draws a turret on the board
	 *
	 * @param imgSrc path to the image
	 * @param X      tbh, I don't remember
	 * @param Y      tbh, I don't remember
	 */
	public Node drawTurret(String imgSrc, Integer X, Integer Y)
	{
		Image turretImage=new Image(Objects.requireNonNull(getClass().getResource(imgSrc)).toString(), sizeY/10, sizeY/10, true, true);
		board[X][Y]=new ImageView(turretImage);
		grid.add(board[X][Y], X, Y, 1, 1);
		return board[X][Y];
	}

	/**
	 * Rotates turret
	 */
	void rotateTurret(Node turret)
	{
		RotateTransition rt=new RotateTransition(Duration.seconds(1), turret);
		rt.setFromAngle(-45);
		rt.setByAngle(45);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setAutoReverse(true);

		rt.play();
	}

	/**
	 * Event Listener - enemy entered the radius, enemy leaves, killed an enemy, etc.
	 */
	void towerListener()
	{

	}
}
