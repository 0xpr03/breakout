package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import gameObjects.GameObject;

/**
 * Background object
 * 
 * @author Aron Heinecke
 *
 */
public class Background extends GameObject {

	private Image image;

	/**
	 * New Background object
	 * 
	 * @param image
	 *            Image for background
	 * @param app
	 *            GameContainer
	 */
	public Background(Image image, @SuppressWarnings("rawtypes") GameState state) {
		super(new Vector2f(state.getWidth() / 2, state.getHeight() / 2), state.getWidth(), state.getHeight(), false);
		this.image = image;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, @SuppressWarnings("rawtypes") GameState state, int delta) {
		// do nothing
	}

	@Override
	public void render(Graphics g) {
		image.draw(position.x - width / 2, position.y - height / 2, width, height);
	}

}
