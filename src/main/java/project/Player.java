package project;

import javafx.application.Platform;

import java.util.concurrent.atomic.AtomicInteger;

import static project.MainVariables.startingMoney;

public class Player {
    public static AtomicInteger health = new AtomicInteger(MainVariables.playerHealth);
    public static AtomicInteger money = new AtomicInteger(startingMoney);

    /**
     * Changes players health by a given value
     *
     * @param value value in question
     */
    public static void changePlayerHealth(int value) {
        health.addAndGet(value);
        Platform.runLater(()->GameMaster.healthText.setText(String.valueOf(health)));
    }


    /**
     * Changes players money by a given value
     *
     * @param value value in question
     */
    public static void changePlayerMoney(int value) {
        money.addAndGet(value);
        Platform.runLater(()->GameMaster.moneyText.setText(String.valueOf(money)));
    }
}
