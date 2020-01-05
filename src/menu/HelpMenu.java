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
	 * HelpMenu konstruktora
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
	
	//HelpMenu grafiaki renderelese
	public void render(Graphics g) {
	
		g.setColor(Color.orange);
		MenuFonts.drawString(g, new Font("arial", Font.BOLD, 30), Color.blue, Game.title, 80);
		
		for (int i=0; i < options.length; i++){
			if (i == selectedSubMenu){
				options[i].setSelected(true);
			}
		
			else { 
				options[i].setSelected(false);
			}	
			//rendereles
			options[i].render(g);
		}
	}
	
	//HelpMenu tick hivasa
	public void tick() {
		
		boolean event = false;
		
		//Opciok tomb figyelese
		for (int i=0; i < options.length; i++) {
			if(options[i].intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1, 1))){
				selectedSubMenu = i;
				event = MouseInput.wasPressed(MouseEvent.BUTTON1);
				System.out.println("Click meghivodik" + event); //teszt
			}
		}
		
		if (event) { 
			selectState();
			System.out.println("SelectState meghivodik"); //teszt
			event = MouseInput.wasReleased(MouseEvent.BUTTON1);
		}
	}
	
	//HelpMenu allapot valtas
	public void selectState() {
		
		switch(selectedSubMenu) {
		
		//Vissza a fomenube
		case 0:
			Game.GameStatus = STATES.Menu;
			
			System.out.println("BACK meghivodik"); //teszt
		break;
	
		//Kilepes Jatekbol
		case 1:	
			Runtime.getRuntime().exit(1);
			
			System.out.println("KILEPES meghivodik"); //teszt
		break;
		}
	}
}
