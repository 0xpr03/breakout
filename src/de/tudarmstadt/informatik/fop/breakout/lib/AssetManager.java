package de.tudarmstadt.informatik.fop.breakout.lib;

import java.util.TreeMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AssetManager {
	
	private TreeMap<String, Image> images = new TreeMap<String, Image>();
	
	public Image get(String path) throws SlickException {
		if(images.containsKey(path))
			return images.get(path);
		
		images.put(path, new Image(path));
		return images.get(path);
	}
	
}
