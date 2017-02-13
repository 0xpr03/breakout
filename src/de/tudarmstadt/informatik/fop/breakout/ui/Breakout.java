package de.tudarmstadt.informatik.fop.breakout.ui;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.AppGameContainer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

public class Breakout extends StateBasedGame implements GameParameters {

	// This has to be created in every class which wants to log smth
	// It doesn't have to be static
	private static Logger logger = LogManager.getLogger(Breakout.class);
	
	// Remember if the game runs in debug mode
	private static boolean debug = false;

	/**
	 * Creates a new Breakout instance
	 * 
	 * @param debug if true, runs in debug mode
	 */
	public Breakout(boolean debug) {
		super("Breakout");
		logger.trace("Debug: {}",debug);
		Breakout.debug = debug;
	}

	public static boolean getDebug() {
		return debug;
	}
	
	/**
	 * Logger demonstration class<br>
	 * Demonstrating multiple log levels, arguments etc
	 * @param arg An argument to be printed
	 */
	private static boolean loggerTest(String arg){
		logger.entry(arg); // log function entry and args
		logger.trace(arg); // lowest log priority
		// second lowest
		logger.debug("Multiple {} INfos {} To {} Print {}","asd",45,true,new Exception("asd"));
		// third lowest
		// we will probably disable everything below info in production
		logger.info("Info MSG {}",true);
		// fourth, warning
		logger.warn("Some warning stuff {}",4645L);
		// fith
		logger.error("Error Test: {}", new Exception("My Exception"));
		// highest
		logger.fatal("Fatal log");
		boolean ret = true;
		logger.exit(ret); // log function exit & return
		return ret;
	}

	public static void main(String[] args) throws SlickException {
		logger.info("Starting game, args: {}",Arrays.toString(args));
		logger.info("==== Logging test ====");
		loggerTest("Arg");
		logger.info("==== Test end ====");
		logger.debug("searching for binaries..");
		// Set the library path depending on the operating system
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			logger.trace("detected windows");
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir") + "/native/windows");
		} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			logger.trace("detected macos");
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir") + "/native/macosx");
		} else {
			logger.trace("detected linux");
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir") + "/native/"
							+ System.getProperty("os.name").toLowerCase());
		}

		// Add this StateBasedGame to an AppGameContainer
		AppGameContainer app = new AppGameContainer(new Breakout(false));

		// Set the display mode and frame rate
		app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
		app.setTargetFrameRate(FRAME_RATE);

		// now start the game!
		app.start();
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// Add the game states (the first added state will be started initially)
	  // This may look as follows, assuming you use the associated class names and constants:
	  /*
	    addState(new MainMenuState(MAINMENU_STATE));
		  addState(new GameplayState(GAMEPLAY_STATE));
		  addState(new HighscoreState(HIGHSCORE_STATE));

		  // Add the states to the StateBasedEntityManager
		  StateBasedEntityManager.getInstance().addState(MAINMENU_STATE);
		  StateBasedEntityManager.getInstance().addState(GAMEPLAY_STATE);
		  StateBasedEntityManager.getInstance().addState(HIGHSCORE_STATE);
    */
	}
}