package de.tudarmstadt.informatik.fop.breakout.states;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.gui.Background;
import de.tudarmstadt.informatik.fop.breakout.gui.Button;
import de.tudarmstadt.informatik.fop.breakout.gui.Button.ButtonAction;
import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.lib.HighscoreLib.Entry;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

/**
 * Class representing the main menu state of the game
 * 
 * @author Aron Heinecke
 */
public class HighscoreState extends GameState<Breakout> {

	private ArrayList<Entry> highscore;
	private Logger logger = LogManager.getLogger(this);
	private DecimalFormat timeFormat = new DecimalFormat("#.##");
	private float timePassed;
	
	/**
	 * Create a new instance of HighscoreState
	 * 
	 * @param stateID
	 * @param stateData
	 */
	public HighscoreState(final int stateID, final Breakout stateData) {
		super(stateID, stateData);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		highscore = stateData.getHighscore().getHighscore();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		AssetManager am = stateData.getAssetManager();
		objects.add(new Background(am.getImg("images/menu.png"), stateData));

		objects.add(new Button(new Vector2f(400, 500), 150, 50, am.getImg("images/back_btn_d.png"), am.getImg("images/back_btn_m.png"),
				new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Exit clicked");
						game.enterState(GameParameters.MAINMENU_STATE);
					}
				}));
		objects.add(new Button(new Vector2f(200, 500), 60, 10, am.getImg("images/stick.png"), am.getImg("images/stick.png"),
				new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Clear clicked");
						highscore.clear();
						stateData.getHighscore().clear();
					}
				}));

		logger.exit();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		if (highscore.size() > 0) {
			float offsetX = 90; 
			float offsetY = 200;
			float factY = 20;
			int i = 0;
			
			g.drawString("Blocks", offsetX, 180);
			g.drawString("Time", offsetX + 90, 180);
			g.drawString("Name", offsetX + 200, 180);
			
			for (Entry entry : this.highscore) {
				g.drawString(""+entry.getBlocks(), offsetX, offsetY + factY * i);
				g.drawString(timeFormat.format(entry.getTime()) + "s", offsetX + 90, offsetY + factY * i);
				g.drawString(entry.getName(), offsetX + 200, offsetY + factY * i);
				i++;
			}
		} else {
			g.drawString("No entries so far..", 90, 200);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE))
			game.enterState(GameParameters.MAINMENU_STATE);
	}

}
