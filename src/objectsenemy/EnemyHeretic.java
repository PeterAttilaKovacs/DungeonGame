package objectsenemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import enums.ID;
import main.Game;
import main.Handler;
import main.Sound;
import objectplayer.PlayerHUD;
import objectscommon.BaseObject;
import objectscommon.BolterRound;
import view.Animation2D;
import view.SpriteCuter;

public class EnemyHeretic extends BaseObject{

	//Variables
	private Handler handler;
	private Game game;
	private PlayerHUD hud;
	private BufferedImage enmheretic[] = new BufferedImage[3];
	public Animation2D animation;
	private Sound effect_Enemy;
	
	/**
	 * EnemyHeretic constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 * @param game - Game class
	 * @param hud - PlayerHUD class
	 */
	public EnemyHeretic(float x, float y, ID id, SpriteCuter imageCut_enemy, Handler handler, 
												Game game, PlayerHUD hud, Sound effect_Enemy) {
		super(x, y, id, imageCut_enemy);
		this.handler = handler;
		this.game = game;
		this.hud = hud;
		this.effect_Enemy = effect_Enemy;
		
		enmheretic[0] = imageCut.grabImage(1, 1, 33, 32);
		enmheretic[1] = imageCut.grabImage(2, 1, 33, 32);
		enmheretic[2] = imageCut.grabImage(3, 1, 33, 32);
		
		animation = new Animation2D(3, enmheretic);
	}

	float diffX, diffY;
	float speed = 0.05f; //base settings: 0.05f
	
	/**
	 * Tick method
	 */
	@Override
	public void tick() {
		
		x += velX;
		y += velY;
		
		for (int i = 0; i < handler.object.size(); i++) {
			BaseObject tempHeretic = handler.object.get(i);
			
			//Intersection check with player bolt
			if (tempHeretic.getId() == ID.BolterRound){
				if (getBounds().intersects(tempHeretic.getBounds())) {
					handler.removeObject(tempHeretic); //removing Player bolt
					enemyLife -= 25;
				}
			}	
			
			//Intersection check with player - AI
			if (tempHeretic.getId() == ID.SpaceMarine) {
				
				diffX = tempHeretic.getX() - x;
				diffY = tempHeretic.getY() - y;
				
				//if player is in attack range, then attack player
				if (getAttack().intersects(tempHeretic.getBounds())) {
					velX = diffX * speed;
					velY = diffY * speed;
					
					//if enemyAI has ammonition, it fires towards the player
					if (enemyAmmo >= 0) {
						SpriteCuter cut = null;
						
						//enemyAI randomly fires his shoots
						if (Math.random() > 0.9) {
							BaseObject tempBolt = handler.addObject(new BolterRound(this.x + 16, this.y + 16, ID.EnemyBolt, cut, handler));
					
							int mx = (int)x;
							int my = (int)y;
							float angle = (float) Math.atan2(tempHeretic.getX() - mx ,  tempHeretic.getY() - my);
							int boltVelocity = 8; //base settings: 10
							tempBolt.velY = (float) ((boltVelocity) * Math.cos(angle)); //cos and sin must be switched!
							tempBolt.velX = (float) ((boltVelocity) * Math.sin(angle));
							enemyAmmo--;
							effect_Enemy.soundEffect_EnemyShoot.play();
						}
						
					}
					//TEST-DEBUG
					//System.out.println("x: " + this.x + " y: " + this.y );
					//System.out.println("mx: " + mx + " my: " + my + " angle: " + angle);
					//System.out.println("velX: " + tempBolt.velX + " velY: " + tempBolt.velY + " x: " + x + " y: " + y);
					
				}
				else {
					velX = 0;
					velY = 0;
				}
			}
			if (tempHeretic.getId() == ID.WallBlock) {
				collision(tempHeretic);
			}
		}
		
		if (enemyLife <= 0) { 
			handler.removeObject(this);
			effect_Enemy.soundEffect_EnemyDeath.play();
			hud.MarineScore += 1;
		}
		
		animation.runAnimation();
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
	
	//Variables for rendering
	private int width = 32;
	private int height = 32;
	//Shape circle = new Ellipse2D.Double(x-85, y-85, width*6, height*6); //test-debug
	
	//Variables for enemy life and ammo
	public int enemyLife = 75;
	public int enemyAmmo = 3;
	
	/**
	 * Rendering method
	 */
	@Override
	public void render(Graphics graphics) {
		//test-debug only - circle shown to know when the enemy is detecting the player
		//Graphics2D graphics2D = (Graphics2D) graphics;
		
		//Enemy
		if (velX == 0 && velY == 0) {
			graphics.drawImage(enmheretic[0], (int)x, (int)y, null);
		}
		
		else { 
			animation.drawAnimation(graphics, x, y, 0); 
		}
		
		//EnemyHUD
		graphics.setColor(Color.orange);
		graphics.fillRect((int)x, (int)y-5, enemyLife/2, 5);
		graphics.setColor(Color.black);
		graphics.drawRect((int)x, (int)y-5, enemyLife/2, 5);
		
		//test-debug only - circle shown to know when the enemy is detecting the player
		//graphics2D.draw(circle);
		
	}

	/**
	 * Base bounds of tesztEnemy
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	//Attack sphear
	public Ellipse2D getAttack() {
		return new Ellipse2D.Double(x-85, y-85, width*6, height*6);
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
