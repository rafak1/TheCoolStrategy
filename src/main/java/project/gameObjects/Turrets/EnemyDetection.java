package project.gameObjects.Turrets;

import project.gameObjects.Enemies.Enemy;

import static project.DeployTurret.allTowers;
import static project.GameMaster.currWave;
import static project.GameMaster.gameState;

public class EnemyDetection
{

	public EnemyDetection()
	{
		Thread listener=new Thread(this::dispatcher);
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
			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}

			for(Turret t: allTowers)
			{
				for(int i=0; i<currWave.size(); i++)
				{
					Enemy x=currWave.get(i);
					if(x!=null && x.isDeployed())
					{
						if((t.turretRadius.getLayoutX()-x.getX())*(t.turretRadius.getLayoutX()-x.getX())+(t.turretRadius.getLayoutY()-x.getY())*(t.turretRadius.getLayoutY()-x.getY())<t.radius*t.radius)
						{
							System.out.println("THEY INTERSECT");
						}
					}
				}
			}
		}
	}

	void addToTurret()
	{

	}

	void removeFromTurret()
	{

	}
}
