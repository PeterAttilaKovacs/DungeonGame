/**
 * Kilepesi pont palyarol - tovabb toltes kovetkezo palyara
 */
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import main.Handler;
import view.SpriteCuter;

public class AmmoCrate extends BaseObject{

	private Handler handler;
	
	//AmmoCrate konstruktora
	public AmmoCrate(float x, float y, ID id, SpriteCuter imageCut, Handler handler) {
		super(x, y, id, imageCut);
		this.handler = handler;
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect((int)x, (int)y, 32, 32);
	}

	private int width = 32;
	private int height = 32;
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
