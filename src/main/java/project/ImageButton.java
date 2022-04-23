package project;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class makes creating buttons with images much faster
 */
public class ImageButton
{
	Button theButton;

	/**
	 * Default constructor, creates a button at (x,y) with image that can be found at path
	 *
	 * @param path path to the image
	 * @param x    x coordinate
	 * @param y    y coordinate
	 */
	public ImageButton(String path, int x, int y)
	{
		theButton=new Button();
		Image img=new Image(getClass().getResource(path).toString());
		ImageView exitIV=new ImageView(img);
		theButton.setGraphic(exitIV);
		theButton.setLayoutX(x);
		theButton.setLayoutY(y);
	}

	/**
	 * Returns a button that can be used somewhere
	 *
	 * @return fancy button
	 */
	public Button get()
	{
		return theButton;
	}
}
