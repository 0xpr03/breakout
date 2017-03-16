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

	}

	Logger logger = LogManager.getLogger();
	int kind = new Random().nextInt(4);
	private final static float pixelPerSecond = 200f;

	// applys the effect which is determined by the kind of the item
	private void applyEffect(InGameState stm){
		switch(kind){
		case 0:
			if(stm.getStick().getWidth() < 175){
				stm.getStick().setWidth((float) (stm.getStick().getWidth() * 1.3));
			}
			logger.debug("Stick width = " + stm.getStick().getWidth());
			break;
		case 1:
			if(stm.getStick().getWidth() > 60){
				stm.getStick().setWidth((float) (stm.getStick().getWidth() / 1.3));
			}
			logger.debug("Stick width = " + stm.getStick().getWidth());
			break;
		case 2:
			if(stm.getBall().getSpeed() < stm.getMapDefaultBallVelocity() * 1.8){
				stm.getBall().setSpeed((float) (stm.getBall().getSpeed() * 1.3));
			}
			logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
			break;
		case 3:
			if(stm.getBall().getSpeed() < stm.getMapDefaultBallVelocity() / 1.8){
				stm.getBall().setSpeed((float) (stm.getBall().getSpeed() / 1.3));
			}
			logger.debug("Ball Velocity = " + stm.getBall().getSpeed());
			break;
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
