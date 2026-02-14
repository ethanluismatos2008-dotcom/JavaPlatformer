package main;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TileMap {
	
	public GamePanel gp;
	private int[][] map;
	private int rows;
	private int cols;
	
	public TileMap(String fileName) {
		loadMap(fileName);
	}
	
	private void loadMap(String fileName) {
		List<String> lines = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(
						getClass().getResourceAsStream(fileName)))) {
			
			String line;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
		rows = lines.size();
		cols = lines.get(0).length();
		map = new int[rows][cols];
		
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				map[row][col] = Character.getNumericValue(
						lines.get(row).charAt(col));
						
			}
		}
				
	}
	
	public void draw (Graphics g, int cameraX, int cameraY) {
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				
				if(map[row][col] == 1) {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(
							col * Constants.TILE_SIZE - cameraX,
							row * Constants.TILE_SIZE - cameraY,
							Constants.TILE_SIZE,
							Constants.TILE_SIZE);
				}
			}
		}
	}

	public boolean isSolid(int row, int col) {
		if(row < 0 || col < 0 || row >= rows || col >= cols) {
			return true;
		}
		return map[row][col] == 1;
	}
	
	public boolean isPlayerOnGround(Player player) {
		int leftTile = (int) player.playerX / Constants.TILE_SIZE;
		int rightTile = (int)(player.playerX + player.playerSize - 1) / Constants.TILE_SIZE;
		int bottomTile = (int)(player.playerY + player.playerSize - 1) / Constants.TILE_SIZE;
		int topTile = (int) (player.playerY / Constants.TILE_SIZE);
		
		for (int col = leftTile; col <= rightTile; col++) {
			if (isSolid(bottomTile, col)) {
				player.playerY = bottomTile * Constants.TILE_SIZE - player.playerSize + 1;
				return true;
			}
			if(player.yVelocity < 0) {
				if(isSolid(topTile, col)) {
					player.playerY = topTile * Constants.TILE_SIZE + Constants.TILE_SIZE; 
					player.yVelocity = 0;
				}
			} 
		}
		return false;
	}
}
