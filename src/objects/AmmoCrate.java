package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import main.Handler;
import view.SpriteCuter;

public class AmmoCrate extends BaseObject{

	//Variables
	private Handler handler;
	
	/**
	 * AmmoCrate constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 */
	public AmmoCrate(float x, float y, ID id, SpriteCuter imageCut, Handler handler) {
		super(x, y, id, imageCut);
		this.handler = handler;
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.cyan);
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
