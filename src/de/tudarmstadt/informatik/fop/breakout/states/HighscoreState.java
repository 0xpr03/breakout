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
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

/**
 * Class representing the main menu state of the game
 * 
 * @author Aron Heinecke
 */
public class HighscoreState extends GameState<Breakout> {

	private final int distance = 100;
	private final int start_Position = 180;

	private Logger logger = LogManager.getLogger(this);

	/**
	 * Create a new instance of HighscoreState
	 * 
	 * @param stateID
	 * @param stateData
	 */
	public HighscoreState(int stateID, Breakout stateData) {
		super(stateID, stateData);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		objects.add(new Background(new Image("images/menu.png"), container));

		objects.add(new Button(new Vector2f(200, 500), 60, 10, new Image("images/stick.png"),
				new Image("images/stick.png"), new ButtonAction() {
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Exit clicked");
						game.enterState(GameParameters.MAINMENU_STATE);
					}
				}));

		logger.exit();
	}

}
