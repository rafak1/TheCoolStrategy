package project.gameObjects.Turrets;

import java.util.ArrayList;

import static project.gameObjects.Turrets.DeployTurret.allTowers;

public class EnemyDetection
{
	public static Thread listener;
	ArrayList <Thread> turretThreads;

	public void dispatcher()
	{
        //basically checks the location of each enemy every x seconds
        turretThreads = new ArrayList<>();
        for (BasicTurret t : allTowers) {
            Thread temp = new Thread(t::fightLogic);
            temp.setDaemon(true);
            temp.start();
            turretThreads.add(temp);
        }
    }

    public void newTower(BasicTurret t) {
        Thread temp = new Thread(t::fightLogic);
        temp.setDaemon(true);
        temp.start();
        turretThreads.add(temp);
    }

    public void killThreads() {
        for (Thread th : turretThreads)
		{
			if(th!=null && th.isAlive())
			{
				th.interrupt();
			}
		}

		turretThreads.clear();
	}
}
