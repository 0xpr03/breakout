package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import de.tudarmstadt.informatik.fop.breakout.gameObjects.Sprite;

/**
 * Base class to represent GUI elements
 * 
 * @author Simon Kohaut
 */
public class GUIElement extends Sprite {

	/**
	 * Create a new GUI element
	 * 
	 * @param image
	 *            the image to represent the gui element on screen
	 * @param position
	 *            the center position of the gui element
	 * @param width
	 *            the width of the gui element
	 * @param height
	 *            the height of the gui element
	 */
	public GUIElement(Image image, Vector2f position, float width, float height) {
		super(image, position, width, height, false);
	}

	/**
	 * Returns if the mouse is hovering over the GUI element
	 * 
	 * @param container
	 *            the game container to get the input
	 * @return if the mouse is hovering over the GUI element
	 */
	public boolean isMouseOver(GameContainer container) {
		Input in = container.getInput();
		int mx = in.getMouseX();
		int my = in.getMouseY();

		Vector2f topLeft = getTopLeft();
		Vector2f bottomRight = getBottomRight();

		if (mx > topLeft.x && mx < bottomRight.x && my > topLeft.y && my < bottomRight.y)
			return true;
		else
			return false;
	}

	/**
	 * Returns if the GUI element was left clicked
	 * 
	 * @param container
	 *            the game container to get the input
	 * @return if the GUI element was left clicked
	 */
	public boolean isClicked(GameContainer container) {
		if (!isMouseOver(container))
			return false;
		else
			return container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);
	}

	/**
	 * Returns if the GUI element was right clicked
	 * 
	 * @param container
	 *            the game container to get the input
	 * @return if the GUI element was right clicked
	 */
	public boolean isRightClicked(GameContainer container) {
		if (!isMouseOver(container))
			return false;
		else
			return container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON);
	}
}
