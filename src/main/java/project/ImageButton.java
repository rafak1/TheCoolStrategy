package project;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

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
	 * @param sizeX width of the button
	 * @param sizeY  height of button
	 */
	public ImageButton(String path, double x, double y, int sizeX, int sizeY)
	{
		theButton=new Button();
		ImageView IV=new ImageView(new Image(Objects.requireNonNull(getClass().getResource(path)).toString(), sizeX, sizeY, true, true));
		theButton.setGraphic(IV);
		theButton.setLayoutX(x);
		theButton.setLayoutY(y);
		theButton.setBackground(null);
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
