package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	private Thread gameThread;
	private final int FPS = 60;
	private boolean up, down, left, right;
	private float playerY = 300;
	private float playerX = 100;
	private int playerSpeed = 5;
	private int playerSize = 50;
	
	//Player physics
	private float yVelocity = 0;
	private float gravity = 0.5f;
	private float jumpStrength = -10f;
	
	//State
	private boolean onGround = false;
	
	//private int bulletSize = 10;
	//private int bulletSpeed = 10;
	public GamePanel() {
		setBackground(Color.BLACK);
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(this);
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
				update();
				repaint();
				delta--;
			}
		}
	}
	
	private void update() {
		
		yVelocity += gravity;
		playerY += yVelocity;
		
		//Ground collision
		
		int groundY = 400;
		
		if(playerY + playerSize >= groundY) {
			playerY = groundY - playerSize;
			yVelocity = 0;
			onGround = true;
		}
		if (up == true) playerY -= playerSpeed;
		if (down == true) playerY += playerSpeed;
		if (left == true) playerX -= playerSpeed;
		if (right == true) playerX += playerSpeed;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Example drawing
		
		g.setColor(Color.WHITE);
		g.fillRect((int)playerX, (int)playerY, playerSize, playerSize);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) up = true;
		if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) down = true;
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) left = true;
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) right = true;
		
		if(key == KeyEvent.VK_SPACE && onGround) {
			yVelocity = jumpStrength;
			onGround = false;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) up = false;
		if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) down = false;
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) left = false;
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) right = false;
	}
	
	@Override
	public void keyTyped (KeyEvent e) {
		
	}
}
