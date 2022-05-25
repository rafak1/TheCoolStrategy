package project.gameObjects.Turrets;

import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
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
		rotationDelay=2;
		radius=300;
		rateOfFire=900;
		damage=10;
		Player.changePlayerMoney(-price);
		turretImage=drawTurret("/images/gameObjects/bigTurret.png");
		rt=new RotateTransition(Duration.seconds(rotationDelay), turretImage);
		rotate=new Rotate();
		idle();
		createRadius();
	}

}
