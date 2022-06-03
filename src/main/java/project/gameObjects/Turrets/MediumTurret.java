package project.gameObjects.Turrets;

import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import project.Player;

public class MediumTurret extends BasicTurret {
    public static Integer price = 40;

    /**
     * Places a new turret
     *
     * @param posX row of new turret
     * @param posY column of new turret
     */
    public MediumTurret(int posX, int posY)
	{
		X=posX;
		Y=posY;
		radius=300;
		rateOfFire=400;
		damage=3;
		Player.changePlayerMoney(-price);
		turretImage=drawTurret("/images/gameObjects/mediumTurret.png");
		rt=new RotateTransition();
		rotate=new Rotate();
		idle();
		createRadius();
	}

}
