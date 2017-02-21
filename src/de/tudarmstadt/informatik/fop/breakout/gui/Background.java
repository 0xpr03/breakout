package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;
import gameObjects.GameObject;

public class Background extends GameObject {

	private Image image;
	
	public Background(Image image, GameContainer app) {
		super(new Vector2f(app.getWidth() / 2, app.getHeight() / 2), app.getWidth(), app.getHeight(), false);
		this.image = image;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState state, int delta) {
		// do nothing
	}

	@Override
	public void render(Graphics g) {
		image.draw(position.x - width / 2, position.y - height / 2, width, height);
	}

}
