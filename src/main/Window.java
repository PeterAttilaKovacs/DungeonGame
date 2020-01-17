package main;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {

	/**
	 * Window constructor
	 * @param width - width of game-field/zone
	 * @param height - height of game-field/zone
	 * @param title - title of the game
	 * @param game - Game class
	 */
	public Window(int width, int height, String title, Game game){
		
		//Variables
		JFrame frame = new JFrame();
		
		//Defining the window
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
			
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
	}
}
