package project.gameObjects.Enemies;

import javafx.scene.image.Image;
import project.MainVariables;
import project.Settings;

import java.util.Objects;

public class BigBigBigEnemy extends BasicEnemy {
    public BigBigBigEnemy(int wave) {
        super(wave);
        pathIndex=0;
        damage=75;
        enemySpeed=2.0;
        if(Settings.difficultyMultiplier==2.0)
        {health=(int)(85*1.7*waveMultiplier);}
        else
        {health=(int)(85*Settings.difficultyMultiplier*waveMultiplier);}
        moneyGiven=100;
        imageUrl="/images/gameObjects/BigBigBigEnemy.png";
        enemySprite=new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toString(), MainVariables.sizeY/8, MainVariables.sizeY/8, true, true);
    }
}
