package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import view.SpriteCuter;

public class Flag extends BaseObject{

	/**
	 * Flag constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 */
	public Flag(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
	}

	@Override
	public void tick() {}
	
	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.yellow);
		graphics.fillRect((int)x, (int)y, width, height);
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
