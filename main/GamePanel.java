package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	private Thread gameThread;
	private final int FPS = 60;
	private Player player = new Player(this);
	public int groundY = 400;
	ArrayList<Platform> platforms = new ArrayList<>();
	ArrayList<Coin> coins = new ArrayList<>();
	TileMap tileMap = new TileMap("/res/Level1.txt");

	int cameraX = 0;
	int cameraY = 0;
	
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
				//player.checkPlatforms(platforms);
				System.out.println(player.onGround);
				update();
				repaint();
				delta--;
			}
		}
	}
	
	private void update() {
		for(Coin coin : coins) {
			if (player.getBounds().intersects(coin.getBounds())) {
				coin.collected = true;
				player.collectCoin();
			}
		}
		player.onGround = tileMap.isPlayerOnGround(player);
		
		coins.removeIf(coin -> coin.collected);
		
		cameraX = (int) (player.playerX + (player.playerSize/2) - Constants.SCREEN_WIDTH / 2);
		cameraY = (int) (player.playerY + (player.playerSize/2) - Constants.SCREEN_HEIGHT / 2);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		tileMap.draw(g, cameraX, cameraY);
		g.setColor(Color.WHITE);
		g.fillRect(
				(int)(player.playerX - cameraX),
				(int)(player.playerY - cameraY),
				player.playerSize,
				player.playerSize);
		
		g.setColor(Color.BLUE);
		for(Platform p : platforms) {
			g.fillRect(
					p.x - cameraX,
					p.y - cameraY, 
					p.width,
					p.height);
		}
		
		g.setColor(Color.YELLOW);
		for(Coin coin : coins) {
			g.fillRect(
					coin.x - cameraX,
					coin.y - cameraY, 
					coin.size,
					coin.size);
		}
		g.drawString("Score: " + player.score, 10, 30);
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
