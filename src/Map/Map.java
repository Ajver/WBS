package Map;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import Other.Handler;

import javax.imageio.ImageIO;

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

		loadFromImg("res/Maps/map1.png");


//
//		for(int yy=0; yy<h; yy++) {
//			for(int xx=0; xx<w; xx++) {
//				grid[xx][yy] = new EmptyPlace(xx, yy);
//			}
//		}
//
//		for(int yy=0; yy<8; yy++) {
//			grid[8][yy+5] = new Rock(8, yy+5);
//		}
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
								if (grid[p.x + xx][p.y + yy].mayBePath()) { // (Is'n a rock, or something...)
									if (handler.getFromMap(p.x + xx, p.y + yy) == null ||
											(p.x + xx == finish.x && p.y + yy == finish.y)) {

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

	private void pathElement(Point p, int xx, int yy, Point finish, Point[][] pGrid, LinkedList<Point> newPoints) {
		if(mayBePath(xx, yy) && pGrid[xx][yy].x == -1) {
			pGrid[xx][yy].x = p.x;
			pGrid[xx][yy].y = p.y;
			newPoints.add(new Point(xx, yy));
		}
	}

	public boolean mayBePath(int xx, int yy) {
		if(xx >= 0 && xx < w && yy >= 0 && yy < h) {
			if(handler.getFromMap(xx, yy) == null) {
				if(grid[xx][yy].mayBePath()) {
					return true;
				}
			}
		}

		return false;
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

	public Point getNearestPoint(LinkedList<Point> list, int x1, int y1) {
		int px = handler.creatures.get(0).getMX();
		int py = handler.creatures.get(0).getMY();

		LinkedList<Point> crossP = new LinkedList<Point>();
		LinkedList<Point> exP = new LinkedList<Point>();

		for(int i=0; i<list.size(); i++) {
			Point p = list.get(i);

			if(px-p.x == 0 || py-p.y == 0) {
				crossP.add(p);
			}else {
				exP.add(p);
			}
		}

		int len;
		Point selP;

		if(crossP.size() > 0) {
			selP = crossP.get(0);
			len = handler.map.getPathLength(x1, y1, selP.x, selP.y);
			for(int i=1; i<crossP.size(); i++) {
				int nLen = handler.map.getPathLength(x1, y1, crossP.get(i).x, crossP.get(i).y);
				if(nLen < len) {
					selP = crossP.get(i);
					len = nLen;
				}
			}
		}else {
			selP = exP.get(0);
			len = handler.map.getPathLength(x1, y1, selP.x, selP.y);
			for(int i=1; i<exP.size(); i++) {
				int nLen = handler.map.getPathLength(x1, y1, exP.get(i).x, exP.get(i).y);
				if(nLen < len) {
					selP = exP.get(i);
					len = nLen;
				}
			}
		}

		return selP;
	}

	public void loadFromImg(String path) {
		try {
			BufferedImage frame = ImageIO.read(new File(path));

			this.w = frame.getWidth();
			this.h = frame.getHeight();

			grid = new MapObject[w][h];

			for(int yy=0; yy<h; yy++) {
				for(int xx=0; xx<w; xx++) {
					MapObject field;

					int rgb = frame.getRGB(xx, yy);
					int r = (rgb >> 16) & 0xff;
					int g = (rgb >> 8) & 0xff;
					int b = (rgb) & 0xff;

					if(r == 100 && g == 100 && b == 100) {
						field = new Rock(xx, yy);
					}

					else {
						field = new EmptyPlace(xx, yy);
					}

					this.grid[xx][yy] = field;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
