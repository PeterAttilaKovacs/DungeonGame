package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import animation.SpriteCuter;
import enums.ID;

public class GameOver extends BaseObject{

	private int timer = 300;
	
	//Jatekvege konstruktora
	public GameOver(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
	}

	@Override
	public void tick() {
		//automatikus kilepes a jatekbol kb. 30 mp elteltevel
		if (timer <= 0){
			System.exit(timer);
		}
		timer--;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.drawString("GAME OVER", (int)x, (int)y);
	}

	@Override
	public Rectangle getBounds() { return null; }

}
