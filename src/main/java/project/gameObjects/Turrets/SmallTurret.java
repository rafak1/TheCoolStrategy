package project.gameObjects.Turrets;

import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
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
		rateOfFire=300;
		rotationDelay=1;
		damage=3;
		Player.changePlayerMoney(-price);
		turretImage=drawTurret("/images/gameObjects/smallTurret.png");
		rt=new RotateTransition(Duration.seconds(rotationDelay), turretImage);
		rotate=new Rotate();
		idle();
		createRadius();
	}

}
