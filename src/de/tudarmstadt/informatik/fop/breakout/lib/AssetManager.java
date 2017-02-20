package de.tudarmstadt.informatik.fop.breakout.lib;

import java.util.TreeMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * AssetManager for Slick Images to load them only once
 * 
 * @author Simon Kohaut
 */
public class AssetManager {

	private TreeMap<String, Image> images = new TreeMap<String, Image>();

	/**
	 * Get the Image at the specified path
	 * 
	 * @param path
	 *            The path to the Image
	 * @return The Image at the specified path
	 * @throws SlickException
	 */
	public Image get(String path) throws SlickException {
		if (images.containsKey(path))
			return images.get(path);

		images.put(path, new Image(path));
		return images.get(path);
	}

}
