package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import animation.SpriteCuter;
import enums.ID;
import enums.STATES;
import main.Camera;
import main.Game;
import main.Handler;
import objects.BaseObject;
import objects.BolterRound;
import objects.PlayerHUD;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	private BaseObject tempPlayer;
	private Game game;
	private Camera camera;
	private SpriteCuter cut;
	private PlayerHUD hud;
	
	/*
	 * Eger parameter nelkuli konstruktora
	 */
	public MouseInput(){}
	
	/**
	 * Eger parameterezett konstruktora
	 * @param handler - handler osztaly
	 * @param camera - camera osztaly
	 * @param game - game osztaly
	 * 
	 * tovabbi bovites: spritecuter, hud osztaly
	 */
	public MouseInput(Handler handler, Camera camera, Game game, SpriteCuter cut, PlayerHUD hud){
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
	
		//ha a jatek statusza: jatek
		if (Game.GameStatus == STATES.Play){

			//ha a jatekos nem nulla, akkor hajtodik vegre
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
	
	private static int x;
	private static int y;
	
	//eger mozgasfigyelo
	public void mouseMoved(MouseEvent e){
		x = e.getX();
		y = e.getY();
		//System.out.println(x + y);
	}
	
	//teszt
	private static final int NM_BTN = 3;
	private static final boolean[] buttons = new boolean[NM_BTN];
	
	public static boolean wasPressed(int button){
		return buttons[button];
	}
	//teszt
	
	//eger x gettere
	public static int getX(){
		return x;
	}
	
	//eger y gettere
	public static int getY(){
		return y;
	}

	public static int mouseClicked(int button) {
		// TODO Auto-generated method stub
		return button;
	}
}
