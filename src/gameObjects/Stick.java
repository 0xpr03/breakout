package gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * Class representing a Stick
 * 
 * @author Tim Jäger
 */
public class Stick extends Sprite {

	/**
	 * Can be -1 for Moving left or 1 for Moving right
	 */
	private int direction;
	/**
	 * Magicnumber how fast the Stick moves (can be changed for better Gameflow)
	 */
	
	private final float pixelPerSecond = 400f;
	
	/**
	 * Create a new instance of Stick
	 * 
	 * @param position
	 *            The position of the center of the Stick
	 * @param width
	 *            The width of the Stick
	 * @param height
	 *            The height of the Stick
	 * @param image
	 *            The Image to represent the Stick on screen
	 */
	public Stick(Vector2f position, float width, float height, Image image) {
		super(image, position, width, height, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		Input in = container.getInput();
		Vector2f topLeft = getTopLeft();
		Vector2f bottomRight = getBottomRight();
		
		if (in.isKeyDown(Input.KEY_LEFT) && topLeft.x > 0) {
			position.x -= (delta / 1000.0f) * pixelPerSecond;
			direction = -1;
		} else if (in.isKeyDown(Input.KEY_RIGHT) && bottomRight.x < container.getWidth()) {
			position.x += (delta / 1000.0f) * pixelPerSecond;
			direction = 1;
		} else direction = 0;
	}

	/**
	 * Returns the speed of the Stick
	 * 
	 * @return The speed from the Stick
	 */
	public float getPixelPerSecond() {
		return pixelPerSecond;
	}
	
	

	/**
	 * Direction can be -1 for Moving left and 1 for Moving right
	 * 
	 * @return The movement direction direction from the Stick
	 */
	public int getDirection() {
		return direction;
	}
}
