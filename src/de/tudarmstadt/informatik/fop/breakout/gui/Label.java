package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;
import gameObjects.GameObject;

public class Label extends GameObject {

	private String text;
	
	public Label(Vector2f position, String text) {
		super(position, 0, 0, false);
		this.text = text;
	}
	
	void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
	}

	@Override
	public void render(Graphics g) {
		g.drawString(text, position.x, position.y);
	}

}
