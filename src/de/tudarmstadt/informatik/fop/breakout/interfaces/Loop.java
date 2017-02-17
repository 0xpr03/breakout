package de.tudarmstadt.informatik.fop.breakout.interfaces;

import java.io.File;

/**
 * Audio Loop
 * @author Aron Heinecke
 *
 */
public class Loop extends Audible {
	
	public Loop(File file) {
		super(file);
	}
	
	/**
	 * Called on every tick
	 */
	public void run() {
		
	}
}
