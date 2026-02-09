package main;

import javax.swing.JFrame;

public class Main {

	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	public static void main(String[] args) {
	
		JFrame window = new JFrame();
		GamePanel panel = new GamePanel();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		window.setResizable(true);
		window.setLocationRelativeTo(null);
		window.setTitle("My First Game");
		
		window.add(panel);
		window.setVisible(true);
	}
}
