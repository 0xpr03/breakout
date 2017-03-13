package de.tudarmstadt.informatik.fop.breakout.lib;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * AssetManager for Slick Images to load them only once
 * 
 * @author Simon Kohaut, Aron Heinecke
 */
public class AssetManager {

	private final HashMap<String, Image> images = new HashMap<String, Image>();
	private final HashMap<String, Sound> sounds = new HashMap<>();

	/**
	 * Get the Image at the specified path
	 * 
	 * @param path
	 *            The path to the Image
	 * @return The Image at the specified path
	 * @throws SlickException
	 */
	public Image getImg(final String path) throws SlickException {
		if (images.containsKey(path))
			return images.get(path);

		images.put(path, new Image(path));
		return images.get(path);
	}
	
	/**
	 * Get the Sound at the specified path
	 * 
	 * @param path
	 * @return The Sound at the specified path
	 * @throws SlickException
	 */
	public Sound getSnd(final String path) throws SlickException {
		if(sounds.containsKey(path))
			return sounds.get(path);
		
		sounds.put(path, new Sound(path));
		return sounds.get(path);
	}

}
