package main;

import java.awt.Color;
import java.awt.Graphics;

public class Platform {
	
	public int x, y, width, height;
	
	public Platform(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(x, y, width, height);
	}
}
