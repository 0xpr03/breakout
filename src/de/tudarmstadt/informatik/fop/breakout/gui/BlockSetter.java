package de.tudarmstadt.informatik.fop.breakout.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.lib.Map;
import de.tudarmstadt.informatik.fop.breakout.states.GameState;

public class BlockSetter extends GUIElement {

	private int blockValue = 0;
	private Image blockM1;
	private Image block1;
	private Image block2;
	private Image block3;
	private Image block4;
	private int column, row;

	public BlockSetter(Vector2f position, float width, float height, int column, int row, Image indestrBlock,
			Image block1, Image block2, Image block3, Image block4) {
		super(null, position, width, height);
		this.blockM1 = indestrBlock;
		this.block1 = block1;
		this.block2 = block2;
		this.block3 = block3;
		this.block4 = block4;
		this.column = column;
		this.row = row;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, @SuppressWarnings("rawtypes") GameState state,
			int delta) {
		if (isClicked(container))
			blockValue = (blockValue + 1) % 6;
		else if (isRightClicked(container))
			blockValue = (blockValue - 1) % 6;

		switch (blockValue) {
		case 5:
		case -1:
			image = blockM1;
			break;
		case 0:
			break;
		case 1:
			image = block1;
			break;
		case 2:
			image = block2;
			break;
		case 3:
			image = block3;
			break;
		case -2:
			blockValue = 4;
		case 4:
			image = block4;
			break;
		}
	}

	@Override
	public void render(Graphics g) {
		if (blockValue != 0)
			super.render(g);
	}
	
	public void writeMap(Map map) {
		map.setBlock(row, column, blockValue);;
	}
	
	public void readMap(Map map) {
		blockValue = map.getMap().get(row).get(column);
	}
}
