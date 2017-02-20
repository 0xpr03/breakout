package gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

public class Ball extends GameObject{
	
	private Image image;   // TODO:: Put the right image of Ball here

	public Ball(Vector2f position, float width, float height, boolean collideable) {
		super(position, width, height, collideable);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		// TODO Auto-generated method stub
		// The physics will calculate here
	}

	@Override
	public void render(Graphics g) {
		image.draw(position.getX() - (width / 2), position.getY() - (height / 2), width, height);
	}
}
