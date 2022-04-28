package project;

import javafx.util.Pair;

import java.util.ArrayList;

import static project.MainVariables.gridSizeX;
import static project.MainVariables.gridSizeY;

public class Level
{

    public int[][] levelObjects;        //can be deleted and replaced only with path
    public ArrayList <Pair <Integer, Integer>> path=new ArrayList <>();
    public int startY;
    public int startX;

    public Level(int k)
    {
        //this is temporary, it fills grid with random values. In the future this data will be read from a file
        levelObjects=new int[gridSizeX][gridSizeY];
        for(int i=0; i<gridSizeX; i++)
        {
            for(int j=0; j<gridSizeY; j++)
            {
                levelObjects[i][j]=0;
            }
        }
        startX=2;
        startY=0;
        path.add(new Pair <>(2, 1));
        path.add(new Pair <>(2, 2));
        path.add(new Pair<>(3,2));
        path.add(new Pair<>(4,2));
        path.add(new Pair<>(5,2));
        path.add(new Pair<>(5,3));
        path.add(new Pair<>(5,4));
        path.add(new Pair<>(5,6));
        path.add(new Pair<>(5,7));
        path.add(new Pair<>(5,8));
        path.add(new Pair<>(5,9));
        path.add(new Pair<>(-1,-1));
        levelObjects[2][0]=1;
        levelObjects[2][1]=2;
        levelObjects[2][2]=3;
        levelObjects[3][2]=4;
        levelObjects[4][2]=5;
        levelObjects[5][2]=6;
        levelObjects[5][3]=7;
        levelObjects[5][4]=8;
        levelObjects[5][5]=9;
        levelObjects[5][6]=10;
        levelObjects[5][7]=11;
        levelObjects[5][8]=12;
        levelObjects[5][9]=13;

    }
}
