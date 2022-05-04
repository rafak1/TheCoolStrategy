package project.gameObjects.Turrets;

import javafx.scene.Node;
import project.Player;

public class SmallTurret extends Turret
{
	Integer X;
	Integer Y;
	public static Integer price=25;
	Integer radius=2;
	Integer damage=20;
	Node turretImage;

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
		Player.changePlayerMoney(-price);
		turretImage=drawTurret("/images/smallTurret.png", X, Y);
		rotateTurret(turretImage);
	}
}
