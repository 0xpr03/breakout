package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * A Button with mouse over visualization and click recognition
 * 
 * @author Simon Kohaut
 */
public class Button extends GUIElement {

	private Image defaultImage;
	private Image mouseOverImage;
	private ButtonAction action;

	/**
	 * Create a new Button
	 * 
	 * @param position
	 *            The position of the center of the button
	 * @param width
	 *            The width of the button
	 * @param height
	 *            The height of the button
	 * @param defaultImage
	 *            The default Image to represent the Button on screen
	 * @param mouseOverImage
	 *            The Image to represent the Button if the cursor is hovering
	 *            over the Button
	 */
	public Button(Vector2f position, float width, float height, Image defaultImage, Image mouseOverImage,
			ButtonAction action) {
		super(defaultImage, position, width, height);
		this.defaultImage = defaultImage;
		this.mouseOverImage = mouseOverImage;
		this.action = action;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, @SuppressWarnings("rawtypes") GameState state,
			int delta) throws SlickException {
		if (isMouseOver(container))
			image = mouseOverImage;
		else
			image = defaultImage;

		if (isClicked(container))
			action.action(container, game, state, delta);
	}

	/**
	 * Sets a new Default Image
	 * 
	 * @param img
	 */
	public void setDefaultImage(Image img) {
		defaultImage = img;
	}

	/**
	 * Sets a new MouseOverImage
	 * 
	 * @param img
	 */
	public void setMouseOverImage(Image img) {
		mouseOverImage = img;
	}

	/**
	 * Button action class
	 * 
	 * @author Aron Heinecke
	 */
	public static abstract class ButtonAction {
		/**
		 * Action on button click
		 * 
		 * @param container
		 *            GameContainer
		 * @param game
		 *            StateBasedGame
		 * @param state
		 *            GameState
		 * @param delta
		 *            Delta
		 * @throws SlickException
		 */
		public abstract void action(GameContainer container, StateBasedGame game,
				@SuppressWarnings("rawtypes") GameState state, int delta) throws SlickException;
	}

}
