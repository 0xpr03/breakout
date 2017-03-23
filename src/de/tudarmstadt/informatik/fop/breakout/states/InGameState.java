package de.tudarmstadt.informatik.fop.breakout.states;

import java.io.File;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.Ball;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.Block;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.Item;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.Sprite;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.Stick;
import de.tudarmstadt.informatik.fop.breakout.gui.Background;
import de.tudarmstadt.informatik.fop.breakout.gui.Button;
import de.tudarmstadt.informatik.fop.breakout.gui.Button.ButtonAction;
import de.tudarmstadt.informatik.fop.breakout.gui.Clock;
import de.tudarmstadt.informatik.fop.breakout.gui.Label;
import de.tudarmstadt.informatik.fop.breakout.gui.TextInputField;
import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.lib.EventAceptor;
import de.tudarmstadt.informatik.fop.breakout.lib.HighscoreLib.HighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.lib.Map;
import de.tudarmstadt.informatik.fop.breakout.lib.MapLoader;
import de.tudarmstadt.informatik.fop.breakout.lib.MapLoader.LoadData;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

/**
 * Class representing the actual game<br>
 * Including life, win, death management
 * 
 * @author Aron Heinecke
 */
public class InGameState extends GameState<Breakout> implements EventAceptor {

	private final Logger logger = LogManager.getLogger(this);

	private final static int I_MAX_LEVEL = 11;
	private MapLoader mapLoader;
	private Map map;
	private LoadData levelData;

	private Button bResume;
	private Button bMainScreen;
	private Stick stick;
	private Ball ball;
	private Button bEnterScore;
	private TextInputField tName;
	private boolean bLoadNext;

	private boolean enableCE;
	private final float defautlBallSize = 15;

	private Clock clock;
	private Label blocksDestroyed;

	private ArrayList<Block> blockList;
	private ArrayList<Sprite> livesLeft = new ArrayList<>();;

	private int level;

	private int score = 0;

	boolean isPaused = false;
	boolean isLost = false;

	/**
	 * Creates a new instance of MainMenuState
	 * 
	 * @param stateID
	 * @param stateData
	 *            Reference to Breakout main instance
	 */
	public InGameState(final int stateID, final Breakout stateData) {
		super(stateID, stateData, stateData.getWidth(), stateData.getHeight());
		this.mapLoader = new MapLoader(stateData.getWidth(), stateData.getHeight(), stateData.getAssetManager());
		enableCE = false;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.level = 1;
		this.score = 0;
		this.isPaused = false;
		this.isLost = false;
		this.clock = null; // reset clock
		livesLeft.clear();
		this.blocksDestroyed = null;
		initLevel();
		generateLiveBalls(3);
	}

	/**
	 * Returns a map based in the ingoing level
	 * 
	 * @param level
	 * @return String Path to map
	 */
	private String getLevel(int level) {
		String path = "level";
		if (level > 0 && level <= I_MAX_LEVEL) {
			path += level;
		} else {
			logger.warn("Unknown Level: {}", level);
			path += 1;
		}
		path += ".map";
		return "maps/" + path;
	}

	/**
	 * Load (next) level
	 * 
	 * @param GameContainer
	 */
	private void initLevel() {
		bLoadNext = false;
		objects.clear();
		try {
			objects.add(0, null);
			map = new Map(new File(getLevel(level)), true);
			levelData = mapLoader.loadMap(map);

			objects.set(0, new Background(levelData.pBackground, this));

			this.blockList = levelData.destroyableBlockList;
			objects.addAll(blockList);
			objects.addAll(levelData.undestroyableBlockList);

			objects.add(stick = new Stick(getStickPosition(), 100, 10, levelData.pStick));

			ball = getNewBall();
			objects.add(ball);

			objects.addAll(livesLeft);

		} catch (SlickException e) {
			logger.error("Error at loading Map: ", e);
		}
		if (clock == null) // don't reset clock on level switch
			clock = new Clock(new Vector2f(5, 580));
		if (blocksDestroyed == null)
			blocksDestroyed = new Label(new Vector2f(5, 560), "0");
		objects.add(clock);
		objects.add(blocksDestroyed);
	}

