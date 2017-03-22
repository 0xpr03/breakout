package de.tudarmstadt.informatik.fop.breakout.gameObjects;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;
import de.tudarmstadt.informatik.fop.breakout.states.InGameState;

/**
 * A Item that will drop from a destructed block
 * 
 * @author Tim Jäger, Niko Mitura
 */
public class Item extends Sprite {

	Logger logger = LogManager.getLogger();
	int kind = new Random().nextInt(6);
	private final static float pixelPerSecond = 200f;

	/**
	 * Create a new item
	 * 
	 * @param image
	 *            the image to use
	 * @param position
	 *            the starting position
	 * @param width
	 *            the width of the item
	 * @param height
	 *            the height of the item
	 * @param collideable
	 *            if the item is collideable
	 */
	public Item(Image image, Vector2f position, float width, float height, boolean collideable) {
		super(image, position, width, height, collideable);
	}

	/**
	 * Aplly the item effect to the game
	 * 
	 * @param stm
	 *            the InGameState object
	 */
	private void applyEffect(InGameState stm) {
		switch (kind) {
		// increases the width from The Stick (whether it is not to big)
		case 0:
			if (stm.getStick().getWidth() < 168) {
				stm.getStick().setWidth((float) (stm.getStick().getWidth() * 1.3));
			}
			logger.debug("Stick width = " + stm.getStick().getWidth());
			break;
		// decreases the width form the Stick (whether it is not to small)
		case 1:
			if (stm.getStick().getWidth() > 60) {
				stm.getStick().setWidth((float) (stm.getStick().getWidth() / 1.3));
			}
			logger.debug("Stick width = " + stm.getStick().getWidth());
			break;
		// increases the Ball speed
		case 2:
			logger.debug("Ball: {}", stm.getBall().toString());
			if (stm.getBall().getSpeed() < stm.getMapDefaultBallVelocity() * 1.6) {
				logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
				logger.debug("increase Ball speed");
				stm.getBall().setSpeed((float) (stm.getBall().getSpeed() * 1.3));
			}
			logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
			break;
		// decreases the Ball speed
		case 3:
			if (stm.getBall().getSpeed() > stm.getMapDefaultBallVelocity() / 1.6) {
				logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
				logger.debug("decrease Ball speed");
				stm.getBall().setSpeed((float) (stm.getBall().getSpeed() / 1.3));
			}
			logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
			break;
		// decreases the Ball size
		case 4:
			if (stm.getBall().getRadius() > stm.getDefaultBallSize() / 1.8) {
				stm.getBall().setWidth((float) ((stm.getBall().getRadius() * 2) / 1.3));
				stm.getBall().setHeight((float) ((stm.getBall().getRadius() * 2) / 1.3));
				stm.getBall().scaleRadius((float) (1 / 1.3));
			}
			logger.debug("Ball Size = " + stm.getBall().getSize());
			break;
		// increases the Ball size
		case 5:
			if (stm.getBall().getRadius() < stm.getDefaultBallSize() * 1.8) {
				stm.getBall().setWidth((float) (stm.getBall().getRadius() * 1.3 * 2));
				stm.getBall().setHeight((float) (stm.getBall().getRadius() * 1.3 * 2));
				stm.getBall().scaleRadius((float) 1.3);
			}
			logger.debug("Ball SIze = " + stm.getBall().getSize());
			break;
		// default when something goes wrong
		default:
			logger.warn("got unhandeld Random int for Item generation");
			break;
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
		position.y += (delta / 1000.0f) * pixelPerSecond;
		InGameState stm = (InGameState) state;
		// collides with the stick
		if (Math.abs(position.y - stm.getStick().position.y) < (stm.getStick().height) / 2 + (height) / 2)
			if (Math.abs(position.x - stm.getStick().position.x) < (stm.getStick().width) / 2 + (width) / 2) {
				applyEffect(stm);
				stm.asyncRemoveObject(this);
			}
			// falls out of the game
			else if (position.y > container.getHeight())
				stm.asyncRemoveObject(this);

	}
}
