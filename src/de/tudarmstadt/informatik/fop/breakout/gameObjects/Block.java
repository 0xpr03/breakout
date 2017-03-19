package de.tudarmstadt.informatik.fop.breakout.gameObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.lib.ThemeManager;
import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * Class to abstract a Block with life
 * 
 * @author Tim Jäger, Aron Heinecke
 */
public class Block extends Sprite {

	private Logger logger = LogManager.getLogger(this);
	private int life;
	private int theme;
	private AssetManager am;

	/**
	 * Create a new instance of Block
	 * 
	 * @param position
	 *            The center position of the Block
	 * @param width
	 *            The width of the Block
	 * @param height
	 *            The height of the Block
	 * @param life
	 *            The life of the Block
	 * @param image
	 *            The Image to represent the Block on screen
	 * @throws SlickException 
	 */
	public Block(Vector2f position, float width, float height, int life, AssetManager am, int theme) throws SlickException {
		super(am.getImg(ThemeManager.getBlockPicturePath(life, theme)), position, width, height, true);
		this.life = life;
		this.am = am;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		// Has to be empty, because he does not move!
	}

	/**
	 * Returns the life the Block has left
	 * 
	 * @return the life the Blick has left
	 */
	public int getLife() {
		return life;
	}
	
	private Image getPicture() throws SlickException{
		return am.getImg(ThemeManager.getBlockPicturePath(life, theme));
	}
	
	private void updatePicture(){
		try{
		this.setImage(getPicture());
		}catch(SlickException e){
			logger.error("Error on block picture change! {}",e);
		}
	}

	/**
	 * Decrement the life by 1
	 * @throws SlickException 
	 */
	public void decreaseLife() {
		if(life > 0){
			life--;
			if(life != 0)
				updatePicture();
		}
	}
	
	/*******************************************
	 *      Testing Functions
	 *******************************************/
	
	/**
	 * Set the amount of hits untill block destruction<br>
	 * For testing purposes only
	 * 
	 * @param hits
	 */
	public void setHitsLeft(int hitsLeft){
		this.life = hitsLeft;
	}
	
	/**
	 * Returns the amount of hits left untill block destruction<br>
	 * For testing purposes only
	 * 
	 * @return int left hits
	 */
	public int getHitsLeft(){
		return this.life;
	}
}
