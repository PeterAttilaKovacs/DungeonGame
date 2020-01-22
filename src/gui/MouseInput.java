package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import enums.ID;
import enums.STATES;
import main.Camera;
import main.Game;
import main.Handler;
import objectplayer.PlayerHUD;
import objectscommon.BaseObject;
import objectscommon.BolterRound;
import sound.Sound;
import view.SpriteCuter;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	private BaseObject tempPlayer;
	private Game game;
	private Camera camera;
	private SpriteCuter imageCut;
	private PlayerHUD hud;
	private Sound effectPlayer;
	
	/*
	 * MouseInput base consturctor
	 */
	public MouseInput(){}
	
	/**
	 * MouseInput parameterized consturctor
	 * @param handler - Handler class
	 * @param camera - Camera class
	 * @param game - Game class
	 * @param spritecuter - SpriteCuter class
	 * @param hud - PlayerHUD class
	 */
	public MouseInput(Handler handler, Camera camera, Game game, SpriteCuter imageCut, PlayerHUD hud, Sound eP) {
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.imageCut = imageCut;
		this.hud = hud;
		this.effectPlayer = eP;
		
		//effectPlayer = new Sound();
	}
	
	//Finding Player
	public void findPlayer() {
		for (int i = 0; i < handler.object.size(); i++) { 
			if (handler.object.get(i).getId() == ID.SpaceMarine) { //finding player by: ID
				tempPlayer = handler.object.get(i);
				break;
			}
		}
	}
	
	//Variables for Mosue Pressed
	private static final int numberOfButtons = 5;
	private static final boolean buttons[] = new boolean[numberOfButtons];
	//private static final boolean lastButtons[] = new boolean[NM_BTN];
		
	/**
	 * Mouse Pressed method override
	 * 
	 * NumberOf_Button : 5 - five buttond mouse
	 * 
	 * Menu state: menu-controll 
	 * Game state: aim and fireing-controll
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
	
		//If menu state is: menu
		if (Game.GameStatus == STATES.Menu) {
			buttons[e.getButton()] = true;
		}
		
		if (Game.GameStatus == STATES.Help) {
			buttons[e.getButton()] = true;
		}
		
		//If menu state is: Play
		if (Game.GameStatus == STATES.Play) {

			//only works, if player is not null
			if (tempPlayer != null) {
				if (hud.MarineAmmo >= 1) {
					BaseObject tempBolt = handler.addObject(new BolterRound(tempPlayer.x + 16, tempPlayer.y + 16, 
																			ID.BolterRound, imageCut, handler));
			
					//calculation of angel for bolt
					float angle = (float) Math.atan2(my - tempPlayer.y - 16 +camera.getY(), mx - tempPlayer.x - 16 +camera.getX());
			
					int boltVelocity = 10; //Bolt base velocity: 10
					tempBolt.velX = (float) ((boltVelocity) * Math.cos(angle));
					tempBolt.velY = (float) ((boltVelocity) * Math.sin(angle));
			
					hud.MarineAmmo--;
					//bolter shoot effect
					effectPlayer.soundEffect_PlayerShoot.play(); //bolt shoot effect
				}
				else {
					//bolter out of ammo effect
					effectPlayer.soundEffect_PlayerOutOfAmmo.play();
				}
			}
			else { findPlayer(); }
		}	
	}
	
	/**
	 * Mouse Released method override
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (Game.GameStatus == STATES.Menu) {
			buttons[e.getButton()] = false;
		}
		if (Game.GameStatus == STATES.Help) { // <-- ez igy nem az igazi (de jobb mint volt), meg 1x atnezni
			buttons[e.getButton()] = false;
		}
	}
	
	//Variables for Mosue Moved
	private static int x;
	private static int y;
	
	/**
	 * Mouse Moved method override
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	/**
	 * Mouse button was pressed oversight
	 * @return the pressed mouse button value
	 */
	public static boolean wasPressed(int button) {
		return buttons[button];
	}
	
	public static boolean wasReleased(int button) {
		return buttons[button];
	}
	
	//Mouse getter X
	public static int getX() {
		return x;
	}
	
	//Mouse getter Y
	public static int getY() {
		return y;
	}
}
