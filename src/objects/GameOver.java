package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import view.SpriteCuter;

public class GameOver extends BaseObject{
	
	/**
	 * GameOver constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 */
	public GameOver(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
	}

	//Variables for tick()
	private int timer = 300;
	
	@Override
	public void tick() {
		//auto close of game after ~30 seconds
		if (timer <= 0){
			System.exit(timer);
		}
		timer--;
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.drawString("GAME OVER", (int)x, (int)y);
	}

	@Override
	public Rectangle getBounds() { 
		return null; 
	}

}
