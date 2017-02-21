package gameObjects;

import java.sql.Array;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.gui.Background;
import de.tudarmstadt.informatik.fop.breakout.states.GameState;

public class Ball extends GameObject{
	
	private Image image;   // TODO:: Put the right image of Ball here
	Vector2f direction = new Vector2f(0, 0);
	

	public Ball(Vector2f position, float width, float height, Image image) {
		super(position, width, height, true);
		this.image = image;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		// usefull shortcuts
		float radius = width / 2;
		float posX = position.getX();
		float posY = position.getY();
		
		// The physics will calculate here
		for(GameObject object : state.getStateObjects()){
			
			// Calculation for Block-Collision
			if(object instanceof Block){                  
				
			}
			
			// Calculation for Stick-Collision
			else if(object instanceof Stick){              
				
			}
			
			// Calculation for Background-Collision
			else if(object instanceof Background){         
				if(position.getY() - radius <= 0){
					direction.set(direction.getX(), (-1) * direction.getY());                                  // Colliding with top border and bounzing back
					position.set(posX + direction.getX(), posY + direction.getY());
				}
				else if(posY - radius >= container.getHeight()){
					state.removeObject(Ball);                                                                  // Ball falling off-Screen and gets removed
				}
				else if(posX + radius >= container.getWidth() || posX - radius <= 0){
					direction.set((-1) * direction.getX(), direction.getY());
					     // Colliding with right or left border and bounzing back
				}
			}
		}
		if(Ball != null){
			position.set(posX + direction.getX(), posY + direction.getY());
		}
	}

	@Override
	public void render(Graphics g) {
		image.draw(position.getX() - (width / 2), position.getY() - (height / 2), width, height);
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
	 * @param dirx 
	 *             direction in X-Achsis
	 * @param diry
	 *             direction in Y-Achsis
	 */
	public void setDirection(float dirx, float diry){
		direction.set(dirx, diry);
	}
}
