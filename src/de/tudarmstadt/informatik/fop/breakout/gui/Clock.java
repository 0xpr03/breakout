package de.tudarmstadt.informatik.fop.breakout.gui;

import java.text.DecimalFormat;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;
import gameObjects.GameObject;

public class Clock extends Label {

	private DecimalFormat timeFormat = new DecimalFormat("#.##");
	private float timePassed;
	
	public Clock(Vector2f position) {
		super(position, "0.0s");
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
		timePassed += delta / 1000.0f;
		setText(timeFormat.format(timePassed) + "s");
	}

}
