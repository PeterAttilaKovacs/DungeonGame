package objectslevel;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import enums.ID;
import main.Handler;
import objectscommon.BaseObject;
import view.SpriteCuter;

public class MediPack extends BaseObject {

	//Variables
	private Handler handler;
	private BufferedImage mediPack;
	
	/**
	 * MediPack constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 */
	public MediPack(float x, float y, ID id, SpriteCuter imageCut_level, Handler handler) {
		super(x, y, id, imageCut_level);
		this.handler = handler;
		
		mediPack = imageCut_level.grabImage(3, 4, width, height);
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(mediPack, (int)x, (int)y, null);
	}

	//Variables for getBounds
	private int width = 32;
	private int height = 32;
	
	/**
	 * Bounds of MediPack
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
