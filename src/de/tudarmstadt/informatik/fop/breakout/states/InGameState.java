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
import gameObjects.Stick;

/**
 * Class representing the main menu state of the game
 * 
 * @author Aron Heinecke
 */
public class InGameState extends GameState<Breakout> implements GameEvent {

	private Logger logger = LogManager.getLogger(this);

	private MapLoader mapLoader;
	private Map map;
	private LoadData ld;
	
	private Button bResume;
	private Button bMainScreen;
	private Background bPaused;
	private Stick stick;
	private Button bEnterScore;
	private TextInputField tName;
	
	private Clock clock;
	
	private ArrayList<Block> blockList;
	
	private int level;
	private int lives;
	
	private long score = 0;
	
	boolean isPaused = false;
	boolean isLost = false;

	/**
	 * Creates a new instance of MainMenuState
	 * 
	 * @param stateID
	 * @param stateData
	 */
	public InGameState(int stateID, Breakout stateData) {
		super(stateID, stateData);
		this.mapLoader = new MapLoader(stateData.getAppGameContainer().getWidth(), stateData.getAppGameContainer().getHeight(), stateData.getAssetManager());
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.lives = 3;
		this.level = 1;
		this.isPaused = false;
		this.isLost = false;
		initLevel(container);
	}
	
	/**
	 * Returns a map based in the ingoing level
	 * @param level
	 * @return String Path to map
	 */
	private String getLevel(int level){
		String path;
		switch(level){
		default:
		case 1:
			path = "level1.map";
			break;
		}
		return "maps/"+path;
	}
	
	/**
	 * Load (next) level
	 * @param GameContainer
	 */
	private void initLevel(GameContainer container){
		objects.clear();
		try {
			objects.add(0, null);
			map = new Map(new File(getLevel(level)), true);
			ld = mapLoader.loadMap(map);
			
			objects.set(0,new Background(ld.pBackground, stateData));
			
			this.blockList = ld.blockList;
			objects.addAll(blockList);
			
			objects.add(stick = new Stick(getStickPosition(), 60, 20,ld.pStick));
			
			objects.add(getNewBall());
	
		} catch (SlickException e) {
			logger.error("Error at loading Map: ",e);
		}
		objects.add(clock = new Clock(new Vector2f(5, 580)));
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		AssetManager am = stateData.getAssetManager();
		
		bPaused = new Background(new Image("images/pause.png"), stateData);
		bResume = new Button(new Vector2f(400, 100), 60, 10, am.get("images/stick.png"),
				am.get("images/stick.png"), new ButtonAction() {

					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Resume pressed");
						isPaused = false;
					}
				});
		bMainScreen = new Button(new Vector2f(400, 200), 60, 10, am.get("images/stick.png"),
				am.get("images/stick.png"), new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Back to MainMenu pressed");
						game.enterState(GameParameters.MAINMENU_STATE);
					}
				});
		
		bEnterScore = new Button(new Vector2f(400, 300), 60, 10, am.get("images/stick.png"),
				am.get("images/stick.png"), new ButtonAction() {
			@SuppressWarnings("rawtypes")
			@Override
			public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
				logger.trace("Enter Highscore pressed");
				stateData.getHighscore().addEntry(new Entry(tName.getText(),score,clock.getTimePassed()));
				game.enterState(GameParameters.HIGHSCORE_STATE);
			}
		});
		
		tName = new TextInputField(new Vector2f(300, 200), 60, 10, "Name: ");

		logger.exit();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		 if(isLost){
				bPaused.render(g);
				tName.render(g);
				bEnterScore.render(g);
		}else if (isPaused) {
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
		if(isLost){
			tName.update(container, game, this, delta);
			bEnterScore.update(container, game, this, delta);
		}else if(isPaused){
			bMainScreen.update(container, game, this, delta);
			bResume.update(container, game, this, delta);
		}else{
			super.update(container, game, delta);
		}
	}

	@Override
	public void ballLost(Ball ball) {
		logger.entry();
		this.removeObject(ball);
		this.lives--;
		if(this.lives > 0){
			logger.debug("Lives left: {}",lives);
			this.asyncAddObject(getNewBall());
			stick.setLocation(getStickPosition());
		}else{
			showHighscoreDialog();
		}
	}
	
	/**
	 * Shows the highscore dialog and stops the timer
	 */
	private void showHighscoreDialog(){
		logger.entry();
		this.removeObject(clock); // stop time
		this.isLost = true;
	}

	@Override
	public void blockHit(Block block) {
		logger.entry();
		block.decreaseLife();
		if(block.getLife() == 0){
			blockList.remove(block);
			this.removeObject(block);
			this.score++;
			if(blockList.size() == 0){
				logger.debug("Level finished");
			}
		}
	}
	
	/**
	 * Returns a new Ball object
	 * @return Ball
	 */
	private Ball getNewBall(){
		return new Ball(new Vector2f(400, 500), 25, ld.pBall, map.getBallVelocity(), map.getGravity(),this);
	}
	
	/**
	 * Returns the starting position of the stick
	 * @return Vector2f
	 */
	private Vector2f getStickPosition(){
		return new Vector2f(400, 550);
	}
}
