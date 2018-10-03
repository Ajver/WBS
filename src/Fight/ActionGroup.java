package Fight;

import Other.Gamecol;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class ActionGroup {
	
	// Position of left-bottom corner 
	private float x, y;

	public LinkedList<Action> actions = new LinkedList<Action>();
	
	private boolean isOpen = false;
	private boolean isVisible = true;

	// Animation (opening, closing)
	private float animationSpeed = 6.0f;
	private float progress = 0.0f;
	private float vel = 0.0f;
	
	public ActionGroup(float x, float y, Action... act) {
		this.x = x;
		this.y = y;
		
		for(int i=0; i<act.length; i++) {
			if(act[i].getDuration() == 1) { 
				act[i].setX(x+32);
			}else { 
				act[i].setX(x); 
			}
			act[i].setY(y - 64 - i*80);
			actions.add(act[i]);
		}
	}
	
	public void update(float et) {
		for(Action a : actions) {
			a.update(et);
		}
		
		progress += vel * et;
		
		if(vel > 0) {
			if(progress >= 1.0f) {
				progress = 1.0f;
				vel = 0.0f;
			}
		}else if(vel < 0) {
			if(progress <= 0.0f) {
				progress = 0.0f;
				vel = 0.0f;
				isOpen = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			float h = 80 + progress * (actions.size()-1) * 80;
//			g.setColor(new Color(30, 30, 30, 150));
			g.setColor(Gamecol.LIGHT);
			g.fillRect((int)(x-16+((1-progress)*64)), (int)(y-h), (int)(160-((1-progress)*128)), (int)h+16);
			
			for(int i=0; i<actions.size(); i++) {
				g.translate(0, (int)((1.0f-progress)*80*i)); 
				actions.get(i).render(g);
				g.translate(0, (int)-((1.0f-progress)*80*i)); 
			}
		}else {
			actions.get(0).render(g);
		}
	}

	public Action mouseReleased(MouseEvent e) {
		if(isOpen && isVisible) {
			for (Action a : actions) {
				if (a.mouseOver(e.getX(), e.getY())) {
					a.hover = false;
					return a;
				}
			}
		}
		return null;
	}
	
	public void mouseMoved(MouseEvent e) {
		if(isVisible) {
			if(mouseOver(e.getX(), e.getY())) {
				open();
			}else {
				close();
			}
			if(isOpen) {
				for (Action a : actions) {
					a.mouseMoved(e);
				}
			}
		}
	}
	
	private boolean mouseOver(int mx, int my) {
	    if(!isVisible) return false;

		float h;
		
		if(isOpen) {
			h = actions.size() * 80;
			return mx >= x-16 && mx <= x+144 && my >= y-h && my <= y+16;
		}else {
			h = 64;
			return mx >= x+32 && mx <= x+96 && my >= y-h && my <= y;
		}	
	}
	
	private void open() {
		if(isOpen) return;

		for(Action a : actions) {
			a.setVisible(true);
		}

		isOpen = true;
		vel = animationSpeed;
	}

	public void close() {
		if(!isOpen) return;

		for(Action a : actions) {
			a.setVisible(false);
		}

		vel = -animationSpeed;
	}
	
	public boolean isOpen() { return this.isOpen && this.isVisible; }
}	
