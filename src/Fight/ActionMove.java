package Fight;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Creatures.Creature;
import Creatures.Human;
import MainFiles.MainClass;
import Other.Handler;

public class ActionMove extends Action {
	
	public ActionMove(float x, float y, Creature c, Handler handler) {
		super(x, y, c, handler);
		
		this.img = MainClass.tex.bootIcon;
	}

	public void slUpdate(float et) {
		
	}
	
	public void select() {
		int cx = c.getMX();
		int cy = c.getMY();
	
		int maxLen = c.att.current[1][3] * 2;
		
		for(int yy=-maxLen; yy<=maxLen; yy++) {
			for(int xx=-maxLen; xx<=maxLen; xx++) {
				if(xx != 0 || yy != 0) {
					if(cx+xx >= 0 && cx+xx < handler.map.w && cy+yy >= 0 && cy+yy < handler.map.h) {
						int len = getPathLength(cx, cy, cx+xx, cy+yy);
						if(len >= 0 && len <= maxLen) {
							handler.map.grid[cx+xx][cy+yy].setClickable(new Color(0, 0, 255));
						}
					}
				}
			}
		}		
	}
	
	private int getPathLength(int x1, int y1, int x2, int y2) {
		int len = 0;
		
		Point finish = new Point(x1, y1);
		LinkedList<Point> points = new LinkedList<Point>();
		points.add(new Point(x2, y2));
		int w = handler.map.w;
		int h = handler.map.h;
		
		// false - empty, true - visited
		boolean[][] grid = new boolean[w][h];
		
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				grid[xx][yy] = false;
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
								if(!grid[p.x+xx][p.y+yy] && 
									handler.map.grid[p.x+xx][p.y+yy].mayBePath()) { // May be the path element 
								
									if(p.x+xx == finish.x) {
										if(p.y+yy == finish.y) {								
											return len;
										}
									}
									grid[p.x+xx][p.y+yy] = true;
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
	
	private LinkedList<Point> getPath(int x1, int y1, int x2, int y2) {
		if(x1 == x2) {
			if(y1 == y2) {
				return null;
			}
		}
		Point finish = new Point(x1, y1);
		LinkedList<Point> points = new LinkedList<Point>();
		points.add(new Point(x2, y2));
		int w = handler.map.w;
		int h = handler.map.h;
		Point[][] grid = new Point[w][h];
		
		for(int yy=0; yy<h; yy++) {
			for(int xx=0; xx<w; xx++) {
				grid[xx][yy] = new Point(-1, -1);
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
							int ncx = grid[cx][cy].x;
							int ncy = grid[cx][cy].y;
							cx = ncx;
							cy = ncy;
							path.add(new Point(cx, cy));
						}while(cx != x2 || cy != y2);
						
						return path;
					}
				}
				
				if(p.y-1 >= 0) {
					if(grid[p.x][p.y-1].x == -1 && 
						handler.map.grid[p.x][p.y-1].mayBePath()) { 
						grid[p.x][p.y-1].x = p.x;
						grid[p.x][p.y-1].y = p.y;
						newPoints.add(new Point(p.x, p.y-1));
					}
				}
				if(p.x+1 < w) {
					if(grid[p.x+1][p.y].x == -1 && 
						handler.map.grid[p.x+1][p.y].mayBePath()) { 
						grid[p.x+1][p.y].x = p.x;
						grid[p.x+1][p.y].y = p.y;
						newPoints.add(new Point(p.x+1, p.y));
					}
				}
				if(p.y+1 < h) {
					if(grid[p.x][p.y+1].x == -1 && 
						handler.map.grid[p.x][p.y+1].mayBePath()) { 
						grid[p.x][p.y+1].x = p.x;
						grid[p.x][p.y+1].y = p.y;
						newPoints.add(new Point(p.x, p.y+1));
					}
				}
				if(p.x-1 >= 0) {
					if(grid[p.x-1][p.y].x == -1 && 
						handler.map.grid[p.x-1][p.y].mayBePath()) { 
						grid[p.x-1][p.y].x = p.x;
						grid[p.x-1][p.y].y = p.y;
						newPoints.add(new Point(p.x-1, p.y));
					}
				}				
			}
			
			for(Point p : points) {
				if(p.y-1 >= 0 && p.x-1 >= 0) {
					if(grid[p.x-1][p.y-1].x == -1 && 
						handler.map.grid[p.x-1][p.y-1].mayBePath()) { 
						grid[p.x-1][p.y-1].x = p.x;
						grid[p.x-1][p.y-1].y = p.y;
						newPoints.add(new Point(p.x-1, p.y-1));
					}
				}
				if(p.x+1 < w && p.y-1 >= 0) {
					if(grid[p.x+1][p.y-1].x == -1 && 
						handler.map.grid[p.x+1][p.y-1].mayBePath()) { 
						grid[p.x+1][p.y-1].x = p.x;
						grid[p.x+1][p.y-1].y = p.y;
						newPoints.add(new Point(p.x+1, p.y-1));
					}
				}
				if(p.y+1 < h && p.x+1 < h) {
					if(grid[p.x+1][p.y+1].x == -1 && 
						handler.map.grid[p.x+1][p.y+1].mayBePath()) { 
						grid[p.x+1][p.y+1].x = p.x;
						grid[p.x+1][p.y+1].y = p.y;
						newPoints.add(new Point(p.x+1, p.y+1));
					}
				}
				if(p.x-1 >= 0 && p.y+1 < h) {
					if(grid[p.x-1][p.y+1].x == -1 && 
						handler.map.grid[p.x-1][p.y+1].mayBePath()) { 
						grid[p.x-1][p.y+1].x = p.x;
						grid[p.x-1][p.y+1].y = p.y;
						newPoints.add(new Point(p.x-1, p.y+1));
					}
				}
			}
			
			points = newPoints;
		}
		
		return null;
	}
	
	public void use(int mapX, int mapY) {
		if(mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
			int cx = c.getMX();
			int cy = c.getMY();
			
			LinkedList<Point> path = getPath(cx, cy, mapX, mapY);
			if(path != null) {
				int maxLen = c.att.current[1][3] * 2;
				if(path.size() > maxLen+1) {
					LinkedList<Point> newPath = new LinkedList<Point>();

					for(int i=0; i<maxLen+1; i++) {
						newPath.add(path.get(i));
					}
					
					path = newPath;
				}
				mayBeCaneled = false;
				c.move(path);
				startTimer(Human.animationSpeed * (path.size()+1));
			}else {
				handler.msg.set("No path");
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);
		
		if(mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
			int cx = c.getMX();
			int cy = c.getMY();
			
			LinkedList<Point> path = getPath(cx, cy, mapX, mapY);
			if(path != null) {
				if(handler.map.grid[mapX][mapY].isClickable()) {
					// Clearing map
					for(int yy=0; yy<handler.map.h; yy++) {
						for(int xx=0; xx<handler.map.w; xx++) {
							handler.map.grid[xx][yy].setClickable(null);
						}
					}
					mayBeCaneled = false;
					c.move(path);
					startTimer(Human.animationSpeed * (path.size()+1));
				}
			}else {
				handler.msg.set("No path");
			}
		}
	}
	
	public void slMouseMoved(MouseEvent e) {
		
	}


	public void canel() {
		for(int yy=0; yy<handler.map.h; yy++) {
			for(int xx=0; xx<handler.map.w; xx++) {
				handler.map.grid[xx][yy].setClickable(null);
			}
		}
	}
}
