package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import main.Handler;
import view.SpriteCuter;

public class BolterRound extends BaseObject {

	//Variables
	private Handler handler;
	private int width = 8;
	private int height = 8;
	
	/**
	 * Bolt constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 */
	public BolterRound(float x, float y, ID id, SpriteCuter imageCut, Handler handler) {
		super(x, y, id, imageCut);
		this.handler = handler;
	}

	/**
	 * If bolt intersects with wall, bolt is deleted from Array
	 */
	@Override
	public void tick(){
		x += velX;
		y += velY;
		
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
	 */
	@Override
	public void render(Graphics graphics) {
		
		for (int i = 0; i < handler.object.size(); i++) {
			BaseObject tempBolt = handler.object.get(i);
			
			//Enemy bolt color: red
			if (tempBolt.getId() == ID.EnemyBolt) {
				graphics.setColor(Color.red);
			}
			
			//Player bolt color: yellow
			else {
				graphics.setColor(Color.yellow);
			}
		}
		graphics.fillOval((int)x, (int)y, width, height);
	}
	
	/**
	 * Bounds of AmmoCrate
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
