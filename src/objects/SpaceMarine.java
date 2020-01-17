package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import enums.ID;
import gui.MouseInput;
import main.Camera;
import main.Game;
import main.Handler;
import main.LevelLoader;
import view.Animation2D;
import view.SpriteCuter;

public class SpaceMarine extends BaseObject{

	//Variables
	private int width;
	private int height;
	private Handler handler;
	private Camera camera;
	private Game game;
	private PlayerHUD hud;
	private final MouseInput mouseSpMarine;
	private BufferedImage spmarine[] = new BufferedImage[3];
	public Animation2D animation;
	
	
	/**
	 * SpaceMarine constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 * @param camera - Camera class
	 * @param game - Game class
	 * @param hud - PlayerHUD class
	 * @param level - LevelLoader class
	 */
	public SpaceMarine(float x, float y, ID id, SpriteCuter imageCut, Handler handler, 
						Camera camera, Game game, PlayerHUD hud, LevelLoader level) {
		super(x, y, id, imageCut);
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.hud = hud;
		this.level = level;
		this.imageCut = imageCut;
		
		width = 32;
		height = 48;
		
		mouseSpMarine = new MouseInput(handler, camera, game, imageCut, hud);
		game.addMouseListener(mouseSpMarine);
		
		//Sprites for animation
		spmarine[0] = imageCut.grabImage(1, 1, width, 39);
		spmarine[1] = imageCut.grabImage(2, 1, 33, 39);
		spmarine[2] = imageCut.grabImage(3, 1, width, 35);
		
		animation = new Animation2D(3, spmarine);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		collision();
				
		// key D --> Right
		// key A --> Left
		if (handler.isRight()){ velX = +5;}
		else if (!handler.isLeft()){ velX = 0; }
		
		if (handler.isLeft()){ velX = -5; }
		else if (!handler.isRight()){ velX = 0; }
		
		// key W --> UP
		// key S --> DOWN
		if (handler.isUp()){ velY = -5;}
		else if (!handler.isDown()){ velY = 0; }
		
		if (handler.isDown()){ velY = +5; }
		else if (!handler.isUp()){ velY = 0; }
		
		//Esc --> Exit game
		if (handler.isEsc()){ Runtime.getRuntime().exit(0); }
		
		animation.runAnimation();
		
	}

	/**
	 * Rendering method
	 */
	@Override
	public void render(Graphics graphics) {
		// Test render
		//g.setColor(Color.blue);
		//g.fillRect((int)x, (int)y, 32, 48);
		
		if (velX == 0 && velY == 0) {
			graphics.drawImage(spmarine[0], (int)x, (int)y, null);
		}
		
		else { 
			animation.drawAnimation(graphics, x, y, 0); 
		}
	}

	/**
	 * Bounds of SpaceMarine
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	//Variables for level loading
	private LevelLoader level;
	
	/**
	 * Collision check for player
	 */
	private void collision(){
		for (int i = 0; i < handler.object.size(); i++) {
			
			BaseObject tempObject = handler.object.get(i);
			
			//Collision with Wall
			if (tempObject.getId() == ID.WallBlock) {
				if (getBounds().intersects(tempObject.getBounds())) {
					x += velX *- 1;
					y += velY *- 1;
				}
			}
			
			//Collision with AmmoCrate
			if (tempObject.getId() == ID.AmmoCrate) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.MarineAmmo += 50;
					handler.removeObject(tempObject);
				}
			}
			
			//Collision with Enemy
			if (tempObject.getId() == ID.Khornet) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.MarineLife--;
				}
			}
			
			//Collision with Enemy Bolt
			if (tempObject.getId() == ID.EnemyBolt) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.MarineLife -= 5;
					handler.removeObject(tempObject); //removing enemy bolt from array
				}
			}
			
			//Collision with Flag - level exit point
			if (tempObject.getId() == ID.Flag){
				if (getBounds().intersects(tempObject.getBounds())) {
					game.removeMouseListener(mouseSpMarine); //removing mouse listener from player
					level.nextLevel(); //loading next level
				}
			}
		}
		/**
		 * If player life = null, then GameOver call
		 * Deleting player from Array, removing Mouse Listener from palyer
		 */
		if (hud.MarineLife <= 0) {
			handler.addObject(new GameOver(this.x, this.y, ID.GameOver, imageCut));
			game.removeMouseListener(mouseSpMarine);
			handler.removeObject(this);
		}
	}
}
