package de.tudarmstadt.informatik.fop.breakout.test.testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.informatik.fop.breakout.test.adapter.Adapter;


public class TestMapParser {

	private Adapter adapter;

	@Before
	public void init() {
		adapter = new Adapter();
		adapter.initializeGame();
	}

	@Test
	public void testFirstRow() {
		for (int i = 0; i <= 15; i++) {
			int hitsLeft = adapter.getHitsLeft(i,0);
			assertEquals("Block should have 1 hit left", 1, hitsLeft);
			assertTrue("Block should have hits left",
					adapter.hasHitsLeft(i,0));
		}
	}

	@Test
	public void testSomeBlocks() {
		assertEquals("Block should have 2 hit left", 2,
				adapter.getHitsLeft(1,1));
		assertTrue("Block should have hits left",
				adapter.hasHitsLeft(1,1));

		assertEquals("Block should have 3 hit left", 3,
				adapter.getHitsLeft(2,4));
		assertTrue("Block should have hits left",
				adapter.hasHitsLeft(2,4));

		assertEquals("Block should have 4 hit left", 4,
				adapter.getHitsLeft(7,5));
		assertTrue("Block should have hits left",
				adapter.hasHitsLeft(7,5));
	}

	@Test(expected = NullPointerException.class)
	public void testBlockNotExists() {
		adapter.getHitsLeft(2,2);
	}

}
