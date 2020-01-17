package view;

import java.awt.image.BufferedImage;

public class SpriteCuter {

	private BufferedImage imageCut;
	
	/**
	 * SpriteCuter constructor
	 * @param imageCut - cuted image
	 */
	public SpriteCuter(BufferedImage imageCut){
		this.imageCut = imageCut;
	}
	
	/**
	 * Image base parameters: 32*32 pixel
	 * If different pixel size is used, reset adjustments. 
	 * (like this: 64*64 pixel: (col*64)-64, (row*64)-64, width, height.) 
	 * @param col int 32
	 * @param row int 32
	 * @param width int 32
	 * @param height int 32 or 48, depending on if the player or the enemy is renderd 	 
	 */
	public BufferedImage grabImage(int col, int row, int width, int height){
		return imageCut.getSubimage((col*32)-32, (row*32)-32, (width), (height));
	}
}
