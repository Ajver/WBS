package Fight;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import MainFiles.MainClass;
import Other.Handler;

public class ActionManager {

	public static float buttonW = 64;
	
	private Handler handler;
	
	private LinkedList<Action> actions = new LinkedList<Action>();
	private Action[] selected = { null, null };
	private int currentAction = 0;
	
	float x = (MainClass.WW / 2.0f) - (buttonW / 2.0f);
	float y = MainClass.WH - buttonW*2.0f;
	
	float selX = buttonW;
	
	public ActionManager(Handler handler) {
		this.handler = handler;	
		
		actions.add(new ActionAttack(x, y, handler));
		actions.add(new ActionMove(x, y, handler));
		
		setButtonsPosition();
	}
	
	public void update(float et) {
		for(Action a : actions) {
			a.update(et);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(204, 189, 83));
		g.fillOval((int)(selX + buttonW*currentAction - 16), (int)y-16, (int)buttonW + 32, (int)buttonW + 32);
		
		g.setColor(new Color(112, 89, 51));
		g.fillRect((int)selX, (int)y, (int)buttonW*2, (int)buttonW);
		
		g.setColor(new Color(0,0,0));
		g.drawRect((int)selX, (int)y, (int)buttonW, (int)buttonW);
		g.drawRect((int)(selX + buttonW), (int)y, (int)buttonW, (int)buttonW);
		
		g.setFont(new Font("arial", 0, 35));
		
		FontMetrics f = g.getFontMetrics();
		int sx = (int)((MainClass.WW - f.stringWidth("Twoja runda")) / 2.0f);
		int sy = (int)(y - buttonW * 0.5f);
		g.drawString("Twoja runda", sx, sy);
		
		sx = (int)(selX + buttonW - f.stringWidth("Wybrane akcje") / 2.0f);
		g.drawString("Wybrane akcje", sx, sy);
		
		for(Action a : actions) {
			a.render(g);
		}
	}
	
	private void setButtonsPosition() {
		float margin = 16;
		float allButtonsW = actions.size() * buttonW + (actions.size()-1) * margin;
		x = (MainClass.WW - allButtonsW) / 2.0f;
		
		for(int i=0; i<actions.size(); i++) {
			actions.get(i).setX(x + (buttonW+16)*i);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(selected[currentAction] == null) {
			for(Action a : actions) {
				if(a.mouseOver(e.getX(), e.getY())) {
					a.use();
					selected[currentAction] = a;
				}
			}		
		}else {
			boolean good = true;
			for(Action a : actions) {
				if(a.mouseOver(e.getX(), e.getY())) {
					good = false;
				}
				if(mouseOver(e.getX(), e.getY(), selX, y, buttonW*2, buttonW)) {
					good = false;
				}
			}
			if(good) {
				selected[currentAction].mouseReleased(e);
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(selected[currentAction] != null) {
			boolean good = true;
			for(Action a : actions) {
				if(a.mouseOver(e.getX(), e.getY())) {
					good = false;
				}
				if(mouseOver(e.getX(), e.getY(), selX, y, buttonW*2, buttonW)) {
					good = false;
				}
			}
			if(good) {
				selected[currentAction].mouseMoved(e);
			}
		}
	}
	
	private boolean mouseOver(int mx, int my, float x, float y, float w, float h) {
		return mx >= x && mx <= x+w && my >= y && my <= y+h;
	}
}
