package main;

import javax.swing.JFrame;

public class Main {

	
	public static void main(String[] args) {
	
		JFrame window = new JFrame();
		GamePanel panel = new GamePanel();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		window.setResizable(true);
		window.setLocationRelativeTo(null);
		window.setTitle("My First Game");
		
		window.add(panel);
		window.setVisible(true);
	}
}
