package objectplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import objectscommon.BaseObject;
import view.SpriteCuter;

public class PlayerHUD extends BaseObject{

	//Variables
	public int MarineLife = 100;
	public int MarineAmmo = 50;
	public int MarineScore = 0;
	
	/**
	 * Hud constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 */
	public PlayerHUD(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
	}

	@Override
	public void tick() {}

	/**
	 * Rendering method
	 */
	@Override
	public void render(Graphics graphics) {
		//Life meter
		graphics.setColor(Color.gray);
		graphics.fillRect(5, 5, 200, 32); //x, y, width, height
		graphics.setColor(Color.green);
		graphics.fillRect(5, 5, MarineLife*2, 32);
		graphics.setColor(Color.black);
		graphics.drawRect(5, 5, 200, 32); //x, y, width, height
			
		//Ammo meter
		graphics.setFont(new Font("arial", Font.PLAIN, 15));
		
		if (MarineAmmo <= 10) {
			graphics.setColor(Color.red); //low ammo color indication
		}
		
		else {
			graphics.setColor(Color.white);
		}
		
		graphics.drawString("Bolter Rounds left: " + MarineAmmo, 6, 60); //x, y
				
		//Slay meter
		graphics.setColor(Color.orange);
		graphics.drawString("Slaying of Heretics: " + MarineScore, 6, 90); //x, y
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
