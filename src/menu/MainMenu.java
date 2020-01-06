/**
 * TODO opciok, help allapotok
 */
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
	 * Fomenu konstruktora
	 */
	public MainMenu() {
		options = new Button[4];
		
		options[0] = new Button("PLAY", 200 + 0 * 80, 
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
		
		options[1] = new Button("OPTIONS", 200 + 1 * 80, 
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
		
		options[2] = new Button("HELP", 200 + 2 * 80, 
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
		
		options[3] = new Button("EXIT", 200 + 3 * 80, 
						new Font("arial", Font.PLAIN, 20), 
						new Font("arial", Font.BOLD, 40),
						Color.blue, Color.white);
	}
	
	//Menu grafika renderelese
	public void render(Graphics g) {
		
		g.setColor(Color.orange);
		MenuFonts.drawString(g, new Font("arial", Font.BOLD, 30), Color.blue, Game.title, 80);
		
		for (int i=0; i < options.length; i++){
			if (i == selectedMenu){
				options[i].setSelected(true);
			}
		
			else { 
				options[i].setSelected(false);
			}	
			//rendereles
			options[i].render(g);
		}
	}
	
	//Menu tick hivasa
	public void tick(){
		
		boolean event = false;
		
		//Opciok tomb figyelese
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
	
	//MainMenu allapot valtas
	public void selectState() {
		
		switch(selectedMenu) {
		
			//Jatek statuszvaltas: jatekra
			case 0:
				Game.GameStatus = STATES.Play;
			break;
		
			//Jatek opciok
			case 1:	
				System.out.println("OPCIOK meghivodik"); //teszt
			break;
			
			//Jatek help
			case 2:
				Game.GameStatus = STATES.Help;
				
				System.out.println("HELP meghivodik"); //teszt
			break;
			
			//Jatekbol kilepes
			case 3:	
				Runtime.getRuntime().exit(1);
			break;
		}
	}
}
