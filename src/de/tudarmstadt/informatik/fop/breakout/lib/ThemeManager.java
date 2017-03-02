package de.tudarmstadt.informatik.fop.breakout.lib;

public class ThemeManager {
	public static String getBlockPicturePath(int val, int theme){
		String path;
		switch(theme){
		case 1:
		default:
			switch(val){
			case 3:
				path = "block_3.png";
				break;
			case 2:
				path = "block_2.png";
				break;
			case 1:
			default:
				path = "block_1.png";
				break;
			}
			break;
		}
		return "images/"+path;
	}
}
