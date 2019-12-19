package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import enums.ID;
import main.Camera;
import main.Game;
import main.Handler;
import objects.BaseObject;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	private BaseObject tempPlayer;
	private Game game;
	private Camera camera;
	
	/**
	 * Eger bevitel konstruktora
	 * @param handler - handler osztaly
	 * @param camera - camera osztaly
	 * @param game - game osztaly
	 */
	public MouseInput(Handler handler, Camera camera, Game game){
		this.handler = handler;
		this.camera = camera;
		this.game = game;
	}
	
	//Jatekos keresese
	public void fndPlayer(){
		for (int i = 0; i < handler.object.size(); i++){ 
			if (handler.object.get(i).getId() == ID.Player){ //jatekos keresese ID alapjan
				tempPlayer = handler.object.get(i);
				break;
			}
		}
	}
	
	//Egergomb lenyomas
	public void mousePressed(MouseEvent e){
		//TODO jatekos lovedek kezeles
	}
}
