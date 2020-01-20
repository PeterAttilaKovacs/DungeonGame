package main;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {

	//Variables for creating the window of the game
	public static int width = 1000;
	public static int height = 600;
	
	/**
	 * Window constructor
	 * @param game - Game class
	 */
	public Window(Game game) {	
		
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
