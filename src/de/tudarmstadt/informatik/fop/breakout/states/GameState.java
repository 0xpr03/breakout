package de.tudarmstadt.informatik.fop.breakout.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import gameObjects.GameObject;

/**
 * Extended version of BasicGameState providing a getter for the GameObjects
 * 
 * @author Aron Heinecke
 *
 */
public abstract class GameState<T> extends BasicGameState {
	protected ArrayList<GameObject> objects = new ArrayList<>();
	private ArrayList<GameObject> clearList = new ArrayList<>(1);
	private ArrayList<GameObject> addList = new ArrayList<>(1);
	protected T stateData;
	private int stateID;

	/**
	 * Creates a new GameState
	 * 
	 * @param stateID
	 * @param stateData
	 */
	public GameState(int stateID, T stateData) {
		this.stateID = stateID;
		this.stateData = stateData;
	}

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
		clearList.add(go);
	}

	/**
	 * Adds the specified game object<br>
	 * Warning: This shouldn't be called from within any update routine!
	 * 
	 * @param go
	 *            GameObject to be added
	 */
	public void addObject(GameObject go) {
		objects.add(go);
	}

	/**
	 * Adds a gameobject to the object list<br>
	 * This function is safe to call from inside an update routing
	 * 
	 * @param go
	 */
	public void asyncAddObject(GameObject go) {
		addList.add(go);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		for (GameObject obj : objects)
			obj.update(container, game, this, delta);
		for (GameObject obj : clearList)
			objects.remove(obj);
		clearList.clear();
		objects.addAll(addList);
		addList.clear();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		for (GameObject go : objects)
			go.render(g);
	}

	@Override
	public int getID() {
		return stateID;
	}
}
