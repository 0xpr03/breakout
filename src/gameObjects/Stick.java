package gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

public class Stick extends GameObject{
	
	private Image image;          // TODO:: Put the right image for Stick here
	private double speed;

	public Stick(Vector2f position, float width, float height, Image image) {
		super(position, width, height, true);
		this.image = image;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		Input in = container.getInput();
		if(in.isKeyPressed(37)){                                     // checks if the left-arrow-key is pressed
			if(position.getX() - (width / 2) > 0){                   // checks if the Stick can move to the left side
				position.set(position.getX() - 1, position.getY());  // TODO:: The Magic number has to be set by Gameflow
				speed = -1;
			}
			else{
				speed = 0;                                           // Resets the speed if there is no Movement
			}
		}
		else if(in.isKeyPressed(39)){                                 // checks if the right-arrow-key is pressed
			if(position.getX() + (width / 2) < container.getWidth()){ // checks if the Stick can move to the right side
				position.set(position.getX() + 1, position.getY());   // TODO:: The Magic number has to be set by Gemflow
				speed = 1;
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
	public double getSpeed(){
		return speed;
	}
	
	/**
	 * @author Tim Jäger
	 * @param image
	 */
	public void setImage(Image image){
		this.image = image;
	}
}
