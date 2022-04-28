package project;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import static project.GameMaster.masterRoot;
import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;
import static project.Menu.menuRoot;
import static project.Menu.scene;

/**
 * You can select a level from this class
 */
public class LevelSelection
{
	public static Group selectionRoot;
	GameMaster gameMaster;
	int currentLevel;

	public LevelSelection()
	{
		selectionRoot=new Group();
		gameMaster=new GameMaster();

		Button[] possibleLevels=new Button[10];

		for(int i=0; i<10; i++)
		{
			possibleLevels[i]=new Button(String.valueOf(i+1));
			possibleLevels[i].setPrefSize(100, 20);
			currentLevel=i;
			possibleLevels[i].setOnAction(value->{
				gameMaster.loadLevel(currentLevel);
				scene.setRoot(masterRoot);
				try
				{
					gameMaster.startLevel();
				}catch(InterruptedException e)
				{

				}
			});
		}
		FlowPane flow=new FlowPane((sizeX-500)/4, 30, possibleLevels);
		flow.setPrefWrapLength(sizeX);

		selectionRoot.getChildren().add(flow);


		ImageButton backButton=new ImageButton("/images/back.png", sizeX-125, sizeY-125, 100, 100);
		selectionRoot.getChildren().add(backButton.get());
		backButton.get().setOnAction(e->scene.setRoot(menuRoot));

	}
}
