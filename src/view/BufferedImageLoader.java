package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image_loader;
	
	//Keptolto metodus
	public BufferedImage loadImage(String path){
		
		try {
			image_loader = ImageIO.read(getClass().getResource(path));
		}
		
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("File I/O error: "+e);
		}
		
		return image_loader;
	}
}
