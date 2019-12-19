package main;

import java.awt.Graphics;
import java.util.ArrayList;
import objects.BaseObject;

public class Handler {

	/**
	 * Tomb objektumoknak
	 */
	public ArrayList<BaseObject> object = new ArrayList<BaseObject>();
	
	/**
	 * Jatekos mozgatasanak valtozoi
	 */
	private boolean up = false, down = false, left = false, right = false, esc = false;
	
	/**
	 * Alap tick metodus
	 */
	public void tick(){
		for (int i = 0; i < object.size(); i++) { //hivja: 
			object.get(i).tick();
		}
	}
	
	/**
	 * Alap render metodus
	 * @param g - grafika
	 */
	public void render(Graphics g){
		for (int i = 0; i < object.size(); i++) {
			object.get(i).render(g);;
		}
	}
	
	//Objektum hozzadasa tombhoz 
	public BaseObject addObject(BaseObject tempObject){
		object.add(tempObject);
		return tempObject;
	}
		
	//Objektum torlese tombbol
	public BaseObject removeObject(BaseObject tempObject){
		object.remove(tempObject);
		return tempObject;
	} 
	
	//Jatekos getterei es setterei
	public boolean isUp(){ 
		return up;}
	
	public void setUp(boolean up){ 
		this.up = up;}
		
	public boolean isDown(){ 
		return down;}
	
	public void setDown(boolean down){ 
		this.down = down;}
		
	public boolean isLeft(){ 
		return left;}
	
	public void setLeft(boolean left){ 
		this.left = left;}
		
	public boolean isRight(){ 
		return right;}
	
	public void setRight(boolean right){ 
		this.right = right;}
	
	//Jatekobol valo kilepes gettere es settere
	public boolean isEsc(){ 
		return esc;}
	
	public void setEsc(boolean esc){
		this.esc = esc;}
}
