package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import view.SpriteCuter;

public class Flag extends BaseObject{

	//Flag konstruktora
	public Flag(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
	}

	@Override
	public void tick() {}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, width, height);
	}
	
	private int width = 32;
	private int height = 32;
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
