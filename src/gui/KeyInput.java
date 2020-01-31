package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import enums.ID;
import main.GameHandler;
import objectscommon.BaseObject;

public class KeyInput extends KeyAdapter {

	/**
	 * 
	 * key W --> UP
	 * key S --> DOWN
	 * key A --> LEFT
	 * key D --> RIGHT
	 * key Esc --> EXIT
	 * 
	 */
	GameHandler handler;
	public boolean keys[] = new boolean[5];
	
	/**
	 * KeyInput constructor
	 * @param handler - Handler class
	 */
	public KeyInput(GameHandler handler){
		this.handler = handler;
	}
	
	/**
	 * Key Pressed method
	 */
	public void keyPressed(KeyEvent event){
			
		int key = event.getKeyCode();
		
		//for cycle on handler object class
		for (int i = 0; i < handler.object.size(); i++){
			BaseObject tempObject = handler.object.get(i);
				
			if (tempObject.getId() == ID.SpaceMarine){
				if (key == KeyEvent.VK_W) { 		handler.setUp(true); }
				if (key == KeyEvent.VK_S) { 		handler.setDown(true); }
				if (key == KeyEvent.VK_A) { 		handler.setLeft(true); }
				if (key == KeyEvent.VK_D) { 		handler.setRight(true); }
				
				if (key == KeyEvent.VK_ESCAPE) { 	handler.setEsc(true); } //only pressed is checkd
				
				if (key == KeyEvent.VK_N) {	handler.setUP(true); }
				if (key == KeyEvent.VK_M) {	handler.setDOWN(true); }
			}
		}
	}
	
	/**
	 * Key Released method
	 */
	public void keyReleased(KeyEvent event){
			
		int key = event.getKeyCode();
			
		//for cycle on handler object class
		for (int i = 0; i < handler.object.size(); i++){
			BaseObject tempObject = handler.object.get(i);
				
			if (tempObject.getId() == ID.SpaceMarine){
				if (key == KeyEvent.VK_W){ handler.setUp(false); }
				if (key == KeyEvent.VK_S){ handler.setDown(false); }
				if (key == KeyEvent.VK_A){ handler.setLeft(false); }
				if (key == KeyEvent.VK_D){ handler.setRight(false); }
				
				if (key == KeyEvent.VK_N) {	handler.setUP(false); }
				if (key == KeyEvent.VK_M) {	handler.setDOWN(false); }
			}
		}
	}
}
