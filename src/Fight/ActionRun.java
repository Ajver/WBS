package Fight;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import Creatures.Creature;
import Creatures.Human;
import MainFiles.MainClass;
import Other.Handler;

public class ActionRun extends Action {
	
	public ActionRun(Creature c, Handler handler) {
		super(c, handler);
		
		this.img = MainClass.tex.bigBootIcon;
		
		this.duration = 2;
	}

	public void slUpdate(float et) {
		
	}
	
	public void select() {
		int cx = c.getMX();
		int cy = c.getMY();

		int maxLen = c.att.current[1][3] * 4;
		
		for(int yy=-maxLen; yy<=maxLen; yy++) {
			for(int xx=-maxLen; xx<=maxLen; xx++) {
				if(xx != 0 || yy != 0) {
					if(cx+xx >= 0 && cx+xx < handler.map.w && cy+yy >= 0 && cy+yy < handler.map.h) {
						int len = handler.map.getPathLength(cx, cy, cx+xx, cy+yy);
						if(len > 0 && len <= maxLen) {
							handler.map.grid[cx+xx][cy+yy].setClickable(0);
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

			c.setFocus(mapX, mapY);

			LinkedList<Point> path = handler.map.getPath(cx, cy, mapX, mapY);
			if(path != null) {
				int maxLen = c.att.current[1][3] * 4;
				
				if(path.size() > maxLen+1) {
					LinkedList<Point> newPath = new LinkedList<Point>();

					for(int i=0; i<maxLen+1; i++) {
						newPath.add(path.get(i));
					}
					
					path = newPath;
				}
				mayBeCaneled = false;

				c.move(path);
				startTimer((int)(Human.animationSpeed * (path.size()+1) * 1000.0f));
			}else {
				handler.msg.set("No path to run");
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);

        canel();
        use(mapX, mapY);
	}

	public void slMouseMoved(MouseEvent e) {

	}

	public void canel() {
		for(int yy=0; yy<handler.map.h; yy++) {
			for(int xx=0; xx<handler.map.w; xx++) {
				handler.map.grid[xx][yy].setClickable(false);
			}
		}
	}
}
