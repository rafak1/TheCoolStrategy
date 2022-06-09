package project.gameObjects.Turrets;

import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import project.Player;

import static project.MainVariables.gridSizeY;
import static project.MainVariables.sizeY;

public class BigTurret extends BasicTurret
{
	public static Integer price=75;

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
		radius=sizeY/gridSizeY*1.5;
		rateOfFire=100;
		damage=1;
		Player.changePlayerMoney(-price);
		turretImage=drawTurret("/images/gameObjects/bigTurret.png");
		rt=new RotateTransition();
		rotate=new Rotate();
		idle();
		createRadius();
	}

}
