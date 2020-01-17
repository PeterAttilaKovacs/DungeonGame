package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import enums.STATES;
import gui.MouseInput;
import main.Game;

public class HelpMenu {

	private Button[] options;
	private int selectedSubMenu;
	
	/**
	 * HelpMenu constructor
	 */
	public HelpMenu() {
		options = new Button[2];
		
		options[0] = new Button("BACK", 200 + 2 * 80, 
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
		
		options[1] = new Button("EXIT", 200 + 3 * 80, 
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
	}
	
	//HelpMenu rendering
	public void render(Graphics graphics) {
	
		//TODO chek: width and height
		graphics.setColor(Color.black);
		graphics.setFont(new Font("arial", Font.ITALIC, 30));
		graphics.drawString("Ez a HELP menu a Jatekhoz.", Game.height/2, Game.width/7);
		graphics.setFont(new Font("arial", Font.ITALIC, 20));
		graphics.drawString("Jatekos mozgatasa: WSAD, loves: bal egergomb, Kilepes: ESC", Game.height/3, Game.width/6);
		graphics.drawString("Jatek celja: tulelni a fertozott vilagokat. Egyik vilagbol a masikba a terkapuk vezetnek.", 
						Game.height/4, Game.width/5);
		graphics.drawString("Sok sikert Urgardista!", Game.height/2, Game.width/4);
		
		graphics.setColor(Color.orange);
		MenuFonts.drawString(graphics, new Font("arial", Font.BOLD, 30), Color.blue, Game.title, 80);
		
		for (int i=0; i < options.length; i++){
			if (i == selectedSubMenu){
				options[i].setSelected(true);
			}
		
			else { 
				options[i].setSelected(false);
			}	
			//rendering
			options[i].render(graphics);
		}
	}
	
	//HelpMenu tick
	public void tick() {
		
		boolean event = false;
		
		//Observing Options Array
		for (int i=0; i < options.length; i++) {
			if(options[i].intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1, 1))){
				selectedSubMenu = i;
				event = MouseInput.wasPressed(MouseEvent.BUTTON1);
				System.out.println("Click meghivodik" + event); //test-debug
			}
		}
		
		if (event) { 
			selectState();
			System.out.println("SelectState meghivodik"); //test-debug
			event = MouseInput.wasReleased(MouseEvent.BUTTON1);
		}
	}
	
	//Help menu status change on event
	public void selectState() {
		
		switch(selectedSubMenu) {
		
		//GameStatus set to: Main menu
		case 0:
			Game.GameStatus = STATES.Menu;
			
			System.out.println("BACK meghivodik"); //test-debug
		break;
	
		//Exit Game
		case 1:	
			Runtime.getRuntime().exit(1);
			
			System.out.println("KILEPES meghivodik"); //test-debug
		break;
		}
	}
}
