/**
 * Palya falanak objektuma
 */
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import animation.SpriteCuter;
import enums.ID;

public class WallBlock extends BaseObject{

	//Fal konstuktora
	public WallBlock(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect((int)x, (int)y, 32, 32);
	}

	private int width = 32;
	private int height = 32;
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
