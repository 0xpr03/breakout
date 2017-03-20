package de.tudarmstadt.informatik.fop.breakout.states;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.Color;
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
import de.tudarmstadt.informatik.fop.breakout.lib.HighscoreLib.HighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

/**
 * Class representing the highscore state of the game<br>
 * This draws the current highscore to the screen providing a clear option
 * 
 * @author Aron Heinecke
 */
public class HighscoreState extends GameState<Breakout> {

	private ArrayList<HighscoreEntry> highscore;
	private Logger logger = LogManager.getLogger(this);
	private DecimalFormat timeFormat = new DecimalFormat("#.##");
	
	/**
	 * Create a new instance of HighscoreState
	 * 
	 * @param stateIDstateData
	 * @param stateData
	 */
	public HighscoreState(final int stateID, final Breakout stateData) {
		super(stateID, stateData,stateData.getWidth(),stateData.getHeight());
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
		objects.add(new Background(am.getImg("images/menu.png"), this));

		objects.add(new Button(new Vector2f(500, 500), 150, 50, am.getImg("images/back_btn_d.png"), am.getImg("images/back_btn_m.png"),
				new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Exit clicked");
						game.enterState(GameParameters.MAINMENU_STATE);
					}
				}));
		objects.add(new Button(new Vector2f(300, 500), 150, 50, am.getImg("images/clear_btn_d.png"), am.getImg("images/clear_btn_m.png"),
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
		float offsetX = 220;
		float offsetY = 200;
		float factY = 20;
		int i = 0;
		
		// draw rectangle
		g.setColor(new Color(50, 50, 50, 180));
		g.fillRect(offsetX-10, offsetY-30, 400, 250);
		g.setColor(Color.white);
		
		// draw header
		g.drawString("Blocks", offsetX, 180);
		g.drawString("Time", offsetX + 90, 180);
		g.drawString("Name", offsetX + 200, 180);
		
		if (highscore.size() > 0) {
			// draw highscore data
			for (HighscoreEntry highscoreEntry : this.highscore) {
				g.drawString(""+highscoreEntry.getNumberOfDestroyedBlocks(), offsetX, offsetY + factY * i);
				g.drawString(timeFormat.format(highscoreEntry.getElapsedTime()) + "s", offsetX + 90, offsetY + factY * i);
				g.drawString(highscoreEntry.getPlayerName(), offsetX + 200, offsetY + factY * i);
				i++;
			}
		} else {
			g.drawString("No entries so far..", offsetX, offsetY+20);
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
