package project.gameObjects.Turrets;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import project.Player;

public class BigTurret extends Turret
{
	public static Integer price=40;

	/**
	 * Places a new turret
	 *
	 * @param posX row of new turret
	 * @param posY column of new turret
	 */
	public BigTurret(int posX, int posY)
	{
		X=posX;
		Y=posY;
		radius=300;
		damage=30;
		Player.changePlayerMoney(-price);
		turretImage=drawTurret("/images/bigTurret.png");
		rt=new RotateTransition(Duration.seconds(2), turretImage);
		rt.setFromAngle(-45);
		rt.setByAngle(45);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setAutoReverse(true);
		idle();
		createRadius();
	}

}
