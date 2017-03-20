package de.tudarmstadt.informatik.fop.breakout.owntests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.tudarmstadt.informatik.fop.breakout.lib.HighscoreLib;
import de.tudarmstadt.informatik.fop.breakout.lib.HighscoreLib.HighscoreEntry;

/**
 * HighscoreLib test unit
 * 
 * @author Aron Heinecke
 *
 */
public class HighschoreLibTest {
	private static File file1;
	private static File file2;
	private static HighscoreEntry[] testEntries;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		file1 = File.createTempFile("tmpHighschore1", null);
		file2 = File.createTempFile("tmpHighschore2", null);
		testEntries = new HighscoreEntry[10];
		testEntries[0] = new HighscoreEntry("a", 100, 2);
		testEntries[1] = new HighscoreEntry("b", 100, 3);
		testEntries[2] = new HighscoreEntry("c", 50, 3);
		testEntries[3] = new HighscoreEntry("d", 40, 3);
		testEntries[4] = new HighscoreEntry("e", 30, 3);
		testEntries[5] = new HighscoreEntry("f", 20, 3);
		testEntries[6] = new HighscoreEntry("g", 20, 4);
		testEntries[7] = new HighscoreEntry("h", 20, 5);
		testEntries[8] = new HighscoreEntry("i", 10, 3);
		testEntries[9] = new HighscoreEntry("j", 1, 3);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		file1.delete();
		file2.delete();
	}

	@Test
	public void testInsert() {
		ArrayList<HighscoreEntry> cList = new ArrayList<HighscoreEntry>(Arrays.asList(testEntries));
		Collections.shuffle(cList);

		HighscoreLib hl = new HighscoreLib(file1, cList.size());
		for (HighscoreEntry e : cList)
			hl.addEntry(e);
		
		ArrayList<HighscoreEntry> output = hl.getHighscore();
		assertEquals("Highscore list size doesn't match input", cList.size(), output.size());
		for (int i = 0; i < output.size(); i++)
			assertTrue("Unequal objects", entryEquals(testEntries[i], output.get(i)));
	}
	
	@Test
	public void testLoad() {
		HighscoreLib hl = new HighscoreLib(file2, 10);
		
		for (HighscoreEntry e : testEntries)
			hl.addEntry(e);
		
		hl = new HighscoreLib(file2, 10);
		
		ArrayList<HighscoreEntry> output = hl.getHighscore();
		assertEquals("Highscore list size doesn't match input", testEntries.length, output.size());
		for (int i = 0; i < output.size(); i++)
			assertTrue("Unequal objects", entryEquals(testEntries[i], output.get(i)));
	}

	/**
	 * Test for value equality of two Entries
	 * 
	 * @param a
	 *            Entry A
	 * @param b
	 *            Entry B
	 * @return true if both are equal
	 */
	private boolean entryEquals(HighscoreEntry a, HighscoreEntry b) {
		if (a.getNumberOfDestroyedBlocks() == b.getNumberOfDestroyedBlocks())
			if (a.getPlayerName().equals(b.getPlayerName()))
				if (a.getElapsedTime() == b.getElapsedTime())
					return true;
		return false;
	}
}
