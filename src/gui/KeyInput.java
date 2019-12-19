package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import enums.ID;
import main.Handler;
import objects.BaseObject;

public class KeyInput extends KeyAdapter {

	/**
	 * 
	 * key W --> fel
	 * key S --> le
	 * key A --> balra
	 * key D --> jobbra
	 * key Esc --> kilepes
	 * 
	 */
	Handler handler;
	public boolean keys[] = new boolean[5];
	
	//KeyInput konstruktora
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	//Gomb lenyomva
	public void keyPressed(KeyEvent e){
			
		int key = e.getKeyCode();
		
		//objektum tomb vegigkeresese
		for (int i = 0; i < handler.object.size(); i++){
			BaseObject tempObject = handler.object.get(i);
				
			if (tempObject.getId() == ID.Player){
				if (key == KeyEvent.VK_W){ handler.setUp(true); }
				if (key == KeyEvent.VK_S){ handler.setDown(true); }
				if (key == KeyEvent.VK_A){ handler.setLeft(true); }
				if (key == KeyEvent.VK_D){ handler.setRight(true); }
				if (key == KeyEvent.VK_ESCAPE){ handler.setEsc(true); } //csak lenyomas vizsglata
			}
		}
	}
	
	//Gomb felengedve
	public void keyReleased(KeyEvent e){
			
		int key = e.getKeyCode();
			
		//objektum tomb vegigkeresese
		for (int i = 0; i < handler.object.size(); i++){
			BaseObject tempObject = handler.object.get(i);
				
			if (tempObject.getId() == ID.Player){
				if (key == KeyEvent.VK_W){ handler.setUp(false); }
				if (key == KeyEvent.VK_S){ handler.setDown(false); }
				if (key == KeyEvent.VK_A){ handler.setLeft(false); }
				if (key == KeyEvent.VK_D){ handler.setRight(false); }
			}
		}
	}
}
