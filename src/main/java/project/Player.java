package project;

public class Player {
    public static int playerHealth=MainVariables.playerHealth;
    public static int money=100;

    /**
     * Decrease players health by a given amount
     *
     * @param number amount subtracted from players health
     */
    void decreasePlayerHealth(int number) {
        playerHealth -= number;
    }

    int getPlayerHealth() {
        return playerHealth;
    }
}
