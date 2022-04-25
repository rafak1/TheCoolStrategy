package project;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import static project.MainVariables.sizeX;
import static project.MainVariables.sizeY;

/**
 * You can select a level from this class
 */
public class LevelSelection
{
	Group root;
	GameMaster gameMaster;

	public LevelSelection(Group menuRoot, Scene scene)
	{
		root=new Group();
		gameMaster =new GameMaster(scene, root);

		Button[] possibleLevels=new Button[10];

		for(int i=0; i<10; i++)
		{
			possibleLevels[i]=new Button(String.valueOf(i+1));
			possibleLevels[i].setPrefSize(100, 20);
			int finalI=i;
			possibleLevels[i].setOnAction(value->{
				gameMaster.loadLevel(finalI);
				scene.setRoot(gameMaster.getRoot());
			});
		}
		FlowPane flow=new FlowPane((sizeX-500)/4, 30, possibleLevels);
		flow.setPrefWrapLength(sizeX);

		root.getChildren().add(flow);


		ImageButton backButton=new ImageButton("/images/back.png", sizeX-125, sizeY-125, 100, 100);
		root.getChildren().add(backButton.get());
		backButton.get().setOnAction(e->scene.setRoot(menuRoot));
	}

	public Group getRoot()
	{
		return root;
	}
}
