package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation2D {

	//Variables
	private int frames;
	private int speed;
	private int count = 0;
	private int index = 0;	
	private BufferedImage[] image = new BufferedImage[14];
	private BufferedImage currentImage;
	
	/**
	 * Animatio2D constructor
	 * @param image - loaded image
	 * @param speed 
	 */
	public Animation2D(int speed, BufferedImage image[]) {
		this.speed = speed;
		
		for(int i = 0; i < image.length; i++) {
			this.image[i] = image[i];
	    }
		
		frames = image.length;
	}
	
	//Running animation
	public void runAnimation() {
		index++;
		
		if(index > speed){
			index = 0;
			nextFrame();
		}
	}
	
	//Calling next frame
	public void nextFrame() {

		for(int i = 2; i < image.length; i++) {
			if(frames == i) {
				for(int j = 0; j < i; j++) {
					if(count == j)
						currentImage = image[j];
				}
				count++;
				
				if(count > frames) {
					count = 0;
				}
				break;
	        }
	    }
	}
	
	//Rendering animation
	public void drawAnimation(Graphics graphics, double x, double y, int offset) {
		graphics.drawImage(currentImage, (int)x - offset, (int)y, null);
	}
	
	//count setters and getters
	public void setCount(int count) { 
		this.count = count; 
	}
	
	public int getCount() { 
		return count; 
	}
		
	//speed setters and getters
	public void setSpeed(int speed) { 
		this.speed = speed; 
	}
	
	public int getSpeed() { 
		return speed; 
	}
	
}
