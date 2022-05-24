package project.gameObjects.Turrets;

import static project.DeployTurret.allTowers;
import static project.GameMaster.gameState;

public class EnemyDetection
{
	public static Thread listener;
	public EnemyDetection()
	{
		listener=new Thread(this::dispatcher);
		listener.setDaemon(true);
		listener.start();
	}

	void dispatcher()
	{
		//basically checks the location of each enemy every x seconds
		while(gameState==1)
		{
			Thread.onSpinWait();
			try
			{
				Thread.sleep(50);
			}catch(InterruptedException ignored) {}

			for(Turret t: allTowers)
			{
				t.findTarget();
			}
		}
	}
}
