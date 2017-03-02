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
import de.tudarmstadt.informatik.fop.breakout.lib.HighscoreLib.Entry;

/**
 * HighscoreLib test unit
 * 
 * @author Aron Heinecke
 *
 */
public class HighschoreLibTest {
	private static File file1;
	private static File file2;
	private static Entry[] testEntries;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		file1 = File.createTempFile("tmpHighschore1", null);
		file2 = File.createTempFile("tmpHighschore2", null);
		testEntries = new Entry[10];
		testEntries[0] = new Entry("a", 100, 2);
		testEntries[1] = new Entry("b", 100, 3);
		testEntries[2] = new Entry("c", 50, 3);
		testEntries[3] = new Entry("d", 40, 3);
		testEntries[4] = new Entry("e", 30, 3);
		testEntries[5] = new Entry("f", 20, 3);
		testEntries[6] = new Entry("g", 20, 4);
		testEntries[7] = new Entry("h", 20, 5);
		testEntries[8] = new Entry("i", 10, 3);
		testEntries[9] = new Entry("j", 1, 3);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		file1.delete();
		file2.delete();
	}

	@Test
	public void testInsert() {
		ArrayList<Entry> cList = new ArrayList<Entry>(Arrays.asList(testEntries));
		Collections.shuffle(cList);

		HighscoreLib hl = new HighscoreLib(file1, cList.size());
		for (Entry e : cList)
			hl.addEntry(e);
		
		ArrayList<Entry> output = hl.getHighscore();
		assertEquals("Highscore list size doesn't match input", cList.size(), output.size());
		for (int i = 0; i < output.size(); i++)
			assertTrue("Unequal objects", entryEquals(testEntries[i], output.get(i)));
	}
	
	@Test
	public void testLoad() {
		HighscoreLib hl = new HighscoreLib(file2, 10);
		
		for (Entry e : testEntries)
			hl.addEntry(e);
		
		hl = new HighscoreLib(file2, 10);
		
		ArrayList<Entry> output = hl.getHighscore();
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
	private boolean entryEquals(Entry a, Entry b) {
		if (a.getBlocks() == b.getBlocks())
			if (a.getName().equals(b.getName()))
				if (a.getTime() == b.getTime())
					return true;
		return false;
	}
}
