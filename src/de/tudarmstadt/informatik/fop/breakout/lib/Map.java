package de.tudarmstadt.informatik.fop.breakout.lib;

import java.io.File;
import java.util.ArrayList;

/**
 * Map Loading mechanism
 * @author Aron Heinecke
 *
 */
public class Map {
	private File file;
	// Column<Row<int>>
	//TODO: change to object type-enum
	private ArrayList<ArrayList<Integer>> map = new ArrayList<>();
	// Gravity for this map
	private float gravity = 0;
	// Theme for this map
	private int theme = 0;
	// default ball velocity for this map
	private float ballVelocity = 1;
	
	/**
	 * Creates a new Map
	 * @param path Path to file
	 */
	public Map(String path){
		this(new File(path));
	}
	
	/**
	 * Creates a new Map
	 * @param file File
	 */
	public Map(File file){
		this.file = file;
	}
	
	/**
	 * Set map data
	 * @param map
	 */
	public void setMap(ArrayList<ArrayList<Integer>> map){
		this.map = map;
	}
	
	/**
	 * Returns the Map data
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> getMap(){
		return this.map;
	}
	
	/**
	 * Sets a block at the specified position<br>
	 * If the column / row is out of bounds, the map will be increased
	 * @param column
	 * @param row
	 * @param value
	 */
	public void setBlock(int column, int row, int value){
		if(map.size() < column){
			for(int i = map.size(); i <= column; i++){
				map.add(new ArrayList<>());
			}
		}
		ArrayList<Integer> col = map.get(column);
		if(col.size() < row){
			for(int i = col.size(); i <= row; i++){
				col.add(0);
			}
		}
		col.set(row, value);
	}

	/**
	 * Returns the gravity for this map
	 * @return the gravity
	 */
	public float getGravity() {
		return gravity;
	}

	/**
	 * Set gravity for this map
	 * @param gravity the gravity to set
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	/**
	 * Returns the theme of the map
	 * @return the theme
	 */
	public int getTheme() {
		return theme;
	}

	/**
	 * Set theme of this map
	 * @param theme the theme to set
	 */
	public void setTheme(int theme) {
		this.theme = theme;
	}

	/**
	 * Returns the default ball velocity for this map
	 * @return the ballVelocity
	 */
	public float getBallVelocity() {
		return ballVelocity;
	}

	/**
	 * Set default ball velocity for this map
	 * @param ballVelocity the ball velocity to set
	 */
	public void setBallVelocity(float ballVelocity) {
		this.ballVelocity = ballVelocity;
	}
}
