package objectsenemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import enums.ID;
import main.Game;
import main.GameHandler;
import objectplayer.PlayerHUD;
import objectscommon.BaseObject;
import objectscommon.BolterRound;
import sound.Sound;
import view.Animation2D;
import view.SpriteCuter;

public class EnemyMutant extends BaseObject {

	//Variables
	private GameHandler handler;
	private Game game;
	private PlayerHUD hud;
	private BaseObject tempPlayerBolt;
	private BufferedImage enmheretic[] = new BufferedImage[3];
	public Animation2D animation;
	private Sound effect_Enemy;
	
	/**
	 * EnemyMutant constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 * @param game - Game class
	 * @param hud - PlayerHUD class
	 * @param effect_Enemy - Sound class
	 */
	public EnemyMutant(float x, float y, ID id, SpriteCuter imageCut_enemy, GameHandler handler, 
												Game game, PlayerHUD hud, Sound effect_Enemy) {
		super(x, y, id, imageCut_enemy);
		this.handler = handler;
		this.game = game;
		this.hud = hud;
		this.effect_Enemy = effect_Enemy;
		
		enmheretic[0] = imageCut.grabImage(1, 2, 33, 32);
		enmheretic[1] = imageCut.grabImage(2, 2, 33, 32);
		enmheretic[2] = imageCut.grabImage(3, 2, 33, 32);
		
		animation = new Animation2D(3, enmheretic);
	}

	//Variables for Enemy movement and fireing
	private float diffX, diffY;
	private float speed, maxSpeed; // = 0.05f; //base settings: 0.05f
	private int mx, my;
	private int timer, movePath;
	private Random randomPath = new Random();
	
	//Variables for enemy life and ammo
	public int enemyLife = 100;
	public int enemyAmmo = 3;
	
	@Override
	public void tick() {
		speed = 0.05f;
		maxSpeed = 3;
		timer = 30;
		movePath = 0;
		x += velX;
		y += velY;
		
		//timed random move generation
		if (timer >= 0){
			movePath = randomPath.nextInt(7); //random number generation betwen 0-7
			timer = 30;
		}

		for (int i = 0; i < handler.object.size(); i++) {
			tempPlayerBolt = handler.object.get(i);
			
			//Intersection check with player bolt
			if (tempPlayerBolt.getId() == ID.BolterRound) {
				if (getBounds().intersects(tempPlayerBolt.getBounds())) {
					handler.removeObject(tempPlayerBolt); //removing Player bolt
					enemyLife -= 25;
				}
			}	
			
			if (tempPlayerBolt.getId() == ID.PlasmaRound) {
				if (getBounds().intersects(tempPlayerBolt.getBounds())) {
					enemyLife = 0;
				}
			}
			
			//Intersection check with player - AI
			if (tempPlayerBolt.getId() == ID.SpaceMarine) {
				
				diffX = tempPlayerBolt.getX() - x;
				diffY = tempPlayerBolt.getY() - y;
				
				//if player is in attack range, then attack player
				if (getAttack().intersects(tempPlayerBolt.getBounds())) {
					velX = diffX * speed;
					velY = diffY * speed;
					fireEnemyWeapon();
				}
				else if (movePath == 0) {
					//Enemys move randomly on level - bit laggy when moveing
					//velX = (randomPath.nextInt(2 * (int) maxSpeed + 1) - maxSpeed);
					//velY = (randomPath.nextInt(2 * (int) maxSpeed + 1) - maxSpeed);
					
					//test-debug
					//Basic settings: Enemys dont move, unless detecting the Player
					velX = 0;
					velY = 0;
				}
			}
			if (tempPlayerBolt.getId() == ID.WallBlock) {
				collision(tempPlayerBolt);
			}
		}
		if (enemyLife <= 0) { 
			handler.removeObject(this);
			effect_Enemy.soundEffect_EnemyDeath.play();
			hud.MarineScMutant += 1;
		}
		animation.runAnimation();
		timer--;	
	}

	//Fireing Enemy weapon if attacking player
	public void fireEnemyWeapon() {
		//if enemyAI has ammonition, it fires towards the player
		if (enemyAmmo >= 1) {
			SpriteCuter cut = null;
				
			//enemyAI randomly fires his shoots
			if (Math.random() > 0.97) {
				enemyAmmo--;
				BaseObject tempBolt = handler.addObject(new BolterRound(this.x + 16, this.y + 16, ID.EnemyBolt, cut, handler));
		
				mx = (int)x;
				my = (int)y;
				float angle = (float) Math.atan2(tempPlayerBolt.getX() - mx ,  tempPlayerBolt.getY() - my);
				int boltVelocity = 8; //base settings: 10
				tempBolt.velY = (float) ((boltVelocity) * Math.cos(angle)); //cos and sin must be switched, like this now!
				tempBolt.velX = (float) ((boltVelocity) * Math.sin(angle));
				effect_Enemy.soundEffect_EnemyShoot.play();
				System.out.println(enemyAmmo);				
			}
		}
	}	
	
	/**
	 * Collision check
	 * @param tempObject - BaseObject
	 */
	public void collision(BaseObject tempObject) {
		if (getBoundsTop().intersects(tempObject.getBounds())) {
			y = tempObject.getY() + height;
			velY = 0;
		}
		
		if (getBoundsBottom().intersects(tempObject.getBounds())) {
			y = tempObject.getY() - height;
			velY = 0;
		}
		
		if (getBoundsLeft().intersects(tempObject.getBounds())) {
			x = tempObject.getX() + width;
			velX = 0;
		}
		
		if (getBoundsRight().intersects(tempObject.getBounds())) {
			x = tempObject.getX() - width;
			velX = 0;
		}
	}
	
	//Variables for rendering and collision
	private int width = 32;
	private int height = 32;
	
	@Override
	public void render(Graphics graphics) {
		
		//test-debug
		//graphics.setColor(Color.green);
		//graphics.drawRect((int)x, (int)y, width, height);
		
		//Enemy
		if (velX == 0 && velY == 0) {
			graphics.drawImage(enmheretic[0], (int)x, (int)y, null);
		}
		else { 
			animation.drawAnimation(graphics, x, y, 0); 
		}

		//EnemyHUD
		graphics.setColor(Color.red);
		graphics.fillRect((int)x, (int)y-5, enemyLife/3, 5);
		graphics.setColor(Color.black);
		graphics.drawRect((int)x, (int)y-5, enemyLife/3, 5);
	}
	
	/**
	 * Base bounds of tesztEnemy
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	/**
	 * Attack sphear
	 * @return Ellipse2D of attack-range / base settings: x-85, y-85, width*6, height*6
	 */
	public Ellipse2D getAttack() {
		return new Ellipse2D.Double(x-125, y-125, width*9, height*9);
	}
	
	/**
	 * Bounds surrounding the enemy object, to get better intersection
	 * @return returns new rectangles for intersection check with: top, bottom, left, right side
	 */
	//Top
	public Rectangle getBoundsTop() {
		return new Rectangle((int)x + (width/2)-((width/2)/2), (int)y, width/2, height/2); //base settings: 64, 64
	}
	
	//Bottom
	public Rectangle getBoundsBottom() {
		return new Rectangle((int)x + (width/2)-((width/2)/2), (int)y + (height/2), width/2, height/2); //base settings:: 64, 64
	}
	
	//Left
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x, (int)y + 4, 5, height-9); //base settings:: 64, 64
	}
		
	//Right
	public Rectangle getBoundsRight() {
		return new Rectangle((int)x + width - 5, (int)y + 4, 5, height-8); //base settings:: 64, 64
	}
}