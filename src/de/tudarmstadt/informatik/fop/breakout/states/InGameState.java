package de.tudarmstadt.informatik.fop.breakout.states;

import java.io.File;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.gui.Background;
import de.tudarmstadt.informatik.fop.breakout.gui.Button;
import de.tudarmstadt.informatik.fop.breakout.gui.Button.ButtonAction;
import de.tudarmstadt.informatik.fop.breakout.gui.Clock;
import de.tudarmstadt.informatik.fop.breakout.gui.TextInputField;
import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.lib.GameEvent;
import de.tudarmstadt.informatik.fop.breakout.lib.HighscoreLib.Entry;
import de.tudarmstadt.informatik.fop.breakout.lib.Map;
import de.tudarmstadt.informatik.fop.breakout.lib.MapLoader;
import de.tudarmstadt.informatik.fop.breakout.lib.MapLoader.LoadData;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;
import gameObjects.Ball;
import gameObjects.Block;
import gameObjects.Sprite;
import gameObjects.Stick;

/**
 * Class representing the actual game<br>
 * Including life, win, death management
 * 
 * @author Aron Heinecke
 */
public class InGameState extends GameState<Breakout> implements GameEvent {

	private final Logger logger = LogManager.getLogger(this);

	private final static int I_MAX_LEVEL = 11;
	private MapLoader mapLoader;
	private Map map;
	private LoadData ld;

	private Button bResume;
	private Button bMainScreen;
	private Background bPaused;
	private Stick stick;
	private Button bEnterScore;
	private TextInputField tName;
	private boolean bLoadNext;

	private Clock clock;

	private ArrayList<Block> blockList;
	private ArrayList<Sprite> livesLeft;

	private int level;

	private long score = 0;

	boolean isPaused = false;
	boolean isLost = false;
	
	/**
	 * Creates a new instance of MainMenuState
	 * 
	 * @param stateID
	 * @param stateData Reference to Breakout main instance
	 */
	public InGameState(final int stateID,final Breakout stateData) {
		super(stateID, stateData,stateData.getWidth(),stateData.getHeight());
		this.mapLoader = new MapLoader(stateData.getAppGameContainer().getWidth(),
				stateData.getAppGameContainer().getHeight(), stateData.getAssetManager());
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.level = 1;
		this.isPaused = false;
		this.isLost = false;
		this.clock = null; // reset clock
		initLevel();
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
			ld = mapLoader.loadMap(map);

			objects.set(0, new Background(ld.pBackground, this));

			this.blockList = ld.destroyableBlockList;
			objects.addAll(blockList);
			objects.addAll(ld.undestroyableBlockList);

			this.livesLeft = new ArrayList<Sprite>();
			Image ballImg = stateData.getAssetManager().getImg("images/ball.png");
			livesLeft.add(new Sprite(ballImg, new Vector2f(780, 580), 20, 20, false));
			livesLeft.add(new Sprite(ballImg, new Vector2f(760, 580), 20, 20, false));
			livesLeft.add(new Sprite(ballImg, new Vector2f(740, 580), 20, 20, false));
			objects.addAll(livesLeft);

			objects.add(stick = new Stick(getStickPosition(), 60, 20, ld.pStick));

			objects.add(getNewBall());

		} catch (SlickException e) {
			logger.error("Error at loading Map: ", e);
		}
		if(clock == null) // don't reset clock on level switch
			clock = new Clock(new Vector2f(5, 580));
		objects.add(clock);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		AssetManager am = stateData.getAssetManager();

		bPaused = new Background(new Image("images/pause.png"), this);
		bResume = new Button(new Vector2f(400, 100), 150, 50, am.getImg("images/continue_btn_d.png"), am.getImg("images/continue_btn_m.png"),
				new ButtonAction() {

					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Resume pressed");
						isPaused = false;
					}
				});
		bMainScreen = new Button(new Vector2f(400, 200), 150, 50, am.getImg("images/mainmenu_btn_d.png"), am.getImg("images/mainmenu_btn_m.png"),
				new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Back to MainMenu pressed");
						game.enterState(GameParameters.MAINMENU_STATE);
					}
				});

		bEnterScore = new Button(new Vector2f(400, 300), 60, 10, am.getImg("images/stick.png"), am.getImg("images/stick.png"),
				new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Enter Highscore pressed");
						stateData.getHighscore().addEntry(new Entry(tName.getText(), score, clock.getTimePassed()));
						game.enterState(GameParameters.HIGHSCORE_STATE);
					}
				});

		tName = new TextInputField(new Vector2f(300, 200), 200, 50, "Name: ");

		logger.exit();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		if (isLost) {
			bPaused.render(g);
			tName.render(g);
			bEnterScore.render(g);
		} else if (isPaused) {
			bPaused.render(g);
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
			if(bLoadNext){ // load next level afterwards, avoid race conditions
				level++;
				if(level > I_MAX_LEVEL){
					showHighscoreDialog();
				}
				else{
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
			this.asyncRemoveObject(livesLeft.get(livesLeft.size() - 1));
			this.livesLeft.remove(livesLeft.size() - 1);
			logger.debug("Lives left: {}", livesLeft.size());
			this.asyncAddObject(getNewBall());
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
		logger.entry();
		block.decreaseLife();
		if (block.getLife() == 0) {
			blockList.remove(block);
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
	private Ball getNewBall() {
		return new Ball(new Vector2f(400, 500), 25, ld.pBall, map.getBallVelocity(), map.getGravity(), this,
				getHeight(), getWidth(), stateData.getAssetManager());
	}

	/**
	 * Returns the starting position of the stick
	 * 
	 * @return Vector2f
	 */
	private Vector2f getStickPosition() {
		return new Vector2f(400, 550);
	}
	
}
