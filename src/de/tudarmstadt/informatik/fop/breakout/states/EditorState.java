package de.tudarmstadt.informatik.fop.breakout.states;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.GameObject;
import de.tudarmstadt.informatik.fop.breakout.gui.Background;
import de.tudarmstadt.informatik.fop.breakout.gui.BlockSetter;
import de.tudarmstadt.informatik.fop.breakout.gui.Button;
import de.tudarmstadt.informatik.fop.breakout.gui.TextInputField;
import de.tudarmstadt.informatik.fop.breakout.gui.Button.ButtonAction;
import de.tudarmstadt.informatik.fop.breakout.lib.AssetManager;
import de.tudarmstadt.informatik.fop.breakout.lib.Map;
import de.tudarmstadt.informatik.fop.breakout.ui.Breakout;

/**
 * A GameState to load, edit and save new maps
 * 
 * @author Simon Kohaut
 */
public class EditorState extends GameState<Breakout> {

	private Logger logger = LogManager.getLogger(this);
	private TextInputField pathInputField;
	private Map map;

	/**
	 * Create a new EditorState
	 * 
	 * @param stateID
	 * @param stateData
	 */
	public EditorState(int stateID, Breakout stateData) {
		super(stateID, stateData, stateData.getWidth(), stateData.getHeight());
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		logger.entry();
		AssetManager am = stateData.getAssetManager();
		objects.add(new Background(am.getImg("images/background.png"), this));

		objects.add(new Button(new Vector2f(400, 500), 200, 50, am.getImg("images/back_btn_d.png"),
				am.getImg("images/back_btn_m.png"), new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Exit clicked");
						game.enterState(GameParameters.MAINMENU_STATE);
					}
				}));

		objects.add(new Button(new Vector2f(150, 500), 200, 50, am.getImg("images/load_btn_d.png"),
				am.getImg("images/load_btn_m.png"), new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Load clicked");
						if (pathInputField.getText() == "")
							return;
						map = new Map(pathInputField.getText(), false);
						if (map.load())
							for (GameObject o : objects)
								if (o instanceof BlockSetter)
									((BlockSetter) o).readMap(map);
					}
				}));

		objects.add(new Button(new Vector2f(650, 500), 200, 50, am.getImg("images/save_btn_d.png"),
				am.getImg("images/save_btn_m.png"), new ButtonAction() {
					@SuppressWarnings("rawtypes")
					@Override
					public void action(GameContainer container, StateBasedGame game, GameState state, int delta) {
						logger.trace("Save clicked");
						File f = new File(pathInputField.getText());
						map = new Map(f, false);
						for (GameObject o : objects)
							if (o instanceof BlockSetter)
								((BlockSetter) o).writeMap(map);
						map.write();
					}
				}));

		for (int x = 0; x < 16; x++)
			for (int y = 0; y < 10; y++)
				objects.add(new BlockSetter(new Vector2f(x * 50 + 25, y * 30 + 15), 50, 30, y, x,
						am.getImg("images/block_m1.png"), am.getImg("images/block_1.png"),
						am.getImg("images/block_2.png"), am.getImg("images/block_3.png"), am.getImg("images/block_4.png")));

		pathInputField = new TextInputField(new Vector2f(400, 550), 750, "Path:");

		logger.exit();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		pathInputField.render(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		pathInputField.update(container, game, this, delta);
	}
}