	/**
	 * Generate the Sprites to show the life the player has
	 * 
	 * @param amount
	 *            the amount of lives
	 * @throws SlickException
	 *             if the Image could not be loaded
	 */
	private void generateLiveBalls(int amount) throws SlickException {
		objects.remove(this.livesLeft);
		this.livesLeft = new ArrayList<Sprite>();
		Image ballImg = stateData.getAssetManager().getImg("images/ball.png");

		int livesOffsetX = 780;
		int livesOffsetY = 580;
		int Xdifference = 20;

		for (int i = 0; i < amount; i++) {
			livesLeft.add(
					new Sprite(ballImg, new Vector2f(livesOffsetX - i * Xdifference, livesOffsetY), 20, 20, false));
		}
		objects.addAll(livesLeft);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		AssetManager am = stateData.getAssetManager();

		bResume = new Button(new Vector2f(400, 100), 150, 50, am.getImg("images/continue_btn_d.png"),
				am.getImg("images/continue_btn_m.png"), new ButtonAction() {

					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Resume pressed");
						isPaused = false;
					}
				});
		bMainScreen = new Button(new Vector2f(400, 200), 150, 50, am.getImg("images/mainmenu_btn_d.png"),
				am.getImg("images/mainmenu_btn_m.png"), new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Back to MainMenu pressed");
						game.enterState(GameParameters.MAINMENU_STATE);
					}
				});

		bEnterScore = new Button(new Vector2f(400, 300), 150, 50, am.getImg("images/continue_btn_d.png"),
				am.getImg("images/continue_btn_m.png"), new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Enter Highscore pressed");
						stateData.getHighscore()
								.addEntry(new HighscoreEntry(tName.getText(), score, clock.getTimePassed()));
						game.enterState(GameParameters.HIGHSCORE_STATE);
					}
				});

		tName = new TextInputField(new Vector2f(400, 200), 200, "Name: ");

		logger.exit();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		if (isLost || isPaused) {
			g.setColor(new Color(50, 50, 50, 180));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.white);
		}
		if (isLost) {
			tName.render(g);
			bEnterScore.render(g);
		} else if (isPaused) {
			bResume.render(g);
			bMainScreen.render(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE) || input.isKeyPressed(Input.KEY_P))
			isPaused = !isPaused;
		if (isLost) {
			tName.update(container, game, this, delta);
			bEnterScore.update(container, game, this, delta);
		} else if (isPaused) {
			bMainScreen.update(container, game, this, delta);
			bResume.update(container, game, this, delta);
		} else {
			super.update(container, game, delta);
			if (bLoadNext) { // load next level afterwards, avoid race
								// conditions
				level++;
				if (level > I_MAX_LEVEL) {
					showHighscoreDialog();
				} else {
					initLevel();
				}
			}
		}
	}

	@Override
	public void ballLost(Ball ball) {
		logger.entry();
		this.asyncRemoveObject(ball);
		if (this.livesLeft.size() > 0) {
			stick.resetWidth();
			this.asyncRemoveObject(livesLeft.get(livesLeft.size() - 1));
			this.livesLeft.remove(livesLeft.size() - 1);
			logger.debug("Lives left: {}", livesLeft.size());
			this.ball = getNewBall();
			this.asyncAddObject(this.ball);
			stick.setLocation(getStickPosition());
		} else {
			showHighscoreDialog();
		}
	}

	/**
	 * Shows the highscore dialog and stops the timer
	 */
	private void showHighscoreDialog() {
		logger.entry();
		this.asyncRemoveObject(clock); // stop time
		this.isLost = true;
	}

	@Override
	public void blockHit(Block block) {
		// logger.entry();
		block.decreaseLife();
		if (block.getLife() == 0) {
			// Random Item generation
			final double random = Math.random();
			if (random > 0.5) {
				try {
					asyncAddObject(new Item(new Image("images/item.png"), block.getLocation(), block.getHeight(),
							block.getHeight(), false));
				} catch (SlickException e) {
					logger.warn("Unable to creat Item", e);
				}
			}
			// Removes the Block
			blockList.remove(block);
			blocksDestroyed.setText(String.valueOf(Integer.parseInt(blocksDestroyed.getText()) + 1));
			this.asyncRemoveObject(block);
			this.score++;
			if (blockList.size() == 0) {
				logger.debug("Level finished");
				bLoadNext = true;
			}
		}
	}

	/**
	 * Returns a new Ball object
	 * 
	 * @return Ball
	 */
	public Ball getNewBall() {
		return new Ball(new Vector2f(stick.getLocation().x, stick.getLocation().y - 7.5f), 15,
				levelData.pBall, map.getBallVelocity(), this, getHeight(), getWidth(),
				stateData.getAssetManager(), stick, enableCE);
	}

	/**
	 * Returns the default ball velocity for the current map
	 * 
	 * @return Float default velocity
	 */
	public float getMapDefaultBallVelocity() {
		if (map != null) {
			return map.getBallVelocity();
		} else {
			logger.warn("getMapDefaultBallVelocity request on null map!");
			return -1;
		}
	}

	/**
	 * Returns the default ball size
	 * 
	 * @return the default ball size
	 */
	public float getDefaultBallSize() {
		if (map != null) {
			return defautlBallSize;
		} else {
			logger.warn("getDefaultBallSize request on null map!");
			return -1;
		}
	}

	/**
	 * Returns the starting position of the stick
	 * 
	 * @return Vector2f
	 */
	private Vector2f getStickPosition() {
		return new Vector2f(400, 550);
	}

	/**
	 * Returns the stick of the game
	 * 
	 * @return Stick
	 */
	public Stick getStick() {
		return this.stick;
	}

	/**
	 * Returns the ball
	 * 
	 * @return Ball
	 */
	public Ball getBall() {
		return this.ball;
	}

	/**
	 * Returns if the CE simulation is enabled
	 * 
	 * @return if the CE simulation is enabled
	 */
	public boolean isEnableCE() {
		return enableCE;
	}

	/**
	 * Toggle the CE simulation
	 */
	public void toggleCE() {
		this.enableCE = !this.enableCE;
	}

	/******************************************************
	 * Testing Functions
	 ******************************************************/

	/**
	 * Returns the Block at position X Y<br>
	 * For testing purposes only
	 * 
	 * @param x
	 * @param y
	 * @return Block at this position
	 */
	public Block getBlockAt(int x, int y) {
		try {
			return this.levelData.testBlockMap.get(y).get(x);
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Miss-call off getBlockAt! {} {}", x, y);
			return null;
		}
	}

	/**
	 * Set the amount of lives (balls)<br>
	 * For testing purposes only
	 * 
	 * @param playerLives
	 *            the number of lives/balls the player shall have left
	 */
	public void setLives(int lives) {
		try {
			generateLiveBalls(lives);
		} catch (SlickException e) {
			logger.error("Unable to set lives!", e);
		}
	}

	/**
	 * Returns the amount of lives the player has left<br>
	 * For testing purposes only
	 * 
	 * @return int lives
	 */
	public int getLivesLeft() {
		return livesLeft.size();
	}

}
