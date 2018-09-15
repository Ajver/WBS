package Fight;

import java.awt.*;
import java.awt.event.MouseEvent;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Handler;
import Character.HUD;

public class ActionAttack extends Action {

	public ActionAttack(Creature c, Handler handler, HUD hud) {
		super(c, handler, hud);
		this.img = MainClass.tex.swordIcon;
	}

	public void select() {
		int cx = c.getMX();
		int cy = c.getMY();

		clearColors();

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

		if(c.att.WW(c.am.MOD[HUD.WW])) {
			int dmg = c.eq.weapon.useInt() + c.am.MOD_DMG;
			enemy.hit(dmg);
		}else {
			handler.addSmallMessage(enemy, "Pud³o", new Color(0, 0, 255));
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
			comment.set("Nie ma przeciwnika w zasiêgu");
		}else {
			good = good && c.am.attackCounter < c.att.getA();

			if(!good) {
				comment.set("Nie mo¿esz ju¿ atakowaæ w tej rundzie");
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

		hud.light(HUD.WW);
		hud.light(HUD.A);
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
