package project;

public class Level
{

    public int[][] levelObjects;

    public Level(int k)
    {
        //this is temporary, it fills grid with random values. In the future this data will be read from a file
        levelObjects=new int[10][10];
        for(int i=0; i<10; i++)
        {
            for(int j=0; j<10; j++)
            {
                levelObjects[i][j]=0;
            }
        }
        levelObjects[0][k]=1;
        levelObjects[1][k]=1;
        levelObjects[2][k]=1;
        levelObjects[3][k]=1;
        levelObjects[4][k]=1;
        levelObjects[5][k]=1;
        levelObjects[6][k]=1;
        levelObjects[7][k]=1;
        levelObjects[8][k]=1;
        levelObjects[9][k]=1;
        levelObjects[k][0]=1;
        levelObjects[k][1]=1;
        levelObjects[k][2]=1;
        levelObjects[k][3]=1;
        levelObjects[k][4]=1;
        levelObjects[k][5]=1;
        levelObjects[k][6]=1;
        levelObjects[k][7]=1;
        levelObjects[k][8]=1;
        levelObjects[k][9]=1;

    }
}
