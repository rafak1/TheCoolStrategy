package project;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Objects;

import static project.MainVariables.sizeY;

public class MessagesAndEffects
{
	/**
	 * It's used to show sprites for a second
	 *
	 * @param src      image source
	 * @param x        where to place it
	 * @param y        where to place it
	 * @param duration how long it lasts
	 */
	public static void showEffect(String src, double x, double y, double duration, Group root)
	{
		Image tempImage=new Image(Objects.requireNonNull(MessagesAndEffects.class.getResource(src)).toString(), sizeY/10, sizeY/10, true, true);
		ImageView iv=new ImageView(tempImage);
		iv.setX(x);
		iv.setY(y);
		iv.setMouseTransparent(true);
		Platform.runLater(()->root.getChildren().add(iv));
		fade(duration, iv, root);


	}

	/**
	 * It's used to show short messages in a chosen place
	 *
	 * @param s         message to put in a box
	 * @param width     width of the message
	 * @param height    height of the message
	 * @param positionX where to put the message
	 * @param positionY where to put the message
	 * @param duration  how long the message is shown
	 */
	public static void showMessage(String s, double width, double height, double positionX, double positionY, double duration, Group root)
	{
		Rectangle r=new Rectangle();
		r.setWidth(width);
		r.setHeight(height);
		r.setFill(Color.RED);
		r.setMouseTransparent(true);

		Text text=new Text(s);
		text.setFill(Color.BEIGE);
		text.setStyle("-fx-font: 20 arial;");
		text.setMouseTransparent(true);

		StackPane stack=new StackPane();
		stack.getChildren().addAll(r, text);
		stack.setLayoutX(positionX);
		stack.setLayoutY(positionY);
		stack.setMouseTransparent(true);

		root.getChildren().add(stack);
		fade(duration, stack, root);
	}

	/**
	 * Fades away a node and removes it
	 *
	 * @param duration how long it lasts
	 * @param node     the node to be removed
	 */
	static void fade(double duration, Node node, Group root)
	{
		//fancy fade away transition
		FadeTransition ft=new FadeTransition(Duration.seconds(duration), node);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setCycleCount(4);
		ft.play();

		//remove after it fades away
		PauseTransition transition=new PauseTransition(Duration.seconds(duration));
		transition.play();
		transition.setOnFinished(e->root.getChildren().remove(node));
	}
}
