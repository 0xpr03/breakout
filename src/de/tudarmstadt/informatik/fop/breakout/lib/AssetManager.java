package de.tudarmstadt.informatik.fop.breakout.lib;

import java.util.HashMap;

import org.apache.logging.log4j.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

/**
 * AssetManager for Slick Images to load them only once
 * 
 * @author Simon Kohaut, Aron Heinecke
 */
public class AssetManager {

	private Logger logger = LogManager.getLogger(Breakout.class);

	private final HashMap<String, Image> images = new HashMap<String, Image>();
	private final HashMap<String, Sound> sounds = new HashMap<>();
	private boolean testMode = false;

	/**
	 * Get the Image at the specified path
	 * 
	 * @param path
	 *            The path to the Image
	 * @return The Image at the specified path
	 * @throws SlickException
	 */
	public Image getImg(final String path) throws SlickException {
		if (testMode)
			return null;

		if (images.containsKey(path))
			return images.get(path);

		images.put(path, new Image(path));
		return images.get(path);
	}

	/**
	 * Play a sound
	 * 
	 * @param path
	 *            the path where the sound is stored
	 */
	public void playSound(final String path) {
		if (testMode)
			return;

		if (sounds.containsKey(path))
			sounds.get(path).play();

		try {
			sounds.put(path, new Sound(path));
			sounds.get(path).play();
		} catch (SlickException e) {
			logger.warn("Unable to play Sound", e);
		}

	}

	/**
	 * Loop a sound
	 * 
	 * @param path
	 *            the path where the sound is stored
	 */
	public void loopSound(final String path) {
		if (testMode)
			return;

		if (sounds.containsKey(path))
			sounds.get(path).loop();

		try {
			sounds.put(path, new Sound(path));
			sounds.get(path).loop();
		} catch (SlickException e) {
			logger.warn("Unable to play Sound", e);
		}

	}

	/**
	 * Set the test mode on or off. Is used for tests where no images or sounds
	 * should be used.
	 * 
	 * @param flag
	 *            True to set the test mode on, false to turn it off.
	 */
	public void setTestMode(boolean flag) {
		testMode = flag;
	}

}
