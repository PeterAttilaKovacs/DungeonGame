package objectscommon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import main.GameHandler;
import view.SpriteCuter;

public class BolterRound extends BaseObject {

	//Variables
	private GameHandler handler;
	private int width = 6;
	private int height = 6;
	
	/**
	 * Bolt constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 */
	public BolterRound(float x, float y, ID id, SpriteCuter imageCut, GameHandler handler) {
		super(x, y, id, imageCut);
		this.handler = handler;
	}

	/**
	 * If bolt intersects with wall, bolt is deleted from Array
	 */
	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		//Collision with WallBlock
		for (int i = 0; i < handler.object.size(); i++) {
			BaseObject tempBolt = handler.object.get(i);
			
			if (tempBolt.getId() == ID.WallBlock) {
				if (getBounds().intersects(tempBolt.getBounds())) {
					handler.removeObject(this);
				}
			}
		}
	}

	/**
	 * Rendering of bolts
	 * Add bolt image for player and AI to remove coloring problems!
	 */
	@Override
	public void render(Graphics graphics) {
		
		for (int i = 0; i < handler.object.size(); i++) {
			BaseObject tempBolt = handler.object.get(i);
			
			//Enemy bolt color: red
			if (tempBolt.getId() == ID.EnemyBolt) {
				graphics.setColor(Color.red);
				graphics.fillOval((int)x, (int)y, width, height);
			}
			
			//Player bolt color: yellow
			else {
			//else if (tempBolt.getId() == ID.SpaceMarine) {
				graphics.setColor(Color.yellow);
				graphics.fillOval((int)x, (int)y, width, height);
			}
		}
	}
	
	/**
	 * Bounds of BolterRound
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
