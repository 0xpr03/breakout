package de.tudarmstadt.informatik.fop.breakout.factories;

import org.newdawn.slick.geom.Vector2f;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.gameObjects.GameObject;

/**
 * Factory for creating Borders of the field. Borders are not visible and not
 * passable entities for holding the ball in the field.
 * 
 * @author Tobias Otterbein, Benedikt Wartusch
 * 
 */
public class BorderFactory implements GameParameters {

	private BorderType type;

	/**
	 * Factory Constructor
	 * 
	 * @param type
	 *            determines the type of a created border (TOP, LEFT or RIGHT)
	 */
	public BorderFactory(BorderType type) {
		this.type = type;
	}

	public GameObject createEntity() {

		switch (type) {

		case TOP:
			return new GameObject(new Vector2f(WINDOW_WIDTH / 2.0f, 0), WINDOW_WIDTH, BORDER_WIDTH, true);
		case LEFT:
			return new GameObject(new Vector2f(0, WINDOW_HEIGHT / 2.0f), BORDER_WIDTH, WINDOW_HEIGHT, true);
		case RIGHT:
			return new GameObject(new Vector2f(WINDOW_WIDTH, WINDOW_HEIGHT / 2.0f), BORDER_WIDTH, WINDOW_HEIGHT, true);
		default:
			return null;
		}
	}

}
