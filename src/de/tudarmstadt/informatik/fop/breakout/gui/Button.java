package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.interfaces.GameObject;
import de.tudarmstadt.informatik.fop.breakout.states.GameState;


/**
 * A Button with mouse over visualization and click recognition
 * 
 * @author Simon Kohaut 
 */
public class Button extends GameObject {

	private Image defaultImage;
	private Image mouseOverImage;
	private boolean mouseOverButton;
	private boolean clicked;
	
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
	public Button(Vector2f position, float width, float height, Image defaultImage, Image mouseOverImage) {
		super(position, width, height, false);
		this.defaultImage = defaultImage;
		this.mouseOverImage = mouseOverImage;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		Input in = container.getInput();
		int mx = in.getMouseX();
		int my = in.getMouseY();

		Vector2f topLeft = new Vector2f(position.x - width / 2, position.y + height / 2);
		Vector2f bottomRight = new Vector2f(position.x + width / 2, position.y - height / 2);

		if (mx > topLeft.x && mx < bottomRight.x && my < topLeft.y && my > bottomRight.y)
			mouseOverButton = true;
		else
			mouseOverButton = false;
		
		if(mouseOverButton && in.isKeyDown(Input.MOUSE_LEFT_BUTTON))
			clicked = true;
		else 
			clicked = false;
	}

	@Override
	public void render(Graphics g) {
		if (mouseOverButton)
			mouseOverImage.draw(position.x - width / 2, position.y - height / 2, width, height);
		else
			defaultImage.draw(position.x - width / 2, position.y - height / 2, width, height);
	}

	/**
	 * Returns if the button was clicked in this frame
	 * 
	 * @return if the button was clicked in this frame
	 */
	public boolean isClicked() {
		return clicked;
	}
	
}
