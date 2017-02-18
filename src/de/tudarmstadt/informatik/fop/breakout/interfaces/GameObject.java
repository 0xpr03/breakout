package de.tudarmstadt.informatik.fop.breakout.interfaces;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * Basic class for every possible object in the game.<b> This includes the ball,
 * stick, items and everything updateable.<br>
 * 
 * @author Aron Heinecke, Simon Kohaut
 *
 */
public abstract class GameObject {

	private Vector2f position;
	private float width;
	private float height;

	private Image image;
	private boolean visible = false;
	private boolean animated = false;

	/**
	 * Set the image that will be rendered
	 * 
	 * @param file
	 *            The image source
	 * @throws SlickException
	 */
	public void setImage(final File file) throws SlickException {
		image = new Image(file.getPath());
	}

	/**
	 * Set a flag to determine if the object will be rendered or not
	 * 
	 * @param visible
	 *            The visibility flag
	 */
	public void setVisibility(final boolean visible) {
		this.visible = visible;
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
	 */
	public abstract void update(GameContainer container, StateBasedGame game, GameState state, int delta);

	/**
	 * Render method that shall be called from the game loop
	 */
	public void render(final Graphics g) {
		if (visible)
			image.draw(position.x - width / 2, position.y - height / 2, width, height);
	}

	/**
	 * Returns whether the object is animated or not
	 * 
	 * @return Whether the object is animated or not
	 */
	public boolean isAnimated() {
		return animated;
	}

	/**
	 * Sets whether the object is animated or not
	 * 
	 * @param animated
	 *            whether the object should be animated or not
	 */
	public void setAnimated(final boolean animated) {
		this.animated = animated;
	}
}