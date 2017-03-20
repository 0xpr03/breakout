package de.tudarmstadt.informatik.fop.breakout.gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * Basic class for every possible object in the game.<b> This includes the ball,
 * stick, items and everything updateable.<br>
 * 
 * @author Aron Heinecke, Simon Kohaut
 */
public class GameObject {

	protected Vector2f position;
	protected float width;
	protected float height;
	
	protected final int id;
	private static int idCounter;
	
	private boolean collideable = false;

	/**
	 * Create a new instance of GameObject
	 * 
	 * @param position
	 *            The position of the GameObject
	 * @param width
	 *            The width of the GameObject
	 * @param height
	 *            The height of the GameObject
	 * @param collideable
	 *            If the GameObject is collideable or not
	 */
	public GameObject(Vector2f position, float width, float height, boolean collideable) {
		id = idCounter++;
		this.position = position;
		this.width = width;
		this.height = height;
		this.collideable = collideable;
	}

	/**
	 * Set the location of the game object
	 * 
	 * @param centerX
	 *            The x coordinate of the center
	 * @param centerY
	 *            The y coordinate of the center
	 */
	public void setLocation(final float centerX, final float centerY) {
		this.position.x = centerX;
		this.position.y = centerY;
	}

	/**
	 * Set the location of the game object
	 * 
	 * @param centerX
	 *            The x coordinate of the center
	 * @param centerY
	 *            The y coordinate of the center
	 */
	public void setLocation(Vector2f pos) {
		this.position = pos;
	}

	/**
	 * Returns the location of the game object
	 * 
	 * @return Vec2f with center x & y coordinates
	 */
	public Vector2f getLocation() {
		return position;
	}

	/**
	 * Set the width of the game object
	 * 
	 * @param width
	 *            The new width
	 */
	public void setWidth(final float width) {
		this.width = width;
	}

	/**
	 * Set the height of the game object
	 * 
	 * @param height
	 *            The new height
	 */
	public void setHeight(final float height) {
		this.height = height;
	}

	/**
	 * Returns the height of this game object
	 * 
	 * @return The height of this game object
	 */
	public float getHeight() {
		return this.height;
	}

	/**
	 * Returns the width of this game object
	 * 
	 * @return The width of this game object
	 */
	public float getWidth() {
		return this.width;
	}

	/**
	 * Update method that shall be called from the game loop
	 * @throws SlickException 
	 */
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) throws SlickException {
	}

	/**
	 * Render method that shall be called from the game loop
	 */
	public void render(Graphics g) {
	}

	/**
	 * Returns whether the object is collideable or not
	 * 
	 * @return Whether the object is collideable or not
	 */
	public boolean isCollideable() {
		return collideable;
	}

	/**
	 * Set whether the object is collideable or not
	 * 
	 * @param Whether
	 *            the object should be collideable or not
	 */
	public void setCollideable(boolean collideable) {
		this.collideable = collideable;
	}

	public int getID() {
		return id;
	}
}
