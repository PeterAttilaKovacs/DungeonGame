package objectscommon;

import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import view.SpriteCuter;

public abstract class BaseObject {

	//Variables
	public float x;
	public float y;
	public float velX, velY;
	protected ID id;
	protected SpriteCuter imageCut;
	
	/**
	 * BaseObject for all objects in game
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param id - Enum class ID
	 * @param imageCut - SpriteCuter class
	 */
	public BaseObject(float x, float y, ID id, SpriteCuter imageCut) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.imageCut = imageCut;
	}
	
	//Abstract definitions
	public abstract void tick();
	public abstract void render(Graphics graphics);
	public abstract Rectangle getBounds();
	
	//getters and setters for X
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	//getters and setters for Y
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	//getters and setters for velX
	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	//getters and setters for velY
	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	//getters for ID
	public ID getId() {
		return id;
	}
}
