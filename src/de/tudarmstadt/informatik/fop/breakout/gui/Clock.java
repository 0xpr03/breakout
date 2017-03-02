package de.tudarmstadt.informatik.fop.breakout.gui;

import java.text.DecimalFormat;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * A Clock to display the elapsed time on screen
 * 
 * @author Simon Kohaut
 */
public class Clock extends Label {

	private DecimalFormat timeFormat = new DecimalFormat("#.##");
	private float timePassed;

	/**
	 * Create a new Clock instance
	 * 
	 * @param position
	 *            The position where the Clock will be displayed
	 */
	public Clock(Vector2f position) {
		super(position, "0.0s");
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
		timePassed += delta / 1000.0f;
		setText(timeFormat.format(timePassed) + "s");
	}

	/**
	 * @return the timePassed
	 */
	public float getTimePassed() {
		return timePassed;
	}

}
