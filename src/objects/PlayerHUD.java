package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import animation.SpriteCuter;
import enums.ID;

public class PlayerHUD extends BaseObject{

	public int MarineLife = 100;
	public int MarineAmmo = 50;
	public int MarineScore = 0;
	
	//Hud konstruktora
	public PlayerHUD(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		//Elet kijelzo
		g.setColor(Color.gray);
		g.fillRect(5, 5, 200, 32); //x, y, width, height
		g.setColor(Color.green);
		g.fillRect(5, 5, MarineLife*2, 32);
		g.setColor(Color.black);
		g.drawRect(5, 5, 200, 32); //x, y, width, height
			
		//Loszer kijelzo
		g.setColor(Color.white);
		g.drawString("Loszer: " + MarineAmmo, 5, 50); //x, y
				
		//Meszarlas kijelzo
		g.setColor(Color.orange);
		g.drawString("Pont: " + MarineScore, 5, 70); //x, y
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
