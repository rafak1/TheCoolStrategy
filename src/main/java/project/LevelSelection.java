package project;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

/**
 * You can select a level from this class
 */
public class LevelSelection
{
	Group root;
	GamePlane gamePlane = new GamePlane();

	public LevelSelection(Group menuRoot, Scene scene)
	{
		root=new Group();

		Button[] possibleLevels=new Button[10];

		for(int i=0; i<10; i++)
		{
			possibleLevels[i]=new Button(String.valueOf(i+1));
			possibleLevels[i].setPrefSize(100, 20);
			int finalI = i;
			possibleLevels[i].setOnAction(value->{
				gamePlane.loadLevel(finalI);
				scene.setRoot(gamePlane.getRoot());
			});
		}
		FlowPane flow=new FlowPane((MainVariables.sizeX-500)/4, 30, possibleLevels);
		flow.setPrefWrapLength(MainVariables.sizeX);

		root.getChildren().add(flow);


		ImageButton backButton=new ImageButton("/images/back.png", 500, 300);
		root.getChildren().add(backButton.get());
		backButton.get().setOnAction(e->scene.setRoot(menuRoot));
	}

	public Group getRoot()
	{
		return root;
	}
}
