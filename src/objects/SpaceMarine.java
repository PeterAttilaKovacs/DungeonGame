package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import animation.SpriteCuter;
import enums.ID;
import gui.MouseInput;
import main.Camera;
import main.Game;
import main.Handler;
import main.LevelLoader;

public class SpaceMarine extends BaseObject{

	private int width = 32;
	private int height = 48;
	private Handler handler;
	private Camera camera;
	private Game game;
	private PlayerHUD hud;
	private final MouseInput SpMarine;
	
	public SpaceMarine(float x, float y, ID id, SpriteCuter imageCut, Handler handler, 
						Camera cam, Game game, PlayerHUD hud, LevelLoader level) {
		super(x, y, id, imageCut);
		this.handler = handler;
		this.camera = cam;
		this.game = game;
		this.hud = hud;
		this.level = level;
		
		SpMarine = new MouseInput(handler, cam, game, imageCut, hud);
		game.addMouseListener(SpMarine);
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
		
	}

	/**
	 * Render metodus felulirasa
	 */
	@Override
	public void render(Graphics g) {
		// Teszt render
		g.setColor(Color.green);
		g.fillRect((int)x, (int)y, 32, 48);
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
		for (int i = 0; i < handler.object.size(); i++){
			
			BaseObject tempObject = handler.object.get(i);
			
			//fallal utkozes figyeles
			if (tempObject.getId() == ID.WallBlock){
				if (getBounds().intersects(tempObject.getBounds())){
					x += velX *- 1;
					y += velY *- 1;
				}
			}
			
			//loszeres ladaval valo utkozes figyelese
			if (tempObject.getId() == ID.AmmoCrate){
				if (getBounds().intersects(tempObject.getBounds())){
					hud.MarineAmmo += 50;
					handler.removeObject(tempObject);
				}
			}
			
			//ellenseggel valo utkozes figyelese
			if (tempObject.getId() == ID.Khornet){
				if (getBounds().intersects(tempObject.getBounds())){
					hud.MarineLife--;
				}
			}
			
			//kilpesi ponttal valo utkozes figyelse
			if (tempObject.getId() == ID.Flag){
				if (getBounds().intersects(tempObject.getBounds())){
					game.removeMouseListener(SpMarine); //egerfigyelo eltavolitasa palyatoltes elott
					level.nextLevel(); //kovetkezo palya hivasa
				}
			}
		}
		/**
		 * Jatekos elete 0, akkor GameOver peldanyositasa, 
		 * jatekos torlese, egerfigyelo eltavolitasa
		 */
		if (hud.MarineLife <= 0){
			handler.addObject(new GameOver(this.x, this.y, ID.GameOver, imageCut));
			game.removeMouseListener(SpMarine);
			handler.removeObject(this);
		}
	}

}
