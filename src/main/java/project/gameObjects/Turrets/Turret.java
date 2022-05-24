package project.gameObjects.Turrets;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
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
	long frequency;
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
			{idle();}
			else
			{
				followAnEnemy();
				shootAnimation();
				target.damageEnemy(damage);
			}
			try
			{
				Thread.sleep(frequency);
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
		Image explosionImage=new Image(Objects.requireNonNull(getClass().getResource("/images/explosion.png")).toString(), sizeY/10, sizeY/10, true, true);
		ImageView iv=new ImageView(explosionImage);
		iv.setX(turretRadius.getLayoutX()-50);
		iv.setY(turretRadius.getLayoutY()-50);
		Platform.runLater(()->masterRoot.getChildren().add(iv));


		//fancy fade away transition
		FadeTransition ft=new FadeTransition(Duration.seconds(0.2), iv);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setCycleCount(4);
		ft.play();

		//remove after it fades away
		PauseTransition transition=new PauseTransition(Duration.seconds(0.2));
		transition.play();
		transition.setOnFinished(e->masterRoot.getChildren().remove(iv));
	}

	boolean findTarget()
	{
		for(Enemy e: currWave)
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
