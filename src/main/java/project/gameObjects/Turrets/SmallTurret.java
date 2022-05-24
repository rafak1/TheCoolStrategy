package project.gameObjects.Turrets;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import project.Player;

public class SmallTurret extends Turret
{
	public static Integer price=25;

	/**
	 * Places a new turret
	 *
	 * @param posX row of new turret
	 * @param posY column of new turret
	 */
	public SmallTurret(int posX, int posY)
	{
		X=posX;
		Y=posY;
		radius=200;
		damage=20;
		Player.changePlayerMoney(-price);
		turretImage=drawTurret("/images/smallTurret.png");
		rt=new RotateTransition(Duration.seconds(1), turretImage);
		rt.setFromAngle(-45);
		rt.setByAngle(45);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setAutoReverse(true);
		idle();
		createRadius();
	}

}
