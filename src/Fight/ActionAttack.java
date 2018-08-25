package Fight;

import java.awt.Color;
import java.awt.event.MouseEvent;

import Character.Dice;
import Creatures.Creature;
import MainFiles.MainClass;
import Other.Handler;

public class ActionAttack extends Action {
	
	public ActionAttack(float x, float y, Creature c, Handler handler) {
		super(x, y, c, handler);
		this.img = MainClass.tex.swordIcon;
	}

	public void slUpdate(float et) {

	}

	public void select() {
		boolean isInRange = false;
		
		for(int yy=-1; yy<=1; yy++) {
			for(int xx=-1; xx<=1; xx++) {
				if(xx != 0 || yy != 0) {
					if(handler.getFromMap(c.getMX()+xx, c.getMY()+yy) != null) {
						isInRange = true;
						handler.map.grid[c.getMX()+xx][c.getMY()+yy].setClickable(new Color(255, 0, 0));
					}
				}
			}
		}
		
		if(!isInRange) {
			handler.msg.set("No enemys in Range");
		}
	}
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x && mx <= x+ActionManager.buttonW * duration && my >= y && my <= y+ActionManager.buttonW;
	}

	public void use(int mapX, int mapY) {
		Creature enemy = handler.getFromMap(mapX, mapY);
		
		if(enemy != null) {
			if(c.att.WW(0)) {
				int dmg = Dice.roll1d10();
				handler.msg.set("Damage: " + dmg);
				enemy.hit(dmg);
			}else {
				handler.msg.set("Miss");
			}
			startTimer(0.1f);
		}else {
			handler.msg.set("No enemy on xy: " + mapX + " | " + mapY);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		int mapX = (int) ((e.getX() + handler.camera.getX()) / Handler.cellW);
		int mapY = (int) ((e.getY() + handler.camera.getY()) / Handler.cellH);
		
		if(mapX >= c.getMX()-1 && mapX <= c.getMX()+1 && mapY >= c.getMY()-1 && mapY <= c.getMY()+1) {
			use(mapX, mapY);
		}else {
			handler.msg.set("out of range!");
		}
	}
	
	public void slMouseMoved(MouseEvent e) {
		
	}

	
	public void canel() {
		
	}
}
