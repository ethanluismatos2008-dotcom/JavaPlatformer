package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin {
	
	public int x, y, size;
	public boolean collected;
	
	public Coin (int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public Rectangle getBounds() {
		return new Rectangle (x, y, size, size);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, size, size);
	}
	
}
