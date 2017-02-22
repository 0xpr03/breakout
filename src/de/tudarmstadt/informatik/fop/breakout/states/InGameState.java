package de.tudarmstadt.informatik.fop.breakout.states;

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
import gameObjects.Ball;
import gameObjects.Block;
import gameObjects.GameObject;
import gameObjects.Stick;

/**
 * Class representing the main menu state of the game
 * 
 * @author Aron Heinecke
 */
public class InGameState extends GameState {
	private int stateID; // Identifier von diesem BasicGameState

	private Logger logger = LogManager.getLogger(this);

	private Button bResume;
	private Button bMainScreen;
	private Background bPaused;

	boolean isPaused = false;

	/**
	 * Creates a new instance of MainMenuState
	 * 
	 * @param sid
	 *            StateID for this state
	 */
	public InGameState(int sid) {
		stateID = sid;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		this.isPaused = false;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();

		objects.add(new Stick(new Vector2f(400, 550), 60, 20, new Image("images/stick.png")));

		objects.add(new Ball(new Vector2f(400, 500), 50, 50, new Image("images/ball.png")));

		objects.add(new Block(new Vector2f(100, 100), 50, 20, 1, new Image("images/block_1.png")));

		bPaused = new Background(new Image("images/pause.png"), container);
		bResume = new Button(new Vector2f(400, 100), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {

					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Resume pressed");
						isPaused = false;
					}
				});
		bMainScreen = new Button(new Vector2f(400, 200), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
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
	public int getID() {
		return stateID;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE))
			isPaused = !isPaused;
		if (isPaused) {
			bMainScreen.update(container, game, this, delta);
			bResume.update(container, game, this, delta);
		}
	}
}
