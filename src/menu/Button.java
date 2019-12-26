package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;

public class Button extends Rectangle{
	
	private Font font, selectedFont;
	private Color color, selectedColor;
	private boolean selected;
	private String text;
	private int textY;
	
	/**
	 * Gomb konstruktora
	 * 
	 * @param text - kiirando szoveg
	 * @param textY - text Y koordinataja
	 * @param font - betutipus
	 * @param selectedFont - kivalasztott szoveg
	 * @param color - szoveg alap szine
	 * @param selectedColor - kivalasztott szoveg szine
	 */
	public Button(String text, int textY, Font font, Font selectedFont, Color color, Color selectedColor) {
		super();
		this.text = text;
		this.textY = textY;
		this.font = font;
		this.selectedFont = selectedFont;
		this.color = color;
		this.selectedColor = selectedColor;
	}
	
	//kivalasztva settere
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	//render felulirasa
	public void render(Graphics g){
		if(selected){
			MenuFonts.drawString(g, selectedFont, selectedColor, text, textY);
		}
		else{
			MenuFonts.drawString(g, font, color, text, textY);
		}
		
		FontMetrics fontMet = g.getFontMetrics();
		this.x = (Game.width - fontMet.stringWidth(text)) / 2;
		this.y = textY - fontMet.getHeight();
		this.width = fontMet.stringWidth(text);
		this.height = fontMet.getHeight();
		
		g.drawRect(x, y, width, height);
	}
}
