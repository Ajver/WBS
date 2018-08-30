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
		this.img[0] = MainClass.tex.swordIcon;
		this.img[1] = MainClass.tex.swordIcon;
	}

	public void slUpdate(float et) {

	}

	public void select() {
		int cx = c.getMX();
		int cy = c.getMY();

		boolean isInRange = false;

		slMouseLeved();

		for(int yy=-1; yy<=1; yy++) {
			for(int xx=-1; xx<=1; xx++) {
				if(xx != 0 || yy != 0) {
					if(handler.getFromMap(c.getMX()+xx, c.getMY()+yy) != null) {
						isInRange = true;
						handler.map.setClickable(cx+xx, cy+yy, 1);
					}
				}
			}
		}
		
		if(!isInRange) {
			handler.msg.set("No enemies in Range");
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
		used = true;

		startTimer((long)(c.attackDuration * 1000.0f));
		c.attack();
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
						handler.map.grid[cx + xx][cy + yy].setColor(new Color(163, 32, 0));
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
