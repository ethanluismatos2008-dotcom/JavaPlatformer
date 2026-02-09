package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Player {
	
	public GamePanel gp;
	public boolean leftPressed, rightPressed;
	public float playerY = 300;
	public float playerX = 100;
	private int maxSpeed = 5;
	private int walkSpeed = 5;
	private int runSpeed = 7;
	public int playerSize = 50;
	//Player physics
	public float xVelocity = 0;
	public float yVelocity = 0;
	private float gravity = 0.5f;
	public float jumpStrength = -10f;
	private float accel = 0.5f;
	private float groundFriction = 0.85f;
	private float airFriction = 0.95f;
	
	//State
	public boolean runKey = false;
	public boolean running = false;
	public boolean onGround = false;
	
	public int score = 0;
	
	public Player(GamePanel gp) {
		this.gp = gp;
	}
	
	public void update() {
		
		yVelocity += gravity;
		playerY += yVelocity;
		playerX += xVelocity;
		
		if(onGround) running = runKey;
		
		if (running == true){
			maxSpeed = runSpeed;
		}
		else {
			maxSpeed = walkSpeed;
		}
		
		if(playerY + playerSize >= gp.groundY) {
			playerY = gp.groundY - playerSize;
			yVelocity = 0;
			onGround = true;
		}
		if (leftPressed == true) {
			xVelocity -= accel;
		}
		if (rightPressed == true) {
			xVelocity += accel;
		}
		if(xVelocity > maxSpeed) xVelocity = maxSpeed;
			
		if(xVelocity < -maxSpeed) xVelocity = -maxSpeed;
		
		if(!leftPressed && !rightPressed) {
			xVelocity *= onGround ? groundFriction : airFriction;
			
			//Stop tiny drifting
			if(Math.abs(xVelocity) < 0.05) {
				xVelocity = 0;
			}
		}
		
	}
	
	public void checkPlatforms(ArrayList<Platform> platforms) {
		
		for(Platform p : platforms) {
			boolean falling = yVelocity > 0;
			
			boolean withinX =
				playerX + playerSize > p.x &&
				playerX < p.x + p.width;
				
			boolean landing = 
				playerY + playerSize <= p.y + yVelocity &&
				playerY + playerSize + yVelocity >= p.y;
				
			if (falling && withinX && landing) {
				playerY = p.y - playerSize;
				yVelocity = 0;
				onGround = true;
			}
		}
	}
	
	public void collectCoin() {
		score += 1;
	}
	
	public Rectangle getBounds() {
		return new Rectangle ((int)playerX, (int)playerY, playerSize, playerSize);
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)playerX, (int)playerY, playerSize, playerSize);
	}	
}
