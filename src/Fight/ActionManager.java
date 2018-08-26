package Fight;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Creatures.Creature;
import MainFiles.MainClass;
import Other.Button;
import Other.Handler;

public class ActionManager {

	public static float buttonW = 64;
	
	private ActionList al;
	private ActionGroup[] ag = new ActionGroup[3];
	
	private Button canelBtn;
	
	private BufferedImage actionBg; 
	
	float x = (MainClass.WW / 2.0f) - (buttonW / 2.0f);
	float y = MainClass.WH - buttonW*2.0f;
	
	float selX = buttonW;
	
	public ActionManager(Handler handler) {
		this.al = handler.creatures.get(0).al;
		this.actionBg = MainClass.tex.actionBg;
		
		canelBtn = new Button(buttonW-16, y+buttonW+20, buttonW*2.0f+32, 32, "Anuluj akcjê");
		
		Creature p = handler.creatures.get(0);
		x = (MainClass.WW - 288) / 2.0f;
		this.ag[0] = new ActionGroup(x, y+64, new ActionAttack(p, handler));
		this.ag[1] = new ActionGroup(x+96, y+64, new ActionMove(p, handler), new ActionRun(p, handler));
		this.ag[2] = new ActionGroup(x+192, y+64, new ActionAttack(p, handler));
	}
	
	public void update(float et) {
		for(ActionGroup ag : ag) {
			ag.update(et);
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
		//g.drawString("Twoja runda", sx, sy);
		
		sx = (int)(selX + buttonW - f.stringWidth("Wybrane akcje") / 2.0f);
		g.drawString("Wybrane akcje", sx, (int)(y - buttonW * 0.5f));
		
		int index = -1;
		for(int i=0; i<3; i++) {
			if(ag[i].isOpen()) {
				index = i;
				i = 3;
			}
		}
		
		if(index != -1) {
			for(int i=0; i<3; i++) {
				if(i != index) {
					ag[i].render(g);
				}
			}
			ag[index].render(g);
		}else {
			for(ActionGroup ag : ag) {
				ag.render(g);
			}
		}
		
		if(al.selected[al.currentAction] != null) {
			if(al.selected[al.currentAction].mayBeCaneled()) {
				canelBtn.render(g);
			}
		}
	}
	
	public void nextAction() {
		al.nextAction();
		showActions();
	}
	
	public void mouseReleased(MouseEvent e) {
		if(al.selected[al.currentAction] == null) {
			for(ActionGroup ag : ag) {
				for(Action a : ag.actions) {
					if(a.mouseOver(e.getX(), e.getY())) {
						if(a.getDuration() == 1 || (a.getDuration() == 2 && al.currentAction == 0)) {
							hideActions();
							
							// Selecting
							a.select();
							al.selected[al.currentAction] = a;
						}
					}
				}	
			}
		}else {
			for(ActionGroup ag : ag) {
				for(Action a : ag.actions) {
					if(a.mouseOver(e.getX(), e.getY())) {
						return;
					}
				}
			}
			
			if(mouseOver(e.getX(), e.getY(), selX, y, buttonW*2, buttonW)) {
				return;
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
		int index = -1;
		for(int i=0; i<3; i++) {
			if(ag[i].isOpen()) {
				index = i;
				i = 3;
			}
		}
		if(index != -1) {
			ag[index].mouseMoved(e);
		}else {
			for(ActionGroup ag : ag) {
				ag.mouseMoved(e);
			}
		}
		
		if(al.selected[al.currentAction] != null) {
			boolean good = true;
						
			if(good) {
				al.selected[al.currentAction].mouseMoved(e);
			}
			if(al.selected[al.currentAction].mayBeCaneled()) {
				canelBtn.hover(e.getX(), e.getY());
			}
		}
	}
	
	private void showActions() {
		
	}
	
	private void hideActions() {
		
	}
	
	private boolean mouseOver(int mx, int my, float x, float y, float w, float h) {
		return mx >= x && mx <= x+w && my >= y && my <= y+h;
	}
}
