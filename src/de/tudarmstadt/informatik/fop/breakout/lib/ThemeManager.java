package de.tudarmstadt.informatik.fop.breakout.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThemeManager {
	private static Logger logger = LogManager.getLogger();
	
	public static String getBlockPicturePath(int val, int theme){
		String path;
		switch(theme){
		case 0:
		default:
			switch(val){
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
				if(val > 3){
					path = "block_3.png";
					logger.info("Using highest block for theme: {} val: {}",theme,val);
				}else{
					path = "block_1.png";
					logger.warn("Unknown image request, theme: {} value: {}",theme,val);
				}
				break;
			}
			break;
		}
		return "images/"+path;
	}
}
