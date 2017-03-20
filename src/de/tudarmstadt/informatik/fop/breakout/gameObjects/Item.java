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

public class Item extends Sprite {

	public Item(Image image, Vector2f position, float width, float height, boolean collideable) {
		super(image, position, width, height, collideable);
//		setKind(kind);
	}
	
	/**
	 * Sets the right image for each kind of Item
	 */
//	public void setKind(int kind){
//		switch(kind){
	    // Image for 
//		case 0:
//			this.setImage();
//			break;
//		case 1:
//			this.setImage();
//			break;
//		case 2:
//			this.setImage();
//			break;
//		case 3:
//			this.setImage();
//			break;
//		default:
//			logger.warn("got unhandeld Random int for Item generation");
//			break;
//		}
//	}

	Logger logger = LogManager.getLogger();
	int kind = new Random().nextInt(6);
	private final static float pixelPerSecond = 200f;

	// applys the effect which is determined by the kind of the item
	private void applyEffect(InGameState stm){
		switch(kind){
		// increases the width from The Stick (whether it is not to big)
		case 0:
			if(stm.getStick().getWidth() < 168){
				stm.getStick().setWidth((float) (stm.getStick().getWidth() * 1.3));
			}
			logger.debug("Stick width = " + stm.getStick().getWidth());
			break;
		// decreases the width form the Stick (whether it is not to small)
		case 1:
			if(stm.getStick().getWidth() > 60){
				stm.getStick().setWidth((float) (stm.getStick().getWidth() / 1.3));
			}
			logger.debug("Stick width = " + stm.getStick().getWidth());
			break;
		// increases the Ball speed
		case 2:
			logger.debug("Ball: {}",stm.getBall().toString());
			if(stm.getBall().getSpeed() < stm.getMapDefaultBallVelocity() * 1.8){
				logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
				logger.debug("increase Ball speed");
				stm.getBall().setSpeed((float) (stm.getBall().getSpeed() * 1.3));
			}
			logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
			break;
		// decreases the Ball speed
		case 3:
			if(stm.getBall().getSpeed() < stm.getMapDefaultBallVelocity() / 1.8){
				logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
				logger.debug("decrease Ball speed");
				stm.getBall().setSpeed((float) (stm.getBall().getSpeed() / 1.3));
			}
			logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
			break;
		// decreases the Ball size
		case 4:
			if(stm.getBall().getRadius() > stm.getDefaultBallSize() / 1.8){
				stm.getBall().scaleRadius((float) (1 / 1.3));
			}
			logger.debug("Ball Size = " + stm.getBall().getSize());
			break;
		// increases the Ball size
		case 5:
			if(stm.getBall().getRadius() < stm.getDefaultBallSize() * 1.8){
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

	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
		position.y += (delta / 1000.0f) * pixelPerSecond;
		InGameState stm = (InGameState) state;
		// collides with the stick
		if (position.y > stm.getStick().position.y - (stm.getStick().height) / 2 - (height) / 2)
			if (Math.abs(position.x - stm.getStick().position.x) < (stm.getStick().width) / 2 - (width) / 2) {
				applyEffect(stm);
				stm.asyncRemoveObject(this);
			}
			// falls out of the game
			else if (position.y > container.getHeight())
				stm.asyncRemoveObject(this);

	}
}
