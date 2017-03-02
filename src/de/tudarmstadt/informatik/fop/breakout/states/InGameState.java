package de.tudarmstadt.informatik.fop.breakout.states;

import java.io.File;

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
import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.lib.GameEvent;
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
	
	private Button bResume;
	private Button bMainScreen;
	private Background bPaused;
	
	private int level = 1;
	private int lives = 3;
	
	boolean isPaused = false;

	/**
	 * Creates a new instance of MainMenuState
	 * 
	 * @param stateID
	 * @param stateData
	 */
	public InGameState(int stateID, Breakout stateData) {
		super(stateID, stateData);
		this.mapLoader = new MapLoader(stateData.getAppGameContainer().getWidth(), stateData.getAppGameContainer().getHeight(), this, stateData.getAssetManager());
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.isPaused = false;
		initLevel(container);
	}
	
	/**
	 * Load (next) level
	 * @param GameContainer
	 */
	private void initLevel(GameContainer container){
		objects.clear();
		try {
			objects.add(0, null);
			Map map = new Map(new File("maps/level1.map"), true);
			LoadData ld = mapLoader.loadMap(map);
			
			objects.set(0,new Background(ld.pBackground, stateData));
			objects.add(new Stick(new Vector2f(400, 550), 60, 20,ld.pStick));
	
			objects.add(new Ball(new Vector2f(400, 500), 25, ld.pBall, 2, 0,this));
	
		} catch (SlickException e) {
			logger.error("Error at loading Map: ",e);
		}
		objects.add(new Clock(new Vector2f(5, 580)));
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

		logger.exit();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		if (isPaused) {
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
		if(isPaused){
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
	}

	@Override
	public void blockDestroyed(Block block) {
		logger.entry();
	}
}
