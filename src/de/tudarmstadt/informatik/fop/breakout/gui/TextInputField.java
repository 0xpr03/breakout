package de.tudarmstadt.informatik.fop.breakout.gui;

import org.apache.logging.log4j.LogManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.states.GameState;

/**
 * A Label that can be clicked to edit the shown text
 * 
 * @author Simon Kohaut
 */
public class TextInputField extends Label implements KeyListener {

	private String label;
	private String entered;

	/**
	 * Create a new TextInputField
	 * 
	 * @param position
	 *            The central position of the new TextInputField
	 * @param width
	 *            The width of the new TextInputField
	 * @param height
	 *            The height of the new TextInputField
	 * @param label
	 *            The label of the new TextInputField
	 */
	public TextInputField(Vector2f position, float width, float height, String label) {
		super(position, label);
		super.width = width;
		super.height = height;
		this.label = label;
		this.entered = "";
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
		if (isClicked(container))
			container.getInput().addKeyListener(this);
		else if (!isMouseOver(container) && container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			container.getInput().removeKeyListener(this);
	}
	
	@Override
	public String getText() {
		return entered;
	}

	@Override
	public void inputEnded() {
	}

	@Override
	public void inputStarted() {
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input arg0) {
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_BACK && entered.length() > 0)
			entered = entered.substring(0, entered.length() - 1);
		else
			entered += c;
		setText(label + " " + entered);
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
	}

	@Override
	public void getText() {
		return entered;
	}
}
