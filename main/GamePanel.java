package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	private Thread gameThread;
	private final int FPS = 60;
	private Player player = new Player(this);
	public int groundY = 400;
	
	public GamePanel() {
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		requestFocusInWindow();
		startGameThread();
	}
	
	private void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		double drawInterval = 1000000000.0 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		
		while (gameThread != null) {
			long currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				player.update();
				update();
				repaint();
				delta--;
			}
		}
	}
	
	private void update() {
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		player.draw(g);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.leftPressed = true;
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.rightPressed = true;
		
		if (key == KeyEvent.VK_SHIFT) player.runKey = true;
		
		if(key == KeyEvent.VK_SPACE && player.onGround) {
			player.yVelocity = player.jumpStrength;
			player.onGround = false;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) player.leftPressed = false;
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) player.rightPressed = false;
		
		if (key == KeyEvent.VK_SHIFT) player.runKey = false;
	}
	
	@Override
	public void keyTyped (KeyEvent e) {
		
	}
    
	
}
