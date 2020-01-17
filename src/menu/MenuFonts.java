package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import main.Game;

public class MenuFonts {

	/**
	 * Helper function to rendering menu
	 * @param graphics - graphics
	 * @param font - font type
	 * @param color - font type color
	 * @param text - text to render
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 */
	public static void drawString(Graphics graphics, Font font, Color color, String text, int x, int y){
		graphics.setColor(color);
		graphics.setFont(font);
		graphics.drawString(text, x, y);
	}
	
	//Helper function, wtih out x, y coordinates
	public static void drawString(Graphics graphics, Font font, Color color, String text){
		FontMetrics fontMet = graphics.getFontMetrics(font);
			int x = (Game.width - fontMet.stringWidth(text)) / 2; //horizontal rendering to center
			int y = (Game.height - fontMet.getHeight()) / 2 + fontMet.getAscent(); //vertical rendering to center
		
		drawString(graphics, font, color, text, x, y);
	}
	
	//Helper function, wtih out x coordinates
	public static void drawString(Graphics graphics, Font font, Color color, String text, int y){
		FontMetrics fontMet = graphics.getFontMetrics(font);
			int x = (Game.width - fontMet.stringWidth(text)) / 2; //horizontal rendering to center
		
		drawString(graphics, font, color, text, x, y);
	}
}
