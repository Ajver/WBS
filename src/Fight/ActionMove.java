package Fight;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Creatures.Player;
import MainFiles.MainClass;
import Other.Handler;

public class ActionMove extends Action {

	private Handler handler;
	
	private float timer = 0.0f, breakTime;
	private boolean isTimer = false;
	
	public ActionMove(float x, float y, Handler handler) {
		super(x, y);
		this.handler = handler;
		
		this.img = MainClass.tex.bootIcon;
	}

	public void slUpdate(float et) {
		if(isTimer) {
			timer += et;
			
			if(timer >= breakTime) {
				// Next action
				handler.actionManager.nextAction();
				
				// Reset timer
				isTimer = false;
				timer = 0.0f;
			}
		}
	}
	
	public void use() {
		int px = handler.player.getMX();
		int py = handler.player.getMY();
	
		for(int yy=-5; yy<=5; yy++) {
			for(int xx=-5; xx<=5; xx++) {
				if(xx != 0 || yy != 0) {
					if(px+xx >= 0 && px+xx < handler.map.w && py+yy >= 0 && py+yy < handler.map.h) {
						int len = getPathLength(px, py, px+xx, py+yy);
						if(len >= 0 && len <= 5) {
							handler.map.grid[px+xx][py+yy].setClickable(true);
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
	
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		int mapX = (int) ((mx + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((my + handler.camera.getY()) / Handler.cellH);
		
		if(mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
			int px = handler.player.getMX();
			int py = handler.player.getMY();
			
			LinkedList<Point> path = getPath(px, py, mapX, mapY);
			if(path != null) {
				if(handler.map.grid[mapX][mapY].isClickable()) {
					// Clearing map
					for(int yy=0; yy<handler.map.h; yy++) {
						for(int xx=0; xx<handler.map.w; xx++) {
							handler.map.grid[xx][yy].setClickable(false);
						}
					}
					mayBeCaneled = false;
					handler.player.move(path);
					breakTime = Player.animationSpeed * (path.size()+1);
					isTimer = true;
				}
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		int mapX = (int) ((mx + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((my + handler.camera.getY()) / Handler.cellH);

		for(int yy=0; yy<handler.map.h; yy++) {
			for(int xx=0; xx<handler.map.w; xx++) {
				handler.map.grid[xx][yy].setHover(false);
			}
		}
		if(mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
			handler.map.grid[mapX][mapY].setHover(true);
		}
	}

	public void canel() {
		for(int yy=0; yy<handler.map.h; yy++) {
			for(int xx=0; xx<handler.map.w; xx++) {
				handler.map.grid[xx][yy].setClickable(false);
			}
		}
	}
}
