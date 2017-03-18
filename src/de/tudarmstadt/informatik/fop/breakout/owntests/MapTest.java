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
	private static File C_FILE;
	private static File C_TMP_FILE;
	private static int[][] C_TEST_MAP_DATA;
	private static float C_VELOCITY = 1.3304f;
	private static float C_GRAVITY = 3.4654f;
	private static int C_THEME = 2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		C_FILE = new File("maps/test.map");
		assertTrue(C_FILE.exists());
		assertTrue(C_FILE.canRead());
		C_TMP_FILE = File.createTempFile("tmpMap", null);

		C_TEST_MAP_DATA = new int[3][3];
		C_TEST_MAP_DATA[0] = new int[] { -1, 2, 3 };
		C_TEST_MAP_DATA[1] = new int[] { 4, 5, 6 };
		C_TEST_MAP_DATA[2] = new int[] { 7, 7, 8 };
		C_VELOCITY = 1.3304f;
		C_GRAVITY = 3.4654f;
		C_THEME = 2;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		C_TMP_FILE.delete();
	}

	/**
	 * Saving test
	 */
	@Test
	public void testSaving() {
		Map map = new Map(C_TMP_FILE, false);
		map.setBallVelocity(C_VELOCITY);
		map.setGravity(C_GRAVITY);
		map.setTheme(C_THEME);
		map.setMap(Int2dArrayToArrayList(C_TEST_MAP_DATA));
		assertTrue("Map write", map.write());
		iLoadingTest(C_TEST_MAP_DATA, C_VELOCITY, C_GRAVITY, C_THEME, C_TMP_FILE);
	}

	/**
	 * Loading test
	 */
	@Test
	public void testLoading() {
		iLoadingTest(C_TEST_MAP_DATA, C_VELOCITY, C_GRAVITY, C_THEME, C_FILE);
	}
	
	@Test
	public void testSetBlock() {
		Map map = new Map(C_TMP_FILE, false);
		map.setMap(Int2dArrayToArrayList(C_TEST_MAP_DATA));
		assertEquals(3,map.getMap().size());
		ArrayList<Integer> row = map.getMap().get(2);
		assertEquals(3, row.size());
		map.setBlock(2, 4, 5);
		assertEquals(5,map.getMap().get(2).size());
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
