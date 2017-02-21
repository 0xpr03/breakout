package de.tudarmstadt.informatik.fop.breakout.owntests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.tudarmstadt.informatik.fop.breakout.lib.Map;

/**
 * Map load / save test
 * 
 * @author Aron Heinecke
 *
 */
public class MapTest {
	private static File file;
	private static File tmpFile;
	private static int[][] testMapData;
	private static float velocity = 1.3304f;
	private static float gravity = 3.4654f;
	private static int theme = 2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		file = new File("maps/test.map");
		assertTrue(file.exists());
		assertTrue(file.canRead());
		tmpFile = File.createTempFile("tmpMap", null);

		testMapData = new int[3][3];
		testMapData[0] = new int[] { -1, 2, 3 };
		testMapData[1] = new int[] { 4, 5, 6 };
		testMapData[2] = new int[] { 7, 7, 8 };
		velocity = 1.3304f;
		gravity = 3.4654f;
		theme = 2;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		tmpFile.delete();
	}

	/**
	 * Saving test
	 */
	@Test
	public void testSaving() {
		Map map = new Map(tmpFile, false);
		map.setBallVelocity(velocity);
		map.setGravity(gravity);
		map.setTheme(theme);
		map.setMap(Int2dArrayToArrayList(testMapData));
		assertTrue("Map write", map.write());
		iLoadingTest(testMapData, velocity, gravity, theme, tmpFile);
	}

	/**
	 * Loading test
	 */
	@Test
	public void testLoading() {
		iLoadingTest(testMapData, velocity, gravity, theme, file);
	}

	/**
	 * Converts an int[][] to ArrayList<ArrayList<Integer>>
	 * 
	 * @param data
	 *            int[][] map data
	 * @return ArrayList<ArrayList<Integer>> of map data
	 */
	private ArrayList<ArrayList<Integer>> Int2dArrayToArrayList(int[][] data) {
		return Arrays.stream(data)
				.map(r -> Arrays.stream(r).mapToObj((s) -> new Integer(s))
						.collect(Collectors.toCollection(ArrayList::new)))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Check loaded parameters for map specified<br>
	 * For all floats a precision of 0.0001 is required
	 * 
	 * @param expectedMap
	 * @param velocity
	 * @param gravity
	 * @param theme
	 * @param cFile
	 */
	private void iLoadingTest(int[][] expectedMap, float velocity, float gravity, int theme, File cFile) {
		Map map = new Map(cFile, true);
		assertFalse(map.write());
		assertTrue(map.load());
		assertEquals("Ball velocity", velocity, map.getBallVelocity(), 0.0001);
		assertEquals("Gravity ", gravity, map.getGravity(), 0.0001);
		assertEquals("Theme", theme, map.getTheme());

		int[][] outputMap = map.getMap().stream().map(u -> u.stream().mapToInt(i -> i).toArray()).toArray(int[][]::new);

		assertEquals("Map data length not equally", expectedMap.length, outputMap.length);
		for (int i = 0; i < expectedMap.length; i++) {
			assertArrayEquals("Map data not equally", expectedMap[i], outputMap[i]);
		}
	}

}
