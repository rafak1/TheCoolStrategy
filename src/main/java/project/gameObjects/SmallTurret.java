package project.gameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.GameMaster;
import project.Player;

import java.util.Objects;

import static project.GameMaster.board;
import static project.GameMaster.grid;
import static project.MainVariables.sizeY;
import static project.TurretVariables.smallTurretPrice;

public class SmallTurret implements Tower
{
	Integer X;
	Integer Y;
	Integer radius;
	Integer damage;

	/**
	 * This is base tower- this class is supposed to be extended. But we don't have any turret types yet, so everything is here
	 *
	 * @param posX
	 * @param posY
	 */
	public SmallTurret()
	{
		radius=2;
		damage=20;
	}

	public SmallTurret(int posX, int posY)
	{
		X=posX;
		Y=posY;
		drawTurret("/images/smallTurret.png");
	}

	@Override
	public void drawTurret(String imgSrc)
	{
		Player.money-=smallTurretPrice;
		GameMaster.moneyText.setText(String.valueOf(Player.money));
		Image turretImage=new Image(Objects.requireNonNull(getClass().getResource(imgSrc)).toString(), sizeY/10, sizeY/10, true, true);
		board[X][Y]=new ImageView(turretImage);
		grid.add(board[X][Y], X, Y, 1, 1);
	}

	@Override
	public void towerListener()
	{
//shooting logic
	}
}
