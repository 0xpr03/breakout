package de.tudarmstadt.informatik.fop.breakout.states;

import java.util.ArrayList;

import org.newdawn.slick.state.BasicGameState;

import de.tudarmstadt.informatik.fop.breakout.interfaces.GameObject;

/**
 * Extended version of BasicGameState providing a getter for the GameObjects
 * @author Aron Heinecke
 *
 */
public abstract class GameState extends BasicGameState {
	
	/**
	 * Returns the GameObjects of this state at the time it's called
	 * @return ArrayList<GameObject> Current GameObjects
	 */
	public abstract ArrayList<GameObject> getStateObjects();
}
