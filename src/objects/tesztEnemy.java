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

public class tesztEnemy extends BaseObject{

	private Handler handler;
	private Game game;
	private PlayerHUD hud;
	
	//tesztEnemy konstruktora
	public tesztEnemy(float x, float y, ID id, SpriteCuter imageCut, Handler handler, Game game, PlayerHUD hud) {
		super(x, y, id, imageCut);
		this.handler = handler;
		this.game = game;
		this.hud = hud;
	}

	float diffX, diffY;
	float speed = 0.05f; //alapbeallitas: 0.05f
	
	@Override
	public void tick() {
		
		x += velX;
		y += velY;
		
		//objektumok vegigkeresese
		for (int i = 0; i < handler.object.size(); i++){
			BaseObject tempTESZT = handler.object.get(i);
			
			//utkozes figyelese lovedekkel
			if (tempTESZT.getId() == ID.BolterRound){
				if (getBounds().intersects(tempTESZT.getBounds())){
					handler.removeObject(tempTESZT); //lovedek torlese
					enemyLife -= 25;
				}
			}	
			
			//utkozes figyelese jatekossal,  - AI
			if (tempTESZT.getId() == ID.SpaceMarine){
				
				diffX = tempTESZT.getX() - x;
				diffY = tempTESZT.getY() - y;
				
				//ha eszlelesikorbe lepett a jatekos, jatekos tamadasa
				if (getAttack().intersects(tempTESZT.getBounds())){
					velX = diffX * speed;
					velY = diffY * speed;
					
					//ez igy nem mukodik...
//					fndEnemy();
//
//					if (tempEnemy != null){
//						SpriteCuter cut = null;
//						BaseObject tempBolt = handler.addObject(new BolterRound(tempEnemy.x + 16, tempEnemy.y + 16, ID.BolterRound, cut, handler));
//					
//						int mx = (int)diffX;
//						int my = (int)diffY;
//						float angle = (float) Math.atan2(my - tempEnemy.y - 16, mx - tempEnemy.x - 16);
//						int boltVelocity = 10; //Loszer sebesseg alapbeallita: 10
//						tempBolt.velX = (float) ((boltVelocity) * Math.cos(angle));
//						tempBolt.velY = (float) ((boltVelocity) * Math.sin(angle));
//						enemyAmmo--;
//						System.out.println("ellenseg loves meghiva: " + enemyAmmo);
//					}
				}
				
				else {
					velX = 0;
					velY = 0;
				}
			}
			
			if (tempTESZT.getId() == ID.WallBlock){
				collision(tempTESZT);
			}
			
		}
		
		if (enemyLife <= 0){ 
			handler.removeObject(this);
			hud.MarineScore += 10; //jatekos pontszamainak novelese 10-el
		}	
	}
	
	//Ellenseg AI kereso metodus, ellenseg loveseihez
//	private BaseObject tempEnemy;
//
//	public void fndEnemy(){
//		for (int i = 0; i < handler.object.size(); i++){ 
//			if (handler.object.get(i).getId() == ID.Khornet){ //jatekos keresese ID alapjan
//				tempEnemy = handler.object.get(i);
//				break;
//			}
//		}
//	}
	
	/**
	 * Utkozes figyeles
	 * @param tempObject
	 */
	public void collision(BaseObject tempObject){
		if (getBoundsTop().intersects(tempObject.getBounds())){
			y = tempObject.getY() + height;
			velY = 0;
		}
		
		if (getBoundsBottom().intersects(tempObject.getBounds())){
			y = tempObject.getY() - height;
			velY = 0;
		}
		
		if (getBoundsLeft().intersects(tempObject.getBounds())){
			x = tempObject.getX() + width;
			velX = 0;
		}
		
		if (getBoundsRight().intersects(tempObject.getBounds())){
			x = tempObject.getX() - width;
			velX = 0;
		}
	}
	
	private int width = 32;
	private int height = 32;
	Shape circle = new Ellipse2D.Double(x-85, y-85, width*6, height*6); //teszt
	
	//Ellenseg elete es lovedek valtozoi
	public int enemyLife = 75;
	public int enemyAmmo = 3;
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; //teszt
		
		//Ellenseg
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, width, height);
		
		//EnemyHUD
		g.setColor(Color.orange);
		g.fillRect((int)x, (int)y-5, enemyLife/2, 5);
		g.setColor(Color.black);
		g.drawRect((int)x, (int)y-5, enemyLife/2, 5);
		
		//teszt - eszlelesi kor
		g2.draw(circle);
		
	}

	/**
	 * Alap utkozeshez
	 */
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	//teszteleshez - eszlelesi kor kijelezese
	public Ellipse2D getAttack() {
		return new Ellipse2D.Double(x-85, y-85, width*6, height*6);
	}
	
	/**
	 * Ellenseget korulolelo ter, fallal valo utkozes figyeleshez
	 * @return getBounds
	 */
	
	//felfele
	public Rectangle getBoundsTop() {
		return new Rectangle((int)x + (width/2)-((width/2)/2), (int)y, width/2, height/2); //ezzel szamolva az utkozes alapbeallitas: 64, 64
	}
	
	//lefele
	public Rectangle getBoundsBottom() {
		return new Rectangle((int)x + (width/2)-((width/2)/2), (int)y + (height/2), width/2, height/2); //ezzel szamolva az utkozes alapbeallitas: 64, 64
	}
	
	//balra
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x, (int)y + 4, 5, height-9); //ezzel szamolva az utkozes alapbeallitas: 64, 64
	}
		
	//jobbra
	public Rectangle getBoundsRight() {
		return new Rectangle((int)x + width - 5, (int)y + 4, 5, height-8); //ezzel szamolva az utkozes alapbeallitas: 64, 64
	}
}
