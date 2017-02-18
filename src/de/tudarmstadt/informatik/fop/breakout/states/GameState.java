package de.tudarmstadt.informatik.fop.breakout.states;

import java.util.ArrayList;

import org.newdawn.slick.state.BasicGameState;

import de.tudarmstadt.informatik.fop.breakout.interfaces.GameObject;

/**
 * Extended version of BasicGameState providing a getter for the GameObjects
 * 
 * @author Aron Heinecke
 *
 */
public abstract class GameState extends BasicGameState {
	protected ArrayList<GameObject> objects = new ArrayList<>();

	/**
	 * Returns the GameObjects of this state at the time it's called
	 * 
	 * @return ArrayList<GameObject> Current GameObjects
	 */
	public ArrayList<GameObject> getStateObjects() {
		return objects;
	}

	/**
	 * Removes the specified object
	 * 
	 * @param go
	 *            GameObject to be removed
	 */
	public void removeObject(GameObject go) {
		objects.remove(go);
	}

	/**
	 * Adds the game object
	 * 
	 * @param go
	 *            GameObject to be added
	 */
	public void addObject(GameObject go) {
		objects.add(go);
	}
}
