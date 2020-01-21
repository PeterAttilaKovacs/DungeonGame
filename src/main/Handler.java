package main;

import java.awt.Graphics;
import java.util.ArrayList;

import objectscommon.BaseObject;

public class Handler {

	//ArrayList of BaseObjects
	public ArrayList<BaseObject> object = new ArrayList<BaseObject>();
	
	//Variables for KeyInput
	private boolean up = false, down = false, left = false, right = false, esc = false;
	
	/**
	 * Main tick method
	 */
	public void tick(){
		for (int i = 0; i < object.size(); i++) { 
			object.get(i).tick();
		}
	}
	
	/**
	 * Main render method
	 * @param graphics - renderd graphics
	 */
	public void render(Graphics graphics){
		for (int i = 0; i < object.size(); i++) {
			object.get(i).render(graphics);;
		}
	}
	
	//Adding BaseObject to ArrayList 
	public BaseObject addObject(BaseObject tempObject) {
		object.add(tempObject);
		return tempObject;
	}
		
	//Deleting BaseObject from ArrayList 
	public BaseObject removeObject(BaseObject tempObject) {
		object.remove(tempObject);
		return tempObject;
	} 
	
	//Clearing level befor loading next level
	public void clearLevel() {
		object.clear();
	}
	
	//Player (SpaceMarine) getters and setters
	public boolean isUp() { 
		return up;
	}
	
	public void setUp(boolean up) { 
		this.up = up; 
	}
		
	public boolean isDown() { 
		return down;
	}
	
	public void setDown(boolean down) { 
		this.down = down;
	}
		
	public boolean isLeft() { 
		return left;
	}
	
	public void setLeft(boolean left) { 
		this.left = left;
	}
		
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
}
