package de.tudarmstadt.informatik.fop.breakout.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Theme manager
 * 
 * @author Aron Heinecke
 *
 */
public class ThemeManager {
	private final static Logger logger = LogManager.getLogger();

	/**
	 * Returns the path to the picture of this block
	 * 
	 * @param val
	 *            Value of the block live
	 * @param theme
	 *            current theme
	 * @return String with path to block image
	 */
	public static String getBlockPicturePath(final int val, final int theme) {
		String path;
		switch (theme) {
		case 0:
		default:
			switch (val) {
			case 3:
				path = "block_3.png";
				break;
			case 2:
				path = "block_2.png";
				break;
			case 1:
				path = "block_1.png";
				break;
			default:
				if (val > 3) {
					path = "block_3.png";
					logger.info("Using highest block for theme: {} val: {}", theme, val);
				} else {
					path = "block_1.png";
					logger.warn("Unknown image request, theme: {} value: {}", theme, val);
				}
				break;
			}
			break;
		}
		return "images/" + path;
	}
}
