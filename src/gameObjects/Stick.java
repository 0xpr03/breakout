package gameObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

public class Stick extends GameObject{
	
	private Logger logger = LogManager.getLogger(this);
	
	private Image image;          // TODO:: Put the right image for Stick here
	/**
	 *  current speed from the Stick
	 */
	private int speed;
	/**
	 *  Can be -1 for Moving left or 1 for Moving right
	 */
	private int direction;           
	/**
	 *  Magicnumber how fast the Stick moves (can be changed for better Gameflow)
	 */
	private final int movementSpeed = 7;          
	

	public Stick(Vector2f position, float width, float height, Image image) {
		super(position, width, height, true);
		this.image = image;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		Input in = container.getInput();
		if(in.isKeyDown(Input.KEY_LEFT)) {
			logger.debug("Pressed: left");
			if(position.getX() - (width / 2) > 0){                   // checks if the Stick can move to the left side
				position.set(position.getX() - movementSpeed, position.getY());
				speed = movementSpeed;
				direction = -1;
			}
			else{
				speed = 0;                                           // Resets the speed if there is no Movement
			}
		}
		else if(in.isKeyDown(Input.KEY_RIGHT)){                   // checks if the right-arrow-key is pressed
			logger.debug("Pressed: right");
			if(position.getX() + (width / 2) < container.getWidth()){ // checks if the Stick can move to the right side
				position.set(position.getX() + movementSpeed, position.getY());
				speed = movementSpeed;
				direction = 1;
			}
			else{
				speed = 0;                                            // Resets the speed if there is no Movement
			}
		}
	}

	@Override
	public void render(Graphics g) {
		image.draw(position.getX() - (width / 2), position.getY() - (height / 2), width, height);
	}
	
	/**
	 * @author Tim Jäger
	 * @return The speed from the Stick
	 */
	public int getSpeed(){
		return speed;
	}
	
	/**
	 * @author Tim Jäger
	 * @param image
	 */
	public void setImage(Image image){
		this.image = image;
	}
	
	/**
	 * @author Tim Jäger
	 * @return the maximum movement Speed from the Stick
	 */
	public int getCurrentSpeed(){
		return speed;
	}
	
	/**
	 * Direction can be -1 for Moving left and 1 for Moving right
	 * @author Tim Jäger
	 * @return direction from the Stick
	 */
	public int getDirection(){
		return direction;
	}
}
