package gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

public class Sprite extends GameObject {
	
	protected Image image;
	
	public Sprite(Image image, Vector2f position, float width, float height, boolean collideable) {
		super(position, width, height, collideable);
		this.image = image;
	}

	public Vector2f getTopLeft() {
		return new Vector2f(position.x - width / 2, position.y + height / 2);
	}
	
	public Vector2f getBottomRight() {
		return new Vector2f(position.x + width / 2, position.y - height / 2);
	}
	
	@Override
	public void render(Graphics g) {
		image.draw(position.x - width / 2, position.y - height / 2, width, height);
	}

	/**
	 * Just to be real
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {}

}
