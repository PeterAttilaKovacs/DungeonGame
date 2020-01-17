package main;

import enums.ID;
import objects.BaseObject;

public class Camera {

	//Variables for camera
	private int x, y;
	private Handler handler;
	private BaseObject tempPlayer;
			
	/**
	 * Camera constructor
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param handler - Handler class
	 */
	public Camera(int x, int y, Handler handler){
		this.x = x;
		this.y = y;
		this.handler = handler;
			
		findPlayer();
	}
			
	//Finding Player method
	public void findPlayer(){
		for (int i = 0; i < handler.object.size(); i++){ 
			if (handler.object.get(i).getId() == ID.SpaceMarine){ //findig Player by: ID
				tempPlayer = handler.object.get(i);
				break;
			}
		}
	}
			
	//tick method
	public void tick(){
				
		if (tempPlayer != null) {
			x = (int)tempPlayer.x - Game.width/2;
			y = (int)tempPlayer.y - Game.height/2;
		}
				
		else { findPlayer(); }
			
		//bloking camera to leave the Game-zone
		if (x <= 0) { x = 0; }
		if (x >= 1052) { x = 1052; } //width 0 - 1052
		if (y <= 0) { y = 0; }
		if (y >= 1108 + 48) { y = 1108 + 48; } //height 0 - 1108 + (48) <-- height of the player
	}
			
	//Camera X getters and setters
	public int getX(){ 
		return x;}
	
	public void setX(int x){ 
		this.x = x;}

	//Camera Y getters and setters
	public int getY(){ 
		return y;}
	
	public void setY(int y){ 
		this.y = y;}
}
