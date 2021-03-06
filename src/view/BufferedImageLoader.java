package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image_loader;
	
	/**
	 * Image loader method
	 * @param path - path of the loaded image
	 * @return the loaded image
	 */
	public BufferedImage loadImage(String path){
		
		try {
			image_loader = ImageIO.read(getClass().getResource(path));
		}
		
		catch (IOException exception) {
			exception.printStackTrace();
			System.err.println("File I/O error: " + exception); //Basic exception handeling
		}
		
		return image_loader;
	}
}
