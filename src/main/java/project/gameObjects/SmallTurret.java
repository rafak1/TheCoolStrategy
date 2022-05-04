package project.gameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.Player;

import java.util.Objects;

import static project.GameMaster.board;
import static project.GameMaster.grid;
import static project.MainVariables.sizeY;

public class SmallTurret implements Tower
{
	Integer X;
	Integer Y;
	public static Integer smallTurretPrice=25;
	Integer radius;
	Integer damage;
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
		Player.changePlayerMoney(-smallTurretPrice);
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
