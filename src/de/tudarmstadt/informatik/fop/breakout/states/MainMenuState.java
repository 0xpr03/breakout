package de.tudarmstadt.informatik.fop.breakout.states;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.gui.Background;
import de.tudarmstadt.informatik.fop.breakout.gui.Button;
import de.tudarmstadt.informatik.fop.breakout.gui.Button.ButtonAction;
import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

/**
 * Class representing the main menu state of the game
 * 
 * @author Aron Heinecke
 */
public class MainMenuState extends GameState<Breakout> {

	private final int distance = 100;
	private final int start_Position = 180;

	private Logger logger = LogManager.getLogger(this);

	/**
	 * Creates a new instance of MainMenuState
	 * 
	 * @param stateID
	 * @param stateData
	 */
	public MainMenuState(int stateID, Breakout stateData) {
		super(stateID, stateData);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		AssetManager am = stateData.getAssetManager();
		objects.add(new Background(am.get("images/menu.png"), container));

		objects.add(new Button(new Vector2f(200, 200), 200, 50, am.get("images/newgame_btn_d.png"),
				am.get("images/newgame_btn_m.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("NewGame clicked");
						game.enterState(GameParameters.GAMEPLAY_STATE);
					}
				}));

		objects.add(new Button(new Vector2f(200, 250), 200, 50, am.get("images/highscore_btn_d.png"),
				am.get("images/highscore_btn_m.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Stats clicked");
						game.enterState(GameParameters.HIGHSCORE_STATE);
					}
				}));

		objects.add(new Button(new Vector2f(200, 300), 60, 10, am.get("images/stick.png"),
				am.get("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Editor clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 350), 60, 10, am.get("images/stick.png"),
				am.get("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Editor clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 400), 60, 10, am.get("images/stick.png"),
				am.get("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Options clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 450), 60, 10, am.get("images/stick.png"),
				am.get("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("About clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 500), 60, 10, am.get("images/stick.png"),
				am.get("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Exit clicked");
						container.exit();
					}
				}));

		logger.exit();
	}

}
