package de.tudarmstadt.informatik.fop.breakout.owntests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

/**
 * Tests for how Vector2f works
 * 
 * @author Aron Heinecke
 */
public class vector_test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		Vector2f vec = new Vector2f(0, 2);
		assertEquals(2, vec.length(), 0.001);
		{
			Vector2f scaledVec = changeLength(1.25f, vec);
			assertEquals(1.25, scaledVec.length(), 0.001);
			System.out.println(scaledVec);
		}
		{
			Vector2f scaledVec = changeLength(4, vec);
			assertEquals(4, scaledVec.length(), 0.001);
			System.out.println(scaledVec);
		}
		{
			Vector2f scaledVec = changeLength(1.25f, new Vector2f(0.5f, 0.247f));
			assertEquals(1.25, scaledVec.length(), 0.001);
			System.out.println(scaledVec);
		}

	}

	/**
	 * Change length of vector2f
	 * 
	 * @param length
	 *            new length
	 * @param vec
	 *            vector to change
	 * @return new vector with changed length
	 */
	private Vector2f changeLength(float length, Vector2f vec) {
		return vec.scale(length / vec.length());
	}

}
