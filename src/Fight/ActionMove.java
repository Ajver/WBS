package Fight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Map.Map.ID;
import Other.Handler;

public class ActionMove extends Action {

	private Handler handler;
	
	public ActionMove(float x, float y, Handler handler) {
		super(x, y);
		
		this.handler = handler;
	}

	public void update(float et) {
		
	}

	public void render(Graphics g) {
		g.setColor(new Color(132, 103, 54));
		
		if(isAnimated) {
			g.fillRect((int)(x + (nextX - x)*progress), (int)(y + (nextY - y)*progress), (int)(ActionManager.buttonW * duration), (int)(ActionManager.buttonW));
		}else {
			g.fillRect((int)x, (int)y, (int)(ActionManager.buttonW * duration), (int)(ActionManager.buttonW));
		}
	}
	
	public void use() {
		int px = handler.player.getMX();
		int py = handler.player.getMY();
	
		for(int yy=-5; yy<=5; yy++) {
			for(int xx=-5; xx<=5; xx++) {
				if(xx != 0 || yy != 0) {
					int len = getPathLength(px, py, px+xx, py+yy);
					if(len >= 0 && len <= 5) {
						handler.map.grid[px+xx][py+yy].setClickable(true);
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
			
			for(Point p : points) {
				if(p.x == finish.x) {
					if(p.y == finish.y) {
						return len;
					}
				}
				
				if(p.y-1 >= 0) {
					if(!grid[p.x][p.y-1] && 
						handler.map.grid[p.x][p.y-1].mayBePath()) { 
						grid[p.x][p.y-1] = true;
						newPoints.add(new Point(p.x, p.y-1));
					}
				}
				if(p.x+1 < w) {
					if(!grid[p.x+1][p.y]&& 
						handler.map.grid[p.x+1][p.y].mayBePath()) { 
						grid[p.x+1][p.y] = true;
						newPoints.add(new Point(p.x+1, p.y));
					}
				}
				if(p.y+1 < h) {
					if(!grid[p.x][p.y+1] && 
						handler.map.grid[p.x][p.y+1].mayBePath()) { 
						grid[p.x][p.y+1] = true;
						newPoints.add(new Point(p.x, p.y+1));
					}
				}
				if(p.x-1 >= 0) {
					if(!grid[p.x-1][p.y] && 
						handler.map.grid[p.x-1][p.y].mayBePath()) { 
						grid[p.x-1][p.y] = true;
						newPoints.add(new Point(p.x-1, p.y));
					}
				}	
			}
			len++;
			points = newPoints;
		}
		
		return -1;
	}
	
	private LinkedList<Point> getPath(int x1, int y1, int x2, int y2) {
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
			points = newPoints;
		}
		
		return null;
	}
	
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		int mapX = (int) ((mx + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((my + handler.camera.getY()) / Handler.cellH);
		
		int px = handler.player.getMX();
		int py = handler.player.getMY();
		
		LinkedList<Point> path = getPath(px, py, mapX, mapY);
		if(path != null) {
			if(handler.map.grid[mapX][mapY].isClickable()) {
				handler.player.move(path);
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
		handler.map.grid[mapX][mapY].setHover(true);
	}
	
}
