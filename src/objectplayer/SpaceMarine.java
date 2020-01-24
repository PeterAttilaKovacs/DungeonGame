package objectplayer;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import enums.ID;
import gui.MouseInput;
import main.Camera;
import main.Game;
import main.Handler;
import main.LevelLoader;
import objectscommon.BaseObject;
import objectscommon.PlasmaRound;
import sound.Sound;
import view.Animation2D;
import view.SpriteCuter;

public class SpaceMarine extends BaseObject {

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
	private Sound effect_Player;
	
	
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
	 * @param effect_Player - Sound class
	 */
	public SpaceMarine(float x, float y, ID id, SpriteCuter imageCut_player, Handler handler, 
						Camera camera, Game game, PlayerHUD hud, Sound effect_Player, LevelLoader level) {
		super(x, y, id, imageCut_player);
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.hud = hud;
		this.level = level;
		this.effect_Player = effect_Player;
		
		width = 32;
		height = 38;
		
		mouseSpMarine = new MouseInput(handler, camera, game, imageCut, hud, effect_Player);
		game.addMouseListener(mouseSpMarine);
		
		//Sprites for animation
		spmarine[0] = imageCut.grabImage(1, 1, width, height);
		spmarine[1] = imageCut.grabImage(2, 1, width, height);
		spmarine[2] = imageCut.grabImage(3, 1, width, height);
		
		animation = new Animation2D(3, spmarine);
	}

	//Variables for palsma-weapon recharge
	protected static long lastAttackTimer, attackCooldown = 8000, attackTimer = attackCooldown;

	//PlasmaRound cooldown counter
	public void coolDown() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
			
		if (attackTimer >= 8000) {
			attackTimer = 8000;
		}
	}
	
	//test-debug
	//int c = 0;
	/**
	 * Tick method
	 */
	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		collision();
		coolDown();
		
		// key D --> Right
		// key A --> Left
		if (handler.isRight()){ velX = +5; }
		else if (!handler.isLeft()){ velX = 0; }
		
		if (handler.isLeft()){ velX = -5; }
		else if (!handler.isRight()){ velX = 0; }
		
		// key W --> UP
		// key S --> DOWN
		if (handler.isUp()){ velY = -5; }
		else if (!handler.isDown()){ velY = 0; }
		
		if (handler.isDown()){ velY = +5; }
		else if (!handler.isUp()){ velY = 0; }
		
		//Esc --> Exit game
		if (handler.isEsc()){ Runtime.getRuntime().exit(0); }
		
		animation.runAnimation();
		
		//test-debug
		//c++;
		//System.out.println("SpPlayer tick meghivva" + c);
	}

	/**
	 * Rendering method
	 */
	@Override
	public void render(Graphics graphics) {
		if (velX == 0 && velY == 0) {
			graphics.drawImage(spmarine[0], (int)x, (int)y, null);
			
			//idel stance sound // TODO better idel sound solution...
			if (Math.random() > 0.99) {
				effect_Player.soundEffect_PlayerIdel.play();
			}
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
			
			//Collision with MediPack
			if (tempObject.getId() == ID.MediPack) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.MarineLife += 50;
					
					//Life overload disabling
					if (hud.MarineLife >= 100) {
						hud.MarineLife = 100;
					}
					handler.removeObject(tempObject);
				}
			}
			
			//Collision with Enemy
			if (tempObject.getId() == ID.Heretic) {
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
					effect_Player.soundEffect_ExitLevel.play();
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
	
	//PlasmaRound Attack timer and AttackCooldown setter and getter
	public static void setAttackTimer(long attackTimer) {
		SpaceMarine.attackTimer = attackTimer;
	}

	public static long getAttackTimer() {
		return attackTimer;
	}
	
	public static long getAttackCooldown() {
		return attackCooldown;
	}
}