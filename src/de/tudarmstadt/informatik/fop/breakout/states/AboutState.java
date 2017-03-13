package de.tudarmstadt.informatik.fop.breakout.states;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
 * About game state
 * 
 * @author Aron Heinecke
 */
public class AboutState extends GameState<Breakout> {

	private Logger logger = LogManager.getLogger(this);

	private Color color;
	private long time = 0;
	private Random rnd = new Random();

	/**
	 * Create a new About game state
	 * 
	 * @param stateID
	 * @param stateData
	 */
	public AboutState(final int stateID, final Breakout stateData) {
		super(stateID, stateData);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		AssetManager am = stateData.getAssetManager();
		objects.add(new Background(am.getImg("images/menu.png"), stateData));

		objects.add(new Button(new Vector2f(370, 500), 200, 50, am.getImg("images/back_btn_d.png"), am.getImg("images/back_btn_m.png"),
				new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Exit clicked");
						game.enterState(GameParameters.MAINMENU_STATE);
					}
				}));
		color = new Color(1f, 1f, 1f, 1f);
		logger.exit();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		this.time = 0;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		time += delta;
		if (time > 750) {
			time = 0;
			color.r = rnd.nextFloat();
			color.g = rnd.nextFloat();
			color.b = rnd.nextFloat();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		int width = 300;
		int height = 225;
		int x = stateData.getWidth() / 2 - width / 2;
		int y = stateData.getHeight() / 2 - height / 2;
		g.setColor(new Color(50, 50, 50, 180));
		g.fillRect(x, y, width, height);
		g.setColor(color);
		g.drawString("About Breakout", x + 10, y + 25);
		g.drawString("Made by", x + 10, y + 50);
		g.drawString("Tim Jäger", x + 80, y + 75);
		g.drawString("Aron Heinecke", x + 80, y + 100);
		g.drawString("Simon Kohaut", x + 80, y + 125);
		g.drawString("Niko Mitura", x + 80, y + 150);
		g.drawString("during FOP 2017, TU Darmstadt", x + 10, y + 175);
		g.setColor(Color.white);
	}
}
