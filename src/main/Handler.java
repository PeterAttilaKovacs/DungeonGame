package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import objectscommon.BaseObject;

public class Handler {

	/**
	 * Synchronized ArrayList of BaseObjects
	 */
	public List<BaseObject> object = Collections.synchronizedList(new ArrayList<BaseObject>());
	
	//Variables for KeyInput
	private boolean up = false, down = false, left = false, right = false, esc = false;
	
	//Variables for Volume control
	private boolean volUP = false, volDOWN = false;
	
	/**
	 * Main tick method
	 */
	public void tick(){
		for (int i = 0; i < object.size(); i++) { 
			object.get(i).tick();
		}
	}
	
	/**
	 * Main synchronized render method
	 * @param graphics - renderd graphics
	 */
	public synchronized void render(Graphics graphics){
		for (int i = 0; i < object.size(); i++) {
			object.get(i).render(graphics);;
		}
	}
	
	//Adding synchronized BaseObject to ArrayList 
	public synchronized BaseObject addObject(BaseObject tempObject) {
		object.add(tempObject);
		return tempObject;
	}
		
	//Deleting synchronized BaseObject from ArrayList 
	public synchronized BaseObject removeObject(BaseObject tempObject) {
		object.remove(tempObject);
		return tempObject;
	} 
	
	//Clearing level befor loading next level
	public void clearLevel() {
		object.clear();
	}
	
	//Player (SpaceMarine) movement getters and setters UP
	public boolean isUp() { 
		return up;
	}
	
	public void setUp(boolean up) { 
		this.up = up; 
	}
	
	//Player (SpaceMarine) movement getters and setters DOWN
	public boolean isDown() { 
		return down;
	}
	
	public void setDown(boolean down) { 
		this.down = down;
	}
	
	//Player (SpaceMarine) movement getters and setters LEFT
	public boolean isLeft() { 
		return left;
	}
	
	public void setLeft(boolean left) { 
		this.left = left;
	}
	
	//Player (SpaceMarine) movement getters and setters RIGHT
	public boolean isRight() { 
		return right;
	}
	
	public void setRight(boolean right) { 
		this.right = right;
	}
	
	//Exiting the Game getters and setters
	public boolean isEsc() { 
		return esc;
	}
	
	public void setEsc(boolean esc) {
		this.esc = esc;
	}
	
	//Volume Control
	public boolean volUP() {
		return volUP;
	}
	
	public void setUP(boolean volUP) {
		this.volUP = volUP;
	}
	
	public boolean volDOWN() {
		return volDOWN;
	}
	
	public void setDOWN(boolean volDOWN) {
		this.volDOWN = volDOWN;
	}
}
