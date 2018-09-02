package Fight;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Creatures.Creature;
import Character.HUD;
import MainFiles.MainClass;
import Other.Handler;

public class ActionRun extends Action {
	
	public ActionRun(Creature c, Handler handler, HUD hud) {
		super(c, handler, hud);
		
		this.img = MainClass.tex.bigBootIcon;

		this.duration = 2;
	}
	
	public void select() {
		int cx = c.getMX();
		int cy = c.getMY();

		slMouseLeved();

		int maxLen = c.att.getSz() * 3;
		
		for(int yy=-maxLen; yy<=maxLen; yy++) {
			for(int xx=-maxLen; xx<=maxLen; xx++) {
				if(xx != 0 || yy != 0) {
					if(cx+xx >= 0 && cx+xx < handler.map.w && cy+yy >= 0 && cy+yy < handler.map.h) {
						int len = handler.map.getPathLength(cx, cy, cx+xx, cy+yy);
						if(len > 0 && len <= maxLen) {
							if(handler.getFromMap(cx+xx, cy+yy) == null) {
								handler.map.setClickable(cx+xx, cy+yy, 0);
							}
						}
					}
				}
			}
		}		
	}
		
	public void use(int mapX, int mapY) {
		if(mapX >= 0 && mapX < handler.map.w && mapY >= 0 && mapY < handler.map.h) {
			int cx = c.getMX();
			int cy = c.getMY();

			LinkedList<Point> path = handler.map.getPath(cx, cy, mapX, mapY);
			if(path != null) {
				int maxLen = c.att.getSz() * 3;
				
				if(path.size() > maxLen) {
					LinkedList<Point> newPath = new LinkedList<Point>();

					for(int i=0; i<maxLen+1; i++) {
						newPath.add(path.get(i));
					}
					
					path = newPath;
				}
				used = true;

				c.move(path);
				startTimer((int)(c.moveDuration * (path.size()+1) * 1000.0f));
			}else {
				System.out.println("Path to run is null");
			}
		}
	}

	public void refresh() {
		if(c.am.currentAction == 0) {
			for (int yy = -1; yy <= 1; yy++) {
				for (int xx = -1; xx <= 1; xx++) {
					if (handler.map.mayBePath(c.getMX() + xx, c.getMY() + yy)) {
						isActive = true;
						return;
					}
				}
			}

			comment.set("Nie masz którêdy biec");
		}else {
			comment.set("Nie mo¿esz ju¿ biec w tej rundzie");
		}

		isActive = false;
	}

	public void slMouseEntered() {
		int cx = c.getMX();
		int cy = c.getMY();

		int maxLen = c.att.getSz() * 3;

		for(int yy=-maxLen; yy<=maxLen; yy++) {
			for(int xx=-maxLen; xx<=maxLen; xx++) {
				if(cx+xx >= 0 && cx+xx < handler.map.w && cy+yy >= 0 && cy+yy < handler.map.h) {
					int len = handler.map.getPathLength(cx, cy, cx+xx, cy+yy);
					if (len > 0 && len <= maxLen) {
						if(handler.getFromMap(cx+xx, cy+yy) == null) {
							handler.map.grid[cx + xx][cy + yy].setColor(new Color(0, 211, 211));
						}
					}
				}
			}
		}

		hud.light(HUD.Sz);
	}

	public void slMouseLeved() {
		handler.map.clearColors();
		hud.unlight(HUD.Sz);
	}
	
	public void mouseReleased(MouseEvent e) {
		int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);

		if(handler.map.isClickable(mapX, mapY)) {
			canel();
			use(mapX, mapY);
		}
	}

	public void canel() {
		for(int yy=0; yy<handler.map.h; yy++) {
			for(int xx=0; xx<handler.map.w; xx++) {
				handler.map.setClickable(xx, yy, false);
			}
		}
	}
}
