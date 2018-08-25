package Fight;

import java.awt.event.MouseEvent;

import MainFiles.MainClass;
import Other.Handler;

public class ActionAttack extends Action {

	private Handler handler;
	
	public ActionAttack(float x, float y, Handler handler) {
		super(x, y);
		
		this.handler = handler;
		this.img = MainClass.tex.swordIcon;
	}

	public void slUpdate(float et) {

	}

	public void use() {
		if(isEnemyInRange()) {
			
		}
	}
	
	private boolean isEnemyInRange() {
		for(int yy=-1; yy<1; yy++) {
			for(int xx=-1; xx<1; xx++) {
				if(handler.getFromMap(handler.player.getMX()+xx, handler.player.getMY()+yy) != null) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x && mx <= x+ActionManager.buttonW * duration && my >= y && my <= y+ActionManager.buttonW;
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		
	}

	public void canel() {
		
	}
}
