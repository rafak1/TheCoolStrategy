package project;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

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
		EventHandler <MouseEvent> bigger=e->{
			ScaleTransition makeBigger=new ScaleTransition(Duration.millis(75), (Node)e.getSource());
			makeBigger.setToY(1.1);
			makeBigger.setToX(1.1);
			makeBigger.play();
		};
		EventHandler <MouseEvent> smaller=e->{
			ScaleTransition reset=new ScaleTransition(Duration.millis(75), (Node)e.getSource());
			reset.setToY(1);
			reset.setToX(1);
			reset.play();
		};
		theButton.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, bigger);
		theButton.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, smaller);

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
