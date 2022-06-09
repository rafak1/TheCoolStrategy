package project.gameObjects.Turrets;

import javafx.animation.RotateTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import project.Player;

import java.util.Objects;

import static project.MainVariables.*;

public class MediumTurret extends BasicTurret
{
	public static Integer price=40;

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
	    radius=sizeY/gridSizeY*2.5;
	    rateOfFire=400;
	    damage=3;
	    sound=new Media(Objects.requireNonNull(getClass().getResource("/music/turret.m4a")).toString());
	    musicPlayer=new MediaPlayer(sound);
	    musicPlayer.setVolume(effectsSound);
	    musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	    Player.changePlayerMoney(-price);
	    turretImage=drawTurret("/images/gameObjects/mediumTurret.png");
	    rt=new RotateTransition();
	    rotate=new Rotate();
	    idle();
	    createRadius();
    }

}
