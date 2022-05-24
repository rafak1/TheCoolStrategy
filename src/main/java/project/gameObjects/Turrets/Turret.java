package project.gameObjects.Turrets;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import project.gameObjects.Enemies.Enemy;

import java.util.Objects;
import java.util.Queue;

import static project.GameMaster.*;
import static project.MainVariables.gridSizeY;
import static project.MainVariables.sizeY;

public class Turret
{
	Integer X;
	Integer Y;
	Integer radius;
	Integer damage;
	Queue <Enemy> inside;
	RotateTransition rt;
	Node turretImage;
	Circle turretRadius;

	/**
	 * Draws a turret on the board
	 *
	 * @param imgSrc path to the image
	 */
	public Node drawTurret(String imgSrc)
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
		rt=new RotateTransition(Duration.seconds(1), turret);
		rt.setFromAngle(-45);
		rt.setByAngle(45);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setAutoReverse(true);

		rt.play();
	}

	void createRadius()
	{
		Circle circle=new Circle();
		circle.setLayoutX(X*sizeY/gridSizeY+50);
		circle.setLayoutY(Y*sizeY/gridSizeY+50);
		circle.setRadius(radius);
		circle.setOpacity(0);
		circle.setMouseTransparent(true);
		turretRadius=circle;
		masterRoot.getChildren().add(turretRadius);
	}
}
