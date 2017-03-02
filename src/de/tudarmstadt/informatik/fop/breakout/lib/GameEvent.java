package de.tudarmstadt.informatik.fop.breakout.lib;

import gameObjects.Ball;
import gameObjects.Block;

/**
 * Interface required for Ball, to be callable<br>
 * Containing game events for ball changes
 * @author Aron Heinecke
 *
 */
public interface GameEvent {
	public void ballLost(Ball ball);
	public void blockHit(Block block);
//	public void itemCollected(Item e);
}
