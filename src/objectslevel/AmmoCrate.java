package objectslevel;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import enums.ID;
import main.GameHandler;
import objectscommon.BaseObject;
import view.SpriteCuter;

public class AmmoCrate extends BaseObject{

	//Variables
	private GameHandler handler;
	private BufferedImage ammoCrate;
	
	/**
	 * AmmoCrate constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 */
	public AmmoCrate(float x, float y, ID id, SpriteCuter imageCut_level, GameHandler handler) {
		super(x, y, id, imageCut_level);
		this.handler = handler;
		
		ammoCrate = imageCut_level.grabImage(9, 2, width, height);
	}

	@Override
	public void tick() {}

	/**
	 * Rendering method
	 */
	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(ammoCrate, (int)x, (int)y, null);
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
