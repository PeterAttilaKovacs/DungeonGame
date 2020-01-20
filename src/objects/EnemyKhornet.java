package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import enums.ID;
import main.Game;
import main.Handler;
import view.SpriteCuter;

public class EnemyKhornet extends BaseObject{

	//Variables
	private Handler handler;
	private Game game;
	private PlayerHUD hud;
	
	/**
	 * EnemyKhornet constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 * @param handler - Handler class
	 * @param game - Game class
	 * @param hud - PlayerHUD class
	 */
	public EnemyKhornet(float x, float y, ID id, SpriteCuter imageCut, Handler handler, Game game, PlayerHUD hud) {
		super(x, y, id, imageCut);
		this.handler = handler;
		this.game = game;
		this.hud = hud;
	}

	float diffX, diffY;
	float speed = 0.05f; //base settings: 0.05f
	
	@Override
	public void tick() {
		
		x += velX;
		y += velY;
		
		for (int i = 0; i < handler.object.size(); i++) {
			BaseObject tempKhornet = handler.object.get(i);
			
			//Intersection check with player bolt
			if (tempKhornet.getId() == ID.BolterRound){
				if (getBounds().intersects(tempKhornet.getBounds())) {
					handler.removeObject(tempKhornet); //removing Player bolt
					enemyLife -= 25;
				}
			}	
			
			//Intersection check with player - AI
			if (tempKhornet.getId() == ID.SpaceMarine) {
				
				diffX = tempKhornet.getX() - x;
				diffY = tempKhornet.getY() - y;
				
				//if player is in attack range, then attack player
				if (getAttack().intersects(tempKhornet.getBounds())) {
					velX = diffX * speed;
					velY = diffY * speed;
					
					//if AI has ammonition, it fires towards the player
					if (enemyAmmo >= 0) {
						SpriteCuter cut = null;
						BaseObject tempBolt = handler.addObject(new BolterRound(this.x + 16, this.y + 16, ID.EnemyBolt, cut, handler));
					
						int mx = (int)x;
						int my = (int)y;
						float angle = (float) Math.atan2(tempKhornet.getX() - mx ,  tempKhornet.getY() - my);
						int boltVelocity = 5; //Loszer sebesseg alapbeallita: 10
						tempBolt.velY = (float) ((boltVelocity) * Math.cos(angle)); //cos es sin fuggveny felcserelve
						tempBolt.velX = (float) ((boltVelocity) * Math.sin(angle));
						enemyAmmo--;
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
			
			if (tempKhornet.getId() == ID.WallBlock) {
				collision(tempKhornet);
			}
			
		}
		
		if (enemyLife <= 0) { 
			handler.removeObject(this);
			hud.MarineScore += 1;
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
	
	//Variables for rendering
	private int width = 32;
	private int height = 32;
	Shape circle = new Ellipse2D.Double(x-85, y-85, width*6, height*6); //test-debug
	
	//Variables for enemy life and ammo
	public int enemyLife = 75;
	public int enemyAmmo = 3;
	
	@Override
	public void render(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics; //test-debug
		
		//Enemy
		graphics.setColor(Color.red);
		graphics.fillRect((int)x, (int)y, width, height);
		
		//EnemyHUD
		graphics.setColor(Color.orange);
		graphics.fillRect((int)x, (int)y-5, enemyLife/2, 5);
		graphics.setColor(Color.black);
		graphics.drawRect((int)x, (int)y-5, enemyLife/2, 5);
		
		//test-debug only - circle shown to know when the enemy is detecting the player
		graphics2D.draw(circle);
		
	}

	/**
	 * Base bounds of tesztEnemy
	 * @return returns new rectangle for intersection check
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	//test-debug only
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
