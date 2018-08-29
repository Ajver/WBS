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
		if(x1 == x2) {
			if(y1 == y2) {
				return 0;
			}
		}
		
		int len = 0;
		
		LinkedList<Point> points = new LinkedList<Point>();
		points.add(new Point(x1, y1));
		Point finish = new Point(x2, y2);
		
		// false - empty, true - visited
		boolean[][] visited = new boolean[w][h];
		
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				visited[xx][yy] = false;
			}
		}
		
		visited[x1][y1] = true;
		
		LinkedList<Point> newPoints;
		
		while(points.size() > 0) {
			newPoints = new LinkedList<Point>();
			len++;
			
			for(Point p : points) {
				for(int yy=-1; yy<=1; yy++) {
					for(int xx=-1; xx<=1; xx++) {
						if(p.x+xx >= 0 && p.x+xx < w && p.y+yy >= 0 && p.y+yy < h) { // Over the map
							if (!visited[p.x + xx][p.y + yy]) {
								if (grid[p.x + xx][p.y + yy].mayBePath()) {
									if (p.x + xx == finish.x && p.y + yy == finish.y) { // (Is'n a rock, or something...)

										if (p.x + xx == finish.x) {
											if (p.y + yy == finish.y) {
												return len;
											}
										}
										visited[p.x + xx][p.y + yy] = true;
										newPoints.add(new Point(p.x + xx, p.y + yy));
									} else if (handler.getFromMap(p.x + xx, p.y + yy) == null) { // (Is'n a rock, or something...)

										if (p.x + xx == finish.x) {
											if (p.y + yy == finish.y) {
												return len;
											}
										}
										visited[p.x + xx][p.y + yy] = true;
										newPoints.add(new Point(p.x + xx, p.y + yy));
									}
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
		
		LinkedList<Point> points = new LinkedList<Point>();
		points.add(new Point(x1, y1));
		Point finish = new Point(x2, y2);
		
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
																	
						LinkedList<Point> tempPath = new LinkedList<Point>();
						
						tempPath.add(new Point(cx, cy));
						
						do {
							Point np = pGrid[cx][cy];
							cx = np.x;
							cy = np.y;
							tempPath.add(new Point(cx, cy));
						}while(cx != x1 || cy != y1);
						
						LinkedList<Point> path = new LinkedList<Point>();
						
						for(int i=tempPath.size()-2; i>=0; i--) {
							path.add(tempPath.get(i));
						}
						
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

	public void clearColors() {
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				grid[xx][yy].setColor(null);
			}
		}
	}

	public boolean isClickable(int mapX, int mapY) {
		if(mapX >= 0 && mapX < w && mapY >= 0 && mapY < h) {
			return grid[mapX][mapY].isClickable();
		}

		return false;
	}

	public void setClickable(int mapX, int mapY, boolean flag) {
		if(mapX >= 0 && mapX < w && mapY >= 0 && mapY < h) {
			grid[mapX][mapY].setClickable(flag);
		}
	}

	public void setClickable(int mapX, int mapY, int nr) {
		if(mapX >= 0 && mapX < w && mapY >= 0 && mapY < h) {
			grid[mapX][mapY].setClickable(nr);
		}
	}

	private void pathElement(Point p, int xx, int yy, Point finish, Point[][] pGrid, LinkedList<Point> newPoints) {
		if(xx >= 0 && xx < w && yy >= 0 && yy < h) {
			if(pGrid[xx][yy].x == -1 &&
					handler.getFromMap(xx, yy) == null &&
					grid[xx][yy].mayBePath()) { 
				pGrid[xx][yy].x = p.x;
				pGrid[xx][yy].y = p.y;
				newPoints.add(new Point(xx, yy));
			}
		}
	}
}
