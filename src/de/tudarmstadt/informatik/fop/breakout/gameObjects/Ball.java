package de.tudarmstadt.informatik.fop.breakout.gameObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.lib.GameEvent;
import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * @author Tim Jäger, Niko Mitura, Aron Heinecke, Simon Kohaut
 *
 *         Class to abstract the Ball
 */
public class Ball extends Sprite {

	private Vector2f direction = new Vector2f(0, 0);
	private AssetManager am;
	private Stick stick;

	private Logger logger = LogManager.getLogger(this);
	private float basicVelocity;
	private float gravity;
	private float radius;
	private int delta;
	private GameEvent ing;
	private int windowHeight;
	private int windowWidth;

	private final int LEFT_BORDER = -1;
	private final int RIGHT_BORDER = -2;
	private final int TOP_BORDER = -3;

	private int lastCollider = -4;

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
	public Ball(Vector2f position, float radius, Image image, float velocity, float gravity, GameEvent ing,
			int windowHeight, int windowWidth, AssetManager am, Stick stick) {
		super(image, position, radius * 2, radius * 2, false);
		this.basicVelocity = velocity;
		this.gravity = gravity;
		this.radius = radius;
		this.ing = ing;
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		this.am = am;
		this.stick = stick;
	}

	/**
	 * Detect if the Ball collides with a GameObject and act according to it
	 * 
	 * @param o
	 *            The object which may be colliding with the Ball
	 * @return If the Ball collides with the object
	 * @throws SlickException
	 */
	public boolean surfaceCollisionTest(GameObject o, int delta) {
		if (!o.isCollideable())
			return false;

		Vector2f half = new Vector2f(o.width / 2.0f, o.height / 2.0f);
		Vector2f distance = new Vector2f(Math.abs(position.x - o.position.x), Math.abs(position.y - o.position.y));
		Vector2f vDelta = new Vector2f(distance.x - half.x, distance.y - half.y);

		if (distance.x > (half.x + radius) || distance.y > (half.y + radius))
			return false;
		boolean collided = false;
		// Left/Right
		if (distance.x <= half.x + radius && distance.y <= half.y) {
			direction.x = -direction.x;
			collided = true;
		}
		// Top/Bottom
		else if (distance.x <= half.x && distance.y <= half.y + radius) {

			if (o instanceof Stick) {
				double thetaantidir = (direction.negateLocal()).getTheta();
				direction.setTheta(
						thetaantidir + 2 * ((((position.x - o.position.x) / half.x) * 15) + 270 - thetaantidir));
			} else
				direction.y = -direction.y;
			collided = true;
		}

		if (collided) {
			if (o instanceof Block) {
				ing.blockHit((Block) o);
			}
			// CE-Aufgabe
			// else if (o instanceof Stick)
			// direction.x = (((Stick) o).getDirection() * ((Stick)
			// o).getPixelPerSecond() * (delta / 1000.0f) ) + direction.x;
			try {
				playSound(o);
			} catch (SlickException e) {
				logger.warn("Unable to play Sound", e);
			}
		}

		return collided;
	}

	// Corner
	public boolean cornerCollisionTest(GameObject o, int delta) {
		if (!o.isCollideable())
			return false;

		Vector2f half = new Vector2f(o.width / 2.0f, o.height / 2.0f);
		Vector2f distance = new Vector2f(Math.abs(position.x - o.position.x), Math.abs(position.y - o.position.y));
		Vector2f vDelta = new Vector2f(distance.x - half.x, distance.y - half.y);

		if (vDelta.x * vDelta.x + vDelta.y * vDelta.y <= radius * radius) {
			float deltaxpr = (position.x - o.position.x) / Math.abs(position.x - o.position.x);
			float deltaypr = (position.y - o.position.y) / Math.abs(position.y - o.position.y);

			Vector2f ballcorner = new Vector2f((position.x - o.position.x) - (deltaxpr * half.x),
					(position.y - o.position.y) - (deltaypr * half.y));

			double thetaantidir = (direction.negateLocal()).getTheta();
			direction.setTheta(thetaantidir + 2 * (ballcorner.getTheta() - thetaantidir));
			if (o instanceof Block)
				ing.blockHit((Block) o);
			try {
				playSound(o);
			} catch (SlickException e) {
				logger.warn("Unable to play Sound", e);
			}

			return true;
		}
		return false;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {	
		// Ball crossing ...
		// ... top border and bouncing back
		if (position.y - radius <= 0){
			direction.set(direction.x, -direction.y);
			lastCollider = TOP_BORDER;
		}
		// ... right border and bouncing back
		else if (position.x + radius >= windowWidth && lastCollider != RIGHT_BORDER) {
			direction.set(-direction.x, direction.y);
			lastCollider = RIGHT_BORDER;
		}
		// ... left border and bouncing back
		else if (position.x - radius <= 0 && lastCollider != LEFT_BORDER) {
			direction.set(-direction.x, direction.y);
			lastCollider = LEFT_BORDER;
		}
		// ... bottom edge and getting removed
		else if (position.y - radius >= windowHeight)
			ing.ballLost(this);

		// Start Ball movement by pressing space
		if (direction.length() == 0) {
			if(container.getInput().isKeyPressed(Input.KEY_SPACE))
				direction.set(0, -basicVelocity);
			else
				position.set(stick.getLocation().x, stick.getLocation().y - radius);
		}
			

		boolean collided = false;
		
		// Test for collision with all GameObjects
		for (GameObject object : state.getStateObjects())
			if (lastCollider != object.getID() && surfaceCollisionTest(object, delta)) {
				lastCollider = object.getID();
				collided = true;
				break;
			}
		if (!collided)
			for (GameObject object : state.getStateObjects())
				if (lastCollider != object.getID() && cornerCollisionTest(object, delta)) {
					lastCollider = object.getID();
					break;
				}

		// Calculate new position
		position.x += direction.x;
		position.y += direction.y;
	}

	public void playSound(GameObject o) throws SlickException {
		if (o instanceof Block) {
			am.getSnd("sounds/hitBlock.wav").play();
			logger.debug("play Block Sound");
		} else if (o instanceof Stick) {
			am.getSnd("sounds/hitStick.wav").play();
			logger.debug("play Stick Sound");
		} else am.getSnd("sounds/hitBlock.wav").play();
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
	
	/**
	 * Returns the current speed of the ball<br>
	 * For testing purposes only
	 * 
	 * @return float current speed
	 */
	public float getSpeed(){
		return direction.length();
	}
	
	/**
	 * Set the speed of the ball<br>
	 * For testing purposes only
	 * 
	 * @param speed new speed to set
	 */
	public void setSpeed(float speed){
		direction = direction.scale(speed / direction.length());
	}
	
	/**
	 * Set the position of the ball<br>
	 * For testing purposes only
	 * 
	 * @param vector2f new position of the ball
	 */
	public void setPosition(Vector2f vector2f){
		this.position.set(vector2f);
	}
}