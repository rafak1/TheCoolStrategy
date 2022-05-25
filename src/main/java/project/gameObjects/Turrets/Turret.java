package project.gameObjects.Turrets;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import project.MessagesAndEffects;
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
	long rateOfFire;
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
		circle.setOpacity(0.05);
		circle.setMouseTransparent(true);
		turretRadius=circle;
		masterRoot.getChildren().add(turretRadius);
	}

	void fightLogic()
	{
		while(gameState==1)
		{
			if(!findTarget())
			{
				idle();
			}
			else
			{
				followAnEnemy();
				shootAnimation();
				target.damageEnemy(damage);
			}
			try
			{
				Thread.sleep(rateOfFire);
			}catch(InterruptedException ignored) {}
		}
	}

	void followAnEnemy()
	{
		rt.pause();
		//TODO: Follows an enemy
	}

	void shootAnimation()
	{
		MessagesAndEffects.showEffect("/images/explosion.png", turretRadius.getLayoutX()-50, turretRadius.getLayoutY()-50, 0.2);
	}

	boolean findTarget()
	{
		for(Enemy e: currWave)//TODO: SYNCHRONIZED ENEMIES
		{
			if(e!=null && e.isDeployed())
			{
				if(inRange(e))
				{
					target=e;
					return true;
				}
			}
		}
		target=null;
		return false;
	}

	boolean inRange(Enemy e)
	{
		return (turretRadius.getLayoutX()-e.getX()-25)*(turretRadius.getLayoutX()-e.getX()-25)+(turretRadius.getLayoutY()-e.getY()-25)*(turretRadius.getLayoutY()-e.getY()-25)<radius*radius;
	}
}
