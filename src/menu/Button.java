package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;

public class Button extends Rectangle {
	
	//Variables
	private Font font, selectedFont;
	private Color color, selectedColor;
	private boolean selected;
	private String text;
	private int textY;
	
	/**
	 * Button constructor
	 * 
	 * @param text - text to show
	 * @param textY - text Y coordinat
	 * @param font - font type
	 * @param selectedFont - selected font type
	 * @param color - font type color
	 * @param selectedColor - selected font type color
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
	
	//setSelected setter
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	//Rendering
	public void render(Graphics graphics) {
		if(selected) {
			MenuFonts.drawString(graphics, selectedFont, selectedColor, text, textY);
		}
		
		else {
			MenuFonts.drawString(graphics, font, color, text, textY);
		}
		
		FontMetrics fontMet = graphics.getFontMetrics();
			this.x = (Game.width - fontMet.stringWidth(text)) / 2;
			this.y = textY - fontMet.getHeight();
			this.width = fontMet.stringWidth(text);
			this.height = fontMet.getHeight();
		graphics.drawRect(x, y, width, height);
	}
}
