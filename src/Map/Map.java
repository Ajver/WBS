package Map;

import java.awt.Graphics;

public class Map {

	// Map size
	public int w, h;
	public MapObject[][] grid;
	
	public enum ID {
		n, 			// Nothing
		rock;		
	}
	
	public Map(int w, int h) {
		this.w = w;
		this.h = h;
		
		grid = new MapObject[w][h];
		
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				grid[xx][yy] = new EmptyPlace(xx, yy);
			}
		}
		
		for(int yy=0; yy<8; yy++) {
			grid[8][yy+5] = new Rock(8, yy+5);
		}
	}
	
	public void update(float et) {
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				grid[xx][yy].update(et);
			}
		}
	}
	
	public void render(Graphics g) {	
		
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				grid[xx][yy].render(g);
			}
		}
	}
}
