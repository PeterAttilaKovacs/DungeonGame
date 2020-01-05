package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import enums.ID;
import enums.STATES;
import main.Camera;
import main.Game;
import main.Handler;
import objects.BaseObject;
import objects.BolterRound;
import objects.PlayerHUD;
import view.SpriteCuter;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	private BaseObject tempPlayer;
	private Game game;
	private Camera camera;
	private SpriteCuter imageCut;
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
	public MouseInput(Handler handler, Camera camera, Game game, SpriteCuter imageCut, PlayerHUD hud) {
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.imageCut = imageCut;
		this.hud = hud;
	}
	
	//Jatekos keresese
	public void fndPlayer(){
		for (int i = 0; i < handler.object.size(); i++) { 
			if (handler.object.get(i).getId() == ID.SpaceMarine) { //jatekos keresese ID alapjan
				tempPlayer = handler.object.get(i);
				break;
			}
		}
	}
	
	/**
	 * Egergomb lenyomas metodus
	 * 
	 * NM_BTN : 5 - otgombos egerkezeleshez
	 * Az ertek novelesevel tobbgombos egerkezeles lehetseges
	 * 
	 * Menu allapotban: menuvezerles 
	 * Jatek allapotben: loves vezerles
	 */
	private static final int NM_BTN = 5;
		
	private static final boolean buttons[] = new boolean[NM_BTN];
	//private static final boolean lastButtons[] = new boolean[NM_BTN];
		
	@Override
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
	
		//ha a jatek statusza: menu
		if (Game.GameStatus == STATES.Menu) {
			buttons[e.getButton()] = true;
		}
		
		//ha a jatek statusza: jatek
		if (Game.GameStatus == STATES.Play) {

			//ha a jatekos nem nulla, akkor hajtodik vegre
			if (tempPlayer != null) {
				BaseObject tempBolt = handler.addObject(new BolterRound(tempPlayer.x + 16, tempPlayer.y + 16, ID.BolterRound, imageCut, handler));
			
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
	
	//Egergomb felengedve 
	@Override
	public void mouseReleased(MouseEvent e) {
		if (Game.GameStatus == STATES.Menu) {
			buttons[e.getButton()] = false;
		}
		if (Game.GameStatus == STATES.Help) { // <-- ez igy nem jo, atnezni!!
			buttons[e.getButton()] = false;
		}
	}
	
	//Eger mozgasfigyelo
	private static int x;
	private static int y;
	
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	/**
	 * Egergomb lenyomasra kerult-e figyelese
	 * @return visszatero ertek a lenyomott egergomb (button)
	 */
	public static boolean wasPressed(int button) {
		return buttons[button];
	}
	
	public static boolean wasReleased(int button) {
		return buttons[button];
	}
	
	//Eger x gettere
	public static int getX() {
		return x;
	}
	
	//Eger y gettere
	public static int getY() {
		return y;
	}
}
