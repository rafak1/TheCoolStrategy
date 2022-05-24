package project.gameObjects.Turrets;

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
		rotateTurret(turretImage);
		createRadius();
	}
}
