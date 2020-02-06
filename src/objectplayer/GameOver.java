package objectplayer;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import enums.ID;
import main.Game;
import objectscommon.BaseObject;
import view.BufferedImageLoader;
import view.SpriteCuter;

public class GameOver extends BaseObject{
	
	//Variables
	private BufferedImage game_over_image = null;
	private BufferedImageLoader gameoverLoader = new BufferedImageLoader();
	
	/**
	 * GameOver constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 */
	public GameOver(float x, float y, ID id, SpriteCuter imageCut) {
		super(x, y, id, imageCut);
		
		//loading gameover.png image
		game_over_image = gameoverLoader.loadImage("/sprites/gameover.png");
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

	/**
	 * Rendering Game Over image on players death
	 */
	@Override
	public void render(Graphics graphics) {
		
		//graphics.drawImage(game_over_image, (int)x, (int)y, null);
		graphics.drawImage(game_over_image, (int)x, (int)y, null);
	}

	@Override
	public Rectangle getBounds() {
		return null; 
	}
}
