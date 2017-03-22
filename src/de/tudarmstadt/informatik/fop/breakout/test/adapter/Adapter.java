package de.tudarmstadt.informatik.fop.breakout.test.adapter;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.Ball;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.Block;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.GameObject;
import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import eea.engine.test.TestAppGameContainer;

public class Adapter implements GameParameters {

	/*
	 * the instance of our game, extends StateBasedGame
	 */
	Breakout breakout;

	/**
	 * The TestAppGameContainer for running the tests
	 */
	TestAppGameContainer app;

	// TODO you should declare the additional attributes you may require here.

	/**
	 * Use this constructor to initialize everything you need.
	 */
	public Adapter() {
		breakout = null;
	}

	/*
	 * *************************************************** ********* initialize,
	 * run, stop the game **********
	 * ***************************************************
	 * 
	 * You can normally leave this code as it is.
	 */

	public StateBasedGame getStateBasedGame() {
		return breakout;
	}

	/**
	 * Diese Methode initialisiert das Spiel im Debug-Modus, d.h. es wird ein
	 * AppGameContainer gestartet, der keine Fenster erzeugt und aktualisiert.
	 * 
	 * Sie müssen diese Methode erweitern
	 */
	public void initializeGame() {

		// Set the library path depending on the operating system
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/windows");
		} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/macosx");
		} else {
			System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/"
					+ System.getProperty("os.name").toLowerCase());
		}

		// Initialize the game in debug mode (no GUI output)
		breakout = new Breakout(true, GameParameters.WINDOW_HEIGHT, GameParameters.WINDOW_WIDTH);

		try {
			app = new TestAppGameContainer(breakout);
			breakout.getAssetManager().setTestMode(true);
			app.start(0);
			changeToGameplayState();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop a running game
	 */
	public void stopGame() {
		if (app != null) {
			app.exit();
			app.destroy();
		}
		breakout = null;
	}

	public void changeToGameplayState() {
		this.getStateBasedGame().enterState(GAMEPLAY_STATE);
		try {
			app.updateGame(1);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void changeToHighScoreState() {
		this.getStateBasedGame().enterState(HIGHSCORE_STATE);
		try {
			app.updateGame(1);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/*
	 * ***************************************************
	 * ********************** Ball **********************
	 * ***************************************************
	 */

	/**
	 * Returns a new Entity that represents a ball with ID ballID. It was added
	 * for tests, as we do not know what class/package will represent your
	 * "ball" entity.
	 * 
	 * @param ballID
	 *            the ID for the new ball instance
	 * @return an entity representing a ball with the ID passed in as ballID
	 */
	public Ball createBallInstance(String ballID) {
		AssetManager am = new AssetManager();
		am.setTestMode(true);
		return new Ball(new Vector2f(50, 50), 15, null,
				5f, null, GameParameters.WINDOW_HEIGHT, GameParameters.WINDOW_WIDTH, am,
				null, false);
	}

	/**
	 * Returns an instance of the IHitable interface that represents a block
	 * with the ID as passed in and the requested number of hits left (1 = next
	 * hit causes the block to vanish, 2 = it takes two hits, ...)
	 * 
	 * @param blockID
	 *            the ID the returns block entity should have
	 * @param hitsUntilDestroyed
	 *            the number of hits (> 0) the block should have left before it
	 *            vanishes (1 = vanishes with next touch by ball)
	 * @return an entity representing a block with the given ID and hits left
	 */
	public IHitable createBlockInstance(String blockID, int hitsUntilDestroyed) {
		AssetManager am = new AssetManager();
		am.setTestMode(true);
		try {
			return new Block(new Vector2f(50.50), 50, 30, hitsUntilDestroyed, am, 1);
		} catch (SlickException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * sets the ball's orientation (angle). Note: the name of the method is
	 * somewhat unfortunate, but is taken from EEA's entity.
	 * 
	 * @param i
	 *            the new orientation angle for the ball (0...360)
	 */
	public void setRotation(int i) {
		breakout.getIngState().getBall().setRotation(i);
	}

	/**
	 * returns the ball's orientation (angle). Note: the name of the method is
	 * somewhat unfortunate, but is taken from EEA's entity.
	 * 
	 * @return the orientation angle for the ball (0...360)
	 */
	public float getRotation() {
		return breakout.getIngState().getBall().getRotation();
	}

	/**
	 * Sets the ball's position to the coordinate provide
	 * 
	 * @param vector2f
	 *            the target position for the ball
	 */
	public void setPosition(Vector2f vector2f) {
		breakout.getIngState().getBall().setPosition(vector2f);
	}

	/**
	 * returns a definition of the ball's size. Typically, the size of the ball
	 * will be constant, but programmers may introduce bonus items that shrink
	 * or enlarge the ball.
	 * 
	 * @return the size of the ball
	 */
	public Vector2f getSize() {
		return breakout.getIngState().getBall().getSize();
	}

	/**
	 * returns the current speed of the ball's movement
	 * 
	 * @return the ball's speed
	 */
	public float getSpeed() {
		return breakout.getIngState().getBall().getSpeed();
	}

	/**
	 * sets the current speed of the ball to the given value
	 * 
	 * @param speed
	 *            the new speed of the ball
	 */
	public void setSpeed(float speed) {
		breakout.getIngState().getBall().setSpeed(speed);
	}

	/**
	 * provide a proper code mapping to a check if your ball entity collides
	 * with 'otherEntity'. You will have to access your ball instance for this
	 * purpose.
	 * 
	 * @param otherEntity
	 *            another entity that the ball may (or may not) collide with
	 * 
	 * @return true if the two entities have collided. Note: your ball should by
	 *         default not collide with itself (or other balls, if there are
	 *         any), null, the background, or "passable" entities (e.g. other
	 *         image you have placed on the screen). It should only collide with
	 *         the stick if the orientation is correct (>90 but <270 degrees,
	 *         else it would "collide with the underside of the stick") but
	 *         should be "gone" then already). It should also collide with the
	 *         borders if the orientation is correct for this, e.g., only
	 *         collide with the top border if the orientation is fitting).
	 */
	public boolean collides(GameObject otherEntity) {
		if(otherEntity == null)
			return false;
		return breakout.getIngState().getBall().collides(otherEntity);
	}

	/*
	 * ***************************************************
	 * ********************** Player *********************
	 * ***************************************************
	 */

	/**
	 * ensures that the player has "value" additional lives (=additional balls
	 * left).
	 * 
	 * @param value
	 *            the number of additional balls/lives to be added.
	 */
	public void addLives(int value) {
		breakout.getIngState().setLives(breakout.getIngState().getLivesLeft() + value);
	}

	/**
	 * ensures that the player has exactly "playerLives" balls/lives left.
	 * 
	 * @param playerLives
	 *            the number of lives/balls the player shall have left
	 */
	public void setLives(int playerLives) {
		breakout.getIngState().setLives(playerLives);
	}

	/**
	 * queries your classes for the number of lives/balls the player has left
	 * 
	 * @return the number of lives/balls left
	 */
	public int getLivesLeft() {
		return breakout.getIngState().getLivesLeft();
	}

	/**
	 * checks if the player still has at least one live/ball left
	 * 
	 * @return true if the player still has at least one live/ball left, else
	 *         false.
	 */
	public boolean hasLivesLeft() {
		return this.getLivesLeft() > 0;
	}

	/*
	 * ***************************************************
	 * ********************** Block **********************
	 * ***************************************************
	 */

	/**
	 * Sets a number of necessary hits for degrading this block
	 * 
	 * @param hitsLeft
	 *            number of necessary hits
	 * @param blockID
	 *            blockID ID of the chosen block
	 */
	public void setHitsLeft(int hitsLeft, int x, int y) {
		breakout.getIngState().getBlockAt(x, y).setHitsLeft(hitsLeft);
	}

	/**
	 * Returns the number of necessary hits for degrading this block
	 * 
	 * @param blockID
	 *            ID of the chosen block
	 * @return number of hits
	 */
	public int getHitsLeft(int x, int y) {
		return breakout.getIngState().getBlockAt(x, y).getHitsLeft();
	}

	/**
	 * Adds a number of necessary hits for degrading this block
	 * 
	 * @param hitsLeft
	 *            number of hits added
	 * @param blockID
	 *            blockID ID of the chosen block
	 */
	public void addHitsLeft(int hitsLeft, int x, int y) {
		Block block = breakout.getIngState().getBlockAt(x, y);
		block.setHitsLeft(block.getHitsLeft()+hitsLeft);
	}

	/**
	 * Returns whether the block has hits left
	 * 
	 * @param blockID
	 *            blockID ID of the chosen block
	 * @return true, if block has hits left, else false
	 */
	public boolean hasHitsLeft(int x, int y) {
		return getHitsLeft(x, y) > 0;
	}

	/*
	 * ***************************************************
	 * ********************** Stick **********************
	 * ***************************************************
	 */

	/**
	 * returns the current position of the stick
	 * 
	 * @return the current position of the stick
	 */
	public Vector2f getStickPosition() {
		return breakout.getIngState().getStick().getLocation();
	}

	/*
	 * ***************************************************
	 * ********************** Input **********************
	 * ***************************************************
	 */

	/**
	 * This Method should emulate the key down event.
	 * 
	 * @param updatetime
	 *            : Zeitdauer bis update-Aufruf
	 * @param input
	 *            : z.B. Input.KEY_K, Input.KEY_L
	 */
	public void handleKeyDown(int updatetime, Integer input) {
		try {
			app.updateGame(updatetime);
			app.getTestInput().setKeyDown(input);
		} catch (SlickException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * This Method should emulate the pressing of the right arrow key.
	 */
	public void handleKeyDownRightArrow() {
		handleKeyDown(1, Input.KEY_RIGHT);
	}

	/**
	 * This Method should emulate the pressing of the left arrow key.
	 */
	public void handleKeyDownLeftArrow() {
		handleKeyDown(1, Input.KEY_LEFT);
	}
}
