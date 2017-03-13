package de.tudarmstadt.informatik.fop.breakout.gameObjects;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;
import de.tudarmstadt.informatik.fop.breakout.states.InGameState;

public class Item extends Sprite{
	

	public Item(Image image, Vector2f position, float width, float height, boolean collideable ) {
		super(image, position, width, height, collideable);
		
	
	}
	int kind = new Random().nextInt(2);
    private final static float pixelPerSecond = 200f;
    
    //applys the effect which is determined by the kind of the item
    private void applyEffect(){
       if(this.kind == 0);
       if(this.kind == 1);
    }
    public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
    	position.y += (delta / 1000.0f) * pixelPerSecond;
    	InGameState stm = (InGameState) state;
    	//collides with the stick
    	if (position.y > stm.getStick().position.y - (stm.getStick().height)/2 - (height)/2 )
    		if( Math.abs(position.x - stm.getStick().position.x) < (stm.getStick().width)/2 - (width)/2){
    			applyEffect();
    			stm.asyncRemoveObject(this);
                }
    	//falls out of the game
        else if (position.y > container.getHeight())
    	stm.asyncRemoveObject(this);
    	
	}
}
