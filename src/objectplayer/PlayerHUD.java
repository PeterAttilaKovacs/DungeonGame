package objectplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import objectscommon.BaseObject;
import sound.Sound;
import view.SpriteCuter;

public class PlayerHUD extends BaseObject {

	//Variables
	public int MarineLife = 100;
	public int MarineAmmo = 50;
	public int MarineScHeretic = 0;
	public int MarineScMutant = 0;
	public int MarineScUnclean = 0;
	
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
		graphics.setFont(new Font("arial", Font.BOLD, 15));
		
		if (MarineAmmo <= 10) {
			graphics.setColor(Color.red); //low ammo color indication
		}
		
		else {
			graphics.setColor(Color.white);
		}
		graphics.drawString("Bolter Rounds left: " + MarineAmmo, 6, 60); //x, y
		
		//Plasma cooldown
		graphics.setColor(Color.white);
		graphics.drawString("Plasma Bolt cooldown: " + SpaceMarine.getAttackTimer(), 6, 90); //x, y
		
		//Slay meters
		graphics.setColor(Color.orange);
		graphics.drawString("Slaying of Heretics: " + MarineScHeretic, 6, 120); //x, y
		graphics.drawString("Slaying of Mutants: " + MarineScMutant, 6, 140); //x, y
		graphics.drawString("Slaying of Unclean: " + MarineScUnclean, 6, 160); //x, y
		
		//Game music volume control
		graphics.setColor(Color.cyan);
		graphics.drawString("Music volume: " + Sound.getVolume(), 6, 540); //x, y
		graphics.drawString("Effect volume: " + Sound.getEffect(), 6, 560); //x, y
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}
