package project.gameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.GameMaster;
import project.Player;

import java.util.Objects;

import static project.GameMaster.board;
import static project.GameMaster.grid;
import static project.MainVariables.sizeY;

public class BigTurret implements Tower
{
	Integer X;
	Integer Y;
	public static Integer bigTurretPrice=40;
	Integer radius;
	Integer damage;

	public BigTurret()
	{
		radius=4;
		damage=30;
	}

	public BigTurret(int posX, int posY)
	{
		X=posX;
		Y=posY;
		drawTurret("/images/bigTurret.png");
	}

	@Override
	public void drawTurret(String imgSrc)
	{
		Player.money-=bigTurretPrice;
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
