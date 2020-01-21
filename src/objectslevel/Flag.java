package objectslevel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import enums.ID;
import objectscommon.BaseObject;
import view.SpriteCuter;

public class Flag extends BaseObject{

	//Variables
	private BufferedImage exitFlag;
	
	/**
	 * Flag constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 */
	public Flag(float x, float y, ID id, SpriteCuter imageCut_level) {
		super(x, y, id, imageCut_level);
		
		exitFlag = imageCut_level.grabImage(5, 3, width, height);
	}

	@Override
	public void tick() {}
	
	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(exitFlag, (int)x, (int)y, null);
	}
	
	//Varibales for getBounds
	private int width = 32;
	private int height = 32;
	
	/**
	 * Bounds of Flag - exit point of level
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
