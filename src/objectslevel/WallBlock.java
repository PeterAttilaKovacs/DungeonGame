package objectslevel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import enums.ID;
import objectscommon.BaseObject;
import view.SpriteCuter;

public class WallBlock extends BaseObject{

	//Variables
	private BufferedImage wallBlock;
	
	/**
	 * WallBlock constuctor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 */
	public WallBlock(float x, float y, ID id, SpriteCuter imageCut_level) {
		super(x, y, id, imageCut_level);
		
		wallBlock = imageCut.grabImage(1, 2, width, height);
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(wallBlock, (int)x, (int)y, null);
	}

	//Variables for getBounds
	private int width = 32;
	private int height = 32;
	
	/**
	 * Bounds of WallBlock
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
