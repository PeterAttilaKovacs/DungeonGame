package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import main.Game;

public class MenuFonts {

	/**
	 * Menu kirajzolast segito klassz
	 * @param g - grafika
	 * @param font - betu tipus
	 * @param color - betu szin
	 * @param text - kiirando szoveg
	 * @param x - x koordinata
	 * @param y - y koordinata
	 */
	public static void drawString(Graphics g, Font font, Color color, String text, int x, int y){
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, x, y);
	}
	
	//x, y nelkuli parameteres drawString metodus
	public static void drawString(Graphics g, Font font, Color color, String text){
		FontMetrics fontMet = g.getFontMetrics(font);
			int x = (Game.width - fontMet.stringWidth(text)) / 2; //horizontalsi rendereles kozepre
			int y = (Game.height - fontMet.getHeight()) / 2 + fontMet.getAscent(); //vertikalis rendereles kozepre
		
		drawString(g, font, color, text, x, y);
	}
	
	//x nelkuli parameteres drawString metodus
	public static void drawString(Graphics g, Font font, Color color, String text, int y){
		FontMetrics fontMet = g.getFontMetrics(font);
			int x = (Game.width - fontMet.stringWidth(text)) / 2; //horizontalsi rendereles kozepre
		
		drawString(g, font, color, text, x, y);
	}
}
