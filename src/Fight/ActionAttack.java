package Fight;

import java.awt.*;
import java.awt.event.MouseEvent;

import Character.Dice;
import Creatures.Creature;
import MainFiles.MainClass;
import Other.Handler;

public class ActionAttack extends Action {

	public ActionAttack(Creature c, Handler handler) {
		super(c, handler);
		this.img = MainClass.tex.swordIcon;
	}

	public void select() {
		int cx = c.getMX();
		int cy = c.getMY();

		this.hover = false;
		slMouseLeved();

		for(int yy=-1; yy<=1; yy++) {
			for(int xx=-1; xx<=1; xx++) {
				if(xx != 0 || yy != 0) {
					if(handler.getFromMap(c.getMX()+xx, c.getMY()+yy) != null) {
						handler.map.setClickable(cx+xx, cy+yy, 1);
					}
				}
			}
		}
	}
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x && mx <= x+ActionManagerGUI.buttonW * duration && my >= y && my <= y+ActionManagerGUI.buttonW;
	}

	public void use(int mapX, int mapY) {
		Creature enemy = handler.getFromMap(mapX, mapY);
		c.setFocus(mapX, mapY);

		if(c.att.WW(0)) {
			int dmg = Dice.roll1d10();
			handler.msg.set("Damage: " + dmg);
			enemy.hit(dmg);
		}else {
			handler.msg.set("Miss");
		}
		c.am.attackCounter++;
		used = true;

		startTimer((long)(c.attackDuration * 1000.0f));
		c.attack();
	}

	public void refresh() {
		this.isActive = can();
	}

	public boolean can() {
		boolean good = false;

		for(int yy=-1; yy<=1; yy++) {
			for(int xx=-1; xx<=1; xx++) {
				if(xx != 0 || yy != 0) {
					if(handler.getFromMap(c.getMX()+xx, c.getMY()+yy) != null) {
						good = true;
						yy = xx = 10;
					}
				}
			}
		}

		if(!good) {
			comment = "Nie ma przeciwnika w zasiêgu";
		}else {
			good = good && c.am.attackCounter < c.att.getA();

			if(!good) {
				comment = "Nie mo¿esz ju¿ atakowaæ w tej rundzie";
			}
		}

		return good;
	}
	
	public void mouseReleased(MouseEvent e) {
		int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);

		if(handler.map.isClickable(mapX, mapY)) {
			canel();
			use(mapX, mapY);
		}
	}

	public void slMouseEntered() {
		int cx = c.getMX();
		int cy = c.getMY();

		for(int yy=-1; yy<=1; yy++) {
			for (int xx = -1; xx <= 1; xx++) {
				if (cx + xx >= 0 && cx + xx < handler.map.w && cy + yy >= 0 && cy + yy < handler.map.h) {
					if (xx != 0 || yy != 0) {
						handler.map.grid[cx + xx][cy + yy].setColor(new Color(142, 29, 1));
					}
				}
			}
		}
	}

	public void slMouseLeved() {
		handler.map.clearColors();
	}

	public void canel() {
		int cx = c.getMX();
		int cy = c.getMY();

		for(int yy=-1; yy<=1; yy++) {
			for(int xx=-1; xx<=1; xx++) {
				if(cx+xx >= 0 && cx+xx < handler.map.w && cy+yy >= 0 && cy+yy < handler.map.h) {
					if(xx != 0 || yy != 0) {
						handler.map.setClickable(cx+xx, cy+yy,false);
					}
				}
			}
		}
	}
}
