package project.gameObjects.Turrets;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
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
	Rotate rotate;
	boolean isIdle;

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
		rt.stop();
		rt.setDuration(Duration.millis(800));
		rt.setNode(turretImage);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setToAngle(turretImage.getRotate()+360);
		rt.play();
	}

	void followAnEnemy()
	{
		rt.stop();
		rt.setDuration(Duration.millis(100));
		rt.setCycleCount(1);
		rt.setToAngle(Math.toDegrees(Math.atan2(target.getX()+50-turretRadius.getLayoutX(), turretRadius.getLayoutY()-50-target.getY())));
		rt.setAutoReverse(false);
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
				if(!isIdle)
				{idle();}
				isIdle=true;
			}
			else
			{
				isIdle=false;
				followAnEnemy();
				shootAnimation();
				synchronized(target)
				{
					if(target!=null)
					{target.damageEnemy(damage);}
				}
			}
			try
			{
				Thread.sleep(rateOfFire);
			}catch(InterruptedException ignored) {}
		}
	}

	void shootAnimation()
	{
		MessagesAndEffects.showEffect("/images/gameObjects/explosion.png", turretRadius.getLayoutX()-50, turretRadius.getLayoutY()-50, 0.2);
	}

	boolean findTarget()
	{
		synchronized(currWave) {
			double minimum=0;
			Enemy curr=null;
			for (Enemy e : currWave)//TODO: SYNCHRONIZED ENEMIES
			{
				if (e != null && e.isDeployed()) {
					if (inRange(e))
					{
						double a=((double)e.getPathTransition().getCurrentTime().toMillis())/(double)e.getPathTransition().getDuration().toMillis();
						if(a>minimum)
						{
							curr=e;
							minimum=a;
						}
					}
				}
			}
			if (curr != null) {
				target = curr;
				return true;
			}
			target = null;
			return false;
		}
	}

	boolean inRange(Enemy e)
	{
		return distance(e)<radius*radius;
	}

	double distance(Enemy e)
	{
		return (turretRadius.getLayoutX()-e.getX()-50)*(turretRadius.getLayoutX()-e.getX()-50)+(turretRadius.getLayoutY()-e.getY()-50)*(turretRadius.getLayoutY()-e.getY()-50);
	}
}
