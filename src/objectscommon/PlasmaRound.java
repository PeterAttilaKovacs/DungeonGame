package objectscommon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import main.Handler;
import view.SpriteCuter;

public class PlasmaRound extends BaseObject {

	//Variables
	private Handler handler;
	private int width = 12;
	private int height = 9;
	private int arcWidth = 3;
	private int arcHeight = 3;
	
	/**
	 * PalsmaRound constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 */
	public PlasmaRound(float x, float y, ID id, SpriteCuter imageCut, Handler handler) {
		super(x, y, id, imageCut);
		this.handler = handler;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		//Collision with WallBlock
		for (int i = 0; i < handler.object.size(); i++) {
			BaseObject tempPlasma = handler.object.get(i);
			
			if (tempPlasma.getId() == ID.WallBlock) {
				if (getBounds().intersects(tempPlasma.getBounds())) {
					handler.removeObject(this); //or tempPlasma to destroy walls... :)
				}
			}
		}
	}
	
	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.blue);
		graphics.fillRoundRect((int)x, (int)y, width, height, arcWidth, arcHeight);		
	}

	/**
	 * Bounds of PalsmaRound
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
