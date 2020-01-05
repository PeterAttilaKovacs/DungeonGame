package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import enums.ID;
import view.SpriteCuter;

public abstract class BaseObject {

	public float x;
	public float y;
	public float velX, velY;
	protected ID id;
	protected SpriteCuter imageCut;
	
	//kozos os jatek objektumoknak
	public BaseObject(float x, float y, ID id, SpriteCuter imageCut) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.imageCut = imageCut;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	//getterek es setterek X
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	//getterek es setterek Y
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	//getterek es setterek velX
	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	//getterek es setterek velY
	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	//getterek es setterek ID
	public ID getId() {
		return id;
	}
}
