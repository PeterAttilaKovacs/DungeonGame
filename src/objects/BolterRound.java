package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import animation.SpriteCuter;
import enums.ID;
import main.Handler;

public class BolterRound extends BaseObject{

	private Handler handler;
	private int width = 8;
	private int height = 8;
	
	//Loszer konstruktora
	public BolterRound(float x, float y, ID id, SpriteCuter imageCut, Handler handler) {
		super(x, y, id, imageCut);
		this.handler = handler;
	}

	/**
	 * Lovedek utkozes figyelese falla, utkozes eseten lovedek torlese
	 */
	@Override
	public void tick(){
		x += velX;
		y += velY;
		
		for (int i = 0; i < handler.object.size(); i++){
			BaseObject tempBolt = handler.object.get(i);
			
			if (tempBolt.getId() == ID.WallBlock){
				if (getBounds().intersects(tempBolt.getBounds())){
					handler.removeObject(this);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillOval((int)x, (int)y, width, height);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
