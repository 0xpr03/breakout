package de.tudarmstadt.informatik.fop.breakout.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Map Object
 * 
 * @author Aron Heinecke
 *
 */
public class Map {

	private Logger logger = LogManager.getLogger(this);

	private final File file;
	// Column<Row<int>>
	private ArrayList<ArrayList<Integer>> map = new ArrayList<>();
	// Gravity for this map
	private float gravity = 0;
	// Theme for this map
	private int theme = 0;
	// default ball velocity for this map
	private float ballVelocity = 5;
	// maximum elements per row, this map has
	private int maxRowLength = 0;

	private final boolean readOnly;

	private static final int POS_VEL = 0;
	private static final int POS_THEME = 1;
	private static final int POS_GRAV = 2;

	/**
	 * Creates a new Map
	 * 
	 * @param path
	 *            Path to file
	 * @param readOnly
	 *            Set to true to disable write
	 */
	public Map(final String path, boolean readOnly) {
		this(new File(path), readOnly);
	}

	/**
	 * Creates a new Map
	 * 
	 * @param file
	 *            File to read/write
	 * @param readOnly
	 *            Set to true to disable write
	 */
	public Map(final File file, boolean readOnly) {
		this.file = file;
		this.readOnly = readOnly;
	}

	/**
	 * Load the map from file
	 * 
	 * @return true on success
	 */
	public boolean load() {
		synchronized (map) {
			String line = "";
			map.clear();
			maxRowLength = 0;
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				boolean passedData = false;
				while ((line = br.readLine()) != null) {
					if (passedData) { // custom data
						ArrayList<Float> cData = parseFloatDataLine(line);
						if (cData.size() < 3) {
							logger.error("Missing custom map data, line: {}", line);
						} else {
							this.setBallVelocity(cData.get(POS_VEL));
							this.setGravity(cData.get(POS_GRAV));
							// Float to primitive float, then to int
							this.setTheme(((int) (float) cData.get(POS_THEME)));
						}
						break;
					} else if (line.equals(";")) { // detect custom data switch
						passedData = true;
					} else { // parse normal map data
						ArrayList<Integer> pRow = parseIntDataLine(line);
						map.add(pRow);
						if (maxRowLength < pRow.size())
							maxRowLength = pRow.size();
					}
				}
				return true;
			} catch (IOException e) {
				logger.error("Unable to read map file: ", e);
			} catch (NumberFormatException e) {
				logger.error("Malformed map file\nLine:{}\n{}", line, e);
			}
			return false;
		}
	}

	/**
	 * Delete the file of this map
	 */
	public void deleteFile() {
		synchronized (map) {
			this.file.delete();
		}
	}

	/**
	 * Write map to file
	 * 
	 * @return true on success
	 */
	public boolean write() {
		if (this.readOnly) {
			return false;
		}
		synchronized (map) {
			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)))) {
				// map data
				for (ArrayList<Integer> row : map) {
					int start = row.remove(0);
					bw.write(row.stream().map((s) -> Integer.toString(s)).reduce(Integer.toString(start),
							(x, y) -> x + "," + y));
					bw.newLine();
				}

				// custom data
				bw.write(";");
				bw.newLine();

				String[] cData = new String[3];
				cData[POS_GRAV] = Float.toString(this.getGravity());
				cData[POS_VEL] = Float.toString(this.getBallVelocity());
				cData[POS_THEME] = Integer.toString(this.getTheme());
				for (String s : cData) {
					bw.write(s);
					bw.write(",");
				}

				bw.flush();
				return true;
			} catch (IOException e) {
				logger.error("Unable to write map file: {} {}", file.getAbsolutePath(), e);
			}
			return false;
		}
	}

	/**
	 * Parses a line of map data as integers
	 * 
	 * @param line
	 * @return ArrayList<Integer> map data
	 * @throws NumberFormatException
	 *             on malformed data
	 */
	private ArrayList<Integer> parseIntDataLine(final String line) throws NumberFormatException {
		return Arrays.stream(line.split(",")).map(s -> Integer.parseInt(s))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Parses a line of map data as floats
	 * 
	 * @param line
	 * @return ArrayList<Integer> map data
	 * @throws NumberFormatException
	 *             on malformed data
	 */
	private ArrayList<Float> parseFloatDataLine(final String line) throws NumberFormatException {
		return Arrays.stream(line.split(",")).map(s -> Float.parseFloat(s))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Set map data
	 * 
	 * @param map
	 */
	public void setMap(final ArrayList<ArrayList<Integer>> map) {
		synchronized (map) {
			this.map = map;
		}
	}

	/**
	 * Returns the Map data
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> getMap() {
		return this.map;
	}

	/**
	 * Sets a block at the specified position<br>
	 * If the column / row is out of bounds, the map will be increased
	 * 
	 * @param column
	 * @param row
	 * @param value
	 */
	public void setBlock(final int column, final int row, final int value) {
		synchronized (map) {
			if (map.size() <= column) {
				for (int i = map.size(); i <= column; i++) {
					map.add(new ArrayList<>());
				}
			}
			ArrayList<Integer> col = map.get(column);
			if (col.size() <= row) {
				for (int i = col.size(); i <= row; i++) {
					col.add(0);
					if (col.size() > maxRowLength)
						maxRowLength = col.size();
				}
			}
			col.set(row, value);
		}
	}

	/**
	 * Returns the gravity for this map
	 * 
	 * @return the gravity
	 */
	public float getGravity() {
		return gravity;
	}

	/**
	 * Set gravity for this map
	 * 
	 * @param gravity
	 *            the gravity to set
	 */
	public void setGravity(final float gravity) {
		this.gravity = gravity;
	}

	/**
	 * Returns the theme of the map
	 * 
	 * @return the theme
	 */
	public int getTheme() {
		return theme;
	}

	/**
	 * Set theme of this map
	 * 
	 * @param theme
	 *            the theme to set
	 */
	public void setTheme(int theme) {
		this.theme = theme;
	}

	/**
	 * Returns the default ball velocity for this map
	 * 
	 * @return the ballVelocity
	 */
	public float getBallVelocity() {
		return ballVelocity;
	}

	/**
	 * Set default ball velocity for this map
	 * 
	 * @param ballVelocity
	 *            the ball velocity to set
	 */
	public void setBallVelocity(final float ballVelocity) {
		this.ballVelocity = ballVelocity;
	}

	/**
	 * Returns whether is map is read only or not
	 * 
	 * @return is read only
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * Returns the maximum amount of elements per Row, this map has
	 * 
	 * @return the maxRowLength
	 */
	public int getMaxRowLength() {
		return maxRowLength;
	}

	/**
	 * Returns the absolute path of this Map
	 * 
	 * @return String absolute path
	 */
	public String getAbsolutePath() {
		return file.getAbsolutePath();
	}

}
