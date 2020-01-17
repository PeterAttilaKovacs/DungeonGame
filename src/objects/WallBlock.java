package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import view.SpriteCuter;

public class WallBlock extends BaseObject{

	/**
	 * WallBlock constuctor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 */
	public WallBlock(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect((int)x, (int)y, 32, 32);
	}

	//Variables for getBounds
	private int width = 32;
	private int height = 32;
	
	/**
	 * Bounds of AmmoCrate
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
