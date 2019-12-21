package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import animation.SpriteCuter;
import enums.ID;
import main.Camera;
import main.Game;
import main.Handler;
import objects.BaseObject;
import objects.BolterRound;
import objects.Hud;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	private BaseObject tempPlayer;
	private Game game;
	private Camera camera;
	private SpriteCuter cut;
	private Hud hud;
	
	/**
	 * Eger bevitel konstruktora
	 * @param handler - handler osztaly
	 * @param camera - camera osztaly
	 * @param game - game osztaly
	 * 
	 * tovabbi bovites: spritecuter, hud osztaly
	 */
	public MouseInput(Handler handler, Camera camera, Game game, SpriteCuter cut, Hud hud){
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.cut = cut;
		this.hud = hud;
	}
	
	//Jatekos keresese
	public void fndPlayer(){
		for (int i = 0; i < handler.object.size(); i++){ 
			if (handler.object.get(i).getId() == ID.SpaceMarine){ //jatekos keresese ID alapjan
				tempPlayer = handler.object.get(i);
				break;
			}
		}
	}
	
	//Egergomb lenyomas
	public void mousePressed(MouseEvent e){
		
		int mx = e.getX();
		int my = e.getY();
	
		if (tempPlayer != null){
			BaseObject tempBolt = handler.addObject(new BolterRound(tempPlayer.x + 16, tempPlayer.y + 16, ID.BolterRound, cut, handler));
			
			//Lovedek iranyszamitas
			float angle = (float) Math.atan2(my - tempPlayer.y - 16 +camera.getY(), mx - tempPlayer.x - 16 +camera.getX());
			
			int boltVelocity = 10; //Loszer sebesseg alapbeallita: 10
			tempBolt.velX = (float) ((boltVelocity) * Math.cos(angle));
			tempBolt.velY = (float) ((boltVelocity) * Math.sin(angle));
			
			hud.MarineAmmo--;
		}
	
		else { fndPlayer(); }
	}
}
