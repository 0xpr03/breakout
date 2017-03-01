package de.tudarmstadt.informatik.fop.breakout.lib;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;
import gameObjects.Block;

/**
 * Map (game) Loader<br>
 * 
 * @author Aron Heinecke
 *
 */
public class MapLoader {
	private Logger logger = LogManager.getLogger(this);
	private int width;
	private int height;
	private GameState<?> state;
	private AssetManager am;
	private final int widthStone = 50;
	private final int heightStone = 30;

	/**
	 * Creates a new MapLoader instance
	 * 
	 * @param width
	 *            Game display width
	 * @param height
	 *            Game dsplay height
	 * @param state
	 *            GameState to which the objects should be added
	 */
	public MapLoader(int width, int height, GameState<?> state, AssetManager am) {
		this.width = width;
		this.height = height;
		this.state = state;
		this.am = am;
	}

	/**
	 * Load map into scenery & return LoadData with theme information
	 * 
	 * @param map
	 * @throws SlickException
	 */
	public LoadData loadMap(Map map) throws SlickException {
		logger.entry("Loading map {}", map.getAbsolutePath());
		map.load();
		int maxRowElements = width / widthStone;
		if (maxRowElements < map.getMaxRowLength()) {
			logger.error("Unable to load Map {}, map too big. Too much elements: {}/{}", map
					.getAbsolutePath(), maxRowElements, map.getMaxRowLength());
		}
		int maxRows = height / heightStone;
		if (maxRows < map.getMap().size()) {
			logger.error("Unable to load Map {], map too big. Too much Rows: {}/{}", map.getMap().size(), maxRows);
		}

		// Set X-offset so it's centered + half the width of a stone
		int startOffsetX = (width - map.getMaxRowLength() * widthStone) / 2 + widthStone / 2;
		// Set Y-offset
		int offsetY = heightStone / 2;
		for (ArrayList<Integer> row : map.getMap()) {
			int offsetX = startOffsetX;
			for (int vStone : row) {
				if (vStone != 0) {
					Block block = new Block(new Vector2f(offsetX, offsetY), widthStone, heightStone, vStone,
							getBallImg(vStone, map.getTheme()));
					state.addObject(block);
					logger.debug("Added stone {}", vStone);
				}
				offsetX += widthStone;
			}
			offsetY += heightStone;
		}
		logger.exit();
		return getLoadData(map.getTheme());
	}

	/**
	 * Loader Data for the specified theme<br>
	 * This class stores images to be used for some elements
	 * 
	 * @author Aron Heinecke
	 *
	 */
	public class LoadData {
		public Image pBackground;
		public Image pStick;
		public Image pBall;

	}

	/**
	 * Returns LoadData for the specified theme
	 * 
	 * @param theme
	 *            ID
	 * @return LoadData
	 * @throws SlickException
	 */
	private LoadData getLoadData(int theme) throws SlickException {
		logger.entry(theme);
		LoadData ld = new LoadData();
		switch (theme) {
		case 0:
		default:
			ld.pBackground = am.get("images/background.png");
			ld.pBall = am.get("images/ball.png");
			ld.pStick = am.get("images/stick.png");
		}
		return ld;
	}

	/**
	 * Returns the correct ball image
	 * 
	 * @param val
	 *            Ball health / type value
	 * @param theme
	 *            Current theme
	 * @return Image
	 * @throws SlickException
	 *             upon loading errors
	 */
	private Image getBallImg(int val, int theme) throws SlickException {
		Image img = null;
		switch (theme) {
		case 0:
		default:
			switch (val) {
			default:
			case 1:
				img = am.get("images/block_1.png");
				break;
			case 2:
				img = am.get("images/block_2.png");
				break;
			case 3:
				img = am.get("images/block_3.png");
			}
		}
		return img;
	}
}