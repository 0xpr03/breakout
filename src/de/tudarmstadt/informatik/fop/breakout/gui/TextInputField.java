package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
	private boolean active;

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
	public TextInputField(Vector2f position, float width, String label) {
		super(position, label);
		super.width = width;
		super.height = 20;
		this.label = label;
		this.entered = "";
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, GameState<?> state, int delta) {
		if (isClicked(container)) {
			container.getInput().addKeyListener(this);
			active = true;
		}
		else if (!isMouseOver(container) && container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			container.getInput().removeKeyListener(this);
			active = false;
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(active)
			g.setColor(new Color(180, 180, 200, 180));
		else
			g.setColor(new Color(255, 255, 255, 180));	
		Vector2f topLeft = getTopLeft();
		Vector2f bottomRight = getBottomRight();
		g.fillRect(topLeft.x, topLeft.y, width, height);
		g.setColor(Color.black);
		super.render(g);
		g.setColor(Color.white);
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
}
