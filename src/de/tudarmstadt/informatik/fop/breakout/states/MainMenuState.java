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

/**
 * Class representing the main menu state of the game
 * 
 * @author Aron Heinecke
 */
public class MainMenuState extends GameState {
	private int stateID; // Identifier von diesem BasicGameState

	private final int distance = 100;
	private final int start_Position = 180;

	private Logger logger = LogManager.getLogger(this);

	/**
	 * Creates a new instance of MainMenuState
	 * 
	 * @param sid
	 *            StateID for this state
	 */
	public MainMenuState(int sid) {
		stateID = sid;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		objects.add(new Background(new Image("images/menu.png"), container));

		objects.add(new Button(new Vector2f(200, 200), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("NewGame clicked");
						game.enterState(GameParameters.GAMEPLAY_STATE);
					}
				}));

		objects.add(new Button(new Vector2f(200, 250), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Stats clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 300), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Editor clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 350), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Editor clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 400), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Options clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 450), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("About clicked");
					}
				}));

		objects.add(new Button(new Vector2f(200, 500), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Exit clicked");
						container.exit();
					}
				}));

		logger.exit();
	}

	@Override
	public int getID() {
		return stateID;
	}

}
