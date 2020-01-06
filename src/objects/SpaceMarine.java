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
	 * Jatekos konstruktora
	 * @param x - x koordinataja
	 * @param y - y koordinataja
	 * @param id - azonosito
	 * @param imageCut - kepvago
	 * @param handler - vegrahajto
	 * @param cam - kameranezet
	 * @param game - jatekter
	 * @param hud - elet/loszer kijelzo
	 * @param level - palya
	 */
	public SpaceMarine(float x, float y, ID id, SpriteCuter imageCut, Handler handler, 
						Camera cam, Game game, PlayerHUD hud, LevelLoader level) {
		super(x, y, id, imageCut);
		this.handler = handler;
		this.camera = cam;
		this.game = game;
		this.hud = hud;
		this.level = level;
		this.imageCut = imageCut;
		
		width = 32;
		height = 48;
		
		mouseSpMarine = new MouseInput(handler, cam, game, imageCut, hud);
		game.addMouseListener(mouseSpMarine);
		
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
				
		// key D --> jobbra
		// key A --> balra
		if (handler.isRight()){ velX = +5;}
		else if (!handler.isLeft()){ velX = 0; }
		
		if (handler.isLeft()){ velX = -5; }
		else if (!handler.isRight()){ velX = 0; }
		
		// key W --> fel
		// key S --> le
		if (handler.isUp()){ velY = -5;}
		else if (!handler.isDown()){ velY = 0; }
		
		if (handler.isDown()){ velY = +5; }
		else if (!handler.isUp()){ velY = 0; }
		
		//Esc <-- kilepes
		if (handler.isEsc()){ Runtime.getRuntime().exit(1); }
		
		animation.runAnimation();
		
	}

	/**
	 * Render metodus felulirasa
	 */
	@Override
	public void render(Graphics g) {
		// Teszt render
		//g.setColor(Color.blue);
		//g.fillRect((int)x, (int)y, 32, 48);
		
		if (velX == 0 && velY == 0) {
			g.drawImage(spmarine[0], (int)x, (int)y, null);
		}
		
		else { 
			animation.drawAnimation(g, x, y, 0); 
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	/**
	 * Utkozes figyelese metodus
	 */
	
	private LevelLoader level;
	
	private void collision(){
		for (int i = 0; i < handler.object.size(); i++) {
			
			BaseObject tempObject = handler.object.get(i);
			
			//fallal utkozes figyeles
			if (tempObject.getId() == ID.WallBlock) {
				if (getBounds().intersects(tempObject.getBounds())) {
					x += velX *- 1;
					y += velY *- 1;
				}
			}
			
			//loszeres ladaval valo utkozes figyelese
			if (tempObject.getId() == ID.AmmoCrate) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.MarineAmmo += 50;
					handler.removeObject(tempObject);
				}
			}
			
			//ellenseggel valo utkozes figyelese
			if (tempObject.getId() == ID.Khornet) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.MarineLife--;
				}
			}
			
			//ellenseg lovesevel valo utkozes figyelese
			if (tempObject.getId() == ID.EnemyBolt) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.MarineLife -= 5;
					handler.removeObject(tempObject); //ellenseg lovedekenek levetele
				}
			}
			
			//kilpesi ponttal valo utkozes figyelse
			if (tempObject.getId() == ID.Flag){
				if (getBounds().intersects(tempObject.getBounds())) {
					game.removeMouseListener(mouseSpMarine); //egerfigyelo eltavolitasa palyatoltes elott
					level.nextLevel(); //kovetkezo palya hivasa
				}
			}
		}
		/**
		 * Jatekos elete 0, akkor GameOver peldanyositasa, 
		 * jatekos torlese, egerfigyelo eltavolitasa
		 */
		if (hud.MarineLife <= 0) {
			handler.addObject(new GameOver(this.x, this.y, ID.GameOver, imageCut));
			game.removeMouseListener(mouseSpMarine);
			handler.removeObject(this);
		}
	}
}
