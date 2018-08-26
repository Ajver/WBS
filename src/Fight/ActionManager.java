package Fight;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import MainFiles.MainClass;
import Other.Button;
import Other.Handler;

public class ActionManager {

	public static float buttonW = 64;
	
	private ActionList al;
	
	private Button canelBtn;
	
	private BufferedImage actionBg; 
	
	float x = (MainClass.WW / 2.0f) - (buttonW / 2.0f);
	float y = MainClass.WH - buttonW*2.0f;
	
	float selX = buttonW;
	
	public ActionManager(Handler handler) {
		this.al = handler.creatures.get(0).al;
		this.actionBg = MainClass.tex.actionBg;
		setButtonsPosition();
	}
	
	public void update(float et) {
		for(Action a : al.actions) {
			a.update(et);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(204, 189, 83));
		g.fillOval((int)(selX + buttonW*al.currentAction - 16), (int)y-16, (int)buttonW + 32, (int)buttonW + 32);
	
		g.setColor(new Color(0,0,0));
		g.drawImage(actionBg, (int)selX, (int)y, null);
		g.drawImage(actionBg, (int)(selX + buttonW), (int)y, null);
		
		if(al.selected[0] != null) {
			g.drawImage(al.selected[0].img, (int)selX, (int)y, null);
			
			if(al.selected[1] != null) {
				g.drawImage(al.selected[1].img, (int)(selX+buttonW), (int)y, null);
			}
		}
		
		FontMetrics f = g.getFontMetrics();
		int sx = (int)((MainClass.WW - f.stringWidth("Twoja runda")) / 2.0f);
		int sy = (int)(al.actions.get(0).getY() - buttonW * 0.5f);
		g.drawString("Twoja runda", sx, sy);
		
		sx = (int)(selX + buttonW - f.stringWidth("Wybrane akcje") / 2.0f);
		g.drawString("Wybrane akcje", sx, (int)(y - buttonW * 0.5f));
		
		for(Action a : al.actions) {
			a.render(g);
		}
		
		if(al.selected[al.currentAction] != null) {
			if(al.selected[al.currentAction].mayBeCaneled()) {
				canelBtn.render(g);
			}
		}
	}
	
	private void setButtonsPosition() {
		float margin = 16;
		float allButtonsW = al.actions.size() * buttonW + (al.actions.size()-1) * margin;
		x = (MainClass.WW - allButtonsW) / 2.0f;
		
		for(int i=0; i<al.actions.size(); i++) {
			al.actions.get(i).setX(x + (buttonW+16)*i);
			al.actions.get(i).setY(y);
		}
		
		canelBtn = new Button(buttonW-16, y+buttonW+20, buttonW*2.0f+32, 32, "Anuluj akcjê");
	}
	
	public void nextAction() {
		al.nextAction();
		showActions();
	}
	
	public void mouseReleased(MouseEvent e) {
		if(al.selected[al.currentAction] == null) {
			for(Action a : al.actions) {
				if(a.mouseOver(e.getX(), e.getY())) {
					hideActions();
					
					// Selecting
					a.select();
					al.selected[al.currentAction] = a;
				}
			}		
		}else {
			for(Action a : al.actions) {
				if(a.mouseOver(e.getX(), e.getY())) {
					return;
				}
				if(mouseOver(e.getX(), e.getY(), selX, y, buttonW*2, buttonW)) {
					return;
				}	
			}
			
			if(al.selected[al.currentAction].mayBeCaneled()) {
				if(canelBtn.mouseOver(e.getX(), e.getY())) {
					al.selected[al.currentAction].canel();
					al.selected[al.currentAction] = null;
					showActions();
					return;
				}
			}
			
			al.selected[al.currentAction].mouseReleased(e);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		for(Action a : al.actions) {
			a.hover(e.getX(), e.getY());
		}
		
		if(al.selected[al.currentAction] != null) {
			boolean good = true;
			for(Action a : al.actions) {
				if(a.mouseOver(e.getX(), e.getY())) {
					good = false;
				}
				if(mouseOver(e.getX(), e.getY(), selX, y, buttonW*2, buttonW)) {
					good = false;
				}
			}
			if(good) {
				al.selected[al.currentAction].mouseMoved(e);
			}
		}
		
		if(al.selected[al.currentAction] != null) {
			if(al.selected[al.currentAction].mayBeCaneled()) {
				canelBtn.hover(e.getX(), e.getY());
			}
		}
	}
	
	private void showActions() {
		for(Action a : al.actions) {
			a.setNextX(a.x);
			a.setNextY(y);
		}
	}
	
	private void hideActions() {
		for(Action a : al.actions) {
			a.setNextX(a.x);
			a.setNextY(MainClass.WH);
		}
	}
	
	private boolean mouseOver(int mx, int my, float x, float y, float w, float h) {
		return mx >= x && mx <= x+w && my >= y && my <= y+h;
	}
}
