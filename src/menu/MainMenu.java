package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import enums.STATES;
import gui.MouseInput;
import main.Game;

public class MainMenu {

	private Button[] options;
	private int selectedMenu;
	
	/**
	 * Main menu constructor
	 */
	public MainMenu() {
		options = new Button[3];
		
		options[0] = new Button("PLAY", 200 + 0 * 80, 
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
		
		options[1] = new Button("HELP", 280 + 1 * 80, 		//Options - 200
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
		
		options[2] = new Button("EXIT", 280 + 2 * 80, 		//Help - 200
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
		
//		options[3] = new Button("EXIT", 200 + 3 * 80, 
//						new Font("arial", Font.PLAIN, 20), 
//						new Font("arial", Font.BOLD, 40),
//						Color.blue, Color.white);
	}
	
	//Menu rendering
	public void render(Graphics graphics) {
		
		graphics.setColor(Color.orange);
		MenuFonts.drawString(graphics, new Font("arial", Font.BOLD, 30), Color.blue, Game.title, 80);
		
		for (int i=0; i < options.length; i++){
			if (i == selectedMenu){
				options[i].setSelected(true);
			}
		
			else { 
				options[i].setSelected(false);
			}	
			//rendereles
			options[i].render(graphics);
		}
	}
	
	//Menu tick
	public void tick(){
		
		boolean event = false;
		
		//Observing Options Array
		for (int i=0; i < options.length; i++) {
			if(options[i].intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1, 1))){
				selectedMenu = i;
				event = MouseInput.wasPressed(MouseEvent.BUTTON1);
			}
		}
		
		if (event) { 
			selectState();
			event = MouseInput.wasReleased(MouseEvent.BUTTON1);
		}
	}
	
	//Main menu status change on event
	public void selectState() {
		
		switch(selectedMenu) {
		
			//GameStatus set to: Play
			case 0:
				Game.GameStatus = STATES.Play;
			break;
		
			//Options menu called
//			case 1:	
//				System.out.println("OPCIOK meghivodik"); //test-debug
//				//TODO make option menu
//			break;
			
			//GameStatus set to: Help
			case 1:
				Game.GameStatus = STATES.Help;
				
				System.out.println("HELP meghivodik"); //test-debug
			break;
			
			//Exit Game
			case 2:	
				Runtime.getRuntime().exit(0);
			break;
		}
	}
}
