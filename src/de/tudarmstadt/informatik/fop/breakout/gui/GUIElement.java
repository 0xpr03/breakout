package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import gameObjects.Sprite;

public class GUIElement extends Sprite {

	public GUIElement(Image image, Vector2f position, float width, float height) {
		super(image, position, width, height, false);
	}

	public boolean isMouseOver(GameContainer container) {
		Input in = container.getInput();
		int mx = in.getMouseX();
		int my = in.getMouseY();
		
		Vector2f topLeft = getTopLeft();
		Vector2f bottomRight = getBottomRight();
		
		if (mx > topLeft.x && mx < bottomRight.x && my > topLeft.y && my < bottomRight.y) 
			return true;
		else 
			return false;
	}
	
	public boolean isClicked(GameContainer container) {
		if(!isMouseOver(container))
			return false;
		else return container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);
	}
	
}
