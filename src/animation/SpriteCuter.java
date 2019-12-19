package animation;

import java.awt.image.BufferedImage;

public class SpriteCuter {

	private BufferedImage cuted_image;
	
	//SpriteCuter konstruktora
	public SpriteCuter(BufferedImage cut_image){
		this.cuted_image = cut_image;
	}
	
	/**
	 * Kep alap merete: 32*32 pixel
	 * Mas pixelu kepmerethez hozzaigazitani. pl. 64*64 pixel: (col*64)-64, (row*64)-64, width, height. 
	 * @param col int 32
	 * @param row int 32
	 * @param width int 32
	 * @param height int 32 vagy 48 attol fuggoen szorny vagy jatekos van renderelve
	 */
	public BufferedImage grabImage(int col, int row, int width, int height){
		return cuted_image.getSubimage((col*32)-32, (row*32)-32, (width), (height));
	}
}
