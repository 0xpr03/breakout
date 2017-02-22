package gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * Base class for all GameObjects that behave like Sprites
 * 
 * @author Simon Kohaut
 */
public class Sprite extends GameObject {

	protected Image image;

	/**
	 * Create a new Sprite
	 * 
	 * @param image
	 *            The image to show on screen
	 * @param position
	 *            The center position of the Sprite
	 * @param width
	 *            The width of the Sprite
	 * @param height
	 *            The height of the Sprite
	 * @param collideable
	 *            If the Sprite is collideable
	 */
	public Sprite(Image image, Vector2f position, float width, float height, boolean collideable) {
		super(position, width, height, collideable);
		this.image = image;
	}

	/**
	 * Returns the coordinates of the top left corner
	 * 
	 * @return The coordinates of the top left corner
	 */
	public Vector2f getTopLeft() {
		return new Vector2f(position.x - width / 2, position.y - height / 2);
	}

	/**
	 * Returns the coordinates of the bottom right corner
	 * 
	 * @return The coordinates of the bottom right corner
	 */
	public Vector2f getBottomRight() {
		return new Vector2f(position.x + width / 2, position.y + height / 2);
	}

	/**
	 * Set the Image to represent the Sprite on screen
	 * 
	 * @param image
	 *            The Image to represent the Sprite on screen
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public void render(Graphics g) {
		image.draw(position.x - width / 2, position.y - height / 2, width, height);
	}

	/**
	 * Just to be real
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
	}

}
