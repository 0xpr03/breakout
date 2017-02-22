package gameObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.gui.Background;
import de.tudarmstadt.informatik.fop.breakout.states.GameState;


/**
 * @author Tim Jäger
 *
 * Class to abstract the Ball
 */
public class Ball extends Sprite {

	private Vector2f direction = new Vector2f(0, 0);
	
	private Logger logger = LogManager.getLogger(this);
	

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
		super(image, position, radius * 2, radius * 2, true);
	}

	// Have fun Niko :D
	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
		// Useful shortcuts
		float radius = width / 2;
		float posX = position.getX();
		float posY = position.getY();
		float dirX = direction.getX();
		float dirY = direction.getY();

		// Calculation for Background-Collision
		if (position.getY() - radius <= 0) {
			// Colliding with top border and bouncing back
			direction.set(dirX,  -dirY); 
		} else if (posY - radius >= container.getHeight()) {
				state.removeObject(this);   // Ball falling off-Screen and gets removed
		} else if (posX + radius >= container.getWidth() || posX - radius <= 0) {
			// Colliding with right or left border and bouncing back
				direction.set(-dirX, dirY);
		}

		for (GameObject object : state.getStateObjects()) {
			float obY = object.getLocation().getY();
			float obX = object.getLocation().getX();
			float obHHeight = object.getHeight() / 2;
			float obHWidth = object.getWidth() / 2;
			
			// Game has not started
			if(direction.length() == 0 && container.getInput().isKeyPressed(Input.KEY_SPACE)){
				direction.set(0, -2);		
			}
			
			// The Physics will calculate here

			// Calculation for Block-Collision
			else if (object instanceof Block) {

			}

			// Calculation for Stick-Collision
			else if (object instanceof Stick) {
				if(posY + radius >=  obY - obHHeight && posX >= obX - obHWidth && posX <= obX + obHHeight){
					direction.set(dirX, -dirY);;
				}
			}
		}
		position.set(posX + direction.getX(), posY + direction.getY());
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
