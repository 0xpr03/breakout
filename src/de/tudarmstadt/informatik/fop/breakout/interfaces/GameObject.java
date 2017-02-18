package de.tudarmstadt.informatik.fop.breakout.interfaces;

import java.io.File;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class GameObject {

	private float centerX;
	private float centerY;
	private float width;
	private float height;

	private Image image;
	private boolean visible = false;

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
		this.centerX = centerX;
		this.centerY = centerY;
	}

	/**
	 * Set the width and height of the game object
	 * 
	 * @param width
	 *            The new width
	 * @param height
	 *            The new height
	 */
	public void setDimension(final float width, final float height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Update method that shall be called from the game loop
	 */
	public abstract void update();

	/**
	 * Render method that shall be called from the game loop
	 */
	public void render() {
		if (visible)
			image.draw(centerX - width / 2, centerY - height / 2, width, height);
	}
}
