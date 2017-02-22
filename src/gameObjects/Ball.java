package gameObjects;

import java.sql.Array;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.gui.Background;
import de.tudarmstadt.informatik.fop.breakout.states.GameState;


/**
 * @author Tim Jäger
 *
 * Class to abstract the Ball
 */
public class Ball extends GameObject {

	private Image image; // TODO:: Put the right image of Ball here
	private Vector2f direction = new Vector2f(0, 0);

	/**
	 * Create a new Ball instance
	 * 
	 * @param position
	 *            The position of the center of the ball
	 * @param radius
	 *            The radius of the ball
	 * @param image
	 *            The image to represent the Ball on screen
	 */
	public Ball(Vector2f position, float radius, Image image) {
		super(position, radius * 2, radius * 2, true);
		this.image = image;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		// usefull shortcuts
		float radius = width / 2;
		float posX = position.getX();
		float posY = position.getY();

		// The physics will calculate here
		for (GameObject object : state.getStateObjects()) {

			// Calculation for Block-Collision
			if (object instanceof Block) {

			}

			// Calculation for Stick-Collision
			else if (object instanceof Stick) {

			}

			// Calculation for Background-Collision
			else if (object instanceof Background) {
				if (position.getY() - radius <= 0) {
					direction.set(direction.getX(), (-1) * direction.getY()); // Colliding
																				// with
																				// top
																				// border
																				// and
																				// bounzing
																				// back
					position.set(posX + direction.getX(), posY + direction.getY());
				} else if (posY - radius >= container.getHeight()) {
					state.removeObject(this); // Ball falling off-Screen and
												// gets removed
				} else if (posX + radius >= container.getWidth() || posX - radius <= 0) {
					direction.set((-1) * direction.getX(), direction.getY());
					// Colliding with right or left border and bounzing back
				}
			}
		}
		position.set(posX + direction.getX(), posY + direction.getY());
	}

	@Override
	public void render(Graphics g) {
		image.draw(position.getX() - (width / 2), position.getY() - (height / 2), width, height);
	}

	/**
	 * Set the new Image to represent the Ball on screen
	 * 
	 * @param image
	 *            The new Image to represent the Ball on screen
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Set the direction of the ball
	 * 
	 * @param dirx
	 *            direction in X-Achsis
	 * @param diry
	 *            direction in Y-Achsis
	 */
	public void setDirection(float dirx, float diry) {
		direction.set(dirx, diry);
	}
}
