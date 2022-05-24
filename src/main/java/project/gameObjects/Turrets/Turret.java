package project.gameObjects.Turrets;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import project.gameObjects.Enemies.Enemy;

import java.util.Objects;

import static project.GameMaster.*;
import static project.MainVariables.gridSizeY;
import static project.MainVariables.sizeY;

public class Turret
{
	Integer X;
	Integer Y;
	Integer radius;
	Integer damage;
	RotateTransition rt;
	Node turretImage;
	Circle turretRadius;
	Enemy target;

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
	void idle()
	{
		rt.play();
	}

	void createRadius()
	{
		Circle circle=new Circle();
		circle.setLayoutX(X*sizeY/gridSizeY+50);
		circle.setLayoutY(Y*sizeY/gridSizeY+50);
		circle.setRadius(radius);
		circle.setOpacity(0.4);
		circle.setMouseTransparent(true);
		turretRadius=circle;
		masterRoot.getChildren().add(turretRadius);
	}

	void followAnEnemy(Enemy e)
	{
		rt.pause();
		//TODO: Follows an enemy
	}

	void findTarget()
	{
		for(int i=0; i<currWave.size(); i++)
		{
			Enemy e=currWave.get(i);
			if(e!=null && e.isDeployed())
			{
				if(inRange(e))
				{
					if(target!=e)
					{rt.pause();}
					target=e;
					return;
				}
			}
		}
		target=null;
		idle();
	}

	boolean inRange(Enemy e)
	{
		return (turretRadius.getLayoutX()-e.getX())*(turretRadius.getLayoutX()-e.getX())+(turretRadius.getLayoutY()-e.getY())*(turretRadius.getLayoutY()-e.getY())<radius*radius;
	}
}
