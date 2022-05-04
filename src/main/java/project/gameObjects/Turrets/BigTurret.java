package project.gameObjects.Turrets;

import javafx.scene.Node;
import project.Player;

public class BigTurret extends Turret
{
	Integer X;
	Integer Y;
	public static Integer price=40;
	Integer radius=4;
	Integer damage=30;
	Node turretImage;


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
		Player.changePlayerMoney(-price);
		turretImage=drawTurret("/images/bigTurret.png", X, Y);
		rotateTurret(turretImage);
	}

}
