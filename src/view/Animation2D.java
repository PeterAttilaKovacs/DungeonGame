package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation2D {

	private int frames;
	private int speed;
	private int count = 0;
	private int index = 0;
	
	private BufferedImage[] image = new BufferedImage[14];
	private BufferedImage currentImage;
	
	/**
	 * Animacio2D konstruktora
	 * @param image - betoltott kep
	 * @param speed 
	 */
	public Animation2D(int speed, BufferedImage image[]){
		this.speed = speed;
		
		for(int i = 0; i < image.length; i++) {
			this.image[i] = image[i];
	    }
		
		frames = image.length;
	}
	
	//animacio futtatasa
	public void runAnimation(){
		index++;
		
		if(index > speed){
			index = 0;
			nextFrame();
		}
	}
	
	//kovetkezo frame hivasa
	public void nextFrame(){

		for(int i = 2; i < image.length; i++) {
			if(frames == i) {
				for(int j = 0; j < i; j++) {
					if(count == j)
						currentImage = image[j];
				}
				count++;
				
				if(count > frames)
					count = 0;
				
				break;
	        }
	    }
	}
	
	//animacio kirajzolasa
	public void drawAnimation(Graphics g, double x, double y, int offset) {
		g.drawImage(currentImage, (int)x - offset, (int)y, null);
	}
	
	//count setterei es getterei
	public void setCount(int count) { 
		this.count = count; 
	}
	
	public int getCount() { 
		return count; 
	}
		
	//speed setterei es getterei
	public void setSpeed(int speed) { 
		this.speed = speed; 
	}
	
	public int getSpeed() { 
		return speed; 
	}
	
}
