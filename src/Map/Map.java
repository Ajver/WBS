package Map;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

import Other.Handler;

public class Map {

	// Map size
	public int w, h;
	public MapObject[][] grid;
	
	private Handler handler;
	
	public enum ID {
		n, 			// Nothing
		rock;		
	}
	
	public Map(int w, int h, Handler handler) {
		this.w = w;
		this.h = h;
		this.handler = handler;
		
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
	
	public int getPathLength(int x1, int y1, int x2, int y2) {
		int len = 0;
		
		Point finish = new Point(x1, y1);
		LinkedList<Point> points = new LinkedList<Point>();
		points.add(new Point(x2, y2));
		
		// false - empty, true - visited
		boolean[][] bGrid = new boolean[w][h];
		
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				bGrid[xx][yy] = false;
			}
		}
		LinkedList<Point> newPoints;
		while(points.size() > 0) {
			newPoints = new LinkedList<Point>();
			len++;
			
			for(Point p : points) {
				for(int yy=-1; yy<=1; yy++) {
					for(int xx=-1; xx<=1; xx++) {
						if(xx != 0 || yy != 0) { 
							if(p.x+xx >= 0 && p.x+xx < w && p.y+yy >= 0 && p.y+yy < h) { // Over the map
								if(!bGrid[p.x+xx][p.y+yy] && (handler.getFromMap(p.x+xx, p.y+yy) == null || p.x+xx == finish.x && p.y+yy == finish.y) &&
									grid[p.x+xx][p.y+yy].mayBePath()) { // May be the path element 
								
									if(p.x+xx == finish.x) {
										if(p.y+yy == finish.y) {								
											return len;
										}
									}
									bGrid[p.x+xx][p.y+yy] = true;
									newPoints.add(new Point(p.x+xx, p.y+yy));
								}
							}
						}
					}
				}
			}
			
			points = newPoints;
		}
		
		return -1;
	}
	
	public LinkedList<Point> getPath(int x1, int y1, int x2, int y2) {
		if(x1 == x2) {
			if(y1 == y2) {
				return null;
			}
		}
		Point finish = new Point(x1, y1);
		LinkedList<Point> points = new LinkedList<Point>();
		points.add(new Point(x2, y2));
		Point[][] pGrid = new Point[w][h];
		
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				pGrid[xx][yy] = new Point(-1, -1);
			}
		}
		
		LinkedList<Point> newPoints;
		while(points.size() > 0) {
			newPoints = new LinkedList<Point>();
			
			for(Point p : points) {
				if(p.x == finish.x) {
					if(p.y == finish.y) {
						// End of algorithm
						// Creating the path
						int cx = finish.x;
						int cy = finish.y;
																	
						LinkedList<Point> path = new LinkedList<Point>();
						
						path.add(new Point(cx, cy));
						
						do {
							int ncx = pGrid[cx][cy].x;
							int ncy = pGrid[cx][cy].y;
							cx = ncx;
							cy = ncy;
							path.add(new Point(cx, cy));
						}while(cx != x2 || cy != y2);
						
						return path;
					}
				}
				
				pathElement(p, p.x, p.y-1, finish, pGrid, newPoints);
				pathElement(p, p.x+1, p.y, finish, pGrid, newPoints);
				pathElement(p, p.x, p.y+1, finish, pGrid, newPoints);
				pathElement(p, p.x-1, p.y, finish, pGrid, newPoints);
				
				pathElement(p, p.x-1, p.y-1, finish, pGrid, newPoints);
				pathElement(p, p.x+1, p.y-1, finish, pGrid, newPoints);
				pathElement(p, p.x+1, p.y+1, finish, pGrid, newPoints);
				pathElement(p, p.x-1, p.y+1, finish, pGrid, newPoints);
			}
			
			points = newPoints;
		}
		
		return null;
	}
	
	private void pathElement(Point p, int xx, int yy, Point finish, Point[][] pGrid, LinkedList<Point> newPoints) {
		if(xx >= 0 && xx < w && yy >= 0 && yy < h) {
			if(pGrid[xx][yy].x == -1 && (handler.getFromMap(xx, yy) == null || (xx == finish.x && yy == finish.y)) &&
				grid[xx][yy].mayBePath()) { 
				pGrid[xx][yy].x = p.x;
				pGrid[xx][yy].y = p.y;
				newPoints.add(new Point(xx, yy));
			}
		}
	}
}
