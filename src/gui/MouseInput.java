package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import enums.ID;
import enums.STATES;
import main.Camera;
import main.Game;
import main.Handler;
import objectplayer.PlayerHUD;
import objectplayer.SpaceMarine;
import objectscommon.BaseObject;
import objectscommon.BolterRound;
import objectscommon.PlasmaRound;
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
		
	//Variables for Mouse-fire event
	private int my;
	private int mx;
	
	/**
	 * Mouse Pressed method override
	 * 
	 * NumberOf_Button : 5 - five buttond mouse
	 * 
	 * Menu state: menu-controll 
	 * Game state: aim and fireing-controll
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		
		mx = event.getX();
		my = event.getY();
	
		//If game state is: menu
		if (Game.GameStatus == STATES.Menu) {
			buttons[event.getButton()] = true;
		}
		
		//If game state is: help
		if (Game.GameStatus == STATES.Help) {
			buttons[event.getButton()] = true;
		}
		
		//If game state is: Play
		if (Game.GameStatus == STATES.Play) {

			//Fire weapon 1
			if (MouseEvent.BUTTON1 == event.getButton()) {
				fireWeaponOne();
			}
			
			//Fire weapon 2
			if (MouseEvent.BUTTON3 == event.getButton()) {
				fireWeaponTwo();
			}
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
	
	//Weapon 1 - Mouse button 1
	public void fireWeaponOne() {
		if (tempPlayer != null) { //only works, if player is not null
			if (hud.MarineAmmo >= 1) {
				BaseObject tempBolt = handler.addObject(new BolterRound(tempPlayer.x + 16, tempPlayer.y + 16, 
																		ID.BolterRound, imageCut, handler));
				//calculation of angel for bolt
				float angle = (float) Math.atan2(my - tempPlayer.y - 16 +camera.getY(), mx - tempPlayer.x - 16 +camera.getX());
				int boltVelocity = 10; //Bolt base velocity: 10
				tempBolt.velX = (float) ((boltVelocity) * Math.cos(angle));
				tempBolt.velY = (float) ((boltVelocity) * Math.sin(angle));
				hud.MarineAmmo--;
				effectPlayer.soundEffect_PlayerBolt.play(); //bolt shoot effect
			}
			else {
				effectPlayer.soundEffect_PlayerOutOfAmmo.play(); //bolter out of ammo effect
			}
		}
		else { findPlayer(); }
	}
	
	//TODO do collision checks
	//Weapon 2 - Mouse button 3
	public void fireWeaponTwo() {
		if (tempPlayer != null) { //only works, if player is not null
			
			if (SpaceMarine.getAttackTimer() < SpaceMarine.getAttackCooldown()) {
				effectPlayer.soundEffect_PlayerPlasmacharg.play(); //plasma recharging effect
				return;
			}
			
			else {
				BaseObject tempPlasma = handler.addObject(new PlasmaRound(tempPlayer.x + 16, tempPlayer.y + 16, 
																		ID.PlasmaRound, imageCut, handler));
				//calculation of angel for plasma shoot
				float angle = (float) Math.atan2(my - tempPlayer.y - 16 +camera.getY(), mx - tempPlayer.x - 16 +camera.getX());

				int palsmaVelocity = 10; //Plasma base velocity: 10
				tempPlasma.velX = (float) ((palsmaVelocity) * Math.cos(angle));
				tempPlasma.velY = (float) ((palsmaVelocity) * Math.sin(angle));
				SpaceMarine.setAttackTimer(0);
				effectPlayer.soundEffect_PlayerPlasma.play(); //plasma shoot effect
			}
		}
		else { findPlayer(); }
	}
	
	/**
	 * Mouse button was pressed oversight
	 * @return the pressed mouse button value
	 */
	public static boolean wasPressed(int button) {
		return buttons[button];
	}
	
	/**
	 * Mouse button was released oversight
	 * @return the pressed mouse button value
	 */
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
