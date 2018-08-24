package Fight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import Other.Handler;

public class ActionAttack extends Action {

	private Handler handler;
	
	public ActionAttack(float x, float y, Handler handler) {
		super(x, y);
		
		this.handler = handler;
		duration = 1;
	}

	public void update(float et) {
		updateAnimation(et);
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
}
