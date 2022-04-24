package project;

import project.gameObjects.BasicEnemy;

import java.util.LinkedList;
import java.util.Queue;

public class GameMaster
{
    int playerHealth = MainVariables.playerHealth;

    Queue<? extends BasicEnemy> enemies = new LinkedList<>();

    /**
     *  Decrease players health by a given amount
     * @param number amount subtracted from players health
     */
    void decreasePlayerHealth(int number){
        playerHealth -=number;
    }

    int getPlayerHealth(){
        return playerHealth;
    }
}
