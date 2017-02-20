package de.tudarmstadt.informatik.fop.breakout.states;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.gui.Button;
import gameObjects.GameObject;

/**
 * Class representing the main menu state of the game
 * 
 * @author Aron Heinecke
 */
public class MainMenuState extends GameState {
	private int stateID; // Identifier von diesem BasicGameState

	private final int distance = 100;
	private final int start_Position = 180;

	private Button bNewGame;
	private Button bAbout;
	private Button bExit;

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
		// Hintergrund laden
		// GameObject background = new GameObject("menu"); // Entitaet fuer
		// // Hintergrund
		// background.setPosition(new Vector2f(400, 300)); // Startposition des
		// // Hintergrunds
		// background.addComponent(new ImageRenderComponent(new
		// Image("/assets/menu.png"))); // Bildkomponente

		// Hintergrund-Entitaet an StateBasedEntityManager uebergeben
		// entityManager.addEntity(stateID, background);

		/* Neues Spiel starten-Entitaet */
		bNewGame = new Button(new Vector2f(0, 0), 500, 500, new Image("images/entry.png"),
				new Image("images/entry.png"));
		objects.add(bNewGame);

		// // Setze Position und Bildkomponente
		// new_Game_Entity.setPosition(new Vector2f(218, 190));
		// new_Game_Entity.setScale(0.28f);
		// new_Game_Entity.addComponent(new ImageRenderComponent(new
		// Image("assets/entry.png")));
		//
		// // Erstelle das Ausloese-Event und die zugehoerige Action
		// ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new
		// MouseClickedEvent());
		// Action new_Game_Action = new
		// ChangeStateInitAction(Launch.GAMEPLAY_STATE);
		// mainEvents.addAction(new_Game_Action);
		// new_Game_Entity.addComponent(mainEvents);
		//
		// // Fuege die Entity zum StateBasedEntityManager hinzu
		// entityManager.addEntity(this.stateID, new_Game_Entity);
		//
		// /* Beenden-Entitaet */
		// Entity quit_Entity = new Entity("Beenden");
		//
		// // Setze Position und Bildkomponente
		// quit_Entity.setPosition(new Vector2f(218, 290));
		// quit_Entity.setScale(0.28f);
		// quit_Entity.addComponent(new ImageRenderComponent(new
		// Image("assets/entry.png")));
		//
		// // Erstelle das Ausloese-Event und die zugehoerige Action
		// ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new
		// MouseClickedEvent());
		// Action quit_Action = new QuitAction();
		// mainEvents_q.addAction(quit_Action);
		// quit_Entity.addComponent(mainEvents_q);
		//
		// // Fuege die Entity zum StateBasedEntityManager hinzu
		// entityManager.addEntity(this.stateID, quit_Entity);

	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		super.update(container, game, delta);
		if (bNewGame.isClicked()) {
			logger.trace("NewGame clicked");
		}
	}

}
