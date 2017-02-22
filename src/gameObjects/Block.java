package gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * Class to abstract a Block with life
 * 
 * @author Tim Jäger
 */
public class Block extends Sprite {

	private int life;

	/**
	 * Create a new instance of Block
	 * 
	 * @param position
	 *            The center position of the Block
	 * @param width
	 *            The width of the Block
	 * @param height
	 *            The height of the Block
	 * @param life
	 *            The life of the Block
	 * @param image
	 *            The Image to represent the Block on screen
	 */
	public Block(Vector2f position, float width, float height, int life, Image image) {
		super(image, position, width, height, true);
		this.life = life;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		// Has to be empty, because he shell not move!
	}

	/**
	 * Returns the life the Block has left
	 * 
	 * @return the life the Blick has left
	 */
	public int getLife() {
		return life;
	}

	/**
	 * Decrement the life by 1
	 */
	public void decreaseLife() {
		life--;
	}
}
